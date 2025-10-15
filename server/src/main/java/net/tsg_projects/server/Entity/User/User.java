package net.tsg_projects.server.Entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "_Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID Id;

    private String authProvider;

    private String authSub;

    private String email;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}


