package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Entity.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    List<Claim> getClaimsByMemberId(UUID memberId);
}
