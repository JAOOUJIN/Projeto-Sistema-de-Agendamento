package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.CursoEntity;
import br.com.ifsp.agendamento.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Endpoint para listar todos os cursos
    @GetMapping("/listar")
    public ResponseEntity<List<CursoEntity>> listarTodos() {
        // Retorna a lista de todos os cursos
        List<CursoEntity> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    // Endpoint para listar todos os cursos apenas pelo nome
    @GetMapping("/listar-nomes")
    public ResponseEntity<List<String>> listarNomes() {
        // Retorna a lista de nomes dos cursos
        List<String> nomesCursos = cursoService.listarNomes();
        return ResponseEntity.ok(nomesCursos);
    }

    // Endpoint para buscar curso por nome
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<CursoEntity>> buscarPorNome(@PathVariable String nome) {
        // Busca cursos pelo nome fornecido
        List<CursoEntity> cursos = cursoService.buscarPorNome(nome);
        return ResponseEntity.ok(cursos);
    }

    // Endpoint para buscar curso por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CursoEntity> buscarPorId(@PathVariable Long id) {
        // Busca o curso pelo ID fornecido
        Optional<CursoEntity> curso = cursoService.buscarPorId(id);
        return curso.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para criar ou atualizar um curso
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarCurso(@RequestBody CursoEntity curso) {
        try {
            cursoService.adicionarCurso(curso.getNomeCurso());
            return ResponseEntity.status(HttpStatus.CREATED).body("Curso adicionado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar curso: " + e.getMessage());
        }
    }

    // Endpoint para deletar curso por ID
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        cursoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    //CONTAR TOTAL
    @GetMapping("/total")
    public ResponseEntity<Integer> contarTotalCursos() {
        int totalCursos = cursoService.contarTotalCursos();
        return ResponseEntity.ok(totalCursos);
    }

}
