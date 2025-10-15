package net.tsg_projects.server.Entity.Accumulator;

import jakarta.persistence.*;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Enums.AccumulatorType;
import net.tsg_projects.server.Enums.NetworkTier;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accumulator")
public class Accumulator {

    @Id
    @UuidGenerator
    private UUID Id;

    @Enumerated(EnumType.STRING)
    private AccumulatorType type;

    @Enumerated(EnumType.STRING)
    private NetworkTier tier;

    private BigDecimal limitAmount;
    private BigDecimal usedAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
}
