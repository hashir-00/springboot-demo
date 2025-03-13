package com.example.demo.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "subject")
@Getter
@Setter
@ToString(exclude = {"teacher"})
public class Subject {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  subjectId ;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonBackReference(value = "teacher-subjects")
    @JoinColumn(name = "teacher_id",referencedColumnName = "teacher_id")
    private Teacher teacher;


}
