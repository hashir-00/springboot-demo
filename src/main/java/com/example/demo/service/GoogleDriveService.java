package com.example.demo.service;

import com.example.demo.service.model.UploadedFileResponse;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
public class GoogleDriveService {
    private final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);

    @Value("${MY_APPLICATION_NAME}")
    private static String APPLICATION_NAME ;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/main/resources/credentials.json"; // Your JSON Key Path

    @Value("${MY_GOOGLE_DRIVE_ID}")
    private  String DRIVE_FOLDER_ID;

    private  final Drive driveService =getDriveService();

    private Drive getDriveService()  {
      try {
          HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
          InputStream serviceAccountStream = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH);

          GoogleCredentials credentials = GoogleCredentials
                  .fromStream(serviceAccountStream)
                  .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
//                  .createScoped(List.of(DriveScopes.DRIVE))
//                  .createScoped(List.of(DriveScopes.DRIVE_APPDATA));

          return new Drive.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                  .setApplicationName(APPLICATION_NAME)
                  .build();

      }catch (Exception e) {
          logger.debug("Error creating Drive service{}", e.getMessage());
          return null;
      }
    }

    public UploadedFileResponse uploadFile(MultipartFile file) {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(file.getOriginalFilename());
            fileMetadata.setParents(Collections.singletonList(DRIVE_FOLDER_ID)); // Save inside folder

            InputStreamContent content = new InputStreamContent(
                    file.getContentType(), file.getInputStream()
            );

            assert driveService != null;
            File uploadedFile = driveService.files().create(fileMetadata, content)
                    .setFields("id,webViewLink,name,size")
                    .execute();
            logger.debug("Uploaded Successfully!");
            UploadedFileResponse uploadedFileResponse = new UploadedFileResponse();
            uploadedFileResponse.setFileId(uploadedFile.getId());
            uploadedFileResponse.setWebViewLink(uploadedFile.getWebViewLink());
            uploadedFileResponse.setName(uploadedFile.getName());
            uploadedFileResponse.setSize(uploadedFile.getSize());

            return uploadedFileResponse;
        } catch (Exception e) {
        logger.debug("Error creating file {}", e.getMessage());
           throw new RuntimeException(e);
        }
    }

    public List<UploadedFileResponse> getAllFilesInDrive() {
        logger.debug("getAllFilesInDrive called");
        try {
            assert driveService != null;
            FileList files = driveService.files().list()
                  .setSpaces("drive")
                    .setFields("nextPageToken, files(id,name,size,webViewLink)")
                    .setPageSize(10)
                    .execute();
            return new ArrayList<>() {{
                for (File file : files.getFiles()) {
                    UploadedFileResponse uploadedFileResponse = new UploadedFileResponse();
                    uploadedFileResponse.setFileId(file.getId());
                    uploadedFileResponse.setName(file.getName());
                    uploadedFileResponse.setSize(file.getSize());
                    uploadedFileResponse.setWebViewLink(file.getWebViewLink());
                    add(uploadedFileResponse);
                }

            }};


        } catch (Exception e) {
            logger.error("Error getting fileList {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

   public  UploadedFileResponse getFileById(String fileId) {
        logger.debug("get file by id {}", fileId);
        try {
            assert driveService != null;
            File result = driveService.files().get(fileId)
                    .setFields("name, id, size, webViewLink")
                    .execute();
            UploadedFileResponse response=  new UploadedFileResponse();
            response.setFileId(fileId);
            response.setSize(result.getSize());
            response.setWebViewLink(result.getWebViewLink());
            response.setName(result.getName());
            return response ;
        }catch (Exception e) {
            logger.debug("Error getting file {}", e.getMessage());
            throw new RuntimeException(e);
        }
   }

}