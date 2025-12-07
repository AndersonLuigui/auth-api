package com.example.auth.service;

import com.example.auth.model.Usuario;
import com.example.auth.repository.UsuarioRepository;  // Importe!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // METODO PARA AUTENTICAR USUARIO
    public boolean autenticar(String cpf, String senha) {
        Optional<Usuario> usuarioOtp = usuarioRepository.findById(cpf); // BUSCAR USUARIO POR CPF
        if (usuarioOtp.isPresent()) {
            Usuario u = usuarioOtp.get();
            return u.getSenha().equals(senha); // VALIDA SE A SENHA COINCIDE E RETORNA TRUE
        }
        return false;
    }

    public boolean cadastrarUsuario(Usuario novoUsuario) {
        if (usuarioRepository.existsById(novoUsuario.getCpf())) { // VALIDA SE O USER JÁ EXISTE
            return false;
        }
        usuarioRepository.save(novoUsuario); // CASO NÃO, SALVA NO BANCO
        return true;
    }



}


