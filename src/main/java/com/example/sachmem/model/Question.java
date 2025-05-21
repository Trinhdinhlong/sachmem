package com.example.sachmem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String mediaUrl;
    private String correct;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
}
