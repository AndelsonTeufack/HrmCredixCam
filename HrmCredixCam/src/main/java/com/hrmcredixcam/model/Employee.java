package com.hrmcredixcam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userName;

    private String firstName;

    private String lastName;

    private String telephone;

    private String password;

    private String email;

    private String post;

    private String department;

    private LocalDateTime creationDate;

    @Column(name = "Activate")
    private boolean isActive=true;

    @OneToMany
    private Set<Role> roles;
}

