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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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
        Random random = new Random();
        List<ClaimStatus> possibleStatuses = List.of(
                ClaimStatus.PROCESSED,
                ClaimStatus.DENIED,
                ClaimStatus.PAID,
                ClaimStatus.IN_REVIEW
        );

        for (int i = 0; i < 10; i++) {
            Claim claim = new Claim();
            claim.setMember(member);
            claim.setProvider(provider);

            // Random claim number
            String claimNumber = "C-" + (10000 + random.nextInt(9999));
            claim.setClaimNumber(claimNumber);

            // Random date within last 90 days
            int daysAgo = random.nextInt(90);
            LocalDate startDate = LocalDate.now().minusDays(daysAgo);
            LocalDate endDate = startDate.plusDays(random.nextInt(5)); // Service lasted 0–5 days
            claim.setServiceStartDate(startDate);
            claim.setServiceEndDate(endDate);

            claim.setReceivedDate(LocalDate.now().minusDays(random.nextInt(100)));

            // Random status
            ClaimStatus status = possibleStatuses.get(random.nextInt(possibleStatuses.size()));
            claim.setStatus(status);

            // Random amounts (with logical consistency)
            BigDecimal billed = new BigDecimal(50 + random.nextInt(500));
            BigDecimal allowed = billed.multiply(BigDecimal.valueOf(0.7 + (0.3 * random.nextDouble()))).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal planPaid = allowed.multiply(BigDecimal.valueOf(0.5 + (0.5 * random.nextDouble()))).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal memberResp = allowed.subtract(planPaid).setScale(2, BigDecimal.ROUND_HALF_UP);

            claim.setTotalBilled(billed);
            claim.setTotalAllowed(allowed);
            claim.setTotalPlanPaid(planPaid);
            claim.setTotalMemberResponsibility(memberResp);

            // Status event
            ClaimStatusEvent statusEvent = new ClaimStatusEvent();
            statusEvent.setOccurredAt(OffsetDateTime.now().minusDays(random.nextInt(30)));
            statusEvent.setStatus(status);
            statusEvent.setNote("Status: " + status);
            statusEvent.setClaim(claim);

            claim.getStatusHistory().add(statusEvent);


            claimRepository.save(claim);
        }
    }



    public void createClaimLine() {
        List<Claim> claims = claimRepository.findAll();
        Random random = new Random();
        for (Claim claim: claims) {

            BigDecimal totalBilled = claim.getTotalBilled();
            BigDecimal totalAllowed = claim.getTotalAllowed();
            BigDecimal totalPlanPaid = claim.getTotalPlanPaid();
            BigDecimal totalMemberResp = claim.getTotalMemberResponsibility();

            BigDecimal billedSoFar = BigDecimal.ZERO;
            BigDecimal allowedSoFar = BigDecimal.ZERO;
            BigDecimal paidSoFar = BigDecimal.ZERO;
            BigDecimal memberRespSoFar = BigDecimal.ZERO;

            for (int i = 0; i < 2; i++) {
                ClaimLine line = new ClaimLine();
                boolean isLast = (i == 1);

                BigDecimal billed, allowed, paid, memberResp;

                if (!isLast) {
                    billed = totalBilled.multiply(BigDecimal.valueOf(0.4 + 0.1 * random.nextDouble())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    allowed = totalAllowed.multiply(BigDecimal.valueOf(0.4 + 0.1 * random.nextDouble())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    paid = totalPlanPaid.multiply(BigDecimal.valueOf(0.4 + 0.1 * random.nextDouble())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    memberResp = allowed.subtract(paid).max(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    billed = totalBilled.subtract(billedSoFar);
                    allowed = totalAllowed.subtract(allowedSoFar);
                    paid = totalPlanPaid.subtract(paidSoFar);
                    memberResp = totalMemberResp.subtract(memberRespSoFar);
                }

                billedSoFar = billedSoFar.add(billed);
                allowedSoFar = allowedSoFar.add(allowed);
                paidSoFar = paidSoFar.add(paid);
                memberRespSoFar = memberRespSoFar.add(memberResp);

                line.setBilledAmount(billed);
                line.setAllowedAmount(allowed);
                line.setPlanPaid(paid);
                line.setMemberResponsibility(memberResp);

                line.setDeductibleApplied(BigDecimal.valueOf(random.nextInt(50)));
                line.setCopayApplied(BigDecimal.valueOf(random.nextInt(30)));
                line.setCoinsuranceApplied(BigDecimal.valueOf(random.nextInt(20)));

                line.setLineNumber(i + 1);
                line.setCptCode("CPT-" + (100 + random.nextInt(900)));
                line.setDescription("Service Line " + (i + 1));

                line.setClaim(claim);
                claim.getLines().add(line);
            }
            claimRepository.save(claim);

        }
    }


    public void createAccumulator(Enrollment enrollment) {
        Accumulator accumulator = new Accumulator();
        accumulator.setType(AccumulatorType.DEDUCTIBLE);
        accumulator.setTier(NetworkTier.IN_NETWORK);
        accumulator.setLimitAmount(new BigDecimal(1500));
        accumulator.setUsedAmount(new BigDecimal(600));
        accumulator.setEnrollment(enrollment);



        Accumulator accumulator2 = new Accumulator();
        accumulator2.setType(AccumulatorType.OOP_MAX);
        accumulator2.setTier(NetworkTier.IN_NETWORK);
        accumulator2.setLimitAmount(BigDecimal.valueOf(80));
        accumulator2.setUsedAmount(BigDecimal.valueOf(1200));
        accumulator2.setEnrollment(enrollment);



        Accumulator accumulator3 = new Accumulator();
        accumulator3.setType(AccumulatorType.DEDUCTIBLE);
        accumulator3.setTier(NetworkTier.OUT_OF_NETWORK);
        accumulator3.setLimitAmount(new BigDecimal(1500));
        accumulator3.setUsedAmount(new BigDecimal(300));
        accumulator3.setEnrollment(enrollment);


        Accumulator accumulator4= new Accumulator();
        accumulator4.setType(AccumulatorType.OOP_MAX);
        accumulator4.setTier(NetworkTier.OUT_OF_NETWORK);
        accumulator4.setLimitAmount(new BigDecimal(1200));
        accumulator4.setUsedAmount(new BigDecimal(400));
        accumulator4.setEnrollment(enrollment);

        accumulatorRepository.saveAll(List.of(accumulator, accumulator2, accumulator3, accumulator4));


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
        plan.setPlanYear(2025);
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
        createAccumulator(enrollment);
        enrollmentRepository.save(enrollment);
        Provider provider = createProvider();
        createClaim(member, provider);
        createClaimLine();

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
        memberDto.setEmail(member.getEmail());
//        memberDto.setEnrollment(enrollmentDto);
//        List<Claim> claims = claimRepository.getClaimsByMemberId(member.getId());
//        List<ClaimDto> claimDtos = new ArrayList<>();
//        claims.forEach(claim -> {
//            ClaimDto claimDto = new ClaimDto();
//            claimDto.setProviderName(claim.getProvider().getName());
//            claimDto.setReceivedDate(claim.getReceivedDate());
//            claimDto.setStatus(claim.getStatus().toString());
//            claimDto.setServiceEndDate(claim.getServiceEndDate());
//            claimDto.setServiceStartDate(claim.getServiceStartDate());
//            claimDto.setTotalAllowed(claim.getTotalAllowed());
//            claimDto.setTotalBilled(claim.getTotalBilled());
//            claimDto.setTotalMemberResponsibility(claim.getTotalMemberResponsibility());
//            claimDto.setTotalPlanPaid(claim.getTotalPlanPaid());
//            claimDtos.add(claimDto);
//        });
//        memberDto.setClaims(claimDtos);
        return memberDto;
    }
}
