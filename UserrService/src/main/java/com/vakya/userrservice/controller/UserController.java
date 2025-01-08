package com.vakya.userrservice.controller;

import com.vakya.userrservice.dtos.LoginRequestDto;
import com.vakya.userrservice.exception.UserNotFoundException;
import com.vakya.userrservice.models.User;
import com.vakya.userrservice.services.SpeechServiceClient;
import com.vakya.userrservice.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class UserController {
    private UserService userService;
    private SpeechServiceClient speechServiceClient;

    //private JwtUtil jwtUtil;


    public UserController(UserService userService, SpeechServiceClient speechServiceClient ) {
        this.userService = userService;
        this.speechServiceClient=speechServiceClient;
        //this.jwtUtil=jwtUtil;
    }


    @PostMapping("user/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("user/login")
    public User loginUser(@RequestBody LoginRequestDto loginRequest) throws UserNotFoundException {
        return userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }



    /*@PostMapping("user/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequestDto loginRequest) throws UserNotFoundException {
        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        throw new UserNotFoundException("Invalid credentials");
    }*/


    @GetMapping("user/{id}")
    public User getUserDetails(@PathVariable("id") Long userId) {
        return userService.getUserDetails(userId);
    }

    @DeleteMapping("user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }


}

