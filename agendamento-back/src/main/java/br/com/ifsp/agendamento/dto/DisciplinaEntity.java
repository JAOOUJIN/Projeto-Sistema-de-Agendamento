package br.com.ifsp.agendamento.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "DISCIPLINA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplinaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long idDisciplina;

    @Column(name = "nome_disciplina", nullable = false, length = 50)
    private String nomeDisciplina;

    @Column(name = "carga_horaria" , nullable = false)
    private Long cargaHoraria;

    @Column(name = "numero_alunos" , nullable = false)
    private Long numeroAlunos;

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public Long getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Long cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Long getNumeroAlunos() {
        return numeroAlunos;
    }

    public void setNumeroAlunos(Long numeroAlunos) {
        this.numeroAlunos = numeroAlunos;
    }
}
