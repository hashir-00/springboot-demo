package com.example.demo.service;

import com.example.demo.data.entity.SchoolSection;
import com.example.demo.data.entity.Student;
import com.example.demo.data.entity.Teacher;
import com.example.demo.data.repository.SchoolSectionRepository;
import com.example.demo.data.repository.StudentRepository;
import com.example.demo.data.repository.TeacherRepository;
import com.example.demo.service.model.CreateStudent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class)
            ;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolSectionRepository schoolSectionRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository, SchoolSectionRepository schoolSectionRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.schoolSectionRepository = schoolSectionRepository;
    }

    public List<Student> getAllStudents() {
        logger.debug("Attempting to get all Students");
        try{
            return this.studentRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Student getStudentById(Long id) {
        logger.debug("Attempting to get Student by ID {}", id);
        try {
            return this.studentRepository.findStudentsByStudentId(id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Student addStudent(CreateStudent studentModal) {
        logger.debug("Attempting to add Student");
        try {
            boolean studentExist = this.studentRepository.existsStudentByFirstName(studentModal.getFirstName()) || this.studentRepository.existsStudentsByLastName(studentModal.getLastName());

            if (studentExist) {
                throw new RuntimeException("Student already exists");
            }

            Teacher teacher = this.teacherRepository.findTeacherByTeacherId(studentModal.getTeacherId());

            SchoolSection section = this.schoolSectionRepository.findBySectionId(studentModal.getSectionId());

            Student student = new Student();
            student.setFirstName(studentModal.getFirstName());
            student.setLastName(studentModal.getLastName());
            student.setTeacher(teacher);
            student.setDob(studentModal.getDob());
            student.setGender(studentModal.getGender());
            student.setSection(section);

            return studentRepository.save(student);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public Student updateStudent(Student student) {
        logger.debug("Attempting to update Student");

        try {
            return this.studentRepository.save(student);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteStudent(Long id) {
        logger.debug("Attempting to delete Student");
        try {
            if(studentRepository.findStudentsByStudentId(id) == null){
                throw new RuntimeException("Student does not exist");
            }
            this.studentRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
