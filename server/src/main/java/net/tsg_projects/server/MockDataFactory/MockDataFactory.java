package net.tsg_projects.server.MockDataFactory;

import net.tsg_projects.server.Dto.ClaimDto;
import net.tsg_projects.server.Dto.EnrollmentDto;
import net.tsg_projects.server.Dto.MemberDto;
import net.tsg_projects.server.Entity.Accumulator.Accumulator;
import net.tsg_projects.server.Entity.Address.Address;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Entity.ClaimLine.ClaimLine;
import net.tsg_projects.server.Entity.ClaimStatusEvent.ClaimStatusEvent;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Plan.Plan;
import net.tsg_projects.server.Entity.Provider.Provider;
import net.tsg_projects.server.Enums.AccumulatorType;
import net.tsg_projects.server.Enums.ClaimStatus;
import net.tsg_projects.server.Enums.NetworkTier;
import net.tsg_projects.server.Enums.PlanType;
import net.tsg_projects.server.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockDataFactory {

    private final ClaimRepository claimRepository;
    private final PlanRepository planRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AccumulatorRepository accumulatorRepository;
    private final MemberRepository memberRepository;
    private final ProviderRepository providerRepository;
    private final ClaimLineRepository claimLineRepository;

    @Autowired
    public MockDataFactory(
            ClaimRepository claimRepository,
            PlanRepository planRepository,
            EnrollmentRepository enrollmentRepository,
            AccumulatorRepository accumulatorRepository,
            MemberRepository memberRepository,
            ProviderRepository providerRepository, ClaimLineRepository claimLineRepository
    ) {
        this.claimRepository = claimRepository;
        this.planRepository = planRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.accumulatorRepository = accumulatorRepository;
        this.memberRepository = memberRepository;
        this.providerRepository = providerRepository;
        this.claimLineRepository = claimLineRepository;
    }

    public void createClaim(Member member, Provider provider) {
        for (int i = 0; i < 10; i++) {
            Claim claim = new Claim();
            claim.setMember(member);
            claim.setProvider(provider);
            claim.setServiceStartDate(LocalDate.now().minusDays(90));
            claim.setServiceEndDate(LocalDate.now().minusDays(88));
            claim.setReceivedDate(LocalDate.now().minusDays(85));
            claim.setStatus(ClaimStatus.IN_REVIEW);
            claim.setTotalBilled(new BigDecimal(175));
            claim.setTotalAllowed(new BigDecimal(300));
            claim.setTotalMemberResponsibility(new BigDecimal(50));
            claim.setTotalPlanPaid(new BigDecimal(100));

            // Initialize collections if not already done in constructor


            // Add a claim status event
            ClaimStatusEvent claimStatusEvent = new ClaimStatusEvent();
            claimStatusEvent.setOccurredAt(OffsetDateTime.now());
            claimStatusEvent.setStatus(ClaimStatus.IN_REVIEW);
            claimStatusEvent.setNote("Claim Status: " + ClaimStatus.IN_REVIEW);
            claimStatusEvent.setClaim(claim); // make sure the back-reference is set

            claim.getStatusHistory().add(claimStatusEvent);

            // Create lines and associate them
            createClaimLine(claim);

            // Save only once after everything is set
            claimRepository.save(claim);
        }
    }


    public void createClaimLine(Claim claim) {
        for (int i = 0; i < 3; i++) {
            ClaimLine claimLine = new ClaimLine();
            claimLine.setAllowedAmount(claim.getTotalAllowed());
            claimLine.setBilledAmount(claim.getTotalBilled());
            claimLine.setCoinsuranceApplied(BigDecimal.valueOf(50));
            claimLine.setPlanPaid(new BigDecimal(100));
            claimLine.setMemberResponsibility(BigDecimal.valueOf(50));
            claimLine.setDeductibleApplied(BigDecimal.valueOf(50));
            claimLine.setCopayApplied(BigDecimal.valueOf(50));
            claimLine.setLineNumber(i + 1);
            claimLine.setCptCode("#C-10" + (1 + i * 78));
            claimLine.setDescription("Hospital Visit: " + (i + 1));
            claimLine.setClaim(claim); // set owning side

            // Add to claim
            claim.getLines().add(claimLine);
        }
    }


    public Accumulator createAccumulator(Enrollment enrollment) {
//        Accumulator accumulator = new Accumulator();
//        accumulator.setType(AccumulatorType.DEDUCTIBLE);
//        accumulator.setTier(NetworkTier.IN_NETWORK);
//        accumulator.setLimitAmount(new BigDecimal(1500));
//        accumulator.setUsedAmount(new BigDecimal(600));
//        accumulator.setEnrollment(enrollment);
//        accumulatorRepository.save(accumulator);

        Accumulator accumulator2 = new Accumulator();
        accumulator2.setType(AccumulatorType.OOP_MAX);
        accumulator2.setTier(NetworkTier.OUT_OF_NETWORK);
        accumulator2.setLimitAmount(BigDecimal.valueOf(80));
        accumulator2.setUsedAmount(BigDecimal.valueOf(1200));
        accumulator2.setEnrollment(enrollment);
        accumulatorRepository.save(accumulator2);

        return accumulator2;
    }

    public Enrollment createEnrollment(Member member) {
        Enrollment enrollment = new Enrollment();
        enrollment.setMember(member);
        member.getEnrollments().add(enrollment);
        Plan plan = planRepository.findByName("Gold PPO");

        if (plan == null) {
            plan = createPlan(); // Create if not found
        }

        enrollment.setPlan(plan);
        enrollment.setCoverageStart(LocalDate.of(2025, 1,12 ));
        enrollment.setCoverageEnd(LocalDate.of(2025, 12,12 ));
        enrollment.setActive(true);
        enrollmentRepository.save(enrollment);
        memberRepository.save(member);

//        Accumulator accumulator = createAccumulator(enrollment);
//        enrollment.getAccumulators().add(accumulator);

//        enrollmentRepository.save(enrollment);
//        accumulatorRepository.save(accumulator); // Persist both sides

        return enrollment;
    }

    public Provider createProvider() {
        Provider provider = new Provider();
        provider.setName("Primary Care Provider");
        provider.setPhone("+1(800)-901-9098");

        Address address = new Address();
        address.setCity("New York");
        address.setState("NY");
        address.setPostalCode("87283");
        address.setLine1("123 Primary Road");

        provider.setAddress(address);
        provider.setSpecialty("Primary Care");

        providerRepository.save(provider);
        return provider;
    }

    public Plan createPlan() {
        Plan existing = planRepository.findByName("Gold PPO");
        if (existing != null) {
            return existing;
        }

        Plan plan = new Plan();
        plan.setName("Gold PPO");
        plan.setNetworkName("Prime");
        plan.setType(PlanType.PPO);
        planRepository.save(plan);
        return plan;
    }

    public MemberDto generate(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new IllegalArgumentException("Member not found with email: " + email);
        }

        boolean ActiveEnrollment = !member.getEnrollments().isEmpty();
        if (ActiveEnrollment) {
            EnrollmentDto enrollmentDto = toDto(member.getEnrollments().get(0));
            return toMemberDto(enrollmentDto, member);
        }

        System.out.println("No active enrollment — seeding mock data...");
        createPlan();
        Enrollment enrollment = createEnrollment(member);
        Accumulator acc = createAccumulator(enrollment);
        enrollment.setAccumulators(acc);
        enrollmentRepository.save(enrollment);
        Provider provider = createProvider();
        createClaim(member, provider);

        EnrollmentDto enrollmentDto = toDto(enrollment);



        return toMemberDto(enrollmentDto, member);
    }

    public EnrollmentDto toDto(Enrollment enrollment) {
        EnrollmentDto enrollmentDto = new EnrollmentDto();
        enrollmentDto.setActive(enrollment.isActive());
        enrollmentDto.setCoverageStart(enrollment.getCoverageStart());
        enrollmentDto.setCoverageEnd(enrollment.getCoverageEnd());
        enrollmentDto.setPlanName(enrollment.getPlan().getName());
        enrollmentDto.setMemberId(enrollment.getMember().getId());

        return enrollmentDto;
    }

    public MemberDto toMemberDto(EnrollmentDto enrollmentDto, Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEnrollment(enrollmentDto);
        memberDto.setEmail(member.getEmail());
        List<Claim> claims = claimRepository.getClaimsByMemberId(member.getId());
        List<ClaimDto> claimDtos = new ArrayList<>();
        claims.forEach(claim -> {
            ClaimDto claimDto = new ClaimDto();
            claimDto.setProviderName(claim.getProvider().getName());
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
        memberDto.setClaims(claimDtos);
        return memberDto;
    }
}
