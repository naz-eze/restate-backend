package com.restateai.repository;

import com.restateai.model.lead.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository <Comment, Long> {

    @Override
    @Query(value = "SELECT * FROM comments ORDER BY created_time DESC;", nativeQuery = true)
    List<Comment> findAll();
}
