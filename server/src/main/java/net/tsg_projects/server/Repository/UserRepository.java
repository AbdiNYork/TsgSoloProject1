package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByAuthProviderAndAuthSub(String authProvider, String authSub);
}
