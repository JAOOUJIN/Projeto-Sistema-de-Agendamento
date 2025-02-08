package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.AgendamentoEntity;
import br.com.ifsp.agendamento.dto.AgendamentoRequest;
import br.com.ifsp.agendamento.dto.responses.HorariosCursoResponse;
import br.com.ifsp.agendamento.service.AgendaService;
import br.com.ifsp.agendamento.service.DiscRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agenda")
public class AgendamentoCursoController {

    @Autowired
    private AgendaService service;

    @Autowired
    private DiscRepService discRepService;

    // Endpoint para receber horários disponíveis para uma disciplina e data
    @GetMapping("/horarios/{idDisciplina}/{data}")
    public ResponseEntity<List<HorariosCursoResponse>> receberHorarios(
            @PathVariable Long idDisciplina,
            @PathVariable LocalDate data) {
        // Busca os agendamentos para a disciplina e data especificadas
        List<AgendamentoEntity> agendamentos = discRepService.buscarAgendamentosPorDisciplinaEData(idDisciplina, data);
        // Mapeia os agendamentos para a resposta desejada
        List<HorariosCursoResponse> horarios = agendamentos.stream()
                .map(ent -> new HorariosCursoResponse(ent.getId_rep(), ent.getHorario(), ent.getData()))
                .toList();
        return ResponseEntity.ok(horarios);
    }

    // Endpoint para receber horários de um agendamento específico
    @GetMapping("/receber-horario/{id}")
    public ResponseEntity<List<HorariosCursoResponse>> receberhorario(@PathVariable Long id){

        return ResponseEntity.ok(service.receberHorario(id));
    }

    // Endpoint para buscar agendamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<AgendamentoEntity> agendamento = service.buscarPorId(id);

        if (agendamento.isPresent()) {
            return ResponseEntity.ok(agendamento.get());
        } else {
            return ResponseEntity.status(404).body("Agendamento com ID " + id + " não encontrado.");
        }
    }

    // Endpoint para agendar uma nova reposição
    @PostMapping("/agendar")
    public ResponseEntity<?> agendar(@RequestBody AgendamentoRequest request) {
        try {
            AgendamentoEntity novoAgendamento = service.criarAgendamento(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar agendamento: " + e.getMessage());
        }
    }

}