package com.example.demo.web.controller;


import com.example.demo.data.entity.File;
import com.example.demo.service.FileService;
import com.example.demo.service.model.UploadedFileResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Uploading file {}", file.getOriginalFilename());
        return fileService.uploadFile(file);
    }

    @GetMapping
    public ResponseEntity<List<UploadedFileResponse>> getAllFilesInDrive() {
        logger.info("getAllFilesInDrive called");
        return this.fileService.getAllFiles();

    }

    @GetMapping ("/{fileId}")
    ResponseEntity<UploadedFileResponse> getFileById(@RequestParam("fileId") String fileId) {
        logger.info("getFileById called");
        return this.fileService.getFileById(fileId);
    }

    @GetMapping("/media-files")
    ResponseEntity<List<UploadedFileResponse>> getAllMediaFiles() {
        logger.info("getAllMediaFiles called");
        return this.fileService.getAllMediaFilesData();
    }





}
