package com.hari.user_service.repo;

import com.hari.user_service.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepo extends JpaRepository<MyUser,Long> {
    MyUser findByName(String name);
}
