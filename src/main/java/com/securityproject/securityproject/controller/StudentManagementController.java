package com.securityproject.securityproject.controller;

import com.securityproject.securityproject.models.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class  StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList
            (new Student(1, "Marko Milosevic"),
                    new Student(2, "James Bond"),
                    new Student(3, "Ana Ivanovic"));

    @GetMapping("/get-all-students")
    public List<Student>getAllStudent(){
        return STUDENTS;
    }

    @PostMapping("/register-new-student")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student.toString());
    }

    @DeleteMapping("/delete-student")
    public void deleteStudent(@RequestParam Integer studentId){
        System.out.println(studentId);
    }

    @PutMapping("/update-student")
    public void updateStudent(@RequestParam Integer studentId,@RequestBody Student student){
        System.out.println(String.format("%s %s",studentId,student.toString()));
    }
}
