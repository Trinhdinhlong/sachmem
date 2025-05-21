package com.example.sachmem.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookDetailReponse {
    private String title;
    private String description;
    private int progress;
    private int totalSections;
    private int completedSections;
    private String level;
    private String author;
    private String publisher;
    private String language;
    private String coverImage;
    private List<SectionResponse> sections;
}
