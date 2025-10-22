package net.tsg_projects.server.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClaimLineDto {

    private String claimNumber;
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
