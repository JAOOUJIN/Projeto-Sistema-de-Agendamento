package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.LoginRequest;
import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import br.com.ifsp.agendamento.service.AlunoService;
import br.com.ifsp.agendamento.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recepcionista")
public class RecepcionistaController {

    @Autowired
    private RecepcionistaService recepcionistaService;

    @Autowired
    private AlunoService alunoService;

    //BUSCAR TODOS
    @GetMapping("/listar")
    public List<RecepcionistaEntity> listarTodos() {
        // Retorna a lista de todos os recepcionistas
        return recepcionistaService.listarTodos();
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Verifica se o username e senha foram fornecidos
        if (loginRequest.getUsername() == null || loginRequest.getSenha() == null) {
            return ResponseEntity.badRequest().body("Código e senha são obrigatórios.");
        }

        // Tenta realizar o login do recepcionista
        boolean isAuthorized = recepcionistaService.loginRecepcionista(loginRequest);
        if (!isAuthorized) {
            return ResponseEntity.status(401).body("Credenciais inválidas para recepcionista.");
        }

        return ResponseEntity.ok("Login realizado com sucesso!");
    }

}
