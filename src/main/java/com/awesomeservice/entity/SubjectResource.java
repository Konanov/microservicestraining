package com.awesomeservice.entity;

import com.awesomeservice.exception.InvalidSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class SubjectResource {

    private final SubjectDaoService subjectDao;

    @GetMapping("/subjects")
    public List<Subject> retrieveAllSubjects() {
        return subjectDao.findAll();
    }

    @GetMapping("/subjects/{uuid}")
    public Resource<Subject> retrieveSubject(@PathVariable UUID uuid) {
        Resource<Subject> subject = new Resource<>(subjectDao.findOne(uuid));
        subject.add(linkTo(
                methodOn(this.getClass())
                        .retrieveAllSubjects())
                .withRel("all-users")
        );
        return subject;
    }

    /**
     * Example of versioning via URL.
     */
    @GetMapping(path = "/subjects/v1/credentials")
    public Resource<Subject> retrieveByCredentials(@RequestParam String name,
                                                   @RequestParam String surname) {
        return subjectResourceVersionOne(name, surname);
    }

    /**
     * Example of versioning via URL.
     */
    @GetMapping(path = "/subjects/v2/credentials")
    public Resource<Subject> retrieveByNameAndSurname(@RequestParam String name,
                                                      @RequestParam String surname) {
        return subjectResourceVersionTwo(name, surname);
    }

    /**
     * Example of versioning via RequestHeader.
     */
    @GetMapping(path = "/subjects/credentials")
    public Resource<Subject> headerWithCredentials(@RequestParam String name,
                                                   @RequestParam String surname,
                                                   @RequestHeader(value = "X-API-VERSION") int version) {
        Resource<Subject> subject = new Resource<>(subjectDao.findOne(name, surname, version));
        subject.add(linkTo(
                methodOn(this.getClass())
                        .retrieveAllSubjects())
                .withRel("all-users")
        );
        return subject;
    }

    /**
     * Example of versioning via Accept header.
     */
    @GetMapping(path = "/subjects/versioning", produces = {"application/api-subject-v1+json"})
    public Resource<Subject> producesVersionOneWithCredentials(@RequestParam String name,
                                                     @RequestParam String surname) {
        return subjectResourceVersionOne(name, surname);
    }

    /**
     * Example of versioning via Accept header.
     */
    @GetMapping(path = "/subjects/versioning", produces = {"application/api-subject-v2+json"})
    public Resource<Subject> producesVersionTwoWithCredentials(@RequestParam String name,
                                                     @RequestParam String surname) {
        return subjectResourceVersionTwo(name, surname);
    }

    private Resource<Subject> subjectResourceVersionOne(@RequestParam String name,
                                                        @RequestParam String surname) {
        Resource<Subject> subject = new Resource<>(subjectDao.findOne(name, surname, 1));
        subject.add(linkTo(
                methodOn(this.getClass())
                        .retrieveAllSubjects())
                .withRel("all-users")
        );
        return subject;
    }

    private Resource<Subject> subjectResourceVersionTwo(@RequestParam String name,
                                                        @RequestParam String surname) {
        Resource<Subject> subject = new Resource<>(subjectDao.findOne(name, surname, 2));
        subject.add(linkTo(
                methodOn(this.getClass())
                        .retrieveAllSubjects())
                .withRel("all-users")
        );
        return subject;
    }

    @PostMapping("/subjects")
    public ResponseEntity<Object> saveSubject(@Valid @RequestBody Subject subject) {
        validateFields(subject);
        Subject savedSubject = subjectDao.save(subject);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(savedSubject.getUuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/subjects/{uuid}")
    public void deleteSubject(@PathVariable UUID uuid) {
        subjectDao.deleteByUuid(uuid);
    }

    private void validateFields(Subject subject) {
        if (null == subject.getName() || null == subject.getBirthDay()) {
            throw new InvalidSubject("Subject's name or birthday has inappropriate format");
        }
    }
}
