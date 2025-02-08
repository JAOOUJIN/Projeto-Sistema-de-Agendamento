package br.com.ifsp.agendamento.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CadastroRequest {
    private Long idRecepcionista;
    private Long idAgendamento;
    private String raAluno;

    public Long getIdRecepcionista() {
        return idRecepcionista;
    }

    public void setIdRecepcionista(Long idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }

    public Long getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Long idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public String getRaAluno() {
        return raAluno;
    }

    public void setRaAluno(String raAluno) {
        this.raAluno = raAluno;
    }

}