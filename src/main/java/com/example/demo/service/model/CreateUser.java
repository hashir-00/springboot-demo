package com.example.demo.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CreateUser {
  private String email;

  private String password;

  private String fullName;
}
