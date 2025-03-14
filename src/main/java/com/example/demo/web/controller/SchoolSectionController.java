package com.example.demo.web.controller;


import com.example.demo.service.SchoolSectionService;
import com.example.demo.service.model.SchoolSectionModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolSection")
public class SchoolSectionController {

    private static final Logger logger = LoggerFactory.getLogger(SchoolSectionController.class.getName());

    private final SchoolSectionService schoolSectionService;

    public SchoolSectionController(SchoolSectionService schoolSectionService) {
        this.schoolSectionService = schoolSectionService;
    }

    @GetMapping
    public List<SchoolSectionModal> getSchoolSections() {
        logger.info("Retrieving all school sections");
      return this.schoolSectionService.getAllSections();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void addSection(@RequestBody SchoolSectionModal schoolSectionModal) {
        logger.info("Adding school section: " + schoolSectionModal);
        this.schoolSectionService.save(schoolSectionModal);
    }
}
