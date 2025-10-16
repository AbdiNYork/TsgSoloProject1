package net.tsg_projects.server.Entity.ClaimLine;

import jakarta.persistence.*;
import lombok.Data;
import net.tsg_projects.server.Entity.Claim.Claim;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity

@Table(name = "claimline")
public class ClaimLine {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;

    // Fk to parent Claim
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    private Integer lineNumber;
    private String cptCode;
    private String description;

    private BigDecimal billedAmount;
    private BigDecimal allowedAmount;
    private BigDecimal deductibleApplied;
    private BigDecimal copayApplied;
    private BigDecimal coinsuranceApplied;
    private BigDecimal planPaid;
    private BigDecimal memberResponsibility;


}
