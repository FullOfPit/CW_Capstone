package com.example.backend.project;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    List<Project> getAll() {
        return this.projectService.getAll();
    }

    @PostMapping
    Project create(@RequestBody Project project) {
        return this.projectService.create(project);
    }

}
