package com.restateai.repository;

import com.restateai.model.AgentModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentModel, Long> {

    Optional<AgentModel> findByEmail(String email);
}
