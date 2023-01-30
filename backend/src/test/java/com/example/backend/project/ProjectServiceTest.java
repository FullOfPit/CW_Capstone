package com.example.backend.project;

import com.example.backend.exception.ProjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ProjectServiceTest {

    Project testProject = new Project(
            "Test ID",
            "Test User",
            "Test Project ID",
            "Test Project Name",
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            ProjectStatus.CURRENT,
            "Test Assessor",
            "Test Details");

    @Test
    void getAll_returnsEmptyListWhenNoProjectRegistered() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        //When
        ProjectService projectService = new ProjectService(projectRepository);
        List<Project> actual =  projectService.getAll();
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
        verify(projectRepository).findAll();
    }

    @Test
    void getAll_returnsListWithProjectWhenProjectRegistered() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findAll()).thenReturn(List.of(testProject));
        //When
        ProjectService projectService = new ProjectService(projectRepository);
        List<Project> actual = projectService.getAll();
        //Then
        Assertions.assertEquals(List.of(testProject), actual);
        verify(projectRepository).findAll();
    }

    @Test
    void create_ReturnsProjectWithDateFilled() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.save(testProject)).thenReturn(testProject);
        //When
        ProjectService projectService = new ProjectService(projectRepository);
        Project actual = projectService.create(testProject);
        //Then
        Assertions.assertEquals(
                new Project(
                "Test ID",
                "Test User",
                "Test Project ID",
                "Test Project Name",
                LocalDate.now(),
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Test Details"), actual);

        verify(projectRepository).save(new Project(
                "Test ID",
                "Test User",
                "Test Project ID",
                "Test Project Name",
                LocalDate.now(),
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Test Details"));
    }

    @Test
    void getById_ReturnsCorrectProject() throws Exception {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));
        //When
        ProjectService projectService = new ProjectService(projectRepository);
        Project actual = projectService.getById("Test ID");
        //Then
        Assertions.assertEquals(testProject, actual);
        verify(projectRepository).findById("Test ID");
    }

    @Test
    void getById_ThrowsExceptionWhenIdNotRegistered() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.empty());
        //When
        ProjectService projectService = new ProjectService(projectRepository);
        //Then
        Assertions.assertThrows(ProjectNotFoundException.class, () -> projectService.getById("Test ID"));
    }

}