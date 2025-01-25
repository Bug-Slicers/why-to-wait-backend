package com.whytowait.api.common.constants;

public class QueryConstants {
    public static  final String FETCH_MERCHANT_BASIC_INFO="SELECT m.restaurant_name as restaurantName, u.first_name as firstName,u.last_name as lastName, u.email, u.mobile\n"+
            "FROM merchant m INNER JOIN users u ON m.owner_id = u.id WHERE m.id = ?";

    public static  final String FETCH_MERCHANT_BASIC_AND_ADDRESS_INFO ="SELECT m.restaurant_name, u.first_name as firstName, u.last_name as lastName, u.email, u.mobile, a.address_line1 as addressLine1, a.address_line2 as addressLine2, a.city as city, a.state as state,a.pincode as pincode FROM merchant m \n" +
            "INNER JOIN users u ON m.owner_id = u.id INNER JOIN address a ON a.id = m.address_id WHERE m.id = ?";

    public static  final String FETCH_MERCHANT_TIMINGS_INFO = "SELECT t.day_of_week AS dayOfWeek, t.open_time AS openTime, t.close_time AS closeTime, t.is_closed AS isClosed " +
            "FROM timing t WHERE t.merchant_id = ?";
}
