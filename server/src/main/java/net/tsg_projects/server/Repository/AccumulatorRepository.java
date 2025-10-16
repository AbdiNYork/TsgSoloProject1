package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Accumulator.Accumulator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccumulatorRepository extends JpaRepository<Accumulator, UUID> {
}
