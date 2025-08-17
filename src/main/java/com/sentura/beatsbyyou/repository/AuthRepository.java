package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<User,Long> {

    List<User> findUserByActiveTrue();

    User findFirstByEmail(String email);
}
