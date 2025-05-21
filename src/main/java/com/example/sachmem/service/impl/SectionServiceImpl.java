package com.example.sachmem.service.impl;

import com.example.sachmem.dto.reponse.SectionReponseAdmin;
import com.example.sachmem.dto.reponse.SectionResponse;
import com.example.sachmem.dto.request.SectionRequest;
import com.example.sachmem.model.Book;
import com.example.sachmem.model.Section;
import com.example.sachmem.model.Status;
import com.example.sachmem.repository.BookRepository;
import com.example.sachmem.repository.SectionRepository;
import com.example.sachmem.repository.StatusRepository;
import com.example.sachmem.service.SectionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<SectionResponse> getSections(Long bookId) {
        List<Section> sections = sectionRepository.findByBookIdAndBookEnableTrue(bookId);
        System.out.println(sections.size());
        return sections.stream().map(section -> {
            SectionResponse response = new SectionResponse();
            response.setId(section.getId());
            response.setTitle(section.getTitle());
            response.setDescription(section.getDescription());
            return response;
        }).collect(Collectors.toList());
    }


    @Override
    public Section upateSection(Long sectionId, SectionRequest sectionData) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy section với id: " + sectionId));

        // Cập nhật các trường từ sectionData
        section.setTitle(sectionData.getTitle());
        section.setDescription(sectionData.getDescription());

        Book book = bookRepository.findById(sectionData.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        section.setBook(book);


        Status status = statusRepository.findById(sectionData.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        section.setStatus(status);

        return sectionRepository.save(section);
    }


    @Override
    @Transactional
    public Section deleteSection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy section với id: " + sectionId));

        sectionRepository.delete(section);
        return section;
    }

    @Override
    @Transactional
    public Section addSection(SectionRequest sectionRequest) {
        Section section = new Section();
        section.setTitle(sectionRequest.getTitle());
        section.setDescription(sectionRequest.getDescription());

        // Giả sử bạn có method lấy Book từ bookId
        Book book = bookRepository.findById(sectionRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        section.setBook(book);

        // Giả sử bạn có method lấy Status từ statusId
        Status status = statusRepository.findById(sectionRequest.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        section.setStatus(status);

        return sectionRepository.save(section);
    }

    @Override
    public List<SectionReponseAdmin> getSectionsOfAdmin(Long bookId) {
        List<Section> sections = sectionRepository.findByBookIdAndBookEnableTrue(bookId);

        return sections.stream().map(section -> {
            Long sectionId = section.getId();

            int exercisesCount = sectionRepository.countExercisesBySectionId(sectionId);
            int lecturesCount = sectionRepository.countLecturesBySectionId(sectionId);

            return new SectionReponseAdmin(
                    section.getId(),
                    section.getTitle(),
                    section.getDescription(),
                    exercisesCount,
                    lecturesCount,
                    section.getStatus().getName(),
                    section.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Section getSectionById(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found with id: " + sectionId));
    }


}
