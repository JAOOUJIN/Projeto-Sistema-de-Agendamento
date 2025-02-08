// AlterarSenhaRequest.java

package br.com.ifsp.agendamento.dto;

import lombok.Data;

@Data
public class AlterarSenhaRequest {
    private String novaSenha;
    private String senhaAtual; // Para verificar se a senha atual está correta

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }
}