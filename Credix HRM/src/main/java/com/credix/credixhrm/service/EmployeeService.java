package com.credix.credixhrm.service;


import com.credix.credixhrm.exceptions.InvalidPasswordException;
import com.credix.credixhrm.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<Employee> getActiveEmployees();

    List<Employee> getArchivedEmployees();

    Employee getEmployeeById(Integer id);

    Employee getEmployeeByEmail(String email);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Integer id, Employee employee);

    void deleteEmployee(Integer id);


    Employee archiveEmployee(Integer id);

    Employee activateEmployee(Integer id);

    void updatePassword(Integer employeeId, String oldPassword, String newPassword) throws InvalidPasswordException;

    void updateRole(Integer employeeId, String newRole);
}
