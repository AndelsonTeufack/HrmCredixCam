package com.hrmcredixcam.service.impl;

import com.hrmcredixcam.exception.DoesNotExistException;
import com.hrmcredixcam.exception.InvalidPasswordException;
import com.hrmcredixcam.exception.ValueAlreadyTakenException;
import com.hrmcredixcam.model.Employee;
import com.hrmcredixcam.repository.EmployeeRepository;
import com.hrmcredixcam.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

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

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        return employee;

    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with email: " + email));
    }

    @Override
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        if(updatedEmployee.getFirstName() != null){
            employee.setFirstName(updatedEmployee.getFirstName());
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

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public Employee archiveEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employee.setActive(false);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee activateEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employee.setActive(true);
        return employeeRepository.save(employee);
    }

    @Override
    public void updatePassword(String employeeId, String oldPassword, String newPassword) throws InvalidPasswordException {
        if (employeeId == null || oldPassword == null || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        if (!passwordEncoder.matches(oldPassword, employee.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, employee.getPassword())) {
            throw new InvalidPasswordException("New password must be different from the old password");
        }

        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }

    @Override
    public void updateRole(String employeeId, String newRole) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
//
//        Role role = Role.fromValueIgnoreCase(newRole);
//
//        if (role == null) {
//            throw new InvalidRoleException("Invalid role: " + newRole);
//        }
//
//        employee.setRole(role);
//        employeeRepository.save(employee);
    }
}
