package com.br.educaweb.controller;

import com.br.educaweb.dto.LoginRequestDTO;
import com.br.educaweb.dto.LoginResponseDTO;
import com.br.educaweb.model.User;
import com.br.educaweb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequest) {

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            String fakeToken = UUID.randomUUID().toString();

            LoginResponseDTO response = LoginResponseDTO.builder()
                    .message("Login realizado com sucesso")
                    .token(fakeToken)
                    .build();

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            LoginResponseDTO response = LoginResponseDTO.builder()
                    .message("Usuário ou senha inválidos")
                    .token(null)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (AuthenticationException e) {
            LoginResponseDTO response = LoginResponseDTO.builder()
                    .message("Falha de autenticação")
                    .token(null)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (Exception e) {
            LoginResponseDTO response = LoginResponseDTO.builder()
                    .message("Erro interno no servidor")
                    .token(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
