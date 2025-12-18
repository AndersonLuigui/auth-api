package com.example.auth.controller;

import com.example.auth.dto.ApiResponse;
import com.example.auth.dto.LoginResponse;
import com.example.auth.model.Usuario;
import com.example.auth.security.JwtUtil;
import com.example.auth.service.UsuarioService;
import org.apache.coyote.Response;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Usuario usuario) {
        boolean autenticado = usuarioService.autenticar(usuario.getCpf(), usuario.getSenha());

        if (autenticado) {
            String token = jwtUtil.gerarToken(usuario.getCpf());
            LoginResponse loginResponse = new LoginResponse(token, 8 * 60 * 60);

            ApiResponse response = new ApiResponse(true, "Login realizado com sucesso", loginResponse);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(false, "CPF ou senha inválidos", null);
            return ResponseEntity.status(401).body(response);
        }
    }

    // ENDPOINT CADASTRAR USUÁRIO
    @PostMapping("/usuarios")
    public ResponseEntity<ApiResponse> cadastrar(@RequestBody Usuario usuario) {
        boolean cadastrado = usuarioService.cadastrarUsuario(usuario);

        if (cadastrado) {
            ApiResponse response = new ApiResponse(true, "Usuário cadastrado com sucesso", null);
            return ResponseEntity.status(201).body(response); // 201 Created é melhor pra cadastro
        } else {
            ApiResponse response = new ApiResponse(false, "CPF já cadastrado ou limite de usuários atingido", null);
            return ResponseEntity.status(400).body(response); // 400 Bad Request
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<ApiResponse> perfil() {
        // Aqui você pode pegar o CPF do token se quiser (mas por agora simples)
        ApiResponse response = new ApiResponse(true, "Você está autenticado com sucesso", null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/usuarios")
    public ResponseEntity<ApiResponse> deletarUsuario(org.springframework.security.core.Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Usuário não autenticado", null));
        }

        String cpf = authentication.getName();

        boolean deletado = usuarioService.deletarUsuario(cpf);

        if (deletado) {
            return ResponseEntity.ok(new ApiResponse(true, "Usuário deletado com sucesso", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Usuário não encontrado", null));
        }
    }


    // UPDATE DE SENHA
    @PutMapping("/usuarios/senha")
    public ResponseEntity<ApiResponse> atualizarSenha(
            org.springframework.security.core.Authentication authentication,
            @RequestBody Map<String, String> requestBody) {

        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Usuário não autenticado", null));
        }

        String cpf = authentication.getName();
        String novaSenha = requestBody.get("senha");

        if (novaSenha == null || novaSenha.isBlank()) {
            return ResponseEntity.status(400).body(new ApiResponse(false, "Nova senha é obrigatória", null));
        }

        boolean atualizado = usuarioService.atualizarSenha(cpf, novaSenha);

        if (atualizado) {
            return ResponseEntity.ok(new ApiResponse(true, "Senha atualizada com sucesso", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Usuário não encontrado", null));
        }
    }



}
