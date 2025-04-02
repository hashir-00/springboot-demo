package com.example.demo.web.controller;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {


  @GetMapping()
  public String getWelcome() {

 return "Hello World v1";}
    

}
