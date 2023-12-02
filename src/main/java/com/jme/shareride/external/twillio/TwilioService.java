package com.jme.shareride.external.twillio;

import com.jme.shareride.entity.user_and_auth.OtpEntity;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.service.userServices.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public class TwilioService {


    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final TwilioSender twilioSender;

    public TwilioService(TwilioSender twilioSender,UserRepository userRepository,OtpRepository otpRepository) {
        this.twilioSender = twilioSender;
        this.userRepository =userRepository;
        this.otpRepository=otpRepository;
    }

    private String generateOtp(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }


    public void SendOtp(String phoneNumber){
        String otp = generateOtp();
        String message = "Dear User Your one Time Password is : " + otp +".It expires in 5 min ";
        twilioSender.SendSms(phoneNumber, message);
        UserEntity user = userRepository.findByPhoneNumber(phoneNumber);

        OtpEntity existingOtp = otpRepository.findByOwnersNumber(phoneNumber);
        if(existingOtp != null){
            otpRepository.delete(existingOtp);
        }
        OtpEntity newOtpEntity = new OtpEntity();
        newOtpEntity.setOwnersNumber(user.getPhoneNumber());
        newOtpEntity.setToken(otp);
        otpRepository.save(newOtpEntity);
        log.info(message + "|| number :" + phoneNumber);
    }

    public boolean verifyOtp(String phoneNumber,String otp){
        OtpEntity entityOtp = otpRepository.findByOwnersNumber(phoneNumber);
        String storedOtp  = entityOtp.getToken();
        return storedOtp.equals(otp) && storedOtp != null && entityOtp.getCreated().toSecondOfDay() <  entityOtp.getExpires().toSecondOfDay();
    }
}
