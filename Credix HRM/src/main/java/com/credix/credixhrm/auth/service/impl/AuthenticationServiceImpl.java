package com.credix.credixhrm.auth.service.impl;

import com.credix.credixhrm.auth.model.AuthenticationRequest;
import com.credix.credixhrm.auth.model.AuthenticationResponse;
import com.credix.credixhrm.auth.model.RegisterRequest;
import com.credix.credixhrm.auth.service.AuthenticationService;
import com.credix.credixhrm.config.JwtService;
import com.credix.credixhrm.repository.EmployeeRepository;
import com.credix.credixhrm.model.Employee;
import com.credix.credixhrm.enumm.Role;
import com.credix.credixhrm.utils.EmployeeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.credix.credixhrm.constents.EmployeeConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        log.info("Inside signup {}", request);
        try {
            // Créer un nouvel employé avec les informations fournies dans la requête
            Employee user = Employee.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .number(request.getNumber())
                    .post(request.getPost())
                    .department(request.getDepartment())
                    .isActive(true)
                    .role(Role.USER)
                    .build();

            // Vérifier si l'email de l'utilisateur existe déjà
            if (employeeRepository.findByEmail(user.getEmail()).isEmpty()) {
                // Vérifier si les données de l'employé sont non nulles
                if (EmployeeUtils.employeeIsNotNull(user)) {
                    // Enregistrer l'employé dans le référentiel
                    employeeRepository.save(user);
                    log.info("Successfully Registered! ");

                    // Générer le jeton JWT pour l'employé enregistré
                    String jwtToken = jwtService.generateToken(user);

                    // Retourner une réponse réussie avec le jeton JWT
                    return EmployeeUtils.getResponseEntity("Successfully Registered!", HttpStatus.OK, jwtToken);
                } else {
                    log.info("null data. ");
                    return EmployeeUtils.getResponseEntity(NOT_NULL_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.info("Email already exists! ");
                return EmployeeUtils.getResponseEntity("Email already exists!", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return EmployeeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            // Authentifier l'utilisateur avec le gestionnaire d'authentification
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Vérifier si l'employé existe dans le référentiel par email
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(request.getEmail());
            if (optionalEmployee.isPresent()) {
                Employee user = optionalEmployee.get();

                // Comparer les mots de passe hachés
                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    // Générer le jeton JWT
                    String jwtToken = jwtService.generateToken(user);

                    // Retourner une réponse réussie avec le jeton JWT
                    return EmployeeUtils.getResponseEntity("Successfully logged in!", HttpStatus.OK, jwtToken);
                } else {
                    // Le mot de passe est incorrect
                    return EmployeeUtils.getResponseEntity(BAD_PASSWORD, HttpStatus.UNAUTHORIZED);
                }
            } else {
                // Aucun employé avec cet email n'a été trouvé
                return EmployeeUtils.getResponseEntity(BAD_EMAIL, HttpStatus.NOT_FOUND);
            }

        } catch (AuthenticationException e) {
            // Erreur d'authentification (mauvais email/mot de passe)
            return EmployeeUtils.getResponseEntity("Invalid email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Gérer les autres exceptions
            e.printStackTrace();
            return EmployeeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

