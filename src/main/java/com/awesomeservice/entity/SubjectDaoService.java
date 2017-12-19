package com.awesomeservice.entity;

import com.awesomeservice.exception.InvalidSubject;
import com.awesomeservice.exception.SubjectNotFound;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.UUID.randomUUID;

@Component
public class SubjectDaoService {

    private List<Subject> subjects = new CopyOnWriteArrayList<>();

    public SubjectDaoService() {
        subjects.add(new Subject(randomUUID(), "John Doe", new Date()));
        subjects.add(new Subject(randomUUID(), "Peter Pan", new Date()));
        subjects.add(new Subject(randomUUID(), "Martin King", new Date()));
    }

    public Subject findOne(UUID uuid) {
        return subjects.stream()
                       .filter(subject -> uuid.equals(subject.getUuid()))
                       .findAny()
                       .orElseThrow(
                               () -> new SubjectNotFound(String.format("Subject with uuid = '%s' not found", uuid))
                       );
    }

    public Subject save(Subject subject) {
        subject.setUuid(randomUUID());
        subjects.add(subject);
        return subject;
    }

    public Subject deleteByUuid(UUID uuid) {
        validate(uuid);
        Subject toDelete =  subjects.stream()
                       .filter(subject -> uuid.equals(subject.getUuid()))
                       .findAny()
                       .orElseThrow(
                               () -> new SubjectNotFound(String.format("Subject with uuid = '%s' not found", uuid))
                       );
        subjects.remove(toDelete);
        return toDelete;
    }

    private void validate(UUID uuid) {
        if (null == uuid) {
            throw new InvalidSubject("Inappropriate uuid format");
        }
    }

    public List<Subject> findAll() {
        return subjects;
    }
}
