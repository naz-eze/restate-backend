package com.restateai.service;

import com.restateai.model.AgentModel;
import com.restateai.repository.AgentRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AgentsService {

    private final AgentRepository agentRepository;

    public AgentsService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public Optional<AgentModel> findByEmail(String email) {
        return agentRepository.findByEmail(email);
    }
}