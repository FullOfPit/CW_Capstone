package com.example.backend.project;

import com.example.backend.exception.ProjectNotFoundException;
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

    @GetMapping("/{id}")
    public Project getById(@PathVariable String id) throws Exception {
        return this.projectService.getById(id);
    }

    @PostMapping("/{id}")
    public List<Project> getAllByUserId(@PathVariable String id) throws Exception {
        return this.projectService.getAllByUserId(id);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable String id, @RequestBody Project project) throws ProjectNotFoundException {
        return this.projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws ProjectNotFoundException {
        this.projectService.deleteById(id);
    }

}
