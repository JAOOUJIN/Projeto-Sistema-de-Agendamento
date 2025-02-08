package br.com.ifsp.agendamento.dto;

import br.com.ifsp.agendamento.dto.DiscRepId;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "DISC_REP")
@Data
@Builder
public class DiscRepEntity implements Serializable {

    @EmbeddedId
    private DiscRepId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idDisciplina")
    @JoinColumn(name = "id_disciplina", nullable = false)
    private DisciplinaEntity disciplina;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idRep")
    @JoinColumn(name = "id_rep", nullable = false)
    private AgendamentoEntity agendamento;

    // Construtor padrão
    public DiscRepEntity() {
    }

    // Construtor com argumentos
    public DiscRepEntity(DiscRepId id, DisciplinaEntity disciplina, AgendamentoEntity agendamento) {
        this.id = id;
        this.disciplina = disciplina;
        this.agendamento = agendamento;
    }

    // Getters e Setters
    public DiscRepId getId() {
        return id;
    }

    public void setId(DiscRepId id) {
        this.id = id;
    }

    public DisciplinaEntity getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaEntity disciplina) {
        this.disciplina = disciplina;
    }

    public AgendamentoEntity getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoEntity agendamento) {
        this.agendamento = agendamento;
    }

    // Implementação manual do builder
    public static DiscRepEntityBuilder builder() {
        return new DiscRepEntityBuilder();
    }

    public static class DiscRepEntityBuilder {
        private DiscRepId id;
        private DisciplinaEntity disciplina;
        private AgendamentoEntity agendamento;

        public DiscRepEntityBuilder id(DiscRepId id) {
            this.id = id;
            return this;
        }

        public DiscRepEntityBuilder disciplina(DisciplinaEntity disciplina) {
            this.disciplina = disciplina;
            return this;
        }

        public DiscRepEntityBuilder agendamento(AgendamentoEntity agendamento) {
            this.agendamento = agendamento;
            return this;
        }

        public DiscRepEntity build() {
            return new DiscRepEntity(id, disciplina, agendamento);
        }
    }
}

