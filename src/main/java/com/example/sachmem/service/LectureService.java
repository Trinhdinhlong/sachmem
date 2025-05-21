package com.example.sachmem.service;

import com.example.sachmem.dto.reponse.LectureReponse;
import com.example.sachmem.dto.reponse.SectionReponseAdmin;
import com.example.sachmem.dto.reponse.SectionResponse;
import com.example.sachmem.dto.request.LectureRequest;
import com.example.sachmem.dto.request.SectionRequest;
import com.example.sachmem.model.Lecture;
import com.example.sachmem.model.Section;

import java.util.List;

public interface LectureService {
    public List<LectureReponse> getLectures(Long sectionId);
    boolean updateLecture(Long lectureId, LectureRequest request);
    public Lecture deleteLecture(Long sectionId);
    Lecture addLecture(LectureRequest request);
//    public List<SectionReponseAdmin> getLecturesOfAdmin(Long bookId);
    public LectureReponse getLectureById(Long lectureId);
}
