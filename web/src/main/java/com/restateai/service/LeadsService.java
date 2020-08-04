package com.restateai.service;

import com.restateai.model.AgentModel;
import com.restateai.model.lead.Lead;
import com.restateai.repository.LeadsRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class LeadsService {

    private final LeadsRepository leadsRepository;

    public LeadsService(LeadsRepository leadsRepository) {
        this.leadsRepository = leadsRepository;
    }

    public List<Lead> findByAgent(AgentModel agent) {
        return leadsRepository.findByAgent(agent);
    }

    public Optional<Lead> findById(Long leadId) {
        return leadsRepository.findById(leadId);
    }

    @Transactional
    public Lead updateLead(Long leadId, Lead lead) {
        Optional<Lead> maybeLead = leadsRepository.findById(leadId);

        return maybeLead.map(foundLead -> {
            lead.setAgent(foundLead.getAgent());
            log.info("Updating lead details: {}",lead);
            return leadsRepository.save(lead);
        }).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
    }
}
