package com.example.sachmem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseRequest {
    private Long sectionId;
    private Long statusId;
    private String title;
    private String description;
    private String type;  // Có thể enum, hoặc String rồi parse trong service
    private int duration;
    private int number;
    private int maxScore;
    private List<QuestionRequest> questions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionRequest {
        private String title;
        private String mediaUrl;
        private String correct;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
    }
}
