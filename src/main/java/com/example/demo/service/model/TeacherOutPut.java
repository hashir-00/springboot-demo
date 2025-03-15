package com.example.demo.service.model;


import com.example.demo.data.entity.Subject;

import com.example.demo.data.entity.Teacher;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeacherOutPut {

    private Long  teacherId ;


    private String firstName ;


    private String email ;


    private Long sectionId;


    private List<Subject> subjects;


    private List<StudentOutput> students;

    public static TeacherOutPut teacherOutPutModal(Teacher teacher) {
        TeacherOutPut teacherOutput = new TeacherOutPut();
       teacherOutput.setFirstName(teacher.getFirstName());
       teacherOutput.setEmail(teacher.getEmail());
       teacherOutput.setSectionId(teacher.getSection().getSectionId());
       teacherOutput.setTeacherId(teacher.getTeacherId());
       teacherOutput.setStudents(teacher.getStudents().stream().map(StudentOutput::studentOutputModal).collect(Collectors.toList()));
       return teacherOutput;

    }
}
