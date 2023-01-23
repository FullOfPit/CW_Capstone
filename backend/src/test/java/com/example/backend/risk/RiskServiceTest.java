package com.example.backend.risk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class RiskServiceTest {

    @Test
    void getAll_returnsEmptyListWhenRepoEmpty() {
        //Given
        RiskRepository riskRepository = mock(RiskRepository.class);
        //When
        RiskService riskService = new RiskService(riskRepository);
        List<Risk> actual = riskService.getAll();
        //Then
        Assertions.assertEquals(new ArrayList<>(), actual);
    }

}