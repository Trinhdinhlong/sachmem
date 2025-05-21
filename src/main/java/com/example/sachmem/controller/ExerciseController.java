package com.example.sachmem.controller;

import com.example.sachmem.dto.reponse.ExerciseResponse;
import com.example.sachmem.dto.request.ExerciseRequest;
import com.example.sachmem.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    // Tạo mới bài tập
    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        ExerciseResponse exerciseResponse = exerciseService.createExercise(exerciseRequest);
        return ResponseEntity.ok(exerciseResponse);
    }

    // Cập nhật bài tập theo id
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponse> updateExercise(@PathVariable Long id,
                                                           @RequestBody ExerciseRequest exerciseRequest) {
        ExerciseResponse exerciseResponse = exerciseService.updateExercise(id, exerciseRequest);
        return ResponseEntity.ok(exerciseResponse);
    }

}
