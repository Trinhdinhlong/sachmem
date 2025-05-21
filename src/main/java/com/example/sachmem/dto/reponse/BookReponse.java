package com.example.sachmem.dto.reponse;

import lombok.Data;

@Data
public class BookReponse {
    private String title;
    private String description;
    private int progress;
    private int totalSections;
    private int completedSections;
    private String level;
    private String author;
    private String publisher;
    private String language;
}
