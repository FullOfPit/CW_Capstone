package com.example.backend.risk;

import com.example.backend.exception.ProjectNotRegisteredException;
import com.example.backend.exception.RiskNotRegisteredException;
import com.example.backend.project.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.*;

class RiskServiceTest {

    //                          -- Auxiliary
    Risk testRisk = new Risk(
                "Test ID",
                "Test Project ID",
                "Test Risk",
                "Test Description",
                "Test Measure 1",
                1,
                1,
                1);

    Risk alternativeTestRisk = new Risk(
            "Test ID",
            "Test Project ID",
            "Test Risk",
            "Alternative Test Description",
            "Test Measure 1",
            1,
            1,
            1);

    @Test
    void getAll_returnsEmptyListWhenRepoEmpty() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        List<Risk> actual = riskService.getAll();
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
        verify(riskRepository).findAll();
    }

    @Test
    void getAll_returnsListOfRisksWhenRepoFilled () {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.findAll()).thenReturn(List.of(new Risk(), new Risk(), new Risk()));
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        List<Risk> actual = riskService.getAll();
        //Then
        Assertions.assertEquals(List.of(new Risk(), new Risk(), new Risk()), actual);
        verify(riskRepository).findAll();
    }

    @Test
    void create_returnsRiskWhenCreated() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.save(testRisk)).thenReturn(testRisk);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        Risk actual = riskService.create(testRisk);
        //Then
        Assertions.assertEquals(testRisk, actual);
        verify(riskRepository).save(testRisk);
    }

    @Test
    void getById_returnRiskWhenRegistered() throws RiskNotRegisteredException {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.findById("Test ID")).thenReturn(Optional.of(testRisk));
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        Risk actual = riskService.getById("Test ID");
        //Then
        Assertions.assertEquals(testRisk, actual);
    }

    @Test
    void getById_throwsExceptionWhenRiskNotRegistered() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.findById("Test ID")).thenReturn(Optional.empty());
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        //Then
        Assertions.assertThrows(RiskNotRegisteredException.class, () -> riskService.getById("Test ID"));
    }

    @Test
    void deleteById_correctlyDeletesProject() throws RiskNotRegisteredException {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.existsById("Test ID")).thenReturn(true);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        riskService.deleteById("Test ID");
        //Then
        verify(riskRepository).deleteById("Test ID");
    }

    @Test
    void deleteById_throwsExceptionWhenRiskNotRegistered() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.existsById("Test ID")).thenReturn(false);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        //Then
        Assertions.assertThrows(RiskNotRegisteredException.class, () -> riskService.deleteById("Test ID"));
    }

    @Test
    void update_correctlyReturnsRiskWhenRiskRegistered() throws RiskNotRegisteredException {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.existsById("Test ID")).thenReturn(true);
        when(riskRepository.save(testRisk)).thenReturn(testRisk);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        Risk actual = riskService.update("Test ID", testRisk);
        //Then
        Assertions.assertEquals(testRisk, actual);
        verify(riskRepository).existsById("Test ID");
        verify(riskRepository).save(testRisk);
    }
    
    @Test
    void update_correctlyUpdatesRiskWhenRiskRegistered() throws RiskNotRegisteredException {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.existsById("Test ID")).thenReturn(true);
        when(riskRepository.save(any(Risk.class))).then(returnsFirstArg());
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        riskService.create(testRisk);
        Risk actual = riskService.update("Test ID", alternativeTestRisk);
        //Then
        Assertions.assertEquals(alternativeTestRisk, actual);
        verify(riskRepository).existsById("Test ID");
        verify(riskRepository).save(alternativeTestRisk);
    }

    @Test
    void update_ReturnsExceptionWhenIDNotRegistered() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(riskRepository.existsById("Test ID")).thenReturn(false);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        //Then
        Assertions.assertThrows(RiskNotRegisteredException.class, () -> riskService.update("Test ID", testRisk));
    }

    @Test
    void getAllByProjectId_returnsEmptyListWhenProjectRegisteredButNoRisksRegistered() throws ProjectNotRegisteredException {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.existsById("Test Project ID")).thenReturn(true);
        when(riskRepository.findAllByProjectId("Test Project ID")).thenReturn(new ArrayList<>());
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        List<Risk> actual = riskService.getAllByProjectId("Test Project ID");
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
        verify(projectRepository).existsById("Test Project ID");
        verify(riskRepository).findAllByProjectId("Test Project ID");
    }

    @Test
    void getAllByProjectID_returnsRiskWhenProjectRegisteredAndRisksRegistered() throws ProjectNotRegisteredException {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.existsById("Test Project ID")).thenReturn(true);
        when(riskRepository.findAllByProjectId("Test Project ID")).thenReturn(List.of(testRisk));
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        List<Risk> actual = riskService.getAllByProjectId("Test Project ID");
        //Then
        Assertions.assertEquals(List.of(testRisk), actual);
        verify(projectRepository).existsById("Test Project ID");
        verify(riskRepository).findAllByProjectId("Test Project ID");
    }

    @Test
    void getAllByUserId_throwsExceptionWhenProjectNotRegistered() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.existsById("Test Project ID")).thenReturn(false);
        //When
        RiskService riskService = new RiskService(riskRepository, projectRepository);
        //Then
        Assertions.assertThrows(ProjectNotRegisteredException.class, () -> riskService.getAllByProjectId("Test Project ID"));
    }



}