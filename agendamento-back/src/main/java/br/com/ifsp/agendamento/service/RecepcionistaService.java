package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.LoginRequest;
import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import br.com.ifsp.agendamento.repository.RecepcionistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecepcionistaService {

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

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
    public boolean loginRecepcionista(LoginRequest loginRequest) {
        // Verifica se o username e a senha foram fornecidos
        if (loginRequest.getUsername() == null || loginRequest.getSenha() == null) {
            throw new IllegalArgumentException("Código e senha são obrigatórios.");
        }
        // Busca o recepcionista pelo código e senha
        Optional<RecepcionistaEntity> recepcionista = recepcionistaRepository.findByCodigoAndSenha(loginRequest.getUsername(), loginRequest.getSenha());

        if (recepcionista.isPresent()) {
            System.out.println("Login bem-sucedido para o recepcionista: " + recepcionista.get().getNome());
            return true;
        } else {
            System.out.println("Nenhum recepcionista encontrado com o código " + loginRequest.getUsername());
            return false;
        }
    }
}

