package br.com.ifsp.agendamento.service;
import br.com.ifsp.agendamento.dto.*;
import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.security.JwtUtil;
import br.com.ifsp.agendamento.util.PasswordUtil;
import org.springframework.http.ResponseEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    // Serviço onde você coloca suas regras

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    // Busca todos os alunos
    public List<AlunoEntity> buscaTodos() {
        List<AlunoEntity> alunos = repository.findAll();
        System.out.println("Total de alunos encontrados: " + alunos.size());
        return alunos;
    }

    // Deleta um aluno pelo ID
    public void delete(Long idDelete) {
        repository.deleteById(idDelete);
    }

    // Cadastra um novo aluno
    public void cadastrarAluno(AlunoRequest alunoRequest) {

        System.out.println("RA: " + alunoRequest.getRa());
        System.out.println("Nome: " + alunoRequest.getNomeAluno());
        System.out.println("Email: " + alunoRequest.getEmailAluno());
        System.out.println("Senha: " + alunoRequest.getSenhaAluno());

        // Verifica se já existe um aluno com o mesmo RA
        if (repository.existsByRa(alunoRequest.getRa())) {
            throw new IllegalArgumentException("Já existe um aluno com este RA.");
        }

        // Verifica se já existe um aluno com o mesmo email
        if (repository.existsByEmailAluno(alunoRequest.getEmailAluno())) {
            throw new IllegalArgumentException("Já existe um aluno com este email.");
        }

        // Cria uma nova instância de AlunoEntity
        AlunoEntity aluno = new AlunoEntity();
        aluno.setRa(alunoRequest.getRa());
        aluno.setNomeAluno(alunoRequest.getNomeAluno());
        aluno.setEmailAluno(alunoRequest.getEmailAluno());
        String senhaCriptografada = passwordEncoder.encode(alunoRequest.getSenhaAluno());
        aluno.setSenhaAluno(senhaCriptografada);

        // Define o status como "Ativo"
        aluno.setStatusAluno("Ativo");

        // Salva o aluno no repositório
        repository.save(aluno);
    }


    // Busca alunos pelo nome
    public List<AlunoEntity> buscarPorNome(String nome) {
        return repository.findByNomeAlunoContainingIgnoreCase(nome); // Supondo que você tenha esse método no repositório
    }

    // Realiza o login do aluno
    public String loginAluno(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getSenha() == null) {
            throw new IllegalArgumentException("RA e senha são obrigatórios.");
        }

        Optional<AlunoEntity> alunoOptional = repository.findByRa(loginRequest.getUsername());

        if (alunoOptional.isPresent()) {
            AlunoEntity aluno = alunoOptional.get();
            if (passwordEncoder.matches(loginRequest.getSenha(), aluno.getSenhaAluno())) {
                return jwtUtil.generateToken(aluno.getRa(), "ALUNO"); // Gera token com o RA do aluno
            }
        }

        throw new RuntimeException("RA ou senha inválidos.");
    }

    // Busca um aluno pelo RA
    public Optional<AlunoEntity> buscarPorRa(String ra) {
        return repository.findByRa(ra);
    }

    // Altera a senha do aluno
    public void alterarSenha(String ra, AlterarSenhaRequest alterarSenhaRequest) {
        Optional<AlunoEntity> alunoOptional = repository.findByRa(ra);
        if (alunoOptional.isPresent()) {
            AlunoEntity aluno = alunoOptional.get();
            // Verifica se a senha atual está correta
            if (!passwordEncoder.matches(alterarSenhaRequest.getSenhaAtual(), aluno.getSenhaAluno())) {
                throw new IllegalArgumentException("A senha atual está incorreta.");
            }
            // Atualiza a senha
            String novaSenhaCriptografada = passwordEncoder.encode(alterarSenhaRequest.getNovaSenha());
            aluno.setSenhaAluno(novaSenhaCriptografada);
            repository.save(aluno);
        } else {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
    }

    public int contarTotalAlunos() {
        return repository.fn_TotalAlunos();
    }

}
