package com.KurdDevs.KurdDevs.controller;

import com.KurdDevs.KurdDevs.DTO.UserDto;
import com.KurdDevs.KurdDevs.Repo.UserRepository;
import com.KurdDevs.KurdDevs.cookies.CookieUtils;
import com.KurdDevs.KurdDevs.model.User;
import com.KurdDevs.KurdDevs.service.FileStorageService;
import com.KurdDevs.KurdDevs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/")
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public ProfileController(UserRepository userRepository, UserService userService, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    // Inject necessary dependencies
    @GetMapping("/profile")
    public String showProfile(Model model, HttpServletRequest request) {
        // Check if the user is logged in by verifying the presence of the "loggedInUser" cookie
        String loggedInUserEmail = CookieUtils.getCookieValue(request, "loggedInUser");
        if (loggedInUserEmail == null) {
            return "redirect:/login"; // Redirect to the login page if the cookie is not present
        }

        // Retrieve the user using the email
        User user = userService.getUserByEmail(loggedInUserEmail);
        if (user == null) {
            // User not found, handle the error
            return "redirect:/login";
        }

        // Add user details to the model
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model,
                                HttpServletRequest request,
                                @RequestParam("profileImage") MultipartFile profileImageFile) {
        // Similar to the showProfile method, validate if the user is logged in

        // Retrieve the user using the email from the loggedInUser cookie
        String loggedInUserEmail = CookieUtils.getCookieValue(request, "loggedInUser");
        if (loggedInUserEmail == null) {
            return "redirect:/login"; // Redirect to the login page if the cookie is not present
        }

        User user = userService.getUserByEmail(loggedInUserEmail);
        if (user == null) {
            // User not found, handle the error
            return "redirect:/login";
        }

        // Update the user details with the form data
        user.setUsername(userDto.getUsername());
        user.setMajor(userDto.getMajor());
        user.setAboutSection(userDto.getAboutSection());
        user.setGithubUrl(userDto.getGithubUrl());
        user.setLinkedinUrl(userDto.getLinkedinUrl());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setLocation(userDto.getLocation());
        // Update other fields as needed

        // Handle profile image
        if (!profileImageFile.isEmpty()) {
            String profileImageFileName = saveFile(profileImageFile);
            if (profileImageFileName != null) {
                String profileImageURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(profileImageFileName)
                        .toUriString();
                user.setProfileImage(profileImageURL);
            }
        }

        // Save the updated user
        userService.saveUser(user);

        // Add success message or redirect to a success page

        return "redirect:/profile";
    }

    private String saveFile(MultipartFile file) {
        try {
            String fileName = fileStorageService.saveFile(file);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/profile/details/{id}")
    public String showUserProfileDetailById(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        User loggedInUser = getLoggedInUser(request);
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = userService.GetUserById(id);
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("profileImageURL", user.getProfileImage());

        return "profileDetail";
    }
    @GetMapping("/profile/detail")
    public String showUserProfileDetail( Model model, HttpServletRequest request) {
        String loggedInUserEmail = CookieUtils.getCookieValue(request, "loggedInUser");
        if (loggedInUserEmail == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(loggedInUserEmail);
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("profileImageURL", user.getProfileImage());

        return "profileDetail";
    }

    private User getLoggedInUser(HttpServletRequest request) {
        String loggedInUserEmail = CookieUtils.getCookieValue(request, "loggedInUser");
        if (loggedInUserEmail != null) {
            return userService.getUserByEmail(loggedInUserEmail);
        }
        return null;
    }


}
