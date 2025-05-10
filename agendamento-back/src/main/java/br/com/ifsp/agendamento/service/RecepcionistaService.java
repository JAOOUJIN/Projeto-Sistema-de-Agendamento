package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.LoginRequest;
import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import br.com.ifsp.agendamento.repository.RecepcionistaRepository;
import br.com.ifsp.agendamento.security.JwtUtil;
import br.com.ifsp.agendamento.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecepcionistaService {

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Listar todos os recepcionistas
    public List<RecepcionistaEntity> listarTodos() {
        return recepcionistaRepository.findAll();
    }

    // Buscar recepcionista por ID
    public RecepcionistaEntity buscarPorId(int id) {
        return recepcionistaRepository.findById((long) id).orElse(null);
    }

    // Remover recepcionista por ID
    public void remover(int id) {
        recepcionistaRepository.deleteById((long) id);
    }

    // Metodo para login do recepcionista
    public String loginRecepcionista(LoginRequest loginRequest) {
        Optional<RecepcionistaEntity> recepcionistaOptional = recepcionistaRepository.findByCdRecep(loginRequest.getUsername());

        if (recepcionistaOptional.isPresent()) {
            RecepcionistaEntity recepcionista = recepcionistaOptional.get();
            if (passwordEncoder.matches(loginRequest.getSenha(), recepcionista.getSenha())) {
                return jwtUtil.generateToken(recepcionista.getCdRecep(), "RECEPCIONISTA");
            }
        }

        throw new RuntimeException("Credenciais inválidas para recepcionista.");
    }
}

