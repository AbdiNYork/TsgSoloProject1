package net.tsg_projects.server.Dto;

import jakarta.persistence.*;
import lombok.Data;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Provider.Provider;
import net.tsg_projects.server.Enums.ClaimStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ClaimDto {
    private String providerName;
    private String claimNumber;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private LocalDate receivedDate;
    private String status;

    private BigDecimal totalBilled;
    private BigDecimal totalAllowed;
    private BigDecimal totalPlanPaid;
    private BigDecimal totalMemberResponsibility;
}