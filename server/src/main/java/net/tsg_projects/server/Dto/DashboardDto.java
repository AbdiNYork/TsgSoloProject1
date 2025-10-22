package net.tsg_projects.server.Dto;

import lombok.Data;
import net.tsg_projects.server.Entity.Accumulator.Accumulator;
import net.tsg_projects.server.Entity.Claim.Claim;
import net.tsg_projects.server.Entity.Plan.Plan;

import java.util.List;

@Data
public class DashboardDto {
    private String name;
    private PlanDto plan;
    private List<AccumulatorDto> accumulators;
    private List<ClaimDto> claims;
}
