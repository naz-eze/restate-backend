package com.restateai.repository;

import com.restateai.model.AgentModel;
import com.restateai.model.lead.LeadModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadsRepository extends JpaRepository<LeadModel, Long> {

    List<LeadModel> findByAgent(AgentModel agent);
}
