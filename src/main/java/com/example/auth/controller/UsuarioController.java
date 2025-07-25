package com.example.auth.controller;

import com.example.auth.model.Usuario;
import com.example.auth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        boolean autenticado = usuarioService.autenticar(usuario.getCpf(), usuario.getSenha());

        if (autenticado) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(401).body("CPF ou senha inválidos.");
        }
    }
}
