package com.example.sachmem.dto.request;

import com.example.sachmem.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LectureRequest {
    private Long sectionId;
    private Long statusId;
    private String title;
    private String content;
    private String mediaUrl;
    private List<VocabularyItemRequest> vocabularyItemRequests;
}
