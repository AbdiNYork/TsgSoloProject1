package net.tsg_projects.server.Entity.Accumulator;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Enums.AccumulatorType;
import net.tsg_projects.server.Enums.NetworkTier;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Accumulator {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;


    @Enumerated(EnumType.STRING)
    private AccumulatorType type;

    @Enumerated(EnumType.STRING)
    private NetworkTier tier;

    private BigDecimal limitAmount;
    private BigDecimal usedAmount;
}

