package com.restateai.controller;

import com.restateai.dto.LeadsDTO;
import com.restateai.model.lead.LeadModel;
import com.restateai.service.AgentsService;
import com.restateai.service.LeadsService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.emptyList;

@RestController
public class LeadsController {

    @Autowired
    private LeadsService leadsService;

    @Autowired
    private AgentsService agentsService;

    @GetMapping("/api/leads")
    public LeadsDTO getAllLeads(Principal principal) {
        List<LeadModel> leads = agentsService.findByEmail(principal.getName())
                .map(agent -> leadsService.findByAgent(agent))
                .orElse(emptyList());
        LeadsDTO leadsDTO = new LeadsDTO();
        leadsDTO.setLeads(new ArrayList<>(leads));
        return leadsDTO;
    }
}
