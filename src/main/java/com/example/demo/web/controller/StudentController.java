package com.example.demo.web.controller;

import com.example.demo.data.entity.Student;
import com.example.demo.service.StudentService;
import com.example.demo.service.model.CreateStudent;
import com.example.demo.service.model.StudentOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class.getName());

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    /**
     * Fetch all students.
     *
     * @return A list of all students.
     */
    @GetMapping
    public ResponseEntity<List<StudentOutput>> getAllStudents() {
        logger.info("Fetching all students");
        try {
            List<StudentOutput> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("Error fetching all students: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Fetch a student by ID.
     *
     * @param id The ID of the student to fetch.
     * @return The student with the specified ID, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentOutput> getStudentById(@PathVariable Long id) {
        logger.info("Fetching student by ID: {}", id);
        try {
            StudentOutput student = studentService.getStudentById(id);
            if (student != null) {
                return ResponseEntity.ok(student);
            } else {
                logger.warn("Student with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error fetching student by ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Add a new student.
     *
     * @param student The student object to add.
     * @return The added student with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<Void> addStudent(@RequestBody CreateStudent student) {
        logger.info("Adding new student: {}", student.getFirstName()+' '+student.getLastName());
        try {
            studentService.addStudent(student);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(student.getFirstName())
                    .toUri();

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error adding student: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update an existing student.
     *
     * @param student The updated student object.
     * @return The updated student, or 404 if the student does not exist.
     */
    @PutMapping
    public ResponseEntity<StudentOutput> updateStudent(@RequestBody CreateStudent student,@RequestParam Long studentId) {
        logger.info("Updating student: {}", student);
        try {
            StudentOutput updatedStudent = studentService.updateStudent(student,studentId);
            if (updatedStudent != null) {
                return ResponseEntity.ok(updatedStudent);
            } else {
                logger.warn("Student with ID {} not found for update", student.getFirstName()+' '+student.getLastName());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error updating student: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete a student by ID.
     *
     * @param id The ID of the student to delete.
     * @return 204 No Content on success, or 404 if the student does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.info("Deleting student with ID: {}", id);
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting student with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
