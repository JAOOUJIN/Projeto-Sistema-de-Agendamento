package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.DiscRepEntity;
import br.com.ifsp.agendamento.dto.DiscRepId;
import br.com.ifsp.agendamento.service.DiscRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disc-rep")
public class DiscRepController {

    @Autowired
    private DiscRepService discRepService;

    // Criar ou atualizar uma relação
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @PostMapping("/cadastrar")
    public ResponseEntity<DiscRepEntity> criarRelacao(
            @RequestParam Long idDisciplina,
            @RequestParam Long idRep) {
        try {
            // Cria uma nova relação entre disciplina e representante
            DiscRepEntity novaRelacao = discRepService.criarRelacao(idDisciplina, idRep);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaRelacao);
        } catch (IllegalArgumentException e) {
            // Retorna erro caso haja problema na criação da relação
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Buscar todas as relações
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping
    public ResponseEntity<List<DiscRepEntity>> buscarTodos() {
        // Retorna a lista de todas as relações entre disciplinas e representantes
        List<DiscRepEntity> relacoes = discRepService.buscarTodos();
        return ResponseEntity.ok(relacoes);
    }

    //BUSCAR POR ID
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/{idDisciplina}/{idRep}")
    public ResponseEntity<DiscRepEntity> buscarPorId(
            @PathVariable Long idDisciplina,
            @PathVariable Long idRep) {
        // Cria um ID composto para buscar a relação
        DiscRepId id = discRepService.criarDiscRepId(idDisciplina, idRep);
        return discRepService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para buscar relação por disciplina e agendamento

    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/buscar")
    public ResponseEntity<DiscRepEntity> buscarPorDisciplinaEAgendamento(
            @RequestParam Long idDisciplina,
            @RequestParam Long idRep) {
        // Busca a relação entre disciplina e representante
        return discRepService.buscarPorDisciplinaEAgendamento(idDisciplina, idRep)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @DeleteMapping("/{idDisciplina}/{idRep}")
    public ResponseEntity<String> remover(
            @PathVariable Long idDisciplina,
            @PathVariable Long idRep) {
        // Cria um ID composto para a relação a ser removida
        DiscRepId id = discRepService.criarDiscRepId(idDisciplina, idRep);
        try {
            // Tenta remover a relação entre disciplina e representante
            discRepService.remover(id);
            return ResponseEntity.ok("Relação removida com sucesso!");
        } catch (RuntimeException e) {
            // Retorna erro caso haja problema na remoção da relação
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}