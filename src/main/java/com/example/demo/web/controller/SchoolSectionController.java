package com.example.demo.web.controller;


import com.example.demo.service.SchoolSectionService;
import com.example.demo.service.model.SchoolSectionModal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolSection")
public class SchoolSectionController {
    private final SchoolSectionService schoolSectionService;

    public SchoolSectionController(SchoolSectionService schoolSectionService) {
        this.schoolSectionService = schoolSectionService;
    }

    @GetMapping
    public List<SchoolSectionModal> getSchoolSections() {
      return this.schoolSectionService.getAllSections();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void addSection(@RequestBody SchoolSectionModal schoolSectionModal) {
        this.schoolSectionService.save(schoolSectionModal);
    }
}
