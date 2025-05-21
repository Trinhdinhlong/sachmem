package com.example.sachmem.dto.request;

import com.example.sachmem.model.Status;
import lombok.Data;

@Data
public class SectionRequest {
    private String title;
    private String description;
    private Long bookId;
    private Long statusId;
}
