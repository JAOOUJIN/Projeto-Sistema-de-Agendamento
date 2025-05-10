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
    private String cdRecep;

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

    public String getCdRecep() {
        return cdRecep;
    }

    public void setCdRecep(String cd_recep) {
        this.cdRecep = cd_recep;
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
