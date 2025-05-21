package com.example.sachmem.repository;

import com.example.sachmem.model.VocabularyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabularyItemRepository extends JpaRepository<VocabularyItem, Long> {
    List<VocabularyItem> findByLectureId(Long lectureId);
}

