package com.hari.user_service.repo;



import com.hari.user_service.entity.CineverseUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CineverseUserRepo extends JpaRepository<CineverseUser,Long> {


    CineverseUser findByUserName(@NotNull(message = "username should be null") String username);
}
