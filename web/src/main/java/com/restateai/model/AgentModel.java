package com.restateai.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "agents", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@EqualsAndHashCode(callSuper = false)
public class AgentModel extends DataAccessObject {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private String deviceId;

    @Column
    private String phoneNumber;

    @Column
    private boolean isEmailVerified = false;
}
