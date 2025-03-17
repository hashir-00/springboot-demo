package com.example.demo.data.repository;

import com.example.demo.data.entity.RefreshToken;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

  Optional<RefreshToken> findByIdAndExpiresAtAfter(UUID id, Instant date);

  List<RefreshToken> findByUser_Id(Integer userId);

  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.user.id = :userId")
  void deleteByUserId(@Param("userId") int userId);

  void deleteAllByUser_Id(Integer userId);
}
