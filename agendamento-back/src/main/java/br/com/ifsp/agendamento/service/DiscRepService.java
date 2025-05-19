package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.AgendamentoEntity;
import br.com.ifsp.agendamento.dto.DiscRepEntity;
import br.com.ifsp.agendamento.dto.DiscRepId;
import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import br.com.ifsp.agendamento.repository.AgendamentoRepository;
import br.com.ifsp.agendamento.repository.DiscRepRepository;
import br.com.ifsp.agendamento.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiscRepService {

    @Autowired
    private DiscRepRepository discRepRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Busca agendamentos por disciplina e data
    public List<AgendamentoEntity> buscarAgendamentosPorDisciplinaEData(Long idDisciplina, LocalDate data) {
        return discRepRepository.findAgendamentosPorDisciplinaEData(idDisciplina, data);
    }

    public DiscRepEntity criarRelacao(Long idDisciplina, Long idRep) {
        // Busca a Disciplina pelo ID
        DisciplinaEntity disciplina = disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina com ID " + idDisciplina + " não encontrada."));

        // Busca o Agendamento pelo ID
        AgendamentoEntity agendamento = agendamentoRepository.findById(idRep)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento com ID " + idRep + " não encontrado."));

        // Cria o ID composto
        DiscRepId discRepId = new DiscRepId(idDisciplina, idRep);

        // Cria a entidade
        DiscRepEntity novaRelacao = new DiscRepEntity();
        novaRelacao.setId(discRepId);
        novaRelacao.setDisciplina(disciplina);
        novaRelacao.setAgendamento(agendamento);

        // Salva a relação no repositório
        return discRepRepository.save(novaRelacao);
    }

    public Optional<DiscRepEntity> buscarPorDisciplinaEAgendamento(Long idDisciplina, Long idRep) {
        return discRepRepository.buscarPorDisciplinaEAgendamento(idDisciplina, idRep);
    }

    public List<DiscRepEntity> buscarTodos() {
        return discRepRepository.findAll();
    }


    public Optional<DiscRepEntity> buscarPorId(DiscRepId id) {
        return discRepRepository.findById(id);
    }


    public void remover(DiscRepId id) {
        if (discRepRepository.existsById(id)) {
            discRepRepository.deleteById(id);
        } else {
            throw new RuntimeException("Relação não encontrada com o ID: " + id);
        }
    }

    public DiscRepId criarDiscRepId(Long idDisciplina, Long idRep) {
        return new DiscRepId(idDisciplina, idRep);
    }

    private void validarRelacionamento(DiscRepEntity discRepEntity) {
        if (discRepEntity.getDisciplina() == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula.");
        }
        if (discRepEntity.getAgendamento() == null) {
            throw new IllegalArgumentException("Agendamento não pode ser nulo.");
        }
    }
}
