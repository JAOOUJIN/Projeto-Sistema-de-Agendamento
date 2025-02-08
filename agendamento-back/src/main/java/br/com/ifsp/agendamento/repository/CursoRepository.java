package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.CursoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long> {
    List<CursoEntity> findByNomeCursoContainingIgnoreCase(String nomeCurso);

    @Query(value = "SELECT dbo.fn_TotalCursos()", nativeQuery = true)
    int fn_TotalCursos();

    @Modifying
    @Transactional
    @Query(value = "EXEC sp_AddCurso :nomeCurso", nativeQuery = true)
    void addCurso(@Param("nomeCurso") String nomeCurso);

}
