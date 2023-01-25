package com.example.backend.risk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Risk {
    @Id
    private String id;
    private String projectId;

    private String riskName;
    private String riskDescription;
    private List<String> riskReductionMeasures;

    private HealthHazard healthHazard;
    private Probability probability;
    private Frequency frequency;


}
