package com.example.sachmem.service.impl;

import com.example.sachmem.model.Status;
import com.example.sachmem.repository.StatusRepository;
import com.example.sachmem.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getStatuss() {
        return statusRepository.findAll();
    }
}
