package com.example.sachmem.service.impl;

import com.example.sachmem.exception.FileStorageException;
import com.example.sachmem.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.nio.file.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final String BASE_DIR = System.getProperty("user.dir") + File.separator + "File";
    @Override
    public String saveFile(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            throw new FileStorageException("Tên file không hợp lệ.");
        }

        String lowerName = originalName.toLowerCase();
        String folder;

        if (lowerName.endsWith(".png") || lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            folder = "image";
        } else if (lowerName.endsWith(".mp4") || lowerName.endsWith(".avi") || lowerName.endsWith(".mkv")) {
            folder = "video";
        } else if (lowerName.endsWith(".mp3") || lowerName.endsWith(".wav")) {
            folder = "audio";
        } else {
            throw new FileStorageException("Loại file không được hỗ trợ: " + originalName);
        }

        try {
            Path dirPath = Paths.get(BASE_DIR, folder);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(originalName);
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

            return originalName;
        } catch (Exception e) {
            throw new FileStorageException("Lỗi lưu file: " + originalName + e.getMessage());
        }
    }

    @Override
    public byte[] getFileByName(String fileName) {
        String lowerName = fileName.toLowerCase();
        String folder;

        if (lowerName.endsWith(".png") || lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            folder = "image";
        } else if (lowerName.endsWith(".mp4") || lowerName.endsWith(".avi") || lowerName.endsWith(".mkv")) {
            folder = "video";
        } else if (lowerName.endsWith(".mp3") || lowerName.endsWith(".wav")) {
            folder = "audio";
        } else {
            throw new FileStorageException("Không xác định được loại file từ tên: " + fileName);
        }

        try {
            Path path = Paths.get(BASE_DIR, folder, fileName);
            if (!Files.exists(path)) {
                throw new FileStorageException("Không tìm thấy file: " + fileName);
            }

            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new FileStorageException("Lỗi đọc file: " + fileName + e.getMessage());
        }
    }

}
