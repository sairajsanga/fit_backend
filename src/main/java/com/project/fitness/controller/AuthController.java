package com.project.fitness.controller;

import com.project.fitness.DTO.LoginResponse;
import com.project.fitness.DTO.Loginrequest;
import com.project.fitness.DTO.UserRequestDTO;
import com.project.fitness.DTO.UserResponseDTO;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.security.JwtUtil;
import com.project.fitness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Value("${FRONTEND.URL}")
    private static String frontend_url;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> Register(@Valid @RequestBody UserRequestDTO userRequestDTO){
        System.out.println(frontend_url);
      return ResponseEntity.ok(userService.create(userRequestDTO));
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.get());
    }


    @PostMapping("/activities")
    public ResponseEntity<List<Activity>> createActivity(@RequestBody Activity activity){
        return ResponseEntity.ok(userService.registerActivity(activity));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Loginrequest loginrequest){
        try{
            User user=userService.authenicateUser(loginrequest);

            String jwtToken= jwtUtil.generateJwtToken(user.getId(),user.getRole().name());

            return ResponseEntity.ok(new LoginResponse(jwtToken,userService.mapToResponse(user)));

        }catch (AuthenticationException e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }


}
