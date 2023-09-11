package com.hrmcredixcam.repository;

import com.hrmcredixcam.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByUserName(String userName);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUserNameOrAndEmailOrTelephone(String username, String email,String telephone);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Boolean existsByTelephone(String telephone);

    List<Employee> findByIsActive(boolean isActive);
}
