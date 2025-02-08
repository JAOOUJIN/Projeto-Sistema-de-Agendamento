package br.com.ifsp.agendamento.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // Pode ser RA para alunos ou cd_recep para recepcionistas
    private String senha;
    private String userType;


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
