package com.example.sachmem.service.impl;

import com.example.sachmem.model.Subject;
import com.example.sachmem.repository.SubjectRepository;
import com.example.sachmem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }
}
