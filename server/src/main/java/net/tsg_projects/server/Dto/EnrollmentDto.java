package net.tsg_projects.server.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Plan.Plan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class EnrollmentDto {
    private UUID memberId;

    private String planName;

    private LocalDate coverageStart;
    private LocalDate coverageEnd;

    boolean active;

}
