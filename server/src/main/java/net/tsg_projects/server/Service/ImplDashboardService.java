package net.tsg_projects.server.Service;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.tsg_projects.server.Dto.*;
import net.tsg_projects.server.Entity.Accumulator.Accumulator;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Plan.Plan;
import net.tsg_projects.server.MockDataFactory.MockDataFactory;
import net.tsg_projects.server.Repository.*;

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


        // Get the list of enrollments a member has
        List<Enrollment> enrollments = member.getEnrollments();
        // Instantiate our currentEnrollment object, leave this unassigned while we do validation step
        Enrollment currentEnrollment;

        // Check to see if the enrollments list is not empty
        // If it's not empty we can grab the first enrollment
        // Else call the createEnrollment method which returns a new enrollment for tied to member
        if(!enrollments.isEmpty()){
            currentEnrollment = enrollments.get(0);
        } else {
           currentEnrollment = mockDataFactory.createEnrollment(member);
        }

        // I can seperate out the dto mapping to a seperate class
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
        dashboardDto.setEmail(email);
        dashboardDto.setPlan(planDto);
        dashboardDto.setAccumulators(accumulatorDtos);
        dashboardDto.setClaims(claimDtos);
        return dashboardDto;
    }


}
