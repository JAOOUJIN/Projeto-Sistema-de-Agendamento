package br.com.ifsp.agendamento.dto;

import java.io.Serializable;
import java.util.Objects;

public class DiscRepId implements Serializable {
    private Long idDisciplina;
    private Long idRep;

    // Construtor padrão (necessário para JPA)
    public DiscRepId() {
    }

    // Construtor com argumentos
    public DiscRepId(Long idDisciplina, Long idRep) {
        this.idDisciplina = idDisciplina;
        this.idRep = idRep;
    }

    // Getters e setters
    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Long getIdRep() {
        return idRep;
    }

    public void setIdRep(Long idRep) {
        this.idRep = idRep;
    }

    // hashCode e equals (necessário para chaves compostas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscRepId that = (DiscRepId) o;
        return Objects.equals(idDisciplina, that.idDisciplina) &&
                Objects.equals(idRep, that.idRep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDisciplina, idRep);
    }
}
