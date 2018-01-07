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
        subjects.add(new Subject(randomUUID(), "John Doe", new Date(), null, 1));
        subjects.add(new Subject(randomUUID(), "Peter Pan", new Date(), null, 1));
        subjects.add(new Subject(randomUUID(), "Martin King", new Date(), null, 1));
        subjects.add(new Subject(randomUUID(), null, new Date(), new Subject.Credentials("John", "Doe"), 2));
        subjects.add(new Subject(randomUUID(), null, new Date(), new Subject.Credentials("Peter", "Pan"), 2));
        subjects.add(new Subject(randomUUID(), null, new Date(), new Subject.Credentials("Martin", "King"), 2));
    }

    public Subject findOne(String name, String surname, int version) {
        Subject foundSubject;
        if (version == 1) {
            foundSubject = subjects.stream()
                    .filter(subject -> String.format("%s %s", name, surname).equals(subject.getName()))
                    .findAny()
                    .orElseThrow(
                            () -> new SubjectNotFound(
                                    String.format("Subject with name = '%s' and surname = '%s' not found", name, surname)
                            )
                    );
        } else {
            foundSubject = subjects.stream()
                    .filter(subject -> new Subject.Credentials(name, surname).equals(subject.getCredentials()))
                    .findAny()
                    .orElseThrow(
                            () -> new SubjectNotFound(
                                    String.format("Subject with name = '%s' and surname = '%s' not found", name, surname)
                            )
                    );
        }
        return foundSubject;
    }

    public Subject findOne(UUID uuid) {
        return subjects.stream()
                .filter(subject -> uuid.equals(subject.getUuid()))
                .findAny()
                .orElseThrow(
                        () -> new SubjectNotFound(
                                String.format("Subject with uuid = '%s' not found", uuid)
                        )
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
