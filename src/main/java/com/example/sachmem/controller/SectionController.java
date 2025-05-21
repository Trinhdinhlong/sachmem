package com.example.sachmem.controller;

import com.example.sachmem.dto.reponse.SectionReponseAdmin;
import com.example.sachmem.dto.request.SectionRequest;
import com.example.sachmem.dto.reponse.SectionResponse;
import com.example.sachmem.model.Section;
import com.example.sachmem.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;  // Directly injecting SectionServiceImpl

    @GetMapping("/user/{bookId}")
    public ResponseEntity<List<SectionResponse>> getSectionsByBook(@PathVariable Long bookId) {
        List<SectionResponse> sections = sectionService.getSections(bookId);
        return ResponseEntity.ok(sections);
    }
    @GetMapping("/admin/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SectionReponseAdmin>> getSectionsByBookOfAdmin(@PathVariable Long bookId) {
        List<SectionReponseAdmin> sections = sectionService.getSectionsOfAdmin(bookId);
        return ResponseEntity.ok(sections);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSection(@RequestBody SectionRequest request) {
        Section createdSection = sectionService.addSection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSection);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody SectionRequest request) {
        Section updatedSection = sectionService.upateSection(id, request);
        return ResponseEntity.ok(updatedSection);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.ok("Xóa section thành công");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.getSectionById(id));
    }

}
