package net.tsg_projects.server.Repository;

import net.tsg_projects.server.Dto.ClaimSummaryDto;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Enums.ClaimStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    List<Claim> getClaimsByMemberId(UUID memberId);

    Claim getClaimByClaimNumber(String claimNumber);

    @Query("""
        SELECT new net.tsg_projects.server.Dto.ClaimSummaryDto(
            c.claimNumber,
            c.serviceStartDate,
            c.serviceEndDate,
            c.provider.name,
            c.status,
            c.totalMemberResponsibility
        )
        FROM Claim c
        WHERE (:claimNumber IS NULL OR c.claimNumber = :claimNumber)
          AND (:providerName IS NULL OR LOWER(c.provider.name) LIKE LOWER(CAST(:providerName AS string)))
          AND (:startDate IS NULL OR c.serviceStartDate >= :startDate)
          AND (:endDate IS NULL OR c.serviceEndDate <= :endDate)
          AND (:statuses IS NULL OR c.status IN :statuses)
        """)
    Page<ClaimSummaryDto> searchClaims(
            @Param("claimNumber") String claimNumber,
            @Param("providerName") String providerName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("statuses") List<ClaimStatus> statuses,
            Pageable pageable
    );
}
