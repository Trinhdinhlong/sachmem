package com.example.sachmem.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SectionReponseAdmin {
    private Long id;
    private String title;
    private String description;
    private int exercisesCount;
    private int lecturesCount;
    private String statusName;
    private LocalDateTime createdAt;

}
