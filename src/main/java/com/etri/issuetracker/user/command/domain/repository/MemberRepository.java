package com.etri.issuetracker.user.command.domain.repository;

import com.etri.issuetracker.user.command.domain.aggregate.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUid(String uid);
}
