package br.com.ifsp.agendamento.dto;

import java.io.Serializable;
import java.util.Objects;

public class CursoHasDisciplinaId implements Serializable {
    private Long idCurso;
    private Long idDisciplina;

    // Construtor padrão (necessário para JPA)
    public CursoHasDisciplinaId() {
    }

    // Construtor com argumentos
    public CursoHasDisciplinaId(Long idCurso, Long idDisciplina) {
        this.idCurso = idCurso;
        this.idDisciplina = idDisciplina;
    }

    // Getters e setters


    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    // hashCode e equals (necessário para chaves compostas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoHasDisciplinaId that = (CursoHasDisciplinaId) o;
        return Objects.equals(idCurso, that.idCurso) &&
                Objects.equals(idDisciplina, that.idDisciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCurso, idDisciplina);
    }
}