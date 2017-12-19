package com.awesomeservice.entity;

import com.awesomeservice.exception.InvalidSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final SubjectDaoService subjectDao;

    @GetMapping("/subjects")
    public List<Subject> retrieveAllSubjects() {
        return subjectDao.findAll();
    }

    @GetMapping("/subjects/{uuid}")
    public Resource<Subject> retrieveSubject(@PathVariable UUID uuid) {
        Resource<Subject> subject =  new Resource<>(subjectDao.findOne(uuid));
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
