package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.Provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
}
