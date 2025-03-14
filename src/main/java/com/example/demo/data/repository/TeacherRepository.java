package com.example.demo.data.repository;

import com.example.demo.data.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  TeacherRepository extends JpaRepository<Teacher, Long> {

  Teacher findTeacherByTeacherId(float teacherId);

    Optional<Teacher> findTeacherByFirstName(String firstName);

}
