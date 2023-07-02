package com.KurdDevs.KurdDevs.service;

import com.KurdDevs.KurdDevs.DTO.UserDto;
import com.KurdDevs.KurdDevs.model.User;
import com.KurdDevs.KurdDevs.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User registerUser(UserDto userDto) {
        User existingUser = userRepository.findByEmail(userDto.getEmail());

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

    public User activateUser(String activationToken) {
        User user = userRepository.findByActivationToken(activationToken);

        if (user != null) {
            user.setActivated(true);
            userRepository.save(user);
            return user;
        }

        return null;
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User getUserByActivationToken(String activationToken) {
        return userRepository.findByActivationToken(activationToken);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
