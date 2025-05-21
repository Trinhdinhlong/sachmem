package com.example.sachmem.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookReponseAdmin {
    private Long id;
    private String title;
    private String description;
    private String subjectName;
    public String statusName;
    private String image;
    private String author;
    private String publisher;
    private String language;
    private String createAt;
}
