package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//DAO Layer, responsible for talking to DB
public interface StudentRepository
        extends MongoRepository<Student, String> {

    Optional<Student> findStudentByEmail(String email);


}
