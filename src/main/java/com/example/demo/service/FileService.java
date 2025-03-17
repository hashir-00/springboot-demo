package com.example.demo.service;



import com.example.demo.data.entity.File;
import com.example.demo.data.repository.FileRepository;


import com.example.demo.service.model.UploadedFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final FileRepository fileRepository;
    private final GoogleDriveService googleDriveService;


    public FileService(FileRepository fileRepository, GoogleDriveService googleDriveService) {
        this.fileRepository = fileRepository;
        this.googleDriveService = googleDriveService;
    }

public ResponseEntity<File> uploadFile(MultipartFile file) {
        logger.debug("Uploading file {} " , file.getOriginalFilename());
    UploadedFileResponse uploadedFileResponse= googleDriveService.uploadFile(file);
    File mediaFile = new File();
    mediaFile.setMediaFileId(uploadedFileResponse.getFileId());
    mediaFile.setMediaSize(uploadedFileResponse.getSize());
    mediaFile.setMediaTitle(uploadedFileResponse.getName());
    mediaFile.setMediaUrl(uploadedFileResponse.getWebViewLink());

    this.fileRepository.save(mediaFile);
    return new ResponseEntity<>(mediaFile, HttpStatus.OK);

}


    public ResponseEntity<List<UploadedFileResponse>> getAllFiles() {
        logger.debug("getAllFiles called");
        List<UploadedFileResponse> responseList = this.googleDriveService.getAllFilesInDrive();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    public ResponseEntity<UploadedFileResponse> getFileById(String fileId) {
        logger.debug("getFileById called");
        UploadedFileResponse response = this.googleDriveService.getFileById(fileId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<UploadedFileResponse>> getAllMediaFilesData() {
        logger.debug("getAllMediaFilesData called");
        try {
            List<File> fileList = this.fileRepository.findAll();
            List<UploadedFileResponse> responseList = fileList.stream().map(UploadedFileResponse::uploadFileResponseModal).toList();
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}


