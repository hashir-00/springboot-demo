package com.example.demo.service.model;

import com.example.demo.data.entity.SchoolSection;
import com.example.demo.data.entity.Student;
import com.example.demo.data.entity.Subject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Teacher {
    private Long  teacherId ;
    private String firstName ;
    private int subjectId ;
    private String email ;
    private SchoolSection section;
    private List<Subject> subjects;
    private List<Student> students;
}
