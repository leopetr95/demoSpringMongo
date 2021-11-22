package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Service Layer
@AllArgsConstructor
@Service  //Spring initializes this class as a Bean
public class StudentService {

    private final StudentRepository studentRepository; //

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {

        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){

            throw new IllegalStateException("Email already exists");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(String studentID) {

        boolean exists = studentRepository.existsById(studentID);
        if(!exists){

            throw new IllegalStateException("User does not exists");
        }

        studentRepository.deleteById(studentID);
    }

    @Transactional
    public void updateStudent(String studentID, String firstName, String email){

        Student student = studentRepository.findById(studentID).orElseThrow(()-> new IllegalStateException("Student with ID " + studentID + "does not exists"));

        if(firstName != null && firstName.length() > 0 && !student.getFirstName().equals(firstName)){

            student.setFirstName(firstName);
        }

        if(email != null && email.length() > 0 && !student.getEmail().equals(email)){

            //Checking if another user has the new email
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){

                throw new IllegalStateException("Email Already Used");
            }

            student.setEmail(email);

        }

        studentRepository.save(student);

    }

}
