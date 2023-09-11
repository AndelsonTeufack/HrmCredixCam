package com.hrmcredixcam.controller;

import com.hrmcredixcam.exception.InvalidPasswordException;
import com.hrmcredixcam.exception.InvalidRoleException;
import com.hrmcredixcam.model.Employee;
import com.hrmcredixcam.model.Response;
import com.hrmcredixcam.publicdtos.UpdateEmployeeDTO;
import com.hrmcredixcam.service.EmployeeService;
import com.hrmcredixcam.utils.EmployeeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.hrmcredixcam.constents.EmployeeConstants.SOMETHING_WENT_WRONG;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/employee/manage")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    @GetMapping("/getAll")
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Employee: ", employeeService.getEmployeeById(id)))
                            .message("The employee who get id " + id)
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id, NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/updateById/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable String id, @RequestBody UpdateEmployeeDTO updatedEmployee) {
        try {
            employeeService.updateEmployee(id, updatedEmployee);
            return EmployeeUtils.getResponseEntityG("Employee successfully updated ", OK);
        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id, NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return EmployeeUtils.getResponseEntityG("Employee successfully deleted.", OK);
        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id, NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN')")
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/archiveEmployeeById/{id}")
    public ResponseEntity<Response> archiveEmployee(@PathVariable String id) {

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
        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id, NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/activateEmployeeById/{id}")
    public ResponseEntity<Response> activateEmployee(@PathVariable String id) {

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
        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + id, NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{employeeId}/resetPasswordById/{newPassword}")
    public ResponseEntity<Response> updatePassword(
            @PathVariable String employeeId,
            @PathVariable String newPassword) throws InvalidPasswordException {

        try {
            employeeService.updatePassword(employeeId, newPassword);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message("Employee Password successfully updated.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("the password provided is invalid.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + employeeId, NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/{employeeId}/updateEmployeeRole/{newRole}")
    public ResponseEntity<Response> updateRole(
            @PathVariable String employeeId,
            @PathVariable String newRole) {

        try {
            employeeService.changeRole(employeeId, newRole);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message("The employee was successfully updated to the " + newRole + " role")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (InvalidRoleException e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("the role provided is invalid.", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + employeeId, HttpStatus.NOT_FOUND);
        }
    }

}


