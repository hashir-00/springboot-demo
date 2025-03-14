package com.example.demo.data.repository;

import com.example.demo.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByStudentId(long studentId);


    Student findStudentsByStudentId(Long studentId);

    boolean existsStudentByFirstName(String firstName);

    boolean existsStudentsByLastName(String lastName);
}
