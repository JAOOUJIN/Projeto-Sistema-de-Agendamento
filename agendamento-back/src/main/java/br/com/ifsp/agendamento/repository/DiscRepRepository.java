package br.com.ifsp.agendamento.repository;


import br.com.ifsp.agendamento.dto.AgendamentoEntity;
import br.com.ifsp.agendamento.dto.DiscRepEntity;
import br.com.ifsp.agendamento.dto.DiscRepId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscRepRepository extends JpaRepository<DiscRepEntity, DiscRepId> {

    @Query("SELECT dr FROM DiscRepEntity dr WHERE dr.disciplina.id = :idDisciplina AND dr.agendamento.id = :idRep")
    Optional<DiscRepEntity> buscarPorDisciplinaEAgendamento(@Param("idDisciplina") Long idDisciplina, @Param("idRep") Long idRep);

    @Query("SELECT dr.agendamento FROM DiscRepEntity dr WHERE dr.disciplina.id = :idDisciplina AND dr.agendamento.data = :data")
    List<AgendamentoEntity> findAgendamentosPorDisciplinaEData(@Param("idDisciplina") Long idDisciplina, @Param("data") LocalDate data);

}
