package com.hari.auth_service.repository;


import com.hari.auth_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<Admin, UUID> {


  Optional<Admin> findByUserName(String userName);


}
