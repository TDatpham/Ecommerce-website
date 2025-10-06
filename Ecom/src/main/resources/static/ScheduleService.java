package com.app.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.dtos.ForgotPasswordRequest;
import com.app.repo.ForgotPasswordOTPRepo;
import com.app.repo.OtpRepo;

@Service
public class ScheduleService {
	
	@Autowired
	private OtpRepo otpRepo;
//	
	@Autowired
	private ForgotPasswordOTPRepo forgotPasswordOTPRepo;
//	@Scheduled(fixedRate  = 10000) // will execute in every 10 seconds
//	public void clean() {
//		System.out.println(LocalDate.now());
//	}
//	
//	// cron = "Seconds Minutes Hours DayOfMonth Month DayOfWeek"
//	
//	@Scheduled(cron = "0 0 12 * * ?") // every day at 12 pm
//	public void clean2() {
//		System.out.println(LocalDate.now());
//	}
	
	@Scheduled(cron = "0 0 12 * * ?") // every day at 12 pm
    public void cleanUpExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        otpRepo.deleteExpiredOtps(now);
        forgotPasswordOTPRepo.deleteExpiredOtps(now);
        System.out.println("Deleted expired OTPs.");
    }
}
