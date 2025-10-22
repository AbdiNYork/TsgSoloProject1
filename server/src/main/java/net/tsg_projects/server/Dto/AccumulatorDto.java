package net.tsg_projects.server.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Enums.AccumulatorType;
import net.tsg_projects.server.Enums.NetworkTier;

import java.math.BigDecimal;
@Data
public class AccumulatorDto {
    private EnrollmentDto enrollment;



    private AccumulatorType type;


    private NetworkTier tier;

    private BigDecimal limitAmount;
    private BigDecimal usedAmount;
}
