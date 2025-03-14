package com.example.demo.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "school_section")
@Getter
@Setter
@ToString(exclude = {"students","teacher"})
public class SchoolSection {

    @Id
    @Column(name = "section_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "class_number")
    private int classNumber;

    @Column (name = "class_type")
    private String classType;

    @OneToMany(mappedBy = "section",fetch = FetchType.EAGER)
    @JsonManagedReference(value = "section-student")
    private List<Student> students;

    @OneToOne(mappedBy = "section", fetch = FetchType.EAGER)
    private Teacher teacher;




}
