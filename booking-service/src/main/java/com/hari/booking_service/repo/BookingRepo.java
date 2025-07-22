package com.hari.booking_service.repo;

import com.hari.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    @Modifying
    @Transactional
    @Query("update  Booking b \n" +
            "set b.status = 'CANCELLED' where b.ID = :bookingId")
    void cancelTicket(Long bookingId);



    @Query(value = "select * from BOOKING where USER_ID = :userId",nativeQuery = true)
    List<Booking> findByUserId(Long userId);
}
