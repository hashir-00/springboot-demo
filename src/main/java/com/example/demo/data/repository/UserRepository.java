package com.example.demo.data.repository;

import com.example.demo.data.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email)
    ;
}
