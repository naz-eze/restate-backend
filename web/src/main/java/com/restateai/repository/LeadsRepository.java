package com.restateai.repository;

import com.restateai.model.AgentModel;
import com.restateai.model.lead.Lead;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadsRepository extends JpaRepository<Lead, Long> {

    List<Lead> findByAgent(AgentModel agent);
}
