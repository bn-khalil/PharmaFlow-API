package com.pharmaflow.demo.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String saveFile(MultipartFile file) throws IOException;
    void deleteFile(String fileName);
}
