package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    boolean existsByEmailProf(String emailProf);

}
