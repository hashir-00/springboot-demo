package com.example.demo.data.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@ToString(exclude = {"section", "subjects", "students"})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long  teacherId ;

    @Column(name = "first_name")
    private String firstName ;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject; // Remove 'subjectId int' and use this instead

    @Column(name = "email")
    private String email ;

    @OneToOne
    @JoinColumn(name="section_id",referencedColumnName = "section_id" )
    private SchoolSection section;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    private List<Student> students;


}
