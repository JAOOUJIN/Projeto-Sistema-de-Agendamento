package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.AlunoEntity;
import br.com.ifsp.agendamento.dto.CadastroRecepcionistaRequest;
import br.com.ifsp.agendamento.dto.LoginRequest;
import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.repository.RecepcionistaRepository;
import br.com.ifsp.agendamento.security.JwtUtil;
import br.com.ifsp.agendamento.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recepcionista")
public class RecepcionistaController {

    @Autowired
    private RecepcionistaService recepcionistaService;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //BUSCAR TODOS
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @GetMapping("/listar")
    public List<RecepcionistaEntity> listarTodos() {
        // Retorna a lista de todos os recepcionistas
        return recepcionistaService.listarTodos();
    }

    //CADASTRAR RECEPCIONISTA
    @PreAuthorize("hasAnyAuthority('ALUNO', 'RECEPCIONISTA')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody CadastroRecepcionistaRequest request) {
        if (recepcionistaRepository.findByCdRecep(request.getCdRecep()).isPresent()) {
            return ResponseEntity.badRequest().body("Recepcionista já existe.");
        }

        RecepcionistaEntity recepcionista = new RecepcionistaEntity();
        recepcionista.setCdRecep(request.getCdRecep());
        recepcionista.setNome(request.getNome());
        recepcionista.setEmail(request.getEmail());
        recepcionista.setSenha(passwordEncoder.encode(request.getSenha()));

        recepcionistaRepository.save(recepcionista);

        return ResponseEntity.ok("Recepcionista cadastrado com sucesso.");
    }
/*
    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String senha = loginRequest.getSenha();
        String userType = loginRequest.getUserType();

        if ("aluno".equalsIgnoreCase(userType)) {
            Optional<AlunoEntity> alunoOpt = alunoRepository.findByRa(username);
            if (alunoOpt.isEmpty() || !passwordEncoder.matches(senha, alunoOpt.get().getSenhaAluno())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos (aluno).");
            }
            String token = jwtUtil.generateToken(alunoOpt.get().getRa(), "ALUNO");
            return ResponseEntity.ok(Map.of("token", token));

        } else if ("recepcionista".equalsIgnoreCase(userType)) {
            Optional<RecepcionistaEntity> recepOpt = recepcionistaRepository.findByCdRecep(username);
            if (recepOpt.isEmpty() || !passwordEncoder.matches(senha, recepOpt.get().getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos (recepcionista).");
            }
            String token = jwtUtil.generateToken(recepOpt.get().getCdRecep(), "RECEPCIONISTA");
            return ResponseEntity.ok(Map.of("token", token));

        } else {
            return ResponseEntity.badRequest().body("Tipo de usuário inválido.");
        }
    }
 */

}
