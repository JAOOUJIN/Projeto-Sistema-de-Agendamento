package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.CursoEntity;
import br.com.ifsp.agendamento.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Endpoint para listar todos os cursos
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/listar")
    public ResponseEntity<List<CursoEntity>> listarTodos() {
        List<CursoEntity> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    // Endpoint para listar todos os cursos apenas pelo nome
    @GetMapping("/listar-nomes")
    public ResponseEntity<List<String>> listarNomes() {
        List<String> nomesCursos = cursoService.listarNomes();
        return ResponseEntity.ok(nomesCursos);
    }

    // Endpoint para buscar curso por nome
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<CursoEntity>> buscarPorNome(@PathVariable String nome) {
        List<CursoEntity> cursos = cursoService.buscarPorNome(nome);
        return ResponseEntity.ok(cursos);
    }

    // Endpoint para buscar curso por ID
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CursoEntity> buscarPorId(@PathVariable Long id) {
        Optional<CursoEntity> curso = cursoService.buscarPorId(id);
        return curso.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para criar ou atualizar um curso
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarCurso(@RequestBody CursoEntity curso) {
        try {
            cursoService.adicionarCurso(curso.getNomeCurso());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensagem", "Curso adicionado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", "Erro ao adicionar curso: " + e.getMessage()));
        }
    }

    // Endpoint para deletar curso por ID
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        cursoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    //CONTAR TOTAL
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @GetMapping("/total")
    public ResponseEntity<Integer> contarTotalCursos() {
        int totalCursos = cursoService.contarTotalCursos();
        return ResponseEntity.ok(totalCursos);
    }

}
