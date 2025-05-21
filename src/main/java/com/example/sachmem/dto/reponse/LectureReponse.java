package com.example.sachmem.dto.reponse;

import com.example.sachmem.model.Section;
import com.example.sachmem.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LectureReponse {
    private Long id;
    private String status;
    private String title;
    private String content;
    private String mediaUrl;
    private LocalDateTime createdAt;
    private List<VocabularyItemResponse> vocabularyItems;
}
