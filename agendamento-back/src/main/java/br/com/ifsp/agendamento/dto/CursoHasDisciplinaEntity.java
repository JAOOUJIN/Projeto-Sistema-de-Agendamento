package br.com.ifsp.agendamento.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Curso_has_DISCIPLINA")
@Data
@Builder
public class CursoHasDisciplinaEntity {

    @EmbeddedId
    private CursoHasDisciplinaId id;

    @ManyToOne
    @MapsId("idCurso")
    @JoinColumn(name = "Curso_id_curso")
    private CursoEntity curso;

    @ManyToOne
    @MapsId("idDisciplina")
    @JoinColumn(name = "DISCIPLINA_id_disciplina")
    private DisciplinaEntity disciplina;

    // Construtor padrão
    public CursoHasDisciplinaEntity() {
    }

    // Construtor com argumentos
    public CursoHasDisciplinaEntity(CursoHasDisciplinaId id, CursoEntity curso, DisciplinaEntity disciplina) {
        this.id = id;
        this.curso = curso;
        this.disciplina = disciplina;
    }

    // Getters e Setters
    public CursoHasDisciplinaId getId() {
        return id;
    }

    public void setId(CursoHasDisciplinaId id) {
        this.id = id;
    }

    public CursoEntity getCurso() {
        return curso;
    }

    public void setCurso(CursoEntity curso) {
        this.curso = curso;
    }

    public DisciplinaEntity getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaEntity disciplina) {
        this.disciplina = disciplina;
    }

    // Implementação manual do builder
    public static CursoHasDisciplinaBuilder builder() {
        return new CursoHasDisciplinaBuilder();
    }

    public static class CursoHasDisciplinaBuilder {
        private CursoHasDisciplinaId id;
        private CursoEntity curso;
        private DisciplinaEntity disciplina;

        public CursoHasDisciplinaBuilder id(CursoHasDisciplinaId id) {
            this.id = id;
            return this;
        }

        public CursoHasDisciplinaBuilder curso(CursoEntity curso) {
            this.curso = curso;
            return this;
        }

        public CursoHasDisciplinaBuilder disciplina(DisciplinaEntity disciplina) {
            this.disciplina = disciplina;
            return this;
        }

        public CursoHasDisciplinaEntity build() {
            return new CursoHasDisciplinaEntity(id, curso, disciplina);
        }
    }
}