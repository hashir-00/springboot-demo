package com.example.demo.service.model;

import com.example.demo.data.entity.File;
import lombok.Data;

@Data
public class UploadedFileResponse {
    private String fileId;
    private String webViewLink;
    private String name;
    private float size;

    public static UploadedFileResponse uploadFileResponseModal(File file){
        UploadedFileResponse uploadedFileResponse = new UploadedFileResponse();
        uploadedFileResponse.setSize(file.getMediaSize());
        uploadedFileResponse.setFileId(file.getMediaFileId());
        uploadedFileResponse.setWebViewLink(file.getMediaUrl());
        return uploadedFileResponse;


    }

}
