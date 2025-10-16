package net.tsg_projects.server.Entity.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import net.tsg_projects.server.Entity.Address.Address;
import net.tsg_projects.server.Entity.Enrollment.Enrollment;
import net.tsg_projects.server.Entity.User.User;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "member")
public class Member {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;


    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    private User user;

    @Embedded
    private Address mailingAddress; // optional embedded/owned


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

}
