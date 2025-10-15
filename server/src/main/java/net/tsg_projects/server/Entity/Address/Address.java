package net.tsg_projects.server.Entity.Address;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Embeddable
public class Address {
   private String line1;
   private String line2;
   private String city;
   private String state;
   private String postalCode;
}
