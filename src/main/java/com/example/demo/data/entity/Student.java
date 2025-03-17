package com.example.demo.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"section","teacher"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;

    @Column(name = "dob", nullable = false)
    private Date dob;


    @Column(name = "gender", length = 1)
    private String gender;

    @ManyToOne
    @JsonBackReference(value = "section-student")
    @JoinColumn(name = "section_id", referencedColumnName = "section_id", nullable = true)
    private SchoolSection section;

    @ManyToOne
    @JsonBackReference(value = "teacher-student")
    @JoinColumn(name = "teacher_id",referencedColumnName = "teacher_id",nullable = true)
    private Teacher teacher;

}
