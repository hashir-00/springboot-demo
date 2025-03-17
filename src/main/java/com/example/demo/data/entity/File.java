package com.example.demo.data.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media_files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "media_title", nullable = false)
    private String mediaTitle;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Column(name = "media_file_id")
    private String mediaFileId;

    @Column(name="media_Size")
    private float mediaSize;
}