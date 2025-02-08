package br.com.ifsp.agendamento.dto;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECEPCIONISTA")
public class RecepcionistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recep")
    private Long idRecep;

    @Column(name = "cd_recep",nullable = false,unique = true,length = 11)
    private String cd_recep;

    @Column(name = "nome_recep", nullable = false, length = 50)
    private String nome;

    @Column(name = "email_recep", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    public Long getIdRecep() {
        return idRecep;
    }

    public void setIdRecep(Long idRecep) {
        this.idRecep = idRecep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCd_recep() {
        return cd_recep;
    }

    public void setCd_recep(String cd_recep) {
        this.cd_recep = cd_recep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailRecep() {
        return email;
    }

    public void setEmailRecep(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
