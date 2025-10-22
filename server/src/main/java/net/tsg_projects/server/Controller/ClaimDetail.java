package net.tsg_projects.server.Controller;


import net.tsg_projects.server.Dto.ClaimDto;
import net.tsg_projects.server.Repository.ClaimRepository;
import net.tsg_projects.server.Service.ClaimDetailService;
import net.tsg_projects.server.Service.ImplClaimDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
public class ClaimDetail {

    @Autowired
    private ImplClaimDetailService claimDetailService;

    @GetMapping("/{claimNumber}")
    public ClaimDto getClaimDetail(@PathVariable String claimNumber) {
        return claimDetailService.getClaimDetail(claimNumber);
    }
}
