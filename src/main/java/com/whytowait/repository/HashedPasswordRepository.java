package com.whytowait.repository;

import com.whytowait.domain.models.HashedPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HashedPasswordRepository extends JpaRepository<HashedPassword, UUID> {
    @Query(nativeQuery = true,value = "select hashed_password from hashed_password where user_id=:userId")
    String findPasswordByUserId(@Param("userId") UUID userId);
}
