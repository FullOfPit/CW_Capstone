package com.example.backend.project;

import com.example.backend.appuser.AppUserRepository;
import com.example.backend.exception.ProjectNotRegisteredException;
import com.example.backend.exception.UserNotRegisteredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    Project testProject = new Project(
            "Test ID",
            "Test User ID",
            "Test Project ID",
            "Test Project Name",
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            ProjectStatus.CURRENT,
            "Test Assessor",
            "Test Details",
            List.of("Test Document ID"));

    Project alternativeTestProject = new Project(
            "Test ID 2",
            "Test User ID",
            "Test Project ID",
            "Test Project Name",
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            LocalDate.of(1, 1, 1),
            ProjectStatus.CURRENT,
            "Test Assessor",
            "Test Details",
            List.of("Test Document ID"));

    @Test
    void getAll_returnsEmptyListWhenNoProjectRegistered() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        List<Project> actual =  projectService.getAll();
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
        verify(projectRepository).findAll();
    }

    @Test
    void getAll_returnsListWithProjectWhenProjectRegistered() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findAll()).thenReturn(List.of(testProject));
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        List<Project> actual = projectService.getAll();
        //Then
        Assertions.assertEquals(List.of(testProject), actual);
        verify(projectRepository).findAll();
    }

    @Test
    void create_ReturnsProjectWithDateFilled() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.save(testProject)).thenReturn(testProject);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.create(testProject);
        //Then
        Assertions.assertEquals(
                new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.now(),
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"))
                , actual);

        verify(projectRepository).save(new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.now(),
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                ProjectStatus.CURRENT,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID")));
    }

    @Test
    void getById_ReturnsCorrectProject() throws Exception {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.getById("Test ID");
        //Then
        Assertions.assertEquals(testProject, actual);
        verify(projectRepository).findById("Test ID");
    }

    @Test
    void getById_ThrowsExceptionWhenIdNotRegistered() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.empty());
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        //Then
        Assertions.assertThrows(ProjectNotRegisteredException.class, () -> projectService.getById("Test ID"));
    }

    @Test
    void deleteById_correctlyDeletesProject() throws ProjectNotRegisteredException {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.existsById("Test ID")).thenReturn(true);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        projectService.deleteById("Test ID");
        //Then
        verify(projectRepository).deleteById("Test ID");
    }

    @Test
    void deleteById_throwsProjectNotRegisteredException() {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.existsById("Test ID")).thenReturn(false);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        //Then
        Assertions.assertThrows(ProjectNotRegisteredException.class, () -> projectService.deleteById("Test ID"));
    }

    @Test
    void update_correctlyReturnsProjectWhenProjectRegistered() throws ProjectNotRegisteredException {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.existsById("Test ID")).thenReturn(true);
        when(projectRepository.save(testProject)).thenReturn(testProject);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.update("Test ID", testProject);
        //Then
        Assertions.assertEquals(testProject, actual);
        verify(projectRepository).existsById("Test ID");
        verify(projectRepository).save(testProject);
    }

    @Test
    void update_correctlyUpdatesProjectWhenProjectRegistered() throws ProjectNotRegisteredException {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.existsById("Test ID")).thenReturn(true);
        when(projectRepository.save(any())).then(returnsFirstArg());
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        projectService.create(testProject);
        Project actual = projectService.update("Test ID", alternativeTestProject);
        //Then
        Assertions.assertEquals(alternativeTestProject, actual);
        verify(projectRepository).existsById("Test ID");
        verify(projectRepository).save(alternativeTestProject);
    }

    @Test
    void update_ReturnsExceptionWhenIDNotRegistered(){
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.existsById("Test ID")).thenReturn(false);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        //Then
        Assertions.assertThrows(ProjectNotRegisteredException.class, () -> projectService.update("Test ID", testProject));
    }

    @Test
    void getAllByUserId_returnsEmptyListWhenUserCorrectButNoProjectsRegistered() throws UserNotRegisteredException {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(appUserRepository.existsById("Test User ID")).thenReturn(true);
        when(projectRepository.findAllByCreatedBy("Test User ID")).thenReturn(new ArrayList<>());
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        List<Project> actual = projectService.getAllByUserId("Test User ID");
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
        verify(appUserRepository).existsById("Test User ID");
        verify(projectRepository).findAllByCreatedBy("Test User ID");
    }

    @Test
    void getAllByUserId_returnsProjectWhenUserCorrectAndProjectRegistered() throws UserNotRegisteredException {
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(appUserRepository.existsById("Test User ID")).thenReturn(true);
        when(projectRepository.findAllByCreatedBy("Test User ID")).thenReturn(List.of(testProject));
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        List<Project> actual = projectService.getAllByUserId("Test User ID");
        //Then
        Assertions.assertEquals(List.of(testProject), actual);
        verify(appUserRepository).existsById("Test User ID");
        verify(projectRepository).findAllByCreatedBy("Test User ID");
    }

    @Test
    void getAllByUserId_throwsExceptionWhenUserNotRegistered(){
        //Given
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(appUserRepository.existsById("Test User ID")).thenReturn(false);
        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        //Then
        Assertions.assertThrows(UserNotRegisteredException.class, () -> projectService.getAllByUserId("Test User ID"));
        verify(appUserRepository).existsById("Test User ID");
    }

    @Test
    void projectStatusCheck_StatusPlannedWhenStartDateAfterCurrentDate() throws ProjectNotRegisteredException {
        //Given
        Project testProject = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of(9999, 1, 1),
                LocalDate.of(9999, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));

        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.getById("Test ID");

        //Then
        Assertions.assertEquals(ProjectStatus.PLANNED, actual.getProjectStatus());
    }

    @Test
    void projectStatusCheck_StatusCurrentWhenCurrentDateBetweenStartAndFinishDate() throws ProjectNotRegisteredException {
        //Given
        Project testProject = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of( 1, 1, 1),
                LocalDate.of(9999, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));

        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.getById("Test ID");

        //Then
        Assertions.assertEquals(ProjectStatus.CURRENT, actual.getProjectStatus());
    }

    @Test
    void projectStatusCheck_StatusCurrentWhenCurrentDateIsStartDate() throws ProjectNotRegisteredException {
        //Given
        Project testProject = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.now(),
                LocalDate.of(9999, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));

        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.getById("Test ID");

        //Then
        Assertions.assertEquals(ProjectStatus.CURRENT, actual.getProjectStatus());
    }

    @Test
    void projectStatusCheck_StatusFinishedWhenFinishDateBeforeCurrentDate() throws ProjectNotRegisteredException {
        //Given
        Project testProject = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));

        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.getById("Test ID");

        //Then
        Assertions.assertEquals(ProjectStatus.FINISHED, actual.getProjectStatus());
    }

    @Test
    void projectStatusCheck_StatusFinishedWhenFinishDateIsCurrentDate() throws ProjectNotRegisteredException {
        //Given
        Project testProject = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                LocalDate.now(),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(projectRepository.findById("Test ID")).thenReturn(Optional.of(testProject));

        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        Project actual = projectService.getById("Test ID");

        //Then
        Assertions.assertEquals(ProjectStatus.FINISHED, actual.getProjectStatus());
    }

    @Test
    void projectStatusCheck_WhenSeveralProjectsRequestedThroughGetAllByUserID_ReturnsProjectStatusesCorrectly()
            throws UserNotRegisteredException {
        //Given
        Project testProjectOne = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of(9999, 1, 1),
                LocalDate.of(9999, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        Project testProjectTwo = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                LocalDate.of(9999, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        Project testProjectThree = new Project(
                "Test ID",
                "Test User ID",
                "Test Project ID",
                "Test Project Name",
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                LocalDate.of(1, 1, 1),
                ProjectStatus.PLANNED,
                "Test Assessor",
                "Test Details",
                List.of("Test Document ID"));

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        when(appUserRepository.existsById("Test User ID")).thenReturn(true);
        when(projectRepository.findAllByCreatedBy("Test User ID"))
                .thenReturn(List.of(testProjectOne, testProjectTwo, testProjectThree));

        //When
        ProjectService projectService = new ProjectService(projectRepository, appUserRepository);
        List<Project> actual = projectService.getAllByUserId("Test User ID");

        //Then
        Assertions.assertEquals(ProjectStatus.PLANNED, actual.get(0).getProjectStatus());
        Assertions.assertEquals(ProjectStatus.CURRENT, actual.get(1).getProjectStatus());
        Assertions.assertEquals(ProjectStatus.FINISHED, actual.get(2).getProjectStatus());
    }
}