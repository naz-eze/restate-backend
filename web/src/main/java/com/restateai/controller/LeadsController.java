package com.restateai.controller;

import com.restateai.dto.CommentsDTO;
import com.restateai.dto.LeadsDTO;
import com.restateai.model.lead.Comment;
import com.restateai.model.lead.Lead;
import com.restateai.service.AgentsService;
import com.restateai.service.CommentService;
import com.restateai.service.LeadsService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@Log4j2
public class LeadsController {

    @Autowired
    private LeadsService leadsService;

    @Autowired
    private AgentsService agentsService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/leads")
    public LeadsDTO getAllLeads(Principal principal) {
        log.info("Fetching leads for agent. email: {}", principal.getName());
        List<Lead> leads = agentsService.findByEmail(principal.getName())
                .map(agent -> leadsService.findByAgent(agent))
                .orElse(emptyList());
        LeadsDTO leadsDTO = new LeadsDTO();
        leadsDTO.setLeads(new ArrayList<>(leads));
        return leadsDTO;
    }

    @PostMapping("/admin/leads")
    public Lead saveLead(@RequestParam(value = "agent-email", required = false) String agentEmail,
                         @RequestBody Lead lead) {

        return leadsService.saveLead(lead, agentEmail);
    }

    @PutMapping("/admin/leads/{leadId}")
    public Lead assignLead(@PathVariable("leadId") Long leadId,
                           @RequestParam("agent-email") String agentEmail) {
        return agentsService.findByEmail(agentEmail)
                .map(agent -> leadsService.assignLead(leadId, agent))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Lead or agent not found"));
    }

    @PutMapping("/api/leads/{leadId}")
    public Lead updateLead(@PathVariable("leadId") Long leadId, @RequestBody Lead lead) {
        return leadsService.updateLead(leadId, lead);
    }

    @GetMapping("/api/leads/{leadId}/comments")
    public CommentsDTO getAllComments(@PathVariable("leadId") Long leadId) {
        List<Comment> comments = commentService.getAllComments(leadId);
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setComments(comments);
        return commentsDTO;
    }

    @PostMapping("/api/leads/{leadId}/comments")
    public Comment saveComment(@PathVariable("leadId") Long leadId, @RequestBody String commentContent) {
        return leadsService.findById(leadId).map(lead -> {
            Comment comment = new Comment();
            comment.setContent(commentContent.replaceAll("\"", ""));
            lead.setUpdatedTime(comment.getUpdatedTime());
            comment.setLead(lead);
            return commentService.saveComment(comment);
        }).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to save comment"));
    }
}
