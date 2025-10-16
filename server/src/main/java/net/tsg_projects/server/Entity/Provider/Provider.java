package net.tsg_projects.server.Entity.Provider;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tsg_projects.server.Entity.Address.Address;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "table")
public class Provider {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;
    private String name;
    private String specialty;

    @Embedded
    private Address address;

    private String phone;
}
