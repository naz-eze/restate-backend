package com.restateai.controller;

import com.restateai.dto.CommentsDTO;
import com.restateai.dto.LeadsDTO;
import com.restateai.model.lead.Comment;
import com.restateai.model.lead.LeadModel;
import com.restateai.service.AgentsService;
import com.restateai.service.CommentService;
import com.restateai.service.LeadsService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Collections.emptyList;

@RestController
public class LeadsController {

    @Autowired
    private LeadsService leadsService;

    @Autowired
    private AgentsService agentsService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/leads")
    public LeadsDTO getAllLeads(Principal principal) {
        List<LeadModel> leads = agentsService.findByEmail("naz@restateai.com")
                .map(agent -> leadsService.findByAgent(agent))
                .orElse(emptyList());
        LeadsDTO leadsDTO = new LeadsDTO();
        leadsDTO.setLeads(new ArrayList<>(leads));
        return leadsDTO;
    }

    @PutMapping("/api/leads/{leadId}")
    public LeadModel updateLead(@PathVariable("leadId") Long leadId, @RequestBody LeadModel leadModel) {
        return leadsService.updateLead(leadId, leadModel);
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
            comment.setLeadModel(lead);
            return commentService.saveComment(comment);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save comment"));
    }
}
