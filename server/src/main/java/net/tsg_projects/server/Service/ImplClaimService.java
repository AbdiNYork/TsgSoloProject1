package net.tsg_projects.server.Service;

import net.tsg_projects.server.Dto.ClaimDto;
import net.tsg_projects.server.Dto.ClaimSummaryDto;
import net.tsg_projects.server.Enums.ClaimStatus;
import net.tsg_projects.server.Repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ImplClaimService implements ClaimService {
    @Autowired
    ClaimRepository claimRepo;


    public Page<ClaimSummaryDto> searchClaims(
                                                String claimNumber,
                                                String providerName,
                                                List<ClaimStatus> statuses,
                                                LocalDate startDate,
                                                LocalDate endDate,
                                                int page,
                                                int size,
                                                String sortBy,
                                                String sortDir
    ) {

        // Create sort object. Assign sortBy
        Sort sort = Sort.by(sortBy != null ? sortBy : "receivedDate");
        if(sortDir != null && sortDir.equalsIgnoreCase("desc")) sort = sort.descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return claimRepo.searchClaims(claimNumber, providerName, startDate, endDate, statuses, pageable);



    }

}
