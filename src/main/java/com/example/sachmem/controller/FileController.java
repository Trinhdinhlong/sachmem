package com.example.sachmem.controller;

import com.example.sachmem.service.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/file")
@CrossOrigin("*")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // Upload file (audio, video, image)
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.saveFile(file);
        // Trả về object JSON thay vì chỉ trả về chuỗi
        return ResponseEntity.ok(Map.of("fileName", fileName));
    }


    // Download file by fileName
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        byte[] fileData = fileStorageService.getFileByName(fileName);

        String contentType = determineContentType(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(fileData);
    }

    // Helper method xác định Content-Type dựa trên đuôi file
    private String determineContentType(String fileName) {
        String lowerName = fileName.toLowerCase();

        if (lowerName.endsWith(".png") || lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerName.endsWith(".mp4")) {
            return "video/mp4";
        } else if (lowerName.endsWith(".avi")) {
            return "video/x-msvideo";
        } else if (lowerName.endsWith(".mkv")) {
            return "video/x-matroska";
        } else if (lowerName.endsWith(".mp3")) {
            return "audio/mpeg";
        } else if (lowerName.endsWith(".wav")) {
            return "audio/wav";
        }
        return "application/octet-stream"; // default fallback
    }
}
