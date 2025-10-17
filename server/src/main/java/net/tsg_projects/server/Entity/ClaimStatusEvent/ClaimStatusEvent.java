package net.tsg_projects.server.Entity.ClaimStatusEvent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Enums.ClaimStatus;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "claimstatusevent")
public class ClaimStatusEvent {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    @JsonBackReference
    private Claim claim;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private OffsetDateTime occurredAt;
    private String note;
}
