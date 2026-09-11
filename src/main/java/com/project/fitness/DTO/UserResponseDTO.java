package com.project.fitness.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private String id;
    private String email;
    private String Password;
    private String FirstName;
    private String LastName;
    private LocalDateTime CreatedAt;
    private  LocalDateTime updatedAt;
}
