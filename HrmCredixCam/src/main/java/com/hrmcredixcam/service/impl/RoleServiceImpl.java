package com.hrmcredixcam.service.impl;

import com.hrmcredixcam.service.RoleService;
import com.hrmcredixcam.authdtos.ERole;
import com.hrmcredixcam.exception.AlreadyExistException;
import com.hrmcredixcam.model.Role;
import com.hrmcredixcam.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getListOfRoleFromListOfRoleStr(Set<String> strRoles){

        Set<Role> roles = new HashSet<>();

        if (strRoles.size()==0) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role User is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByRole(ERole.ROLE_MODERATOR.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Moderation Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByRole(ERole.ROLE_USER.toString())
                                .orElseThrow(() -> new RuntimeException("Error: User Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        return  roles;
    }

    @Override
    public Role saveRole(Role roleData) throws AlreadyExistException {
        var role=roleRepository.existsByRole(roleData.getRole());
        if(role){
            throw new AlreadyExistException("Role",roleData.getRole());
        }
       return  roleRepository.save(roleData);
    }

}
