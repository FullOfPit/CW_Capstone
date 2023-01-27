package com.example.backend.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String id;
    private String projectName;
    private LocalDateTime createdAt;
    private LocalDateTime plannedStartDate;
    private LocalDateTime plannedFinishDate;
    private ProjectStatus projectStatus;
    private String assessorName;
    private String projectDetails;
}
