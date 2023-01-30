package com.example.backend.risk;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRepository extends MongoRepository<Risk, String> {
    List<Risk> findAllByProjectId(String id);
}
