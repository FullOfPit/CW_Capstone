package com.example.backend.project;

import com.example.backend.appuser.AppUserRepository;
import com.example.backend.exception.ProjectNotRegisteredException;
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

    private Project projectStatusCheck(Project currentProject) {

        LocalDate currentDate = LocalDate.now();
        LocalDate projectStart = currentProject.getPlannedStartDate();
        LocalDate projectFinish = currentProject.getPlannedFinishDate();

        if ((currentDate.isAfter(projectStart) && currentDate.isBefore(projectFinish))
                || currentDate.isEqual(projectStart)) {
            currentProject.setProjectStatus(ProjectStatus.CURRENT);
        } else if ((currentDate.isAfter(projectStart) && currentDate.isAfter(projectFinish))
                || currentDate.isEqual(projectFinish)) {
            currentProject.setProjectStatus(ProjectStatus.FINISHED);
        }
        return currentProject;
    }

    public List<Project> getAll() {
        return this.projectRepository.findAll();
    }

    public Project create(Project project) {
        project.setCreatedAt(LocalDate.now());
        return this.projectRepository.save(project);
    }

    public Project getById(String id) throws ProjectNotRegisteredException {
        return projectStatusCheck(this.projectRepository.findById(id)
                .orElseThrow(ProjectNotRegisteredException::new));
    }

    public void deleteById(String id) throws ProjectNotRegisteredException {
        if (this.projectRepository.existsById(id)) {
            this.projectRepository.deleteById(id);
        } else {
            throw new ProjectNotRegisteredException();
        }
    }

    public Project update(String id, Project project) throws ProjectNotRegisteredException {
        project.setId(id);

        if (!this.projectRepository.existsById(id)) {
            throw new ProjectNotRegisteredException();
        }
        return this.projectRepository.save(project);
    }

    public List<Project> getAllByUserId(String id) throws UserNotRegisteredException {
        if (appUserRepository.existsById(id)) {
            return this.projectRepository
                    .findAllByCreatedBy(id)
                    .stream()
                    .map((this::projectStatusCheck))
                    .toList();
        }
        throw new UserNotRegisteredException();
    }
}
