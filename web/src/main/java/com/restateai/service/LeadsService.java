package com.restateai.service;

import com.restateai.model.AgentModel;
import com.restateai.model.lead.LeadModel;
import com.restateai.repository.LeadsRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LeadsService {

    private final LeadsRepository leadsRepository;

    public LeadsService(LeadsRepository leadsRepository) {
        this.leadsRepository = leadsRepository;
    }

    public List<LeadModel> findByAgent(AgentModel agent) {
        return leadsRepository.findByAgent(agent);
    }
}
