package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.*;
import br.com.ifsp.agendamento.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // BUSCAR todos os alunos
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/buscar")
    public List<AlunoEntity> buscaTodos(){
        // Retorna a lista de todos os alunos cadastrados no sistema
        return alunoService.buscaTodos();
    }

    //DELETE
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long idDelete) {
        alunoService.delete(idDelete);
        return ResponseEntity.noContent().build();
    }

    //CADASTRAR
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @PostMapping("/cadastro")
    public ResponseEntity<Map<String, String>> cadastrarAluno(@RequestBody AlunoRequest alunoRequest) {
        try {
            alunoService.cadastrarAluno(alunoRequest);
            return ResponseEntity.ok(Map.of("mensagem", "Aluno cadastrado com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro: " + e.getMessage()));
        }
    }

    // BUSCAR aluno por RA
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/{ra}")
    public ResponseEntity<AlunoEntity> buscarPorRa(@PathVariable String ra) {
        // Busca o aluno pelo RA fornecido
        Optional<AlunoEntity> aluno = alunoService.buscarPorRa(ra);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // BUSCAR aluno por nome
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<AlunoEntity>> buscarPorNome(@PathVariable String nome) {
        // Busca alunos pelo nome fornecido
        List<AlunoEntity> alunos = alunoService.buscarPorNome(nome);
        return ResponseEntity.ok(alunos);
    }

    // ALTERAR senha do aluno
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @PutMapping("/alterar-senha/{ra}")
    public ResponseEntity<String> alterarSenha(@PathVariable String ra, @RequestBody AlterarSenhaRequest alterarSenhaRequest) {
        try {
            // Altera a senha do aluno
            alunoService.alterarSenha(ra, alterarSenhaRequest);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (IllegalArgumentException e) {
            // Retorna erro caso haja problema na alteração da senha
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    //BUSCA TOTAL ALUNOS
    @PreAuthorize("hasAuthority('RECEPCIONISTA')")
    @GetMapping("/total")
    public ResponseEntity<Integer> contarTotalAlunos() {
        int totalAlunos = alunoService.contarTotalAlunos();
        return ResponseEntity.ok(totalAlunos);
    }

}
