package com.example.demo.data.repository;

import com.example.demo.data.entity.SchoolSection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolSectionRepository extends JpaRepository<SchoolSection, Long> {

  Optional<SchoolSection> findSchoolSectionByNameContainsIgnoreCase(String name);

  SchoolSection findSchoolSectionByClassNumber(int classNumber);

  SchoolSection findSchoolSectionByTeacher_TeacherId(float teacherId);

  SchoolSection findSchoolSectionBySectionId(float sectionId);

  SchoolSection findBySectionId(float sectionId);
}
