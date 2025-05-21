package com.example.sachmem.dto.reponse;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SectionResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
