package com.hari.booking_service.repo;

import com.hari.booking_service.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Long> {

@Query(value = "SELECT COUNT(*) FROM SEAT\n" +
        "WHERE show_time_id = :showTimeId\n" +
        "  AND seat_number IN (:seatNumbers)\n" +
        "  AND is_booked = 1;\n",nativeQuery = true)
    Long validate(Long showTimeId, String seatNumbers);


@Modifying
@Transactional
@Query(value = "\n" +
        "update SEAT s set  s.is_booked =  0 where SEAT_NUMBER IN (:seatNumbers) and SHOW_TIME_ID =:showTimeId" , nativeQuery = true)
    void updateSeatAvailability(String seatNumbers, Long showTimeId);
}
