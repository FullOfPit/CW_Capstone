package com.example.backend.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String id;
    private String createdBy;
    private String projectId;
    private String projectName;
    private LocalDate createdAt;
    private LocalDate plannedStartDate;
    private LocalDate plannedFinishDate;
    private ProjectStatus projectStatus;
    private String assessorName;
    private String projectDetails;
}
