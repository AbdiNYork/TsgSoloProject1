package net.tsg_projects.server.Dto;

import lombok.Data;
import net.tsg_projects.server.Entity.Claim.Claim;

import java.util.List;

@Data
public class MemberDto {
    private String email;
    private EnrollmentDto enrollment;
    private List<ClaimDto> claims;
    // no lazy proxies!
}
