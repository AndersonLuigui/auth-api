package com.example.auth.repository;

import com.example.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Diz para Spring que é um repo
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // JpaRepository<Classe, TipoDoId> - Aqui ID é String (CPF)

    // Métodos prontos: save(), findById(), etc.
}