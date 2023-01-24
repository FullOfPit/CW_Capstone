package com.example.backend.risk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class RiskServiceTest {

    //                          -- Auxiliary

    private Risk generateTestRisk () {
        return new Risk(
                "1",
                "1",
                "risk",
                "description",
                List.of("measure 1", "measure 2"),
                1,
                1,
                1
        );
    }

    @Test
    void getAll_returnsEmptyListWhenRepoEmpty() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        //When
        RiskService riskService = new RiskService(riskRepository);
        List<Risk> actual = riskService.getAll();
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
        verify(riskRepository).findAll();
    }

    @Test
    void getAll_returnsListOfRisksWhenRepoFilled () {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        when(riskRepository.findAll()).thenReturn(List.of(new Risk(), new Risk(), new Risk()));
        //When
        RiskService riskService = new RiskService(riskRepository);
        List<Risk> actual = riskService.getAll();
        //Then
        Assertions.assertEquals(List.of(new Risk(), new Risk(), new Risk()), actual);
        verify(riskRepository).findAll();
    }

    @Test
    void create_returnsRiskWhenCreated() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        when(riskRepository.save(generateTestRisk())).thenReturn(generateTestRisk());
        //When
        RiskService riskService = new RiskService(riskRepository);
        Risk actual = riskService.create(generateTestRisk());
        //Then
        Assertions.assertEquals(generateTestRisk(), actual);
        verify(riskRepository).save(generateTestRisk());

    }

}