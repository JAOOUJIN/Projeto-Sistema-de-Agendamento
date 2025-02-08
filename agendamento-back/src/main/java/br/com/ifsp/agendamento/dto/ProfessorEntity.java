package br.com.ifsp.agendamento.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROFESSOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prof")
    private Long idProf;

    @Column(name = "nome_prof", nullable = false, length = 50)
    private String nomeProf;

    @Column(name = "email_prof", nullable = false, unique = true)
    private String emailProf;

    @Column(name = "status_prof", nullable = false)
    private String statusProf;

    public Long getIdProf() {
        return idProf;
    }

    public void setIdProf(Long idProf) {
        this.idProf = idProf;
    }

    public String getNomeProf() {
        return nomeProf;
    }

    public void setNomeProf(String nomeProf) {
        this.nomeProf = nomeProf;
    }

    public String getEmailProf() {
        return emailProf;
    }

    public void setEmailProf(String emailProf) {
        this.emailProf = emailProf;
    }

    public String getStatusProf() {
        return statusProf;
    }

    public void setStatusProf(String statusProf) {
        this.statusProf = statusProf;
    }
}
