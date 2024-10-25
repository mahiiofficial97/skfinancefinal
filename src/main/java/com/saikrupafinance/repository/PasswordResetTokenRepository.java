package com.saikrupafinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.saikrupafinance.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
