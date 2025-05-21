package com.example.sachmem.dto.reponse;

import com.example.sachmem.model.ExerciseType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ExerciseResponse {
    private Long id;
    private Long sectionId;
    private Long statusId;
    private String title;
    private String description;
    private ExerciseType type;
    private int duration;
    private int number;
    private int maxScore;
    private LocalDateTime createdAt;
    private List<QuestionResponse> questions;

    @Data
    @Builder
    public static class QuestionResponse {
        private Long id;
        private String title;
        private String mediaUrl;
        private String correct;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
    }
}
