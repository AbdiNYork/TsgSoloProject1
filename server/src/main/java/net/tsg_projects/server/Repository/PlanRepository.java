package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
    Plan findByName(String goldPpo);
}
