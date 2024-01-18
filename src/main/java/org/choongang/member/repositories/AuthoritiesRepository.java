package org.choongang.member.repositories;

import org.choongang.member.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    Optional<List<Authorities>> findByMemberSeq(Long seq);
}
