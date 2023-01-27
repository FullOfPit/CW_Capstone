package com.example.backend.project;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    LocalDateTime testTime = LocalDateTime.of(
            2023,
            1,
            1,
            0,
            0,
            0);
    Project testProject = new Project("Test Project ID",
            "Test Project Name",
            testTime,
            testTime,
            testTime,
            ProjectStatus.CURRENT,
            "Test Assessor",
            "Test Details");

}