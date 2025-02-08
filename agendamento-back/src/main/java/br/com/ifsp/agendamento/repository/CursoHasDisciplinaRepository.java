package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.CursoHasDisciplinaEntity;
import br.com.ifsp.agendamento.dto.CursoHasDisciplinaId;
import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoHasDisciplinaRepository extends JpaRepository<CursoHasDisciplinaEntity, CursoHasDisciplinaId> {

    @Query("SELECT cd.disciplina FROM CursoHasDisciplinaEntity cd WHERE cd.curso.idCurso = :cursoId")
    List<DisciplinaEntity> findDisciplinasByCursoId(@Param("cursoId") Long cursoId);

}