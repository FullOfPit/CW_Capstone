package com.example.backend.risk;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risks")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @GetMapping
    List<Risk> getAll() {
        return this.riskService.getAll();
    }

    @PostMapping
    Risk create(@RequestBody Risk risk) {
        return this.riskService.create(risk);
    }
}
