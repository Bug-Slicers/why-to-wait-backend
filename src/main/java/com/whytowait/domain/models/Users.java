package com.whytowait.domain.models;
import com.whytowait.domain.models.Enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    private String FirstName;
    private String Lastname;

    @Column(nullable = false, unique = true)
    private String Email;

    @ColumnDefault("false")
    private boolean IsEmailVerified;

    @Column(nullable = false, unique = true)
    private String Mobile;

    @ColumnDefault("false")
    private boolean IsMobileVerified;

    @Column(unique = true)
    private String GoogleId;

    private UserRole Role;

    private Instant LastLogout;
    private Instant CreatedAt;
    private Instant UpdatedAt;
}
