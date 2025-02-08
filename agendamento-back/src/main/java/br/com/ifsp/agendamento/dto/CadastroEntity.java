package br.com.ifsp.agendamento.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Entity
@Table(name = "CADASTRO")
@Data
public class CadastroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cadastro", nullable = false)
    private Long id;

    // Relacionamento com a entidade Recepcionista (um para muitos)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_recep", nullable = false, referencedColumnName = "id_recep")
    private RecepcionistaEntity recepcionista;

    // Relacionamento com a entidade Agendamento (um para muitos)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cadastros"})
    @JoinColumn(name = "id_rep", nullable = false, referencedColumnName = "id_rep")
    private AgendamentoEntity agendamento;

    // Relacionamento com a entidade Aluno (um para muitos)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ra", nullable = false, referencedColumnName = "ra")
    private AlunoEntity aluno;

    // Data do cadastro
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    // Status do cadastro
    @Nationalized
    @Column(name = "status_cadastro", nullable = false, length = 20)
    private String statusCadastro;

    // Construtor padrão
    public CadastroEntity() {
    }

    // Construtor com argumentos
    public CadastroEntity(Long id, RecepcionistaEntity recepcionista, AgendamentoEntity agendamento, AlunoEntity aluno, LocalDate dataCadastro, String statusCadastro) {
        this.id = id;
        this.recepcionista = recepcionista;
        this.agendamento = agendamento;
        this.aluno = aluno;
        this.dataCadastro = dataCadastro;
        this.statusCadastro = statusCadastro;
    }

    // Implementação manual do builder
    public static CadastroEntityBuilder builder() {
        return new CadastroEntityBuilder();
    }

    public static class CadastroEntityBuilder {
        private Long id;
        private RecepcionistaEntity recepcionista;
        private AgendamentoEntity agendamento;
        private AlunoEntity aluno;
        private LocalDate dataCadastro;
        private String statusCadastro;

        public CadastroEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CadastroEntityBuilder recepcionista(RecepcionistaEntity recepcionista) {
            this.recepcionista = recepcionista;
            return this;
        }

        public CadastroEntityBuilder agendamento(AgendamentoEntity agendamento) {
            this.agendamento = agendamento;
            return this;
        }

        public CadastroEntityBuilder aluno(AlunoEntity aluno) {
            this.aluno = aluno;
            return this;
        }

        public CadastroEntityBuilder dataCadastro(LocalDate dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public CadastroEntityBuilder statusCadastro(String statusCadastro) {
            this.statusCadastro = statusCadastro;
            return this;
        }

        public CadastroEntity build() {
            return new CadastroEntity(id, recepcionista, agendamento, aluno, dataCadastro, statusCadastro);
        }
    }

    @PrePersist
    public void prePersist() {
        if (this.statusCadastro == null) {
            this.statusCadastro = "Pendente";
        }
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDate.now();
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecepcionistaEntity getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(RecepcionistaEntity recepcionista) {
        this.recepcionista = recepcionista;
    }

    public AgendamentoEntity getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoEntity agendamento) {
        this.agendamento = agendamento;
    }

    public AlunoEntity getAluno() {
        return aluno;
    }

    public void setAluno(AlunoEntity aluno) {
        this.aluno = aluno;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatusCadastro() {
        return statusCadastro;
    }

    public void setStatusCadastro(String statusCadastro) {
        this.statusCadastro = statusCadastro;
    }
}
