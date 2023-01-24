package com.example.backend.risk;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRepository extends MongoRepository<Risk, String> {
}
