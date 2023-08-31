package com.credix.credixhrm.controller;

import com.credix.credixhrm.exceptions.InvalidPasswordException;
import com.credix.credixhrm.exceptions.InvalidRoleException;
import com.credix.credixhrm.model.Employee;
import com.credix.credixhrm.model.Response;
import com.credix.credixhrm.service.EmployeeService;
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
@RequestMapping("/employees/manage")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Response> getAllEmployees() {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employees: ", employeeService.getAllEmployees()))
                            .message("list of all the employees.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", employeeService.getEmployeeById(id)))
                            .message("The employee who get id " +id)
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/createEmployee")
    public ResponseEntity<Response> createEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", employeeService.createEmployee(employee)))
                            .message("Employee successfully created.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );

        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("You cannot create an employee with null fields", HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee with the same email already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", employeeService.updateEmployee(id, updatedEmployee)))
                            .message("Employee successfully updated.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteEmployee{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return EmployeeUtils.getResponseEntityG("Employee successfully deleted.", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllActiveEmployees")
    public ResponseEntity<Response> getActiveEmployees() {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Active Employees: ", employeeService.getActiveEmployees()))
                            .message("List of active employees. ")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAllArchivedEmployees")
    public ResponseEntity<Response> getArchivedEmployees() {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Archived Employees: ", employeeService.getArchivedEmployees()))
                            .message("List of archived employees")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/archiveEmployeeById/{id}")
    public ResponseEntity<Response> archiveEmployee(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", employeeService.archiveEmployee(id)))
                            .message("Employee successfully archived.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/activateEmployeeById/{id}")
    public ResponseEntity<Response> activateEmployee(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", employeeService.activateEmployee(id)))
                            .message("Employee successfully active.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/resetPasswordById/{employeeId}")
    public ResponseEntity<Response> updatePassword(
            @PathVariable Integer employeeId,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) throws InvalidPasswordException {

        try {
            employeeService.updatePassword(employeeId, oldPassword, newPassword);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message("Employee Password successfully updated.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + employeeId , HttpStatus.NOT_FOUND);
        }catch (InvalidPasswordException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("the password provided is invalid." , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{employeeId}/updateEmployeeRole/role")
    public ResponseEntity<Response> updateRole(
            @PathVariable Integer employeeId,
            @RequestParam("newRole") String newRole) {

       try {
           employeeService.updateRole(employeeId, newRole);
           return ResponseEntity.ok(
                   Response.builder()
                           .timeStamp(now())
                           .message("The employee was successfully updated to the "+ newRole +" role")
                           .status(OK)
                           .statusCode(OK.value())
                           .build()
           );
    }catch (EntityNotFoundException e){
        e.printStackTrace();
        return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + employeeId , HttpStatus.NOT_FOUND);
    }catch (InvalidRoleException e){
        e.printStackTrace();
        return EmployeeUtils.getResponseEntityG("the role provided is invalid." , HttpStatus.BAD_REQUEST);
    }
    }

}
