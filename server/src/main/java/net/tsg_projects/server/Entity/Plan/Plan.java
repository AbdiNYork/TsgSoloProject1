package net.tsg_projects.server.Entity.Plan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tsg_projects.server.Enums.PlanType;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "plan")
@NoArgsConstructor
@Data
public class Plan {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlanType type;

    private String networkName;

    private Integer planYear;
}
