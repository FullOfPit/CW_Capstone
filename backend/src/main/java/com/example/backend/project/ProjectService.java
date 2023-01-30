package com.example.backend.project;

import com.example.backend.appuser.AppUserRepository;
import com.example.backend.exception.ProjectNotFoundException;
import com.example.backend.exception.UserNotRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;

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

    public void deleteById(String id) throws ProjectNotFoundException {
        if (this.projectRepository.existsById(id)) {
            this.projectRepository.deleteById(id);
        } else {
            throw new ProjectNotFoundException();
        }
    }

    public Project update(String id, Project project) throws ProjectNotFoundException {
        project.setId(id);

        if(!this.projectRepository.existsById(id)) {
            throw new ProjectNotFoundException();
        }
        return this.projectRepository.save(project);
    }

    public List<Project> getAllByUserId(String id) throws UserNotRegisteredException {
        if(appUserRepository.existsById(id)) {
            List<Project> fullProjectList = this.projectRepository.findAll();
            return fullProjectList.stream()
                    .filter((project) -> project.getCreatedBy().equals(id))
                    .toList();
        }
        throw new UserNotRegisteredException();
    }
}
