package com.restateai.model.lead;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.restateai.model.Agent;
import com.restateai.model.DataAccessObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.restateai.model.lead.LeadStatus.NEW_LEAD;
import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "leads", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phoneNumber")
})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(NON_NULL)
public class Lead extends DataAccessObject {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Enumerated(STRING)
    @Column(nullable = false)
    private LeadStatus leadStatus = NEW_LEAD;

    @Enumerated(STRING)
    @Column(nullable = false)
    private LeadType leadType;

    private String motivation;

    private String propertyType;

    private Integer yearsInProperty;

    @ManyToOne
    @JoinColumn(name = "agentId", referencedColumnName = "id")
    @JsonIgnore
    private Agent agent;
}
