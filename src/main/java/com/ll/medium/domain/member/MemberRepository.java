package com.ll.medium.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByusername(String username);

    boolean existsByUsernameOrEmail(String username, String email);
}
