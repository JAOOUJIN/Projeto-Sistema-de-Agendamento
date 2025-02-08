package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<DisciplinaEntity, Long> {
    List<DisciplinaEntity> findByNomeDisciplinaContainingIgnoreCase(String nomeDisciplina);

    @Query(value = "SELECT dbo.fn_TotalDisciplinas()", nativeQuery = true)
    int fn_TotalDisciplinas();

    @Modifying
    @Transactional
    @Query(value = "EXEC sp_AddDisciplina :nomeDisciplina, :cargaHoraria, :numeroAlunos", nativeQuery = true)
    void addDisciplina(@Param("nomeDisciplina") String nomeDisciplina,
                       @Param("cargaHoraria") Long cargaHoraria,
                       @Param("numeroAlunos") Long numeroAlunos);

}
