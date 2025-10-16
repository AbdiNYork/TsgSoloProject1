package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Claim.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {
}
