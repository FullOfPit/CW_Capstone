package com.example.backend.risk;

import com.example.backend.exception.ProjectNotRegisteredException;
import com.example.backend.exception.RiskNotRegisteredException;
import com.example.backend.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskRepository riskRepository;
    private final ProjectRepository projectRepository;

    public List<Risk> getAll() {
        return this.riskRepository.findAll();
    }

    public Risk create(Risk risk) {
        return this.riskRepository.save(risk);
    }

    public Risk getById(String id) throws RiskNotRegisteredException {
        return this.riskRepository.findById(id).orElseThrow(RiskNotRegisteredException::new);
    }

    public void deleteById(String id) throws RiskNotRegisteredException {
        if (!this.riskRepository.existsById(id)) {
            throw new RiskNotRegisteredException();
        }
        this.riskRepository.deleteById(id);
    }

    public Risk update(String id, Risk risk) throws RiskNotRegisteredException {
        risk.setId(id);

        if(!this.riskRepository.existsById(id)) {
            throw new RiskNotRegisteredException();
        }
        return this.riskRepository.save(risk);
    }

    public List<Risk> getAllByProjectId(String id) throws ProjectNotRegisteredException {
        if(projectRepository.existsById(id)) {
            return this.riskRepository.findAllByProjectId(id);
        }
        throw new ProjectNotRegisteredException();

    }
}
