package com.KurdDevs.KurdDevs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    public void saveFile(MultipartFile file, String fileName) throws IOException {
        // Create the upload directory if it doesn't exist
        Path uploadDir = Path.of(UPLOAD_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Save the file to the upload directory
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] getFileBytes(String fileName) throws IOException {
        // Read the file bytes from the upload directory
        Path filePath = Path.of(UPLOAD_DIR, fileName);
        return Files.readAllBytes(filePath);
    }
}
