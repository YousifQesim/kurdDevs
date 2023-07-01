package com.KurdDevs.KurdDevs.controller;

import com.KurdDevs.KurdDevs.DTO.UserDto;
import com.KurdDevs.KurdDevs.DTO.UserLoginDto;
import com.KurdDevs.KurdDevs.Repo.UserRepository;
import com.KurdDevs.KurdDevs.cookies.CookieUtils;
import com.KurdDevs.KurdDevs.model.User;
import com.KurdDevs.KurdDevs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
            User existingUser = userService.getUserByEmail(userDto.getEmail());
            if (existingUser != null) {
                model.addAttribute("errorMessage", "User with the provided email already exists.");
                return "registration";
            }

            User createdUser = userService.registerUser(userDto);
            userService.sendActivationEmail(createdUser.getEmail(), createdUser.getActivationToken());

            model.addAttribute("successMessage", "User registration successful! Please check your email for activation instructions.");
            return "registration";
        } catch (Exception e) {
            result.reject("error.user", e.getMessage());
            return "registration";
        }


    }

    @GetMapping("/activate")
    public String activateUser(@RequestParam("activationToken") String activationToken, Model model) {
        User user = userService.getUserByActivationToken(activationToken);

        if (user != null) {
            user.setActivated(true);
            userService.saveUser(user);

            model.addAttribute("successMessage", "Your account has been activated successfully!");
        } else {
            model.addAttribute("errorMessage", "Invalid activation token");
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("userLoginDto") UserLoginDto userLoginDto, BindingResult result, Model model, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "login";
        }

        try {
            boolean isAuthenticated = userService.authenticateUserByEmail(userLoginDto.getEmail(), userLoginDto.getPassword());

            if (isAuthenticated) {
                User user = userService.getUserByEmail(userLoginDto.getEmail());
                if (!user.isActivated()) {
                    model.addAttribute("errorMessage", "Your account is not activated. Please check your email for activation instructions.");
                    return "login";
                } else {
                    // Add the cookie here
                    CookieUtils.addCookie(response, "loggedInUser", user.getEmail(), 3600, "/");

                    return "redirect:/dashboard";
                }
            } else {
                model.addAttribute("errorMessage", "Invalid email or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }
    @GetMapping("/")
    public String RedirectToDashboard(Model model, HttpServletRequest request) {
        // Check if the user is logged in by verifying the presence of the "loggedInUser" cookie
        String loggedInUser = CookieUtils.getCookieValue(request, "loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to the login page if the cookie is not present
        }

        // Continue with rendering the dashboard page
        // ...
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpServletRequest request) {
        // Check if the user is logged in by verifying the presence of the "loggedInUser" cookie
        String loggedInUser = CookieUtils.getCookieValue(request, "loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to the login page if the cookie is not present
        }

        // Continue with rendering the dashboard page
        // ...
        List<User> AllUsers = userRepository.findAll();
        model.addAttribute("Allusers", AllUsers);
        return "/dashboardpage";
    }
}