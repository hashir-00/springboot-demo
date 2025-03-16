package com.example.demo.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Date;


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
