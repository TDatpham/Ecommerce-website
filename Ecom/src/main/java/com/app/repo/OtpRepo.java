package com.app.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Otp;

import jakarta.transaction.Transactional;

public interface OtpRepo extends JpaRepository<Otp, Long>{
	
	Optional<Otp> findByOtpAndUser(String otp, String user);
	

    @Modifying
    @Transactional
	@Query("DELETE FROM Otp o WHERE o.expiryTime < :now")
	void deleteExpiredOtps(LocalDateTime now);

}
