package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Services.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String saveFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Path uploadPath = Paths.get(uploadDir).resolve(fileName).normalize();
            Files.deleteIfExists(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file : "+ e.getMessage());
        }
    }
}
