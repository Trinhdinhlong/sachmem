package com.example.sachmem.service;

import com.example.sachmem.dto.reponse.SectionReponseAdmin;
import com.example.sachmem.dto.reponse.SectionResponse;
import com.example.sachmem.dto.request.SectionRequest;
import com.example.sachmem.model.Section;

import java.util.List;

public interface SectionService {
    public List<SectionResponse> getSections(Long bookId);
    public Section upateSection(Long sectionId, SectionRequest section);
    public Section deleteSection(Long sectionId);
    public Section addSection(SectionRequest section);
    public List<SectionReponseAdmin> getSectionsOfAdmin(Long bookId);
    // ✅ Thêm mới:
    public Section getSectionById(Long sectionId);
}
