package com.KurdDevs.KurdDevs.controller;

import com.KurdDevs.KurdDevs.model.User;
import com.KurdDevs.KurdDevs.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        User createdUser = userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto) {
        UsernamePasswordAuthenticationToken authentication = userService.loginUser(userLoginDto);
        return ResponseEntity.ok(authentication);
    }
}
