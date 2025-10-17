package net.tsg_projects.server.Entity.Claim;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import net.tsg_projects.server.Entity.ClaimLine.ClaimLine;
import net.tsg_projects.server.Entity.ClaimStatusEvent.ClaimStatusEvent;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Provider.Provider;
import net.tsg_projects.server.Enums.ClaimStatus;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;

    private String claimNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private BigDecimal totalBilled;
    private BigDecimal totalAllowed;
    private BigDecimal totalPlanPaid;
    private BigDecimal totalMemberResponsibility;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ClaimLine> lines = new ArrayList<>();


    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ClaimStatusEvent> statusHistory = new ArrayList<>();

    public void setStatusHistory(ClaimStatusEvent status){
        this.statusHistory.add(status);
    }

    public void setLines(ClaimLine line) {
        this.lines.add(line);
    }



}
