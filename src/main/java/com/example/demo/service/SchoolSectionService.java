package com.example.demo.service;

import com.example.demo.data.entity.SchoolSection;
import com.example.demo.data.repository.SchoolSectionRepository;
import com.example.demo.data.repository.StudentRepository;
import com.example.demo.data.repository.TeacherRepository;
import com.example.demo.service.model.SchoolSectionModal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolSectionService {
    private final SchoolSectionRepository schoolSectionRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public SchoolSectionService(SchoolSectionRepository schoolSectionRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.schoolSectionRepository = schoolSectionRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public void save(SchoolSectionModal schoolSectionModal) {
        SchoolSection schoolSection = new SchoolSection();
        schoolSection.setName(schoolSectionModal.getName());
        schoolSection.setClassNumber(schoolSectionModal.getClassNumber());
        schoolSection.setClassType(schoolSectionModal.getClassType());
        schoolSectionRepository.save(schoolSection);
    }

    public SchoolSectionModal getSectionByTeacherId(long id) {
        SchoolSection schoolSection = schoolSectionRepository.findSchoolSectionByTeacher_TeacherId(id);
        if (schoolSection == null) {
            return null;
        }

        SchoolSectionModal modal = new SchoolSectionModal();
        modal.setName(schoolSection.getName());
        modal.setClassNumber(schoolSection.getClassNumber());
        modal.setClassType(schoolSection.getClassType());

        return modal;
    }

    public List<SchoolSectionModal> getAllSections() {
//        List<SchoolSection> modals = this.schoolSectionRepository.findAll();
//        List<SchoolSectionModal> modalModals = new ArrayList<>();
//        for (SchoolSection schoolSection : modals) {
//            SchoolSectionModal modal = new SchoolSectionModal();
//            modal.setSectionId(schoolSection.getSectionId());
//            modal.setName(schoolSection.getName());
//            modal.setClassNumber(schoolSection.getClassNumber());
//            modal.setClassType(schoolSection.getClassType());
//            modalModals.add(modal);
//        }
//        return modalModals;
        return schoolSectionRepository.findAll().stream()
                .map(schoolSection -> {
                    SchoolSectionModal modal = new SchoolSectionModal();
                    modal.setName(schoolSection.getName());
                    modal.setClassNumber(schoolSection.getClassNumber());
                    modal.setClassType(schoolSection.getClassType());
                    return modal;
                })
                .toList();
    };

}
