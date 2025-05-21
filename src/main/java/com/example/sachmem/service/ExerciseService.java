package com.example.sachmem.service;

import com.example.sachmem.dto.reponse.ExerciseResponse;
import com.example.sachmem.dto.reponse.LectureReponse;
import com.example.sachmem.dto.request.ExerciseRequest;
import com.example.sachmem.dto.request.LectureRequest;
import com.example.sachmem.model.Exercise;
import com.example.sachmem.model.Lecture;

import java.util.List;

public interface ExerciseService {
      ExerciseResponse createExercise(ExerciseRequest request);
      ExerciseResponse updateExercise(Long exerciseId, ExerciseRequest request);
//    public List<LectureReponse> getLectures(Long LectureId);
//    public boolean upateLecture(Long sectionId, LectureRequest section);
//    public Lecture deleteLecture(Long sectionId);
//    public Lecture addLecture(LectureRequest section);
//    public List<SectionReponseAdmin> getLecturesOfAdmin(Long bookId);
}
