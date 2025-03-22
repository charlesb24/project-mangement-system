package com.example.charlesb.projectmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    @Size(min = 8, max = 63)
    private String password;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    private String middleName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @Email
    private String workEmail;

    @Email
    private String personalEmail;

    private String workPhone;
    private String mobilePhone;

    private Long managerId;

}
