package com.example.auth.service;

import com.example.auth.model.Usuario;
import com.example.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // BCrypt com cost 12 → padrão seguro 2025
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // AUTENTICAR
    public boolean autenticar(String cpf, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(cpf);
        if (usuarioOpt.isPresent()) {
            return encoder.matches(senha, usuarioOpt.get().getSenha());
        }
        return false;
    }

    // CADASTRAR
    public boolean cadastrarUsuario(Usuario novoUsuario) {
        if (usuarioRepository.existsById(novoUsuario.getCpf())) {
            return false;
        }

        // AQUI ELE CRIPTOGRAFA A SENHA ANTES DE SALVAR
        novoUsuario.setSenha(encoder.encode(novoUsuario.getSenha()));

        usuarioRepository.save(novoUsuario);
        return true;
    }
}