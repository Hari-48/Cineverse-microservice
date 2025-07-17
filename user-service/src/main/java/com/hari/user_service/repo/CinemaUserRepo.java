package com.hari.user_service.repo;

import com.hari.user_service.entity.CinemaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaUserRepo extends JpaRepository<CinemaUser,Long> {
    CinemaUser findByUserName(String username);
}
