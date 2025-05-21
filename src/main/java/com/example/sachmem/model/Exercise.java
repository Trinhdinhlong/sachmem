package com.example.sachmem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercises")
@Builder
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING) // <- Ánh xạ enum thành chuỗi trong DB
    @Column(nullable = false)
    private ExerciseType type;

    private int duration;

    @Column(nullable = false)
    private int number;

    private int maxScore;
    private String image;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Attempts> attempts;

}
