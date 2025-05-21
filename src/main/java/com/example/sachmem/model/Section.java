package com.example.sachmem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "section")
@Builder
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
//    @JsonIgnore
    private Book book;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    private String title;
    private String description;
    // private int exerciseCount;
    //private int LectureCount;
    @CreationTimestamp
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lecture> lecture;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Exercise> exercises;

}
