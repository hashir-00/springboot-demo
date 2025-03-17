package com.example.demo.service.model;

import com.example.demo.data.entity.RefreshToken;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class LoginResponse {
  private String token;

  private long expiresIn;

  private UUID refreshToken;
}
