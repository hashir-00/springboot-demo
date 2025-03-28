package com.example.demo.web.controller;

import com.example.demo.data.entity.Users;
import com.example.demo.service.UserService;
import com.example.demo.service.model.UserOutput;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Users> authenticatedUser() {
        logger.info("Authenticated user");
        return ResponseEntity.ok(this.userService.getCurrentUser());
    }

    @GetMapping()
    public ResponseEntity<List<UserOutput>> allUsers() {
        logger.info("Authenticated users");
        List <Users> users = userService.allUsers();

        List<UserOutput> usersOut = users.stream().map(UserOutput::userOutput).collect(Collectors.toList());

        return ResponseEntity.ok(usersOut);
    }
}