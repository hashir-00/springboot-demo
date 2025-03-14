package com.example.demo.service;

import com.example.demo.data.entity.SchoolSection;
import com.example.demo.data.repository.SchoolSectionRepository;
import com.example.demo.data.repository.StudentRepository;
import com.example.demo.data.repository.TeacherRepository;
import com.example.demo.service.model.SchoolSectionModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolSectionService {
    private static final Logger logger = LoggerFactory.getLogger(SchoolSectionService.class);

    private final SchoolSectionRepository schoolSectionRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public SchoolSectionService(SchoolSectionRepository schoolSectionRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.schoolSectionRepository = schoolSectionRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public void save(SchoolSectionModal schoolSectionModal) {
       try{
           logger.debug("Saving school section: {}", schoolSectionModal);

           SchoolSection schoolSection = new SchoolSection();
           schoolSection.setName(schoolSectionModal.getName());
           schoolSection.setClassNumber(schoolSectionModal.getClassNumber());
           schoolSection.setClassType(schoolSectionModal.getClassType());
           schoolSectionRepository.save(schoolSection);

           logger.debug("Saved school section: {}", schoolSectionModal);
       }catch (Exception e){
           throw new RuntimeException("Failed to save school section: " + e.getMessage());
       }
    }

    public SchoolSectionModal getSectionByTeacherId(long id) {
       try{
           logger.debug("Attempting to get school section by Teacher Id: {}", id);
           SchoolSection schoolSection = schoolSectionRepository.findSchoolSectionByTeacher_TeacherId(id);
           if (schoolSection == null) {
               return null;
           }

           SchoolSectionModal modal = new SchoolSectionModal();
           modal.setName(schoolSection.getName());
           modal.setClassNumber(schoolSection.getClassNumber());
           modal.setClassType(schoolSection.getClassType());
           logger.debug("Successfully recieved school section: {}", id);
           return modal;
       }catch (Exception e){
           throw new RuntimeException("Failed to get school section: " + e.getMessage());
       }
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
        try{
            logger.debug("Retrieving all school sections");
            return schoolSectionRepository.findAll().stream()
                    .map(schoolSection -> {
                        SchoolSectionModal modal = new SchoolSectionModal();
                        modal.setName(schoolSection.getName());
                        modal.setClassNumber(schoolSection.getClassNumber());
                        modal.setClassType(schoolSection.getClassType());
                        return modal;
                    })
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Failed to get school sections: " + e.getMessage());
        }
    };

}
