package com.example.demo.service.model;

import com.example.demo.data.entity.SchoolSection;
import com.example.demo.data.entity.Student;
import com.example.demo.data.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class StudentOutput {

    private Long studentId;


    private String firstName;


    private String lastName;


    private Date dob;


    private String gender;


    private Long sectionId;


    private Long teacherId;

    public static StudentOutput studentOutputModal(Student student) {
        StudentOutput studentOutput = new StudentOutput();
        studentOutput.setFirstName(student.getFirstName());
        studentOutput.setLastName(student.getLastName());
        studentOutput.setDob(student.getDob());
        studentOutput.setGender(student.getGender());
        studentOutput.setStudentId(student.getStudentId());
        studentOutput.setTeacherId(student.getTeacher().getTeacherId());
        studentOutput.setSectionId(student.getSection().getSectionId());
        return studentOutput;
    }

}
