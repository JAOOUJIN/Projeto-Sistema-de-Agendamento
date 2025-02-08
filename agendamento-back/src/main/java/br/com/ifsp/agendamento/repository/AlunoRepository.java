package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long>, CrudRepository<AlunoEntity, Long> {
// faz as transacoes com o banco

    @Query("SELECT a FROM AlunoEntity a WHERE a.ra = :ra AND a.senhaAluno = :senha")
    Optional<AlunoEntity> findByRaAndSenha(@Param("ra") String ra, @Param("senha") String senha);

    boolean existsByEmailAluno(String email);
    boolean existsByRa(String ra);
    Optional<AlunoEntity> findByRa(String ra);

    List<AlunoEntity> findByNomeAlunoContainingIgnoreCase(String nomeAluno);

    @Query(value = "SELECT dbo.fn_TotalAlunos()", nativeQuery = true)
    int fn_TotalAlunos();

}


