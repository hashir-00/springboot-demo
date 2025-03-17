package com.example.demo.service.model;

import com.example.demo.data.entity.SchoolSection;
import java.util.List;
import lombok.Data;

@Data
public class SchoolSectionOutput {


    private Long sectionId;


    private String name;


    private int classNumber;


    private String classType;


    private List<StudentOutput> students;

    private TeacherOutPut teacher;


    public static SchoolSectionOutput schoolSectionOutput(SchoolSection schoolSection) {
        SchoolSectionOutput schoolSectionOutput = new SchoolSectionOutput();
        schoolSectionOutput.setSectionId(schoolSection.getSectionId());
        schoolSectionOutput.setName(schoolSection.getName());
        schoolSectionOutput.setClassNumber(schoolSection.getClassNumber());
        schoolSectionOutput.setClassType(schoolSection.getClassType());
        schoolSectionOutput.setStudents(schoolSection.getStudents().stream().map(StudentOutput::studentOutputModal).toList());
        return schoolSectionOutput;
    }
}
