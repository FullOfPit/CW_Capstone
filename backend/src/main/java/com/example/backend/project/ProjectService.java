package com.example.backend.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAll() {
        return this.projectRepository.findAll();
    }

    public Project create(Project project) {
        return this.projectRepository.save(project);
    }
}
