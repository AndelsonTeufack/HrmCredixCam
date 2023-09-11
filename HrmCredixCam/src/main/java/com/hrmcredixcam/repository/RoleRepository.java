package com.hrmcredixcam.repository;

import com.hrmcredixcam.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {
    Optional<Role>  findByRole(String role);

     boolean existsByRole(String role);
}
