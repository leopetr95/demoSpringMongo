package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Makes this class as a class to serve HTTP requests from client
@RequestMapping("api/v1/students")
@AllArgsConstructor //Instead of using an explicit constructor
public class StudentController {

    private final StudentService studentService;

    //Method annotated with HTTP sematics
    @GetMapping
    public List<Student> fetchAllStudents(){

        return studentService.getAllStudents();

    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){

        studentService.addNewStudent(student);

    }

    @DeleteMapping(path = "{studentID}")
    public void deleteStudent(@PathVariable("studentID") String studentId){

        studentService.deleteStudent(studentId);
    }

    @PutMapping(path="{studentID}")
    public void updateStudent(@PathVariable("studentID") String studentID,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String email){

        studentService.updateStudent(studentID, firstName, email);
    }

}
