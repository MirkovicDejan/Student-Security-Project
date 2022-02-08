package com.securityproject.securityproject.controller;

import com.securityproject.securityproject.models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class StudentController {

    private static final List<Student> STUDENT = Arrays.asList
            (new Student(1, "Marko Milosevic"),
                    new Student(2, "James Bond"),
                    new Student(3, "Ana Ivanovic"));

  /*  @RequestMapping("/index")
    public String getIndex() {
        return "index";
    }*/

    @GetMapping("/get-student")
    public Student getStudent(@RequestParam Integer studentId) {
        for (Student s : STUDENT) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }
        return null;
    }

}
