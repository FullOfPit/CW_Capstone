package com.example.backend.risk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Risk {
    @Id
    private String id;
    private String projectId;
    private String riskName;
    private String riskDescription;
    private String riskReductionMeasures;
    private int healthHazard;
    private int probability;
    private int frequency;
}
