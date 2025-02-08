package br.com.ifsp.agendamento.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ALUNO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AlunoEntity {

    @Id
    @Column(name = "id_aluno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    @Column(name = "ra",nullable = false, unique = true)
    private String ra;

    @Column(name = "nome_aluno", nullable = false)
    private String nomeAluno;

    @Column(name = "email_aluno", nullable = false, unique = true)
    private String emailAluno;

    @Column(name = "status_aluno")
    private String statusAluno;

    @Column(name = "senha", nullable = false)
    private String senhaAluno;

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getStatusAluno() {
        return statusAluno;
    }

    public void setStatusAluno(String statusAluno) {
        this.statusAluno = statusAluno;
    }

    public String getSenhaAluno() {
        return senhaAluno;
    }

    public void setSenhaAluno(String senhaAluno) {
        this.senhaAluno = senhaAluno;
    }
}
