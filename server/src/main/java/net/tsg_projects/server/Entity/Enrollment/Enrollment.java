package net.tsg_projects.server.Entity.Enrollment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tsg_projects.server.Entity.Accumulator.Accumulator;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.Plan.Plan;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Enrollment {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    private LocalDate coverageStart;
    private LocalDate coverageEnd;

    boolean active;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accumulator> accumulators = new ArrayList<>();

    public void setAccumulators(Accumulator accumulator) {
        this.accumulators.add(accumulator);
    }
}
