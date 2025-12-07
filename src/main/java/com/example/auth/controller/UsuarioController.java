package com.example.auth.controller;

import com.example.auth.dto.LoginResponse;
import com.example.auth.model.Usuario;
import com.example.auth.security.JwtUtil;
import com.example.auth.service.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        boolean autenticado = usuarioService.autenticar(usuario.getCpf(), usuario.getSenha());

        if (autenticado) {
            String token = jwtUtil.gerarToken(usuario.getCpf());
            LoginResponse response = new LoginResponse(token, 8 * 60 * 60);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("CPF ou senha inválidos.");
        }
    }

    // ENDPOINT CADASTRAR USUÁRIO
    @PostMapping("/usuarios")
    public ResponseEntity<String> cadastrar(@RequestBody Usuario usuario) {
        boolean cadastrado = usuarioService.cadastrarUsuario(usuario);

        if (cadastrado) {
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } else {
            return ResponseEntity.status(400).body("Erro: CPF já cadastrado.");
        }
    }

    // ENDPOINT PROTEGIDO /PERFIL
    @GetMapping("/perfil")
    public ResponseEntity<String> perfil() {
        return ResponseEntity.ok("Você está logado! Somente usuários com token válido chega aqui!!!");
    }

}
