package com.example.sachmem.controller;

import com.example.sachmem.dto.reponse.LectureReponse;
import com.example.sachmem.dto.request.LectureRequest;
import com.example.sachmem.model.Lecture;
import com.example.sachmem.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecture")
@CrossOrigin("*")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    // Lấy danh sách Lecture theo sectionId
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<LectureReponse>> getLecturesBySection(@PathVariable Long sectionId) {
        List<LectureReponse> lectures = lectureService.getLectures(sectionId);
        return ResponseEntity.ok(lectures);
    }

    // Thêm Lecture mới
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lecture> addLecture(@RequestBody LectureRequest lectureRequest) {
        Lecture lecture = lectureService.addLecture(lectureRequest);
        return ResponseEntity.ok(lecture);
    }

    // Cập nhật Lecture theo id
    @PutMapping("/{lectureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLecture(@PathVariable Long lectureId, @RequestBody LectureRequest lectureRequest) {
        Boolean updatedLecture = lectureService.updateLecture(lectureId, lectureRequest);
        return ResponseEntity.ok(updatedLecture);
    }

    // Xóa Lecture theo id
    @DeleteMapping("/{lectureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lecture> deleteLecture(@PathVariable Long lectureId) {
        Lecture deletedLecture = lectureService.deleteLecture(lectureId);
        return ResponseEntity.ok(deletedLecture);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LectureReponse> getLecture(@PathVariable Long id) {
        LectureReponse response = lectureService.getLectureById(id);
        return ResponseEntity.ok(response);
    }

}
