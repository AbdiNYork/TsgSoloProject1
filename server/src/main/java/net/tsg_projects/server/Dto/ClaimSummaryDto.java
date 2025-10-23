package net.tsg_projects.server.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.tsg_projects.server.Enums.ClaimStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClaimSummaryDto {
    private String claimNumber;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String providerName;
    private ClaimStatus status;
    private BigDecimal memberResponsibility;

}
