package net.tsg_projects.server.Entity.Plan;

import jakarta.persistence.*;
import net.tsg_projects.server.Enums.PlanType;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    private UUID Id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlanType type;

    private String networkName;

    private Integer planYear;
}
