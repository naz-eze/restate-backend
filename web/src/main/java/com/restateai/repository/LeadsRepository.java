package com.restateai.repository;

import com.restateai.model.lead.Lead;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadsRepository extends JpaRepository<Lead, Long> {

    @Query(value = "SELECT * FROM leads l WHERE l.agent_id = ?1 ORDER BY updated_time DESC;", nativeQuery = true)
    List<Lead> findByAgent(Long agentId);
}
