package com.restateai.service;

import com.restateai.model.Agent;
import com.restateai.model.lead.Lead;
import com.restateai.repository.LeadsRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Log4j2
public class LeadsService {

    private final LeadsRepository leadsRepository;
    private final PushNotificationService notificationService;
    private final AgentsService agentsService;

    public LeadsService(LeadsRepository leadsRepository,
                        PushNotificationService notificationService,
                        AgentsService agentsService) {
        this.leadsRepository = leadsRepository;
        this.notificationService = notificationService;
        this.agentsService = agentsService;
    }

    public List<Lead> findByAgent(Agent agent) {
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
            log.info("Updating lead details: {}", lead);
            return leadsRepository.save(lead);
        }).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
    }

    @Transactional
    public Lead saveLead(Lead lead, String agentEmail) {

        return agentsService.findByEmail(agentEmail)
                .map(agent -> {
                    lead.setAgent(agent);
                    Lead savedLead = leadsRepository.save(lead);
                    notificationService.notify(agent.getDeviceId(), lead);
                    return savedLead;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Lead or agent not found"));
    }

    @Transactional
    public Lead assignLead(Long leadId, Agent agent) {
        Optional<Lead> maybeLead = leadsRepository.findById(leadId);

        return maybeLead.map(foundLead -> {
            foundLead.setAgent(agent);
            notificationService.notify(agent.getDeviceId(), foundLead);
            return leadsRepository.save(foundLead);
        }).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
    }
}
