package com.hrmcredixcam.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class Utils {

    public String generateInternalId(String msisdn){
      return   LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS"))+":"+msisdn;
    }

    public String generateDisbursementProcessingId(String msisdn){
      return   LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS"))+"PMW"+msisdn.substring(4,9)+"LN";
    }

    public String generateRefundId(String msisdn,String bankCode,String statusCode,String momoTransactionId){
        if(Objects.equals(statusCode, "01")) {
            return   LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS"))+"PMW"+msisdn.substring(3,9)+"R"+momoTransactionId;
        } else {
            return  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS"))+"PMW"+msisdn.substring(3,9)+"R";
        }
    }

    public String generateRefundProcessingId(String msisdn){
      return   LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS"))+"PMW"+msisdn.substring(4,9)+"RF";
    }

    public String formatDateForGetCutomerDetails(String fulldate){
        var stdt=fulldate.split("/");
        var day = stdt[0];
        var month = stdt[1];
        var year = stdt[2];
        return day+"-"+month+"-"+year;
    }

    public  Boolean isMTN(String number) {
        return number.startsWith("23767") || number.startsWith("237650") || number.startsWith("237651") ||
                number.startsWith("237652") || number.startsWith("237653") || number.startsWith("237654") ||
                number.startsWith("237680") || number.startsWith("237681") || number.startsWith("237682") ||
                number.startsWith("237683") || number.startsWith("237684") || number.startsWith("237685") ||
                number.startsWith("237686") || number.startsWith("237687") || number.startsWith("237688") ||
                number.startsWith("237689");
    }

    public  Boolean isOrange(String number) {
        return number.startsWith("23769") || number.startsWith("237655") || number.startsWith("237656") ||
                number.startsWith("237657") || number.startsWith("237658") || number.startsWith("237659");
    }
}
