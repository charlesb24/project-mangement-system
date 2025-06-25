package com.example.charlesb.projectmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, max = 63)
    private String password;

    private Set<String> roles = new HashSet<>();

    private boolean enabled;
    private boolean locked;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    private String middleName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;
    private String phone;

    private String contactMethod;

    private Long managerId;

}
