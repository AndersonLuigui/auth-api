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
        // Limite de 100 usuários
        if (usuarioRepository.count() >= 100) {
            return false; // ou lança exceção
        }

        if (usuarioRepository.existsById(novoUsuario.getCpf())) {
            return false;
        }

        novoUsuario.setSenha(encoder.encode(novoUsuario.getSenha()));
        usuarioRepository.save(novoUsuario);
        return true;
    }

    // DELETAR
    public boolean deletarUsuario(String cpf) {
        if (usuarioRepository.existsById(cpf)) {
            usuarioRepository.deleteById(cpf);
            return true;
        }
        return  false;
    }

    // UPDATE SENHA SERVICE - LOGICA
    public boolean atualizarSenha(String cpf, String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(cpf);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            usuario.setSenha(encoder.encode(novaSenha));

            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }
}