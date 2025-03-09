package com.example.demo.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "school_section")
@Getter
@Setter
@ToString
public class SchoolSection {

    @Id
    @Column(name = "section_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "class_number")
    private int classNumber;

    @Column (name = "class_type")
    private String classType;

    @OneToMany(mappedBy = "section",fetch = FetchType.EAGER)
    private List<Student> students;

    @OneToOne(mappedBy = "section", fetch = FetchType.EAGER)
    private Teacher teacher;




}
