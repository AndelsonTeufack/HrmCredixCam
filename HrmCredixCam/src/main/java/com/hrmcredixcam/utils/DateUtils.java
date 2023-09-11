package com.hrmcredixcam.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class DateUtils {


    public  String generateBankRoundId(int bankId){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+":"+bankId;
    }
}
