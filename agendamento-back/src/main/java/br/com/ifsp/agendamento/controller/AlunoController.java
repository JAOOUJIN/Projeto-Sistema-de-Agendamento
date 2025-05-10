package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.*;
import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.security.JwtUtil;
import br.com.ifsp.agendamento.service.AlunoService;
import br.com.ifsp.agendamento.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    // Recebe as requisições do fronta
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if (!"aluno".equalsIgnoreCase(loginRequest.getUserType())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tipo de usuário inválido.");
        }

        Optional<AlunoEntity> alunoOpt = alunoRepository.findByRa(loginRequest.getUsername());

        if (alunoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aluno não encontrado.");
        }

        AlunoEntity aluno = alunoOpt.get();

        if (!passwordEncoder.matches(loginRequest.getSenha(), aluno.getSenhaAluno())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
        }

        String token = jwtUtil.generateToken(aluno.getRa(), "ALUNO");

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
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
