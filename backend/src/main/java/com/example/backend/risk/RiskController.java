package com.example.backend.risk;

import com.example.backend.exception.ProjectNotRegisteredException;
import com.example.backend.exception.RiskNotRegisteredException;
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

    @GetMapping("/{id}")
    Risk getById(@PathVariable String id) throws RiskNotRegisteredException {
        return this.riskService.getById(id);
    }

    @GetMapping("/projects/{id}")
    List<Risk> getAllByProjectId(@PathVariable String id) throws ProjectNotRegisteredException {
        return this.riskService.getAllByProjectId(id);
    }

    @PutMapping("/{id}")
    Risk update(@PathVariable String id, @RequestBody Risk risk) throws RiskNotRegisteredException {
        return this.riskService.update(id, risk);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) throws RiskNotRegisteredException {
        this.riskService.deleteById(id);
    }
}
