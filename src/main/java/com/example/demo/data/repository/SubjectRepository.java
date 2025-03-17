package com.example.demo.data.repository;

import com.example.demo.data.entity.Subject;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findSubjectBySubjectId(long subjectId);
}
