package com.restateai.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restateai.model.lead.Comment;
import java.util.List;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class CommentsDTO {
    List<Comment> comments;
}
