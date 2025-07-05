package br.appLogin.br.appLogin.controllers;

import br.appLogin.br.appLogin.models.UserModel;
import br.appLogin.br.appLogin.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logar")
    public String logar(@Valid UserModel user, HttpServletResponse response) {
        UserModel userModel = this.userRepository.login(user.getEmail(), user.getSenha());
        if (userModel != null) {
            return "redirect:/";
        }
        return "login/login";
    }


    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String cadastro(@Valid UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/cadastro";
        }
        userRepository.save(userModel);
        return "";
    }

}
