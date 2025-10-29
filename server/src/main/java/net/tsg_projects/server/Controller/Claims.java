package net.tsg_projects.server.Controller;

import net.tsg_projects.server.Dto.ClaimDto;
import net.tsg_projects.server.Dto.ClaimSummaryDto;
import net.tsg_projects.server.Enums.ClaimStatus;
import net.tsg_projects.server.Service.ImplClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/claims")
public class Claims {

    @Autowired
    private ImplClaimService claimService;


    @GetMapping("")
    public Page<ClaimSummaryDto> getClaims(@RequestParam(required = true) String claimNumber,
                                           @RequestParam(required = false) String providerName,
                                           @RequestParam(required = false) List<ClaimStatus> statuses,
                                           @RequestParam(required = false) LocalDate startDate,
                                           @RequestParam(required = false) LocalDate endDate,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "receivedDate") String sortBy,
                                           @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return claimService.searchClaims(
                claimNumber, providerName, statuses, startDate, endDate,
                page, size, sortBy, sortDir
        );

    }
}
