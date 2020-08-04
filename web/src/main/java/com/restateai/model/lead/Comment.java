package com.restateai.model.lead;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.restateai.model.DataAccessObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Entity
@Table(name = "comments")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(NON_NULL)
public class Comment extends DataAccessObject {

    @Column(name = "comment_content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "leadId", referencedColumnName = "id")
    @JsonIgnore
    private Lead lead;

    public Long getLeadId() {
        return lead.getId();
    }
}