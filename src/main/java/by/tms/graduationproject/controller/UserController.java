package by.tms.graduationproject.controller;


import by.tms.graduationproject.entity.User;
import by.tms.graduationproject.service.UserService;
import by.tms.graduationproject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final ImageService imageService;

    @Value("${upload.path}")
    private String path;

    @GetMapping("/reg")
    public String createNewCoach(Model model) {
        return "reg";
    }

    @PostMapping("/reg")
    public String save(MultipartFile image, User user, Model model) throws IOException {
        String resultImageName = imageService.upload(image, path);
        user.setImageName(resultImageName);
        if (!userService.saveUser(user)) {
            model.addAttribute("errorMessage", "User username" + user.getUsername() + " is exist!");
            return "reg";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String fillLogin() {
        return "login";
    }

    @GetMapping("/info/{id}")
    public String coachInfo(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "page";
    }

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("loggedUser", user);
        return "account";
    }
}
