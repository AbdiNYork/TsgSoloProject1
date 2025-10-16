package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.ClaimLine.ClaimLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimLineRepository extends JpaRepository<ClaimLine, UUID> {
}
