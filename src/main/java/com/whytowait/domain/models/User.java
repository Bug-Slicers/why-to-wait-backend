package com.whytowait.domain.models;

import com.whytowait.domain.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile"),
        @UniqueConstraint(columnNames = "google_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Builder.Default
    private Boolean emailVerified = false;

    @NotNull
    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false)
    @Builder.Default
    private Boolean mobileVerified = false;

    @Column(unique = true)
    private String googleId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.CUSTOMER;

    private Instant lastLogout;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
