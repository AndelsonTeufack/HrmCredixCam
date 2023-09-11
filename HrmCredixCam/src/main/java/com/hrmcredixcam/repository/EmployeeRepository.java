package com.hrmcredixcam.repository;

import com.hrmcredixcam.model.Employee;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByUserName(String userName);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUserNameOrAndEmailOrTelephone(String username, String email,String telephone);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Boolean existsByTelephone(String telephone);

    List<Employee> findByIsActive(boolean isActive);
}
