package com.awesomeservice.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, String> {

    Optional<Subject> findByNameAndVersion(String name, int version);

    Optional<Subject> findByVersionAndCredentials_NameAndCredentials_Surname(int version, String name, String surname);

    @Override
    Subject getOne(String uuid);
}
