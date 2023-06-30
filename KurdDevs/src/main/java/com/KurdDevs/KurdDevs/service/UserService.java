package com.KurdDevs.KurdDevs.service;

import com.KurdDevs.KurdDevs.DTO.UserDto;
import com.KurdDevs.KurdDevs.Repo.UserRepository;
import com.KurdDevs.KurdDevs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private EmailService emailService;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User registerUser(UserDto userDto) {
        User existingUser = userRepository.findByUsername(userDto.getUsername());


        existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already exists");
        }

        String activationToken = UUID.randomUUID().toString();
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActivated(false);
        user.setActivationToken(activationToken);

        userRepository.save(user);

        return user;
    }

    public void sendActivationEmail(String recipientEmail, String activationToken) {
        emailService.sendActivationEmail(recipientEmail, activationToken);
    }

    public User getUserByActivationToken(String activationToken) {
        return userRepository.findByActivationToken(activationToken);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean authenticateUserByEmail(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}