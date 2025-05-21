package com.example.sachmem.dto.request;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String description;
    private String author;
    private String publisher;
    private String language;
    private String image;
    private Long subjectId;
    private Long statusId;
}

