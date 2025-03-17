package com.example.demo.service.model;

import com.example.demo.data.entity.Users;
import lombok.Data;


import java.util.Date;

@Data
public class UserOutput {

    private Integer id;


    private String fullName;


    private String email;



    private Date createdAt;


    private Date updatedAt;

    public static  UserOutput userOutput(Users user) {
        UserOutput userOutput = new UserOutput();
        userOutput.setId(user.getId());
        userOutput.setFullName(user.getFullName());
        userOutput.setEmail(user.getEmail());
        userOutput.setCreatedAt(user.getCreatedAt());
        userOutput.setUpdatedAt(user.getUpdatedAt());
        return userOutput;

    }
}
