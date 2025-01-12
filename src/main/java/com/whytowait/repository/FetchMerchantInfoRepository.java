package com.whytowait.repository;

import com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicInfoDTO;
import com.whytowait.domain.dto.fetchMerchantInfo.FetchTimingProjection;
import com.whytowait.domain.dto.fetchMerchantInfo.TimingDTO;
import com.whytowait.domain.models.Merchant;
import com.whytowait.domain.models.enums.DayOfWeek;
import jakarta.persistence.Column;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FetchMerchantInfoRepository extends JpaRepository<Merchant, UUID> {

    @Query("SELECT new com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicInfoDTO(m.restaurantName, " +
            "CONCAT(u.firstName, ' ', u.lastName), u.email, u.mobile) " +
            "FROM Merchant m " +
            "INNER JOIN User u ON m.owner.id = u.id where m.id=:mid")
    FetchBasicInfoDTO fetchMerchantUserBasicDetails(@Param("mid") UUID mid);

    @Query("SELECT new com.whytowait.domain.dto.fetchMerchantInfo.FetchBasicInfoDTO(m.restaurantName, " +
            "CONCAT(u.firstName, ' ', u.lastName), u.email, u.mobile,CONCAT(a.addressLine1,' ',a.addressLine2,' ',a.city,' ',a.pincode)) " +
            "FROM Merchant m " +
            "INNER JOIN User u ON m.owner.id = u.id " +
            "INNER JOIN Address a ON a.id=m.address.id "+
            "where m.id=:mid")
    FetchBasicInfoDTO fetchMerchantUserBasicAndAddressDetails(@Param("mid") UUID mid);

    @Query(value = "select day_of_week,open_time,close_time,is_closed from timing where merchant_id=:mid",nativeQuery = true)
    List<FetchTimingProjection> fetchTimingDetails(@Param("mid") UUID mid);
}
