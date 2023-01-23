package com.example.backend.risk;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RiskService {

    private final RiskRepository riskRepository;

    public List<Risk> getAll() {
        return this.riskRepository.findAll();
    }
}
