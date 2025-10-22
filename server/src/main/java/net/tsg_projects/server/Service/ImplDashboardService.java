package net.tsg_projects.server.Service;

import lombok.Data;
import net.tsg_projects.server.Controller.Dashboard;
import net.tsg_projects.server.Dto.*;
import net.tsg_projects.server.Entity.Accumulator.Accumulator;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Plan.Plan;
import net.tsg_projects.server.MockDataFactory.MockDataFactory;
import net.tsg_projects.server.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ImplDashboardService implements DashboardService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private AccumulatorRepository accumulatorRepository;

    @Autowired
    MockDataFactory mockDataFactory;


    public DashboardDto dashboard(String email) {
        Member member = memberRepository.findByEmail(email);
        Enrollment currentEnrollment = member.getEnrollments().get(0);
        EnrollmentDto enrollmentDto = mockDataFactory.toDto(currentEnrollment);


        List<Accumulator> accumulators = currentEnrollment.getAccumulators();
        List<AccumulatorDto> accumulatorDtos = new ArrayList<>();
        accumulators.forEach(accumulator -> {
            AccumulatorDto accumulatorDto = new AccumulatorDto();
            accumulatorDto.setEnrollment(enrollmentDto);
            accumulatorDto.setTier(accumulator.getTier());
            accumulatorDto.setLimitAmount(accumulator.getLimitAmount());
            accumulatorDto.setType(accumulator.getType());
            accumulatorDto.setUsedAmount(accumulator.getUsedAmount());
            accumulatorDtos.add(accumulatorDto);
        });

        List<Claim> claims = claimRepository.getClaimsByMemberId(member.getId());
        List<ClaimDto> claimDtos = new ArrayList<>();
        claims.forEach(claim -> {
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
            claimDtos.add(claimDto);
        });



        Plan plan = currentEnrollment.getPlan();
        PlanDto planDto = new PlanDto();
        planDto.setName(plan.getName());
        planDto.setPlanYear(plan.getPlanYear());
        planDto.setNetworkName(plan.getNetworkName());
        planDto.setType(plan.getType());


        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setName(member.getFirstName());
        dashboardDto.setPlan(planDto);
        dashboardDto.setAccumulators(accumulatorDtos);
        dashboardDto.setClaims(claimDtos);
        return dashboardDto;
    }


}
