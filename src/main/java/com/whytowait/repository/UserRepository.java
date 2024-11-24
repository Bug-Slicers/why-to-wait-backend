package com.whytowait.repository;

import com.whytowait.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByMobile(String mobile);

    @Modifying
    @Query(nativeQuery = true, value = "update users set last_logout=current_timestamp where mobile=:mobile")
    Integer updateLastLogoutTimestamp(String mobile);

    @Query(nativeQuery = true, value = "select last_logout from users where mobile=:mobile")
    Date findLastLogoutByMobile(String mobile);
}
