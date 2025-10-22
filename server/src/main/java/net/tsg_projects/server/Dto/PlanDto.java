package net.tsg_projects.server.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import net.tsg_projects.server.Enums.PlanType;

@Data
public class PlanDto {

    private String name;


    private PlanType type;

    private String networkName;

    private Integer planYear;
}

