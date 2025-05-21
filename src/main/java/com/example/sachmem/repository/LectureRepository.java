package com.example.sachmem.repository;

import com.example.sachmem.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l WHERE l.section.id = :sectionId")
    List<Lecture> findLecturesBySectionId(@Param("sectionId") Long sectionId);

}
