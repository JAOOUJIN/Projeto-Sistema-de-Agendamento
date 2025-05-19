package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.LoginRequest;
import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.repository.RecepcionistaRepository;
import br.com.ifsp.agendamento.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Object> login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String senha = loginRequest.getSenha();
        String userType = loginRequest.getUserType();

        if ("aluno".equalsIgnoreCase(userType)) {
            return alunoRepository.findByRa(username)
                    .filter(aluno -> passwordEncoder.matches(senha, aluno.getSenhaAluno()))
                    .map(aluno -> gerarTokenResponse(aluno.getRa(), "ALUNO"))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .<Object>body("Usuário ou senha inválidos (aluno)."));

        } else if ("recepcionista".equalsIgnoreCase(userType)) {
            return recepcionistaRepository.findByCdRecep(username)
                    .filter(recep -> passwordEncoder.matches(senha, recep.getSenha()))
                    .map(recep -> gerarTokenResponse(recep.getCdRecep(), "RECEPCIONISTA"))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .<Object>body("Usuário ou senha inválidos (recepcionista)."));

        } else {
            return ResponseEntity.badRequest()
                    .<Object>body("Tipo de usuário inválido.");
        }
    }

    private ResponseEntity<Object> gerarTokenResponse(String username, String role) {
        String token = jwtUtil.generateToken(username, role);
        return ResponseEntity.ok(Map.of("token", token));
    }

}

