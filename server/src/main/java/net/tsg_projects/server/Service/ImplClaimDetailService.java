package net.tsg_projects.server.Service;

import lombok.Data;
import net.tsg_projects.server.Dto.ClaimDto;
import net.tsg_projects.server.Dto.ClaimLineDto;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Repository.ClaimLineRepository;
import net.tsg_projects.server.Repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Data
@Service
public class ImplClaimDetailService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimLineRepository claimLineRepository;


    public ClaimDto getClaimDetail(String claimNumber) {
        Claim claim = claimRepository.getClaimByClaimNumber(claimNumber);

        ClaimDto claimDto = new ClaimDto();
        claimDto.setProviderName(claim.getProvider().getName());
        claimDto.setClaimNumber(claim.getClaimNumber());
        claimDto.setReceivedDate(claim.getReceivedDate());
        claimDto.setStatus(claim.getStatus().toString());
        claimDto.setServiceEndDate(claim.getServiceEndDate());
        claimDto.setServiceStartDate(claim.getServiceStartDate());
        claimDto.setTotalAllowed(claim.getTotalAllowed());
        claimDto.setTotalBilled(claim.getTotalBilled());
        claimDto.setTotalMemberResponsibility(claim.getTotalMemberResponsibility());
        claimDto.setTotalPlanPaid(claim.getTotalPlanPaid());

        claim.getLines().forEach(claimLine -> {
            ClaimLineDto claimLineDto = new ClaimLineDto();
            claimLineDto.setLineNumber(claimLine.getLineNumber());
            claimLineDto.setClaimNumber(claim.getClaimNumber());
            claimLineDto.setCptCode(claimLine.getCptCode());
            claimLineDto.setDescription(claimLine.getDescription());
            claimLineDto.setAllowedAmount(claimLine.getAllowedAmount());
            claimLineDto.setBilledAmount(claimLine.getBilledAmount());
            claimLineDto.setCopayApplied(claimLine.getCopayApplied());
            claimLineDto.setDeductibleApplied(claimLine.getDeductibleApplied());
            claimLineDto.setCoinsuranceApplied(claimLine.getCoinsuranceApplied());
            claimLineDto.setPlanPaid(claimLine.getPlanPaid());
            claimLineDto.setMemberResponsibility(claimLine.getMemberResponsibility());
            claimDto.getClaimLines().add(claimLineDto);
        });

        return claimDto;


    }
}
