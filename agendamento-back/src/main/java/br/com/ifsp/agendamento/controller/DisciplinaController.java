package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.ifsp.agendamento.service.DisciplinaService;

import java.util.List;
import java.util.Optional;
import java.util.Map;


@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    //BUSCAR TODOS
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/listar")
    public List<DisciplinaEntity> listarTodas() {
        // Retorna a lista de todas as disciplinas
        return disciplinaService.listarTodas();
    }

    // Endpoint para listar todas as disciplinas apenas pelo nome
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/listar-nomes")
    public ResponseEntity<List<String>> listarNomes() {
        // Retorna a lista de nomes das disciplinas
        List<String> nomesDisciplinas = disciplinaService.listarNomes();
        return ResponseEntity.ok(nomesDisciplinas);
    }

    // Buscar uma disciplina por ID
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DisciplinaEntity>> buscarPorId(@PathVariable Long id) {
        // Busca a disciplina pelo ID fornecido
        Optional<Optional<DisciplinaEntity>> disciplina = Optional.ofNullable(disciplinaService.buscarPorId(id));
        return disciplina.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Buscar uma disciplina por nome
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<DisciplinaEntity>> buscarPorNome(@PathVariable String nome) {
        // Busca disciplinas pelo nome fornecido
        List<DisciplinaEntity> disciplinas = disciplinaService.buscarPorNome(nome);
        return ResponseEntity.ok(disciplinas);
    }

    //CADASTRAR DISCIPLINA
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarDisciplina(@RequestBody DisciplinaEntity disciplina) {
        try {
            disciplinaService.adicionarDisciplina(
                    disciplina.getNomeDisciplina(),
                    disciplina.getCargaHoraria(),
                    disciplina.getNumeroAlunos()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensagem", "Disciplina adicionada com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", "Erro ao adicionar disciplina: " + e.getMessage()));
        }
    }

    //DELETE
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        disciplinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //ATUALIZA DISCIPLINA
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @PutMapping("/atualizar/{id}")
    public DisciplinaEntity atualizarDisciplina(@PathVariable("id") Long id, @RequestBody DisciplinaEntity disciplinaAtualizada) {
        return disciplinaService.atualizarDisciplina(id, disciplinaAtualizada);
    }

    //CONTAR TODOS
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @GetMapping("/total")
    public ResponseEntity<Integer> contarTotalDisciplinas() {
        int totalDisciplinas = disciplinaService.contarTotalDisciplinas();
        return ResponseEntity.ok(totalDisciplinas);
    }

}
