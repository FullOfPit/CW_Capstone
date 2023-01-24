package com.example.backend.risk;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskRepository riskRepository;

    public List<Risk> getAll() {
        return this.riskRepository.findAll();
    }

    public Risk create(Risk risk) {
        return this.riskRepository.save(risk);
    }
}
