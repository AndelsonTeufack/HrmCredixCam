package com.hrmcredixcam.authdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupUserRequestDTO {

    private String userName;
    private String firstName;
    private String lastName;
    private String post;
    private String password;
    private String department;
    private String telephone;
    private String email;

}
