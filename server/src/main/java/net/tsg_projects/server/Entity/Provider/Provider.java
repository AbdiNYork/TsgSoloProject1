package net.tsg_projects.server.Entity.Provider;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import net.tsg_projects.server.Entity.Address.Address;

import java.util.UUID;

@Entity
@Table(name = "table")
public class Provider {
    @Id
    private UUID Id;
    private String name;
    private String specialty;

    @Embedded
    private Address address;

    private String phone;
}
