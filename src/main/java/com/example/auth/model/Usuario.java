package com.example.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Diz: Essa classe é uma tabela no DB
@Table(name = "usuarios")
public class Usuario {
    @Id // chave primária
    private String cpf; // CPF como ID (pra simplicidade, mas em real use UUID ou auto-increment)
    private String senha;

    public Usuario() {
    }

    public Usuario(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
