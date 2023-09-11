package com.hrmcredixcam.service;

import com.hrmcredixcam.exception.DoesNotExistException;
import com.hrmcredixcam.exception.InvalidPasswordException;
import com.hrmcredixcam.exception.ValueAlreadyTakenException;
import com.hrmcredixcam.model.Employee;
import com.hrmcredixcam.publicdtos.UpdateEmployeeDTO;

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

    void updateEmployee(String id, UpdateEmployeeDTO updatedEmployee);

    void deleteEmployee(String id);


    Employee archiveEmployee(String id);

    Employee activateEmployee(String id);

    void updatePassword(String employeeId, String newPassword);

    void changeRole(String employeeId, String newRole);
}
