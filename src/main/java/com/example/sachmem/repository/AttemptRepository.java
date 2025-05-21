package com.example.sachmem.repository;

import com.example.sachmem.model.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends JpaRepository<Attempts, Long> {
    @Query("SELECT COUNT(DISTINCT a.lecture.id) FROM Attempts a WHERE a.user.id = :userId AND a.lecture.id IS NOT NULL AND a.lecture.section.book.id = :bookId")
    int countCompletedLectures(Long userId, Long bookId);

    @Query("SELECT COUNT(DISTINCT a.exercise.id) FROM Attempts a WHERE a.user.id = :userId AND a.exercise.id IS NOT NULL AND a.exercise.section.book.id = :bookId")
    int countCompletedExercises(Long userId, Long bookId);

    @Query("SELECT COUNT(DISTINCT a.lecture.id) FROM Attempts a WHERE a.user.id = :userId AND a.lecture.section.id = :sectionId")
    int countLecturesCompletedInSection(Long userId, Long sectionId);

    @Query("SELECT COUNT(DISTINCT a.exercise.id) FROM Attempts a WHERE a.user.id = :userId AND a.exercise.section.id = :sectionId")
    int countExercisesCompletedInSection(Long userId, Long sectionId);
}

