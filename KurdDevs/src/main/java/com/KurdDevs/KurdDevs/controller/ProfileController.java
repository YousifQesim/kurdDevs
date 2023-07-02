package com.KurdDevs.KurdDevs.controller;

import com.KurdDevs.KurdDevs.model.User;
import com.KurdDevs.KurdDevs.service.FileStorageService;
import com.KurdDevs.KurdDevs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ProfileController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Autowired
    public ProfileController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.getUserByUsername(username); // Assuming you have a method to retrieve the user by username
            if (user != null) {
                model.addAttribute("email", user.getEmail());
                model.addAttribute("username", user.getUsername());
            }
        }

        model.addAttribute("user", new User()); // Add the user object to the model

        return "profile";
    }

    @PostMapping("/profile/save")
    public String saveProfile(@Valid @ModelAttribute("user") User user,
                              BindingResult bindingResult,
                              @RequestParam("profileImage") MultipartFile profileImage,
                              @RequestParam("pdfCv") MultipartFile pdfCv,
                              RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            System.out.println("fail");
            return "profile";
        }

        if (!profileImage.isEmpty()) {
            String profileImageFileName = StringUtils.cleanPath(profileImage.getOriginalFilename());
            user.setProfileImage(profileImageFileName);
            fileStorageService.saveFile(profileImage, profileImageFileName);
        }

        if (!pdfCv.isEmpty()) {
            String pdfCvFileName = StringUtils.cleanPath(pdfCv.getOriginalFilename());
            user.setPdfCv(pdfCvFileName);
            fileStorageService.saveFile(pdfCv, pdfCvFileName);
        }

        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("successMessage", "Profile saved successfully.");
        System.out.println("success");
        return "redirect:/profile";
    }
}
