package com.ll.medium.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<SiteMember, Long> {
    Optional<SiteMember> findBymembername(String membername);
}
