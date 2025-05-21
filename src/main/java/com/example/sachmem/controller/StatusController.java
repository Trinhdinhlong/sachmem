package com.example.sachmem.controller;

import com.example.sachmem.model.Status;
import com.example.sachmem.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<List<Status>> getStatuss() {
        return ResponseEntity.ok(statusService.getStatuss());
    }
}
