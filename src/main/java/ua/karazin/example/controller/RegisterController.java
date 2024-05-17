package ua.karazin.example.controller;

import ua.karazin.example.captcha.CaptchaException;
import ua.karazin.example.captcha.CaptchaValidationUtil;
import ua.karazin.example.dto.UserDto;
import ua.karazin.example.service.UserService;
import ua.karazin.example.service.impl.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserRegistrationService registrationService;
    private CaptchaValidationUtil validationUtil;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRegistrationService(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Autowired
    public void setValidationUtil(CaptchaValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        return new ModelAndView("register-page", "registerUser", new UserDto());
    }

    @PostMapping("/register")
    public String registerProcess(
            @Valid @ModelAttribute("registerUser") UserDto userDto, BindingResult result,
            HttpServletRequest request, Model model) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("password", "passwordMatch", "Passwords are not equal");
            result.rejectValue("confirmPassword", "passwordMatch", "Passwords are not equal");
            model.addAttribute("registerUser", userDto);
            return "register-page";
        }
        if (result.hasErrors()) {
            model.addAttribute("registerUser", userDto);
            return "register-page";
        }
        String password = userDto.getPassword();
        try {
            if (validationUtil.isCaptchaValid(request.getParameter("g-recaptcha-response"))) {
                userDto.setPassword(passwordEncoder.encode(password));
                userDto.setConfirmPassword(passwordEncoder.encode(password));
                registrationService.registerNewUser(userDto.toUser());
                return "redirect:/login";
            } else {
                result.rejectValue("captcha", "captchaInvalid", "Please confirm captcha");
                model.addAttribute("registerUser", userDto);
                return "register-page";
            }
        } catch (CaptchaException ex) {
            model.addAttribute("registerUser", userDto);
            return "register-page";
        } catch (RuntimeException e) {
            if (userService.findByLogin(userDto.getLogin()) != null) {
                result.rejectValue("login", "existLogin", "User with this login is already exist");
            }
            if (userService.findByEmail(userDto.getEmail()) != null) {
                result.rejectValue("email", "existEmail", "User with this email is already exist");
            }
            userDto.setPassword(password);
            userDto.setConfirmPassword(password);
            model.addAttribute("registerUser", userDto);
            return "register-page";
        }
    }
}
