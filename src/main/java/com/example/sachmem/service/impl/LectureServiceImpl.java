package com.example.sachmem.service.impl;

import com.example.sachmem.dto.reponse.LectureReponse;
import com.example.sachmem.dto.reponse.VocabularyItemResponse;
import com.example.sachmem.dto.request.LectureRequest;
import com.example.sachmem.dto.request.VocabularyItemRequest;
import com.example.sachmem.exception.EntityNotFoundException;
import com.example.sachmem.model.Lecture;
import com.example.sachmem.model.Section;
import com.example.sachmem.model.Status;
import com.example.sachmem.model.VocabularyItem;
import com.example.sachmem.repository.LectureRepository;
import com.example.sachmem.repository.SectionRepository;
import com.example.sachmem.repository.StatusRepository;
import com.example.sachmem.repository.VocabularyItemRepository;
import com.example.sachmem.service.LectureService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final SectionRepository sectionRepository;
    private final StatusRepository statusRepository;
    private final VocabularyItemRepository vocabularyItemRepository;

    @Override
    public List<LectureReponse> getLectures(Long sectionId) {
        List<Lecture> lectures = lectureRepository.findLecturesBySectionId(sectionId);
        System.out.println("Số bài giảng tìm thấy: " + lectures.size());

        return lectures.stream().map(lecture -> {
            List<VocabularyItem> vocabularies = vocabularyItemRepository.findByLectureId(lecture.getId());

            List<VocabularyItemResponse> vocabularyResponses = vocabularies.stream()
                    .map(v -> VocabularyItemResponse.builder()
                            .id(v.getId())
                            .chineseWord(v.getChineseWord())
                            .pinyin(v.getPinyin())
                            .correctImageLabel(v.getCorrectImageLabel())
                            .build()
                    ).toList();

            return LectureReponse.builder()
                    .id(lecture.getId())
                    .status(lecture.getStatus().getName())
                    .title(lecture.getTitle())
                    .content(lecture.getContent())
                    .mediaUrl(lecture.getMediaUrl())
                    .createdAt(lecture.getCreatedAt())
                    .vocabularyItems(vocabularyResponses)
                    .build();
        }).toList();
    }


    public boolean updateLecture(Long lectureId, LectureRequest request) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        // Cập nhật thông tin bài học
        lecture.setTitle(request.getTitle());
        lecture.setContent(request.getContent());
        lecture.setMediaUrl(request.getMediaUrl());
        lecture.setStatus(statusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found")));
        lecture.setSection(sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found")));

        lectureRepository.save(lecture);

        // Lấy các ID từ request để phục vụ xử lý xoá (nếu cần)
        Set<Long> requestIds = request.getVocabularyItemRequests().stream()
                .filter(v -> v.getId() != null)
                .map(VocabularyItemRequest::getId)
                .collect(Collectors.toSet());

        // Lấy tất cả vocab hiện tại
        List<VocabularyItem> existingItems = vocabularyItemRepository.findByLectureId(lectureId);
        for (VocabularyItem item : existingItems) {
            if (!requestIds.contains(item.getId())) {
                vocabularyItemRepository.delete(item); // Xoá vocab không còn
            }
        }

        // Duyệt từng phần tử trong request
        for (VocabularyItemRequest viReq : request.getVocabularyItemRequests()) {
            if (viReq.getId() == null) {
                // Insert mới
                VocabularyItem newItem = VocabularyItem.builder()
                        .lecture(lecture)
                        .chineseWord(viReq.getChineseWord())
                        .pinyin(viReq.getPinyin())
                        .correctImageLabel(viReq.getCorrectImageLabel())
                        .build();
                vocabularyItemRepository.save(newItem);
            } else {
                // Update
                VocabularyItem existing = vocabularyItemRepository.findById(viReq.getId())
                        .orElseThrow(() -> new RuntimeException("VocabularyItem not found with id " + viReq.getId()));
                existing.setChineseWord(viReq.getChineseWord());
                existing.setPinyin(viReq.getPinyin());
                existing.setCorrectImageLabel(viReq.getCorrectImageLabel());
                vocabularyItemRepository.save(existing);
            }
        }

        return true;
    }


    @Override
    @Transactional
    public Lecture deleteLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy bài giảng với id: " + lectureId));
        lectureRepository.delete(lecture);
        return lecture;
    }

    @Override
    public Lecture addLecture(LectureRequest request) {
        // Tạo đối tượng Lecture
        Lecture lecture = Lecture.builder()
                .section(sectionRepository.findById(request.getSectionId()).orElseThrow())
                .status(statusRepository.findById(request.getStatusId()).orElseThrow())
                .title(request.getTitle())
                .content(request.getContent())
                .mediaUrl(request.getMediaUrl())
                .build();

        Lecture savedLecture = lectureRepository.save(lecture);

        // Xử lý thêm các từ vựng nếu có
        if (request.getVocabularyItemRequests() != null && !request.getVocabularyItemRequests().isEmpty()) {
            for (VocabularyItemRequest item : request.getVocabularyItemRequests()) {
                VocabularyItem vocabularyItem = VocabularyItem.builder()
                        .lecture(savedLecture)
                        .chineseWord(item.getChineseWord())
                        .pinyin(item.getPinyin())
                        .correctImageLabel(item.getCorrectImageLabel())
                        .build();

                vocabularyItemRepository.save(vocabularyItem);
            }
        }

        return savedLecture;
    }
    public LectureReponse getLectureById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        List<VocabularyItem> vocabularyItems = vocabularyItemRepository.findByLectureId(lectureId);

        List<VocabularyItemResponse> vocabResponses = vocabularyItems.stream()
                .map(item -> VocabularyItemResponse.builder()
                        .id(item.getId())
                        .chineseWord(item.getChineseWord())
                        .pinyin(item.getPinyin())
                        .correctImageLabel(item.getCorrectImageLabel())
                        .build())
                .collect(Collectors.toList());

        return LectureReponse.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .mediaUrl(lecture.getMediaUrl())
                .status(lecture.getStatus().getName())
                .vocabularyItems(vocabResponses)
                .build();
    }

}
