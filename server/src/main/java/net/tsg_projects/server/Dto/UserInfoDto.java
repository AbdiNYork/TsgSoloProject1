package net.tsg_projects.server.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String email;
    private String name;
    private String lastName;
}
