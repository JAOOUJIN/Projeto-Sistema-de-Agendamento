package br.com.ifsp.agendamento.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "AGENDAMENTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rep", nullable = false)
    private Long id_rep;

    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @OneToMany(mappedBy = "agendamento")
    private Set<CadastroEntity> cadastros;

    @ManyToMany
    @JoinTable(name = "DISC_REP",
            joinColumns = @JoinColumn(name = "id_rep"),
            inverseJoinColumns = @JoinColumn(name = "id_disciplina"))
    private Set<DisciplinaEntity> disciplinas = new LinkedHashSet<>();

    public Long getId_rep() {
        return id_rep;
    }

    public void setId_rep(Long id_rep) {
        this.id_rep = id_rep;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Set<CadastroEntity> getCadastros() {
        return cadastros;
    }

    public void setCadastros(Set<CadastroEntity> cadastros) {
        this.cadastros = cadastros;
    }

    public Set<DisciplinaEntity> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<DisciplinaEntity> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
