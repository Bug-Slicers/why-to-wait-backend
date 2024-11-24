package com.whytowait.repository;

import com.whytowait.domain.dto.user.FetchUserDTO;
import com.whytowait.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(nativeQuery = true,value = "select id from users where mobile=:mobile")
    UUID findUserByMobile(@Param("mobile") String mobile);

    @Query(nativeQuery = true,value = "select id,first_name as firstName, last_name as lastName, email, mobile from users where mobile=:mobile")
    FetchUserDTO findUserByMobileWithUserBody(@Param("mobile") String mobile);

    @Modifying
    @Query(nativeQuery = true, value = "update users set last_logout=current_timestamp where mobile=:mobile")
    Integer updateLastLogoutTimestamp(@Param("mobile") String mobile);

    @Query(nativeQuery = true, value = "select last_logout from users where id = :userId")
    Timestamp findLastLogoutById(@Param("userId") UUID userId);

}
