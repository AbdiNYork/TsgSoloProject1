package net.tsg_projects.server.MockDataFactory;

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

            ClaimStatusEvent claimStatusEvent = new ClaimStatusEvent();
            claimStatusEvent.setOccurredAt(OffsetDateTime.now());
            claimStatusEvent.setStatus(ClaimStatus.IN_REVIEW);
            claimStatusEvent.setNote("Claim Status: " + ClaimStatus.IN_REVIEW);

            claim.getStatusHistory().add(claimStatusEvent);
            createClaimLine(claim);

            claimRepository.save(claim);
        }
    }

    public void createClaimLine(Claim claim) {
        for (int i = 0; i < 3; i++) {
            ClaimLine claimLine = new ClaimLine();
            claimLine.setAllowedAmount(claim.getTotalAllowed());
            claimLine.setBilledAmount(claim.getTotalBilled());
            claimLine.setCoinsuranceApplied(new BigDecimal(25));
            claimLine.setPlanPaid(new BigDecimal(100));
            claimLine.setMemberResponsibility(new BigDecimal(50));
            claimLine.setDeductibleApplied(new BigDecimal(50));
            claimLine.setCopayApplied(new BigDecimal(50));
            claimLine.setLineNumber(i + 1);
            claimLine.setCptCode("C-0" + (1 + i * i * i) + ".med");
            claimLine.setDescription("Hospital Visit: " + (i + 1));
            claimLine.setClaim(claim);
            claimLineRepository.save(claimLine);
            claim.getLines().add(claimLine);
        }

        claimRepository.save(claim); // Save once after all lines are added
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
        accumulator2.setLimitAmount(new BigDecimal(2500));
        accumulator2.setUsedAmount(new BigDecimal(1200));
        accumulator2.setEnrollment(enrollment);
        accumulatorRepository.save(accumulator2);

        return accumulator2;
    }

    public Enrollment createEnrollment(Member member) {
        Enrollment enrollment = new Enrollment();
        enrollment.setMember(member);
        Plan plan = planRepository.findByName("Gold PPO");

        if (plan == null) {
            plan = createPlan(); // Create if not found
        }

        enrollment.setPlan(plan);
        enrollment.setCoverageStart(LocalDate.now());
        enrollment.setCoverageEnd(LocalDate.now().plusDays(364));
        enrollment.setActive(true);
        enrollmentRepository.save(enrollment);

        Accumulator accumulator = createAccumulator(enrollment);
        enrollment.getAccumulators().add(accumulator);

        enrollmentRepository.save(enrollment);
        accumulatorRepository.save(accumulator); // Persist both sides

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

    public Member generate(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new IllegalArgumentException("Member not found with email: " + email);
        }

        boolean hasActiveEnrollment = enrollmentRepository.existsByMemberIdAndActiveTrue(member.getId());
        if (hasActiveEnrollment) {
            return member;
        }

        System.out.println("No active enrollment — seeding mock data...");
        createPlan();
        Enrollment enrollment = createEnrollment(member);
        createAccumulator(enrollment);
        Provider provider = createProvider();
        createClaim(member, provider);

        return member;
    }
}
