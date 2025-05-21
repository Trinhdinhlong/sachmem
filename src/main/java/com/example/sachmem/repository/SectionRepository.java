package com.example.sachmem.repository;

import com.example.sachmem.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByBookIdAndBookEnableTrue(Long bookId);
    // SectionRepository.java
    List<Section> findByBookIdOrderByCreatedAtDesc(Long bookId);

    @Query("SELECT COUNT(l.id) FROM Lecture l WHERE l.section.book.id = :bookId AND l.status.id = 1")
    int countLecturesByBookId(Long bookId);

    @Query("SELECT COUNT(e.id) FROM Exercise e WHERE e.section.book.id = :bookId AND e.status.id = 1")
    int countExercisesByBookId(Long bookId);

    // Đếm tổng số Section có status active của một cuốn sách cụ thể (theo bookId)
    @Query("SELECT COUNT(s) FROM Section s WHERE s.status.id = 1 AND s.book.id = :bookId")
    int countTotalSectionsWithStatusActiveByBookId(Long bookId);

    // Lấy danh sách id Section có status active của một cuốn sách cụ thể (theo bookId)
    @Query("SELECT s.id FROM Section s WHERE s.status.id = 1 AND s.book.id = :bookId")
    List<Long> findAllActiveSectionIdsByBookId(Long bookId);

    @Query("SELECT COUNT(e.id) FROM Exercise e WHERE e.section.id = :sectionId AND e.status.id = 1")
    int countExercisesBySectionId(Long sectionId);

    @Query("SELECT COUNT(l.id) FROM Lecture l WHERE l.section.id = :sectionId AND l.status.id = 1")
    int countLecturesBySectionId(Long sectionId);



}

