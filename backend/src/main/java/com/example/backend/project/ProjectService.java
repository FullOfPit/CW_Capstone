package com.example.backend.project;

import com.example.backend.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAll() {
        return this.projectRepository.findAll();
    }

    public Project create(Project project) {
        project.setCreatedAt(LocalDate.now());
        return this.projectRepository.save(project);
    }

    public Project getById(String id) throws Exception{
        return this.projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }
}
