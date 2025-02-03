package com.whytowait.repository;

import com.whytowait.api.common.constants.QueryConstants;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.domain.dto.publicMerchantInfo.PublicBasicInfoAndAddressDTO;
import com.whytowait.domain.dto.publicMerchantInfo.TimingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PublicMerchantInfoRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public PublicBasicInfoAndAddressDTO fetchMerchantUserBasicDetails(UUID mid) throws BadRequestException {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.FETCH_MERCHANT_BASIC_INFO, new BeanPropertyRowMapper<>(PublicBasicInfoAndAddressDTO.class), mid);
        } catch (Exception e) {
            System.out.println("exception while fetching basic info" + e);
            throw new BadRequestException("Invalid Mid/Query Exception");
        }
    }

    public PublicBasicInfoAndAddressDTO fetchMerchantUserBasicAndAddressDetails(UUID mid) throws BadRequestException {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.FETCH_MERCHANT_BASIC_AND_ADDRESS_INFO, new BeanPropertyRowMapper<>(PublicBasicInfoAndAddressDTO.class), mid);
        } catch (Exception e) {
            System.out.println("exception while fetching basic info" + e);
            throw new BadRequestException("Invalid Mid/Query Exception");
        }

    }

    public List<TimingDTO> fetchTimingDetails(UUID mid) throws BadRequestException {
        try {
            return jdbcTemplate.query(QueryConstants.FETCH_MERCHANT_TIMINGS_INFO, new BeanPropertyRowMapper<>(TimingDTO.class), mid);
        } catch (Exception e) {
            System.out.println("exception while fetching basic info" + e);
            throw new BadRequestException("Invalid Mid/Query Exception");
        }

    }

}
