package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.*;
import br.com.ifsp.agendamento.service.AlunoService;
import br.com.ifsp.agendamento.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    // Recebe as requisições do fronta
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private RecepcionistaService recepcionistaService;

    // BUSCAR todos os alunos
    @GetMapping("/buscar")
    public List<AlunoEntity> buscaTodos(){
        // Retorna a lista de todos os alunos cadastrados no sistema
        return alunoService.buscaTodos();
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletar(@PathVariable("id") Long idDelete) {
        alunoService.delete(idDelete);
        return ResponseEntity.ok("Aluno deletado com sucesso!");
    }

    //CADASTRAR
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarAluno(@RequestBody AlunoRequest alunoRequest) {
        try {
            // Cadastra um novo aluno
            alunoService.cadastrarAluno(alunoRequest);
            return ResponseEntity.ok("Aluno cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            // Retorna erro caso haja problema no cadastro
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Verifica se o username e senha foram fornecidos
        if (loginRequest.getUsername() == null || loginRequest.getSenha() == null) {
            return ResponseEntity.badRequest().body("Código e senha são obrigatórios.");
        }
        // Verifica o tipo de usuário e realiza o login
        if ("recepcionista".equals(loginRequest.getUserType())) {
            boolean isAuthorized = recepcionistaService.loginRecepcionista(loginRequest);
            if (!isAuthorized) {
                return ResponseEntity.status(401).body("Credenciais inválidas para recepcionista.");
            }
        } else if ("aluno".equals(loginRequest.getUserType())) {
            boolean isAuthorized = alunoService.loginAluno(loginRequest);
            if (!isAuthorized) {
                return ResponseEntity.status(401).body("Credenciais inválidas para aluno.");
            }
        } else {
            return ResponseEntity.badRequest().body("Tipo de usuário inválido.");
        }

        return ResponseEntity.ok("Login realizado com sucesso!");
    }

    // BUSCAR aluno por RA
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
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<AlunoEntity>> buscarPorNome(@PathVariable String nome) {
        // Busca alunos pelo nome fornecido
        List<AlunoEntity> alunos = alunoService.buscarPorNome(nome);
        return ResponseEntity.ok(alunos);
    }

    // ALTERAR senha do aluno
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
    @GetMapping("/total")
    public ResponseEntity<Integer> contarTotalAlunos() {
        int totalAlunos = alunoService.contarTotalAlunos();
        return ResponseEntity.ok(totalAlunos);
    }

}
