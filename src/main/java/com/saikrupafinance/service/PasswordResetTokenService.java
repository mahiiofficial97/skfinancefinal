package com.saikrupafinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.saikrupafinance.model.PasswordResetToken;
import com.saikrupafinance.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {

	@Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Transactional
    public void save(PasswordResetToken resetToken) {
        passwordResetTokenRepository.save(resetToken);
    }

    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
