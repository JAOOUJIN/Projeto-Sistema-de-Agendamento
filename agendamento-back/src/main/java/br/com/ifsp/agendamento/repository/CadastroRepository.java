package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.CadastroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CadastroRepository extends JpaRepository<CadastroEntity, Long> {

    List<CadastroEntity> findByAlunoRa(String alunoRa);

    List<CadastroEntity> findByStatusCadastro(String statusCadastro);

    @Query(value = "SELECT dbo.fn_TotalAgendamentosPendentes()", nativeQuery = true)
    int fn_TotalAgendamentosPendentes();

    @Query(value = "SELECT dbo.fn_TotalAgendamentosAtivos()", nativeQuery = true)
    int fn_TotalAgendamentosAtivos();

}
