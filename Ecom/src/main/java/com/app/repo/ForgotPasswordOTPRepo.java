package com.app.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.ForgotPasswordOTP;

import jakarta.transaction.Transactional;

public interface ForgotPasswordOTPRepo extends JpaRepository<ForgotPasswordOTP, Long>{
	
	Optional<ForgotPasswordOTP> findByOtpAndEmail(String otp, String email);
	
	
	@Modifying
    @Transactional
	@Query("DELETE FROM Otp o WHERE o.expiryTime < :now")
	void deleteExpiredOtps(LocalDateTime now);
}
