package com.hrmcredixcam.service;

import com.hrmcredixcam.exception.DoesNotExistException;
import com.hrmcredixcam.exception.InvalidPasswordException;
import com.hrmcredixcam.exception.ValueAlreadyTakenException;
import com.hrmcredixcam.model.Employee;

import java.util.List;

public interface EmployeeService {
    boolean verifyIfUserNameExist(String userName) throws ValueAlreadyTakenException;

    boolean verifyIfEmailExist(String email) throws ValueAlreadyTakenException;

    Employee saveEmployee(Employee user);

    Employee findByUserName(String userName) throws DoesNotExistException;

    List<Employee> getAllEmployees();

    List<Employee> getActiveEmployees();

    List<Employee> getArchivedEmployees();

    Employee getEmployeeById(String id);

    Employee getEmployeeByEmail(String email);


    Employee updateEmployee(String id, Employee employee);

    void deleteEmployee(String id);


    Employee archiveEmployee(String id);

    Employee activateEmployee(String id);

    void updatePassword(String employeeId, String oldPassword, String newPassword) throws InvalidPasswordException;

    void updateRole(String employeeId, String newRole);
}
