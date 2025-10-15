package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID>{

    Member findByEmail(String email);
}
