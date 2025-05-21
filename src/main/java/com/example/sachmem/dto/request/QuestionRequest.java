package com.example.sachmem.dto.request;

import lombok.Data;

@Data
public class QuestionRequest {
    private String type;
    private String content;
    private String mediaUrl;
}

