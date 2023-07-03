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

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model,
                                HttpServletRequest request,
                                @RequestParam("profileImage") MultipartFile profileImageFile,
                                @RequestParam("pdfCv") MultipartFile pdfCvFile) {
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
        // Update other fields as needed

        // Handle profile image
        if (!profileImageFile.isEmpty()) {
            String profileImageFilePath = saveFile(profileImageFile);
            String profileImageURL = "http://localhost:8080/uploads/" + profileImageFilePath;
            user.setProfileImage(profileImageURL);

        }

//        // Handle PDF CV upload
//        if (!pdfCvFile.isEmpty()) {
//            // Save the PDF CV file and set the path/URL in the user model
//            String pdfCvFilePath = saveFile(pdfCvFile);
//            user.setPdfCv(pdfCvFilePath);
//        }

        // Save the updated user
        userService.saveUser(user);

        // Add success message or redirect to a success page

        return "redirect:/profile";
    }

    private String saveFile(MultipartFile file) {
        try {
            String fileName = fileStorageService.saveFile(file);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    @GetMapping("/profile/downloadPdfCv/{userId}")
//    public ResponseEntity<Resource> downloadPdfCv(@PathVariable("userId") Long userId) {
//        User user = userRepository.findById(userId).orElse(null);
//        if (user == null || user.getPdfCv() == null) {
//            // Handle error if user or PDF CV is not found
//            return ResponseEntity.notFound().build();
//        }
//
//        try {
//            Resource resource = fileStorageService.loadFileAsResource(user.getPdfCv());
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                    .body(resource);
//        } catch (IOException e) {
//            // Handle error if PDF CV cannot be loaded
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    @GetMapping("/profile/detail")
    public String showUserProfileDetail(Model model, HttpServletRequest request) {
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
        return "profileDetail";
    }


    // Rest of the controller methods

}
