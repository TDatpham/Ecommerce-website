package com.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.repo.ForgotPasswordOTPRepo;
import com.app.repo.OtpRepo;

@Service
public class ScheduleService {
    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private ForgotPasswordOTPRepo forgotPasswordOTPRepo;

    @Scheduled(cron = "0 0 12 * * ?") // every day at 12 pm
    public void cleanUpExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        otpRepo.deleteExpiredOtps(now);
        forgotPasswordOTPRepo.deleteExpiredOtps(now);
        System.out.println("Deleted expired OTPs.");
    }
}
