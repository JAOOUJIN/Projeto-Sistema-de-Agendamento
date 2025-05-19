package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.AgendamentoEntity;
import br.com.ifsp.agendamento.dto.AgendamentoRequest;
import br.com.ifsp.agendamento.dto.responses.HorariosCursoResponse;
import br.com.ifsp.agendamento.repository.AgendamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private AgendamentoRepository repository;

    // Metodo para receber horários de um curso
    public List<HorariosCursoResponse> receberHorario(Long id) {
        // Busca os horários associados ao ID do curso
        List<AgendamentoEntity> horario = repository.findHorariosPorIdCurso(id);
        return horario.stream()
                .map(ent -> new HorariosCursoResponse(ent.getId_rep(), ent.getHorario(), ent.getData()))
                .toList();
    }

    // Metodo para buscar um agendamento pelo ID
    public Optional<AgendamentoEntity> buscarPorId(Long id) {
        // Busca um agendamento pelo ID
        return repository.findById(id);
    }

    // Metodo para criar um novo agendamento
    public AgendamentoEntity criarAgendamento(AgendamentoRequest request) {
        // Cria um novo agendamento com os dados fornecidos
        AgendamentoEntity novoAgendamento = new AgendamentoEntity();
        novoAgendamento.setData(request.getData());
        novoAgendamento.setHorario(request.getHorario());

        return repository.save(novoAgendamento);
    }

}
