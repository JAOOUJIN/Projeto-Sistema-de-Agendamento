package br.com.ifsp.agendamento.dto;

import lombok.Data;

@Data
public class CadastroRecepcionistaRequest {

    private String cdRecep;
    
    private String nome;
    
    private String email;
    
    private String senha;

    public String getCdRecep() {
        return cdRecep;
    }

    public void setCdRecep(String cdRecep) {
        this.cdRecep = cdRecep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
