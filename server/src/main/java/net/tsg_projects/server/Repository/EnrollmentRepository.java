package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    boolean existsByMemberIdAndActiveTrue(UUID id);
}
