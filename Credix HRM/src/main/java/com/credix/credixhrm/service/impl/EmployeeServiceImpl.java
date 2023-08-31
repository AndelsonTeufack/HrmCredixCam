package com.credix.credixhrm.service.impl;

import com.credix.credixhrm.enumm.Role;
import com.credix.credixhrm.exceptions.InvalidPasswordException;
import com.credix.credixhrm.exceptions.InvalidRoleException;
import com.credix.credixhrm.repository.EmployeeRepository;
import com.credix.credixhrm.service.EmployeeService;
import com.credix.credixhrm.model.Employee;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Employee getEmployeeById(Integer id) {

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
    public Employee createEmployee(Employee employee) {

        if (employee.getFirstName() == null || employee.getEmail() == null || employee.getLastName() == null ||
                employee.getPassword() == null || employee.getPost() == null || employee.getDepartment() == null ||
                employee.getNumber() == null) {
            throw new IllegalArgumentException("You cannot create an employee with null fields");
        }

        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("Employee with the same email already exists");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employee.setEmail(updatedEmployee.getEmail());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setNumber(updatedEmployee.getNumber());
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setPost(updatedEmployee.getPost());
        employee.setActive(updatedEmployee.isActive());
        employee.setRole(updatedEmployee.getRole());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public Employee archiveEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employee.setActive(false);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee activateEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        employee.setActive(true);
        return employeeRepository.save(employee);
    }

    @Override
    public void updatePassword(Integer employeeId, String oldPassword, String newPassword) throws InvalidPasswordException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        if (!passwordEncoder.matches(oldPassword, employee.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, employee.getPassword())) {
            throw new InvalidPasswordException("New password must be different from the old password");
        }

        String encryptedPassword = passwordEncoder.encode(newPassword);
        employee.setPassword(encryptedPassword);

        employeeRepository.save(employee);
    }

    @Override
    public void updateRole(Integer employeeId, String newRole) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Role role = Role.fromValueIgnoreCase(newRole);

        if (role == null) {
            throw new InvalidRoleException("Invalid role: " + newRole);
        }

        employee.setRole(role);
        employeeRepository.save(employee);
    }
}
