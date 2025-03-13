package com.example.demo.data.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@ToString(exclude = {"students","subjects","section"})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "teacher_id")
    private Long  teacherId ;

    @Column(name = "first_name")
    private String firstName ;

    @Column(name = "email")
    private String email ;

    @OneToOne
    @JoinColumn(name="section_id",referencedColumnName = "section_id" )
    private SchoolSection section;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference(value = "teacher-subjects")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference(value = "teacher-student")
    private List<Student> students;


}
