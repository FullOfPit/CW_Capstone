package com.example.backend.risk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Risk {
    @Id
    private String id;
    @NotNull
    private String projectId;
    @NotBlank(message = "Risk Factors must be named!")
    private String riskName;
    @NotBlank
    private String riskDescription;
    @NotBlank
    private String riskReductionMeasures;
    @Min(1)
    @Max(4)
    private int healthHazard;
    @Min(1)
    @Max(4)
    private int probability;
    @Min(1)
    @Max(4)
    private int frequency;
}
