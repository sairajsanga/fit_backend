package com.project.fitness.service;

import com.project.fitness.DTO.Loginrequest;
import com.project.fitness.DTO.UserRequestDTO;
import com.project.fitness.DTO.UserResponseDTO;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.model.UserRole;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User mapToEntity(UserRequestDTO userRequestDTO){
        UserRole role=userRequestDTO.getRole()!=null?userRequestDTO.getRole(): UserRole.USER;
        User user=User.builder()
                .email(userRequestDTO.getEmail())
                .Password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .FirstName(userRequestDTO.getFirstName())
                .LastName(userRequestDTO.getLastName())
                .role(role)
                .build();
        return  user;
    }

    public UserResponseDTO mapToResponse(User user){
        UserResponseDTO userResponseDTO=UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .Password(user.getPassword())
                .FirstName(user.getFirstName())
                .LastName(user.getLastName())
                .CreatedAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
        return  userResponseDTO;
    }
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
       User saveduser= mapToEntity(userRequestDTO);
          userRepository.save(saveduser);
          return mapToResponse(saveduser);
    }

    public List<UserResponseDTO> get(){
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> output=new ArrayList<>();
        for(User u:users){
            output.add(mapToResponse(u));
        }
        return output;
    }

    public List<Activity> registerActivity(Activity activities){
        List<Activity> savedActivities=new ArrayList<>();
        savedActivities.add(activities);
        User u=activities.getUser();
        userRepository.save(u);
        return savedActivities;
    }

    public User authenicateUser(Loginrequest loginrequest){
        User user=userRepository.findByEmail(loginrequest.getEmail());

        if(user==null) throw new RuntimeException("Invalid Credintials");

        if(!passwordEncoder.matches(loginrequest.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Credintials");
        }
        return  user;
    }
}
