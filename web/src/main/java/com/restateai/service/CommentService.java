package com.restateai.service;

import com.restateai.model.lead.Comment;
import com.restateai.repository.CommentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> getAllComments(Long leadId) {
        return repository.findAll()
                .stream()
                .filter(comment -> comment.getLeadId().equals(leadId))
                .collect(toList());
    }

    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }
}
