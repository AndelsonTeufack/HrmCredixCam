package com.hrmcredixcam.controller;

import com.hrmcredixcam.authdtos.LoginRequestDTO;
import com.hrmcredixcam.authdtos.RefreshTokenDTO;
import com.hrmcredixcam.authdtos.SignupRequestDTO;
import com.hrmcredixcam.authdtos.SignupUserRequestDTO;
import com.hrmcredixcam.model.Employee;
import com.hrmcredixcam.publicdtos.ResponseDTO;
import com.hrmcredixcam.service.AuthService;
import com.hrmcredixcam.service.EmployeeService;
import com.hrmcredixcam.service.RoleService;
import com.hrmcredixcam.utils.EntityResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {


    private final EntityResponseUtils entityResponseUtils;
    private final AuthService authService;
    private final PasswordEncoder encoder;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @PostMapping("signIn")
    public ResponseEntity<ResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        try {

            var employee=authService.login(loginRequestDTO);
            log.info("Successful login : {}",employee);
            return new ResponseEntity<>(entityResponseUtils.SuccessFullResponse("Authenticating employee",employee,1), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error login : {}",e.getMessage());
            return new ResponseEntity<>(entityResponseUtils.ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("registerByAdmin")
    public ResponseEntity<ResponseDTO> registerUserByAdmin(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) {

        try {
            var employee=authService.registerByAdmin(signUpRequestDTO);
            log.info("Success full creating employee : {}",employee);
            return new ResponseEntity<>(entityResponseUtils.SuccessFullResponse("Employee registered successfully!",employee,0), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception creating employee : {}",e.getMessage());
            return new ResponseEntity<>(entityResponseUtils.ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }


    @PostMapping("registerByUser")
    public ResponseEntity<ResponseDTO> registerUserByUSer(@Valid @RequestBody SignupUserRequestDTO signUpRequestDTO) {

        try {
            var employee=authService.registerByUser(signUpRequestDTO);
            log.info("Success full creating employee : {}",employee);
            return new ResponseEntity<>(entityResponseUtils.SuccessFullResponse("Employee registered successfully!",employee,0), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception creating employee : {}",e.getMessage());
            return new ResponseEntity<>(entityResponseUtils.ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokendto) {

        try {
            var employee=authService.refreshToken(refreshTokendto);
            log.info("Successfull refreshing token for employee : {}",employee);
            return new ResponseEntity<>(entityResponseUtils.SuccessFullResponse("Refreshing  Token ",employee,1), HttpStatus.OK);


        } catch (Exception e) {
            log.info("Exception refreshing token for employee : {}",e.getMessage());
            return new ResponseEntity<>(entityResponseUtils.ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("add/admin")
    public ResponseEntity<ResponseDTO> addAdmin(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) {

        try {
            var employee = Employee.builder()
                    .userName(signUpRequestDTO.getUserName())
                    .lastName(signUpRequestDTO.getLastName())
                    .firstName(signUpRequestDTO.getFirstName())
                    .email(signUpRequestDTO.getEmail())
                    .telephone(signUpRequestDTO.getTelephone())
                    .post(signUpRequestDTO.getPost())
                    .department(signUpRequestDTO.getDepartment())
                    .isActive(true)
                    .password(encoder.encode(signUpRequestDTO.getPassword()))
                    .creationDate(LocalDateTime.now()).build();


            Set<String> strRoles = signUpRequestDTO.getRole();
            var roles = roleService.getListOfRoleFromListOfRoleStr(strRoles);
            employee.setRoles(roles);

            if(employeeService.getAllEmployees().size()==0){

                var usr=employeeService.saveEmployee(employee);

                return new ResponseEntity<>(entityResponseUtils.SuccessFullResponse("Admin registered successfully!",usr,1), HttpStatus.OK);

            }else{
                return new ResponseEntity<>(entityResponseUtils.ErrorResponse("Admin registered successfully!"), HttpStatus.OK);
            }

        } catch (Exception e) {

            return new ResponseEntity<>(entityResponseUtils.ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

}
