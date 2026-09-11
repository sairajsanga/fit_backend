package com.project.fitness.DTO;

import com.project.fitness.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 5,max = 10,message = "password must be valid length")
    private String Password;

    private String FirstName;

    @NotBlank(message = "LastName is required")
    @Size(min = 2,max = 10,message = "LastName must be valid length")
    private String LastName;

    private UserRole role;


}
