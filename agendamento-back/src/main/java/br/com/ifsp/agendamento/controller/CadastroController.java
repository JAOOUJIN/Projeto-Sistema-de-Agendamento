package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.CadastroEntity;
import br.com.ifsp.agendamento.dto.CadastroRequest;
import br.com.ifsp.agendamento.service.CadastroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cadastro")
@RequiredArgsConstructor
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    // Listar todos os cadastros
    @GetMapping
    public ResponseEntity<List<CadastroEntity>> listarTodos() {
        // Retorna a lista de todos os cadastros
        List<CadastroEntity> cadastros = cadastroService.listarTodos();
        return ResponseEntity.ok(cadastros);
    }

    // Buscar cadastro por ID
    @GetMapping("/{id}")
    public ResponseEntity<CadastroEntity> buscarPorId(@PathVariable Long id) {
        // Busca o cadastro pelo ID fornecido
        Optional<CadastroEntity> cadastro = cadastroService.buscarPorId(id);
        return cadastro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Agendar um novo cadastro
    @PostMapping("/agendar")
    public ResponseEntity<CadastroEntity> agendar(@RequestBody CadastroRequest request) {
        try {
            // Cria um novo cadastro com os dados fornecidos
            CadastroEntity novoCadastro = cadastroService.criarRelacionamento(
                    request.getIdRecepcionista(),
                    request.getIdAgendamento(),
                    request.getRaAluno()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCadastro);
        } catch (Exception e) {
            // Retorna erro caso haja problema no agendamento
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Deletar cadastro por ID
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Verifica se o cadastro existe antes de deletar
        Optional<CadastroEntity> cadastro = cadastroService.buscarPorId(id);
        if (cadastro.isPresent()) {
            cadastroService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        // Retorna erro caso o cadastro não seja encontrado
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Listar cadastros pendentes
    @GetMapping("/pendentes")
    public ResponseEntity<List<CadastroEntity>> listarCadastrosPendentes() {
        // Retorna a lista de cadastros pendentes
        List<CadastroEntity> pendentes = cadastroService.listarPendentes();
        return ResponseEntity.ok(pendentes);
    }

    //ACEITAR AGENDAMENTO
    @PostMapping("/aceitar/{id}")
    public ResponseEntity<?> aceitarCadastro(@PathVariable Long id) {
        try {
            // Aceita o cadastro com o ID fornecido
            CadastroEntity cadastro = cadastroService.aceitarCadastro(id);
            return ResponseEntity.ok(cadastro);
        } catch (Exception e) {
            // Retorna erro caso haja problema ao aceitar o cadastro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao aceitar o cadastro.");
        }
    }

    //REJEITAR AGENDAMENTO
    @PostMapping("/rejeitar/{id}")
    public ResponseEntity<?> rejeitarCadastro(@PathVariable Long id) {
        try {
            // Rejeita o cadastro com o ID fornecido
            cadastroService.rejeitarCadastro(id);
            return ResponseEntity.ok("Agendamento rejeitado com sucesso.");
        } catch (Exception e) {
            // Retorna erro caso haja problema ao rejeitar o cadastro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao rejeitar o cadastro.");
        }
    }

    // Buscar cadastros por RA do aluno
    @GetMapping("/aluno/{ra}")
    public ResponseEntity<List<CadastroEntity>> buscarPorAluno(@PathVariable String ra) {
        // Busca cadastros pelo RA do aluno fornecido
        List<CadastroEntity> cadastros = cadastroService.buscarPorAluno(ra);
        if (!cadastros.isEmpty()) {
            return ResponseEntity.ok(cadastros);
        }
        // Retorna erro caso não encontre cadastros
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // CONTAR TOTAL PENDENTE
    @GetMapping("/total/pendentes")
    public ResponseEntity<Integer> contarTotalAgendamentosPendentes() {
        int totalPendentes = cadastroService.contarTotalAgendamentosPendentes();
        return ResponseEntity.ok(totalPendentes);
    }

    //CONTAR TOTAL ATIVO
    @GetMapping("/total/ativos")
    public ResponseEntity<Integer> contarTotalAgendamentosAtivos() {
        int totalAtivos = cadastroService.contarTotalAgendamentosAtivos();
        return ResponseEntity.ok(totalAtivos);
    }

}
