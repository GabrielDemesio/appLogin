package br.appLogin.br.appLogin.controllers;

import br.appLogin.br.appLogin.models.UserModel;
import br.appLogin.br.appLogin.repositories.UserRepository;
import br.appLogin.br.appLogin.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/logar")
    public ResponseEntity<?> logar(@RequestBody @Valid UserModel user, HttpServletResponse response) throws UnsupportedEncodingException {
        UserModel userModel = this.userRepository.login(user.getEmail(), user.getSenha());
        if (userModel != null) {
            CookieService.setCookie(response, "userId", String.valueOf(userModel.getId()), 10000);
            CookieService.setCookie(response, "nameUser", String.valueOf(userModel.getNome()), 10000);
            return ResponseEntity.ok(userModel);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha invalido");
    }
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody @Valid UserModel userModel) {
        if (userModel.getNome().isEmpty() || userModel.getSenha().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome ou senha invalido");
        }

        UserModel savedUser = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}
