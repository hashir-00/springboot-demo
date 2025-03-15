package com.example.demo.service.model;

import com.example.demo.data.entity.SchoolSection;
import com.example.demo.data.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Optional;

@Setter
@Getter
@Accessors(chain = true)
public class CreateStudent {



    private String firstName;


    private String lastName;


    private Date dob;


    private String gender;


    private Long sectionId;


    private Long teacherId;

}
