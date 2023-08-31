package com.credix.credixhrm.controller;

import com.credix.credixhrm.model.Holiday;
import com.credix.credixhrm.model.Response;
import com.credix.credixhrm.service.HolidayService;
import com.credix.credixhrm.utils.EmployeeUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.credix.credixhrm.constents.EmployeeConstants.SOMETHING_WENT_WRONG;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/holidays/manage")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;

    @GetMapping("/getAllHolidays")
    public ResponseEntity<Response> getAllHolidays() {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Holidays: ", holidayService.getAllHolidays()))
                            .message("Holidays successfully charged.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/getAllPendingHolidays")
    public ResponseEntity<Response> getAllPendingHolidays() {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Holidays: ", holidayService.getAllPendingHolidays()))
                            .message("Holidays successfully charged.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAllAcceptedHolidays")
    public ResponseEntity<Response> getAllAcceptedHolidays() {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Holidays: ", holidayService.getAllAcceptedHolidays()))
                            .message("Holidays successfully charged.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAllRefusedHolidays")
    public ResponseEntity<Response> getAllRefusedHolidays() {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Holidays: ",  holidayService.getAllRefusedHolidays()))
                            .message("Holidays successfully charged.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/getHolidayById/{id}")
    public ResponseEntity<Response> getHolidayById(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Holiday: ", holidayService.getHolidayById(id)))
                            .message("Holiday whit id {} is successfully created."+ id)
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{employeeId}/createHoliday")
    public ResponseEntity<Response> createHoliday(@RequestBody Holiday holiday, @PathVariable Integer employeeId) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", holidayService.holidayRequest(holiday, employeeId)))
                            .message("Holiday successfully created.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + employeeId , HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("You cannot request a holiday with null fields", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateHolidayById/{id}")
    public ResponseEntity<Response> updateHoliday(@PathVariable Integer id, @RequestBody Holiday updatedHoliday) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                        .timeStamp(now())
                        .data(of("Employee: ", holidayService.updateHoliday(id, updatedHoliday)))
                        .message("Holiday successfully updated.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteHolidayById/{id}")
    public ResponseEntity<Response> deleteHoliday(@PathVariable Integer id) {

        try {
            holidayService.deleteHoliday(id);
            return EmployeeUtils.getResponseEntityG("Holiday successfully deleted.", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/acceptHolidayById/{id}")
    public ResponseEntity<Response> acceptHoliday(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", holidayService.acceptHoliday(id)))
                            .message("Holiday successfully updated.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("You can only accept pending requests.", HttpStatus.BAD_REQUEST);
        }
    }

        @PostMapping("/refuseHolidayById/{id}")
        public ResponseEntity<Response> refuseHoliday (@PathVariable Integer id){

            try {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(now())
                                .data(of("Employee: ", holidayService.refuseHoliday(id)))
                                .message("Holiday successfully updated.")
                                .status(OK)
                                .statusCode(OK.value())
                                .build()
                );
            }catch (EntityNotFoundException e){
                e.printStackTrace();
                return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                return EmployeeUtils.getResponseEntityG("You can only accept pending requests.", HttpStatus.BAD_REQUEST);
            }
        }
    }