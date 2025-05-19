package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.CursoHasDisciplinaEntity;
import br.com.ifsp.agendamento.dto.CursoHasDisciplinaId;
import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import br.com.ifsp.agendamento.service.CursoHasDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curso-disciplina")
public class CursoHasDisciplinaController {

    @Autowired
    private CursoHasDisciplinaService service;

    //CRIAR RELACIONAMENTO
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> criar(@RequestParam Long idCurso, @RequestParam Long idDisciplina) {
        try {
            // Cria uma nova relação entre curso e disciplina
            CursoHasDisciplinaEntity novaRelacao = service.salvar(idCurso, idDisciplina);
            return ResponseEntity.ok(novaRelacao);
        } catch (RuntimeException e) {
            // Retorna erro caso haja problema na criação da relação
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //BUSCA TODOS RELACIONAMENTOS
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @GetMapping("/listar")
    public ResponseEntity<List<CursoHasDisciplinaEntity>> buscarTodos() {
        // Retorna a lista de todas as relações entre cursos e disciplinas
        List<CursoHasDisciplinaEntity> relacoes = service.buscarTodos();
        return ResponseEntity.ok(relacoes);
    }

    //BUSCA POR ID
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @GetMapping("/{cursoId}/{disciplinaId}")
    public ResponseEntity<CursoHasDisciplinaEntity> buscarPorId(
            @PathVariable Long cursoId,
            @PathVariable Long disciplinaId) {
        // Cria um ID composto para buscar a relação
        CursoHasDisciplinaId id = service.criarCursoHasDisciplinaId(cursoId, disciplinaId);
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Listar Disciplina por Curso
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<DisciplinaEntity>> listarDisciplinasPorCurso(@PathVariable Long cursoId) {
        // Busca as disciplinas associadas ao curso fornecido
        List<DisciplinaEntity> disciplinas = service.listarDisciplinasPorCurso(cursoId);
        return ResponseEntity.ok(disciplinas);
    }

    //DELETE POR ID
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @DeleteMapping("/{cursoId}/{disciplinaId}")
    public ResponseEntity<String> remover(
            @PathVariable Long cursoId,
            @PathVariable Long disciplinaId) {
        // Cria um ID composto para a relação a ser removida
        CursoHasDisciplinaId id = service.criarCursoHasDisciplinaId(cursoId, disciplinaId);
        try {
            // Tenta remover a relação entre curso e disciplina
            service.remover(id);
            return ResponseEntity.ok("Relação removida com sucesso!");
        } catch (RuntimeException e) {
            // Retorna erro caso haja problema na remoção da relação
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}