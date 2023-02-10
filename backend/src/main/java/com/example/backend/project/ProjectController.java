package com.example.backend.project;

import com.example.backend.exception.ProjectNotRegisteredException;
import com.example.backend.exception.UserNotRegisteredException;
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
    public Project getById(@PathVariable String id) throws ProjectNotRegisteredException {
        return this.projectService.getById(id);
    }

    @GetMapping("/app-users/{id}")
    public List<Project> getAllByUserId(@PathVariable String id) throws UserNotRegisteredException {
        return this.projectService.getAllByUserId(id);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable String id, @RequestBody Project project) throws ProjectNotRegisteredException {
        return this.projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws ProjectNotRegisteredException {
        this.projectService.deleteById(id);
    }

}
