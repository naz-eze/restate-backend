package com.restateai.controller;

import com.restateai.model.Agent;
import com.restateai.service.AgentsService;
import java.security.Principal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@Log4j2
public class AgentsController {

    @Autowired
    private AgentsService agentsService;

    @GetMapping("/api/agent")
    public Agent getAgentDetails(Principal principal) {
        return agentsService.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Agent not found"));
    }

    @PutMapping("/api/agent")
    public Agent updateAgentDeviceToken(Principal principal,
                                        @RequestParam(value = "device-id") String deviceId) {
        log.debug("Setting device-id: {}, agent: {}", deviceId, principal.getName());

        return agentsService.updateAgent(principal.getName(), deviceId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Agent not found"));
    }
}
