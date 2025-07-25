package com.example.auth.service;

import com.example.auth.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        // Usuários cadastrados na "base"
        usuarios.add(new Usuario("06950708190", "1234"));
        usuarios.add(new Usuario("12345678900", "senha123"));
    }

    public boolean autenticar(String cpf, String senha) {
        return usuarios.stream()
                .anyMatch(u -> u.getCpf().equals(cpf) && u.getSenha().equals(senha));
    }
}


