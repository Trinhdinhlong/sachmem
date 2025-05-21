package com.example.sachmem.service.impl;

import com.example.sachmem.dto.reponse.ExerciseResponse;
import com.example.sachmem.dto.request.ExerciseRequest;
import com.example.sachmem.model.*;
import com.example.sachmem.repository.ExerciseRepository;
import com.example.sachmem.repository.SectionRepository;
import com.example.sachmem.repository.StatusRepository;
import com.example.sachmem.service.ExerciseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final SectionRepository sectionRepository;
    private final StatusRepository statusRepository;

    @Override
    public ExerciseResponse createExercise(ExerciseRequest request) {
        Exercise exercise = mapRequestToEntity(request, null);
        Exercise saved = exerciseRepository.save(exercise);
        return mapEntityToResponse(saved);
    }

    @Override
    public ExerciseResponse updateExercise(Long exerciseId, ExerciseRequest request) {
        Exercise existing = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id " + exerciseId));

        Exercise updated = mapRequestToEntity(request, existing);
        Exercise saved = exerciseRepository.save(updated);
        return mapEntityToResponse(saved);
    }

    private Exercise mapRequestToEntity(ExerciseRequest request, Exercise existing) {
        Exercise exercise = existing == null ? new Exercise() : existing;

        // Set Section
        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found with id " + request.getSectionId()));
        exercise.setSection(section);

        // Set Status
        Status status = statusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found with id " + request.getStatusId()));
        exercise.setStatus(status);

        // Set basic fields
        exercise.setTitle(request.getTitle());
        exercise.setDescription(request.getDescription());
        exercise.setDuration(request.getDuration());
        exercise.setNumber(request.getNumber());
        exercise.setMaxScore(request.getMaxScore());

        // Convert String to Enum ExerciseType
        ExerciseType typeEnum;
        try {
            typeEnum = ExerciseType.valueOf(request.getType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid exercise type: " + request.getType());
        }
        exercise.setType(typeEnum);

        // Map questions
        List<Question> questionList = new ArrayList<>();
        if (request.getQuestions() != null) {
            for (ExerciseRequest.QuestionRequest qReq : request.getQuestions()) {
                Question question = new Question();
                question.setTitle(qReq.getTitle());
                question.setMediaUrl(qReq.getMediaUrl());
                question.setCorrect(qReq.getCorrect());
                question.setOptionA(qReq.getOptionA());
                question.setOptionB(qReq.getOptionB());
                question.setOptionC(qReq.getOptionC());
                question.setOptionD(qReq.getOptionD());
                question.setExercise(exercise);
                questionList.add(question);
            }
        }
        exercise.setQuestions(questionList);

        return exercise;
    }

    private ExerciseResponse mapEntityToResponse(Exercise exercise) {
        // Implement mapping logic from Exercise entity to ExerciseResponse DTO
        // This depends on your ExerciseResponse structure
        return null; // placeholder
    }
}
