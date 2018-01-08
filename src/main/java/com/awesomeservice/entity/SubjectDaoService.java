package com.awesomeservice.entity;

import com.awesomeservice.exception.InvalidSubject;
import com.awesomeservice.exception.SubjectNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class SubjectDaoService {

    private final SubjectRepository repository;

    Subject findOne(String name, int version) {
        return repository.findByNameAndVersion(name, version)
                .orElseThrow(
                        () -> new SubjectNotFound(
                                String.format("Subject with name = '%s' not found", name)
                        )
                );
    }

    Subject findOne(String name, String surname, int version) {
        return repository.findByVersionAndCredentials_NameAndCredentials_Surname(version, name, surname)
                .orElseThrow(
                        () -> new SubjectNotFound(
                                String.format("Subject with name = '%s' and surname = '%s' not found", name, surname)
                        )
                );
    }

    Subject findOne(String uuid) {
        return repository.getOne(uuid);
    }

    Subject save(Subject subject) {
        subject.setUuid(randomUUID().toString());
        return repository.save(subject);
    }

    void deleteByUuid(String uuid) {
        validate(uuid);
        repository.deleteById(uuid);
    }

    private void validate(String uuid) {
        if (null == uuid || uuid.isEmpty()) {
            throw new InvalidSubject("Inappropriate uuid format");
        }
    }

    List<Subject> findAll() {
        return repository.findAll();
    }
}
