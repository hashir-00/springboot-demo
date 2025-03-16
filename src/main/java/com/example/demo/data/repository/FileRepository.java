package com.example.demo.data.repository;

import com.example.demo.data.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File getMediaFileById(long id);

    List<File> getAllByOrderByIdDesc();
}
