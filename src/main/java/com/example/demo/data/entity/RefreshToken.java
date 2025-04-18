package com.example.demo.data.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id")
  private Users user;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private Instant createdAt;

  @Column(nullable = false)
  private Instant expiresAt;
}
