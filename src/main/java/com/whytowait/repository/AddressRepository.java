package com.whytowait.repository;

import com.whytowait.domain.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Modifying
    @Query(value = "update address set address_line1=:addressLine1,address_line2=:addressLine2,city=:city,district=:district,state=:state, pincode=:pincode ,updated_at=now() where id=:id ",nativeQuery = true)
    int updateAddress(@Param("addressLine1") String addressLine1,@Param("addressLine2") String addressLine2,@Param("city") String city,@Param("district") String district,@Param("state") String state,@Param("pincode") String pincode,@Param("id") UUID id);
}
