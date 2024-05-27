package com.example.freedom.repository;

import com.example.freedom.domain.Member;
import com.example.freedom.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
}
