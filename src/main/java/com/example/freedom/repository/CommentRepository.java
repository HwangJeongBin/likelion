package com.example.freedom.repository;

import com.example.freedom.domain.Comment;
import com.example.freedom.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
