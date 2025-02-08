package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.AgendamentoEntity;
import br.com.ifsp.agendamento.dto.responses.HorariosCursoResponse;
import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {

    @Query(nativeQuery = true, value = "select a.id_rep, a.horario, a.data from Curso c  " +
            " inner join Curso_has_DISCIPLINA chd on c.id_curso = chd.Curso_id_curso " +
            "inner join DISCIPLINA dis on dis.id_disciplina = chd.DISCIPLINA_id_disciplina " +
            "inner join DISC_REP dr on dr.id_disciplina = dis.id_disciplina " +
            "inner join agendamento a on a.id_rep = dr.id_rep " +
            "where c.id_curso = ?1")
    List<AgendamentoEntity> findHorariosPorIdCurso(Long id);

}
