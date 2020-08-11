package com.restateai.controller;

import com.restateai.model.Agent;
import com.restateai.service.AgentsService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class AgentsController {

    @Autowired
    private AgentsService agentsService;

    @GetMapping("/api/agent")
    public Agent getAgentDetails(Principal principal) {
        return agentsService.findByEmail("naz@restateai.com")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Agent not found"));
    }
}
