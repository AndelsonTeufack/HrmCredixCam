package com.hrmcredixcam.utils;

import com.hrmcredixcam.model.Employee;
import com.hrmcredixcam.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.time.LocalDateTime.now;


public class EmployeeUtils {

    private EmployeeUtils(){

    }


    public static ResponseEntity<Response> getResponseEntityG(String responseMessage, HttpStatus httpStatus){

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message(responseMessage)
                        .status(httpStatus)
                        .statusCode(httpStatus.value())
                        .build()
        );
    }








//    public static ResponseEntity<Response> getResponseEntitySub(String responseMessage, HttpStatus httpStatus){
////        return new ResponseEntity<String>(
////                "{\"message\":\"" + responseMessage + "\",\n\"code\":\"" + httpStatus.value() + "\",\n\"body\":\"" + object + "\"}",
////                httpStatus
////        );
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(now())
//                        .message(responseMessage)
//                        .status(httpStatus)
//                        .statusCode(httpStatus.value())
//                        .build()
//        );
//    }
}
