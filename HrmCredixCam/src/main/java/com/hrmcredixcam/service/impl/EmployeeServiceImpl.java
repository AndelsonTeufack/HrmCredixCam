package com.hrmcredixcam.service.impl;

import com.hrmcredixcam.authdtos.ERole;
import com.hrmcredixcam.exception.DoesNotExistException;
import com.hrmcredixcam.exception.InvalidRoleException;
import com.hrmcredixcam.exception.ValueAlreadyTakenException;
import com.hrmcredixcam.model.Employee;
import com.hrmcredixcam.model.Role;
import com.hrmcredixcam.publicdtos.UpdateEmployeeDTO;
import com.hrmcredixcam.repository.EmployeeRepository;
import com.hrmcredixcam.repository.RoleRepository;
import com.hrmcredixcam.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public boolean verifyIfUserNameExist(String userName) throws ValueAlreadyTakenException {
        var bool=  employeeRepository.existsByUserName(userName);
        if (bool){
            throw new  ValueAlreadyTakenException("Employee",userName);
        }
        return false;
    }

    @Override
    public boolean verifyIfEmailExist(String email) throws ValueAlreadyTakenException {
        var bool=  employeeRepository.existsByEmail(email);
        if (bool){
            throw new  ValueAlreadyTakenException("Email",email);
        }
        return false;
    }

    @Override
    public Employee saveEmployee(Employee user){
        return employeeRepository.save(user);
    }

    @Override
    public  Employee findByUserName(String userName) throws DoesNotExistException {
        return  employeeRepository.findByUserName(userName).orElseThrow(()->new DoesNotExistException("Employee",userName));
    }


    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByIsActive(true);
    }

    @Override
    public List<Employee> getArchivedEmployees() {
        return employeeRepository.findByIsActive(false);
    }

    @Override
    public Employee getEmployeeById(String id) {

        return employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

    }

    @Override
    public Employee getEmployeeByEmail(String email){
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
    }

    @Override
    public void updateEmployee(String id, UpdateEmployeeDTO updatedEmployee){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        if(updatedEmployee.getFirstName() != null){
            employee.setFirstName(updatedEmployee.getFirstName());
        }
        if(updatedEmployee.getUserName() != null){
            employee.setUserName(updatedEmployee.getUserName());
        }
        if(updatedEmployee.getEmail() != null){
            employee.setEmail(updatedEmployee.getEmail());
        }
        if(updatedEmployee.getDepartment() != null){
            employee.setDepartment(updatedEmployee.getDepartment());
        }
        if(updatedEmployee.getTelephone() != null){
            employee.setTelephone(updatedEmployee.getTelephone());
        }
        if(updatedEmployee.getLastName() != null){
            employee.setLastName(updatedEmployee.getLastName());
        }
        if(updatedEmployee.getPost() != null){
            employee.setPost(updatedEmployee.getPost());
        }

        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public Employee archiveEmployee(String id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employee.setActive(false);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee activateEmployee(String id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        employee.setActive(true);
        return employeeRepository.save(employee);
    }

    @Override
    public void updatePassword(String employeeId, String newPassword){
        if (employeeId == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }

    @Override
    public void changeRole(String employeeId, String newRole) {

        Role roleToUpdate = null;

        String lowerCaseNewRole = newRole.toLowerCase();

        switch (lowerCaseNewRole) {
            case "admin":
                roleToUpdate = roleRepository.findByRole(ERole.ROLE_ADMIN.toString())
                        .orElseThrow(() -> new InvalidRoleException("Error: Admin Role is not found."));
                break;
            case "superadmin":
                roleToUpdate = roleRepository.findByRole(ERole.ROLE_SUPERADMIN.toString())
                        .orElseThrow(() -> new InvalidRoleException("Error: Superadmin Role is not found."));
                break;
            case "user":
                roleToUpdate = roleRepository.findByRole(ERole.ROLE_USER.toString())
                        .orElseThrow(() -> new InvalidRoleException("Error: User Role is not found."));
                break;
            default:
                throw new RuntimeException("Error: Invalid Role");
        }

        Employee userToUpdate = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Error: Employee not found."));


        userToUpdate.setRoles(Collections.singleton(roleToUpdate));
        employeeRepository.save(userToUpdate);
    }
}
