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

  @ApiResponses({
    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
  })
  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  @ResponseBody
  public String getWelcome(@RequestParam(value = "name", required = false) String name) {
    String greeting = "Welcome ";
    if (StringUtils.isNotBlank(name)) {
      greeting = "Welcome " + name;
    } else {
      greeting = "Welcome " + "World";
    }
    String welcome = "<h1>" + greeting + "</h1>";
    return welcome;
  }
}
