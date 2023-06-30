package com.KurdDevs.KurdDevs.controller;

import com.KurdDevs.KurdDevs.DTO.UserDto;
import com.KurdDevs.KurdDevs.DTO.UserLoginDto;
import com.KurdDevs.KurdDevs.model.User;
import com.KurdDevs.KurdDevs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirectToSignUp() {

        return "redirect:/register";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        try {
            User createdUser = userService.registerUser(userDto);
            // Perform additional actions if needed

            // Add success message to the model
            model.addAttribute("successMessage", "User registration successful!");
        } catch (Exception e) {
            result.reject("error.user", e.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("userLoginDto") UserLoginDto userLoginDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }

        try {
            // Authenticate the user using your authentication logic
            // For example, you can use Spring Security's AuthenticationManager
            // and AuthenticationProvider to authenticate the user credentials

            // Replace the following line with your authentication logic
            boolean isAuthenticated = userService.authenticateUserByEmail(userLoginDto.getEmail(), userLoginDto.getPassword());

            if (isAuthenticated) {
                // Perform additional actions after successful login, e.g., set authentication in SecurityContext

                // Redirect to the dashboard page
                return "redirect:/dashboard";
            } else {
                // Add error message to the model
                model.addAttribute("errorMessage", "Invalid email or password");
                return "login";
            }
        } catch (Exception e) {
            // Add error message to the model
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }


}
