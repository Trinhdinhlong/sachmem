package com.example.sachmem.dto.reponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VocabularyItemResponse {
    private Long id;
    private String chineseWord;
    private String pinyin;
    private char correctImageLabel;
}

