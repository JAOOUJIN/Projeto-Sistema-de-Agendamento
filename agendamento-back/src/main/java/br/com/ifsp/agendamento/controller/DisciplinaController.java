package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ifsp.agendamento.service.DisciplinaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    //BUSCAR TODOS
    @GetMapping("/listar")
    public List<DisciplinaEntity> listarTodas() {
        // Retorna a lista de todas as disciplinas
        return disciplinaService.listarTodas();
    }

    // Endpoint para listar todas as disciplinas apenas pelo nome
    @GetMapping("/listar-nomes")
    public ResponseEntity<List<String>> listarNomes() {
        // Retorna a lista de nomes das disciplinas
        List<String> nomesDisciplinas = disciplinaService.listarNomes();
        return ResponseEntity.ok(nomesDisciplinas);
    }

    // Buscar uma disciplina por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DisciplinaEntity>> buscarPorId(@PathVariable Long id) {
        // Busca a disciplina pelo ID fornecido
        Optional<Optional<DisciplinaEntity>> disciplina = Optional.ofNullable(disciplinaService.buscarPorId(id));
        return disciplina.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<DisciplinaEntity>> buscarPorNome(@PathVariable String nome) {
        // Busca disciplinas pelo nome fornecido
        List<DisciplinaEntity> disciplinas = disciplinaService.buscarPorNome(nome);
        return ResponseEntity.ok(disciplinas);
    }

    //CADASTRAR DISCIPLINA
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarDisciplina(@RequestBody DisciplinaEntity disciplina) {
        try {
            disciplinaService.adicionarDisciplina(disciplina.getNomeDisciplina(),
                    disciplina.getCargaHoraria(),
                    disciplina.getNumeroAlunos());
            return ResponseEntity.status(HttpStatus.CREATED).body("Disciplina adicionada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id) {
        disciplinaService.deletar(id);
    }

    //ATUALIZA DISCIPLINA
    @PutMapping("/atualizar/{id}")
    public DisciplinaEntity atualizarDisciplina(@PathVariable("id") Long id, @RequestBody DisciplinaEntity disciplinaAtualizada) {
        return disciplinaService.atualizarDisciplina(id, disciplinaAtualizada);
    }

    //CONTAR TODOS
    @GetMapping("/total")
    public ResponseEntity<Integer> contarTotalDisciplinas() {
        int totalDisciplinas = disciplinaService.contarTotalDisciplinas();
        return ResponseEntity.ok(totalDisciplinas);
    }

}
