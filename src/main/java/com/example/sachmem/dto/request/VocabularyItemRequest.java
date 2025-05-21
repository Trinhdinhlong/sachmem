package com.example.sachmem.dto.request;
import lombok.Data;

@Data
public class VocabularyItemRequest {
    private Long id;
    private Long lectureId;
    private String chineseWord;
    private String pinyin;
    private char correctImageLabel;
}

