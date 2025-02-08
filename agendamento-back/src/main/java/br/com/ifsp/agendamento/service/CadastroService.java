package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.AgendamentoEntity;
import br.com.ifsp.agendamento.dto.AlunoEntity;
import br.com.ifsp.agendamento.dto.CadastroEntity;
import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import br.com.ifsp.agendamento.repository.AgendamentoRepository;
import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.repository.CadastroRepository;
import br.com.ifsp.agendamento.repository.RecepcionistaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroService {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Listar todos os cadastros
    public List<CadastroEntity> listarTodos() {
        return cadastroRepository.findAll();
    }

    // Buscar cadastro por ID
    public Optional<CadastroEntity> buscarPorId(Long id) {
        return cadastroRepository.findById(id);
    }

    // Criar relacionamento entre recepcionista, agendamento e aluno
    public CadastroEntity criarRelacionamento(Long idRecepcionista, Long idAgendamento, String raAluno) {
        try {
            // Log para verificar os dados recebidos
            System.out.println("ID Recepcionista: " + idRecepcionista);
            System.out.println("ID Agendamento: " + idAgendamento);
            System.out.println("RA Aluno: " + raAluno);

            // Buscar a recepcionista pelo ID
            RecepcionistaEntity recepcionista = recepcionistaRepository.findById(idRecepcionista)
                    .orElseThrow(() -> new IllegalArgumentException("Recepcionista não encontrada com ID: " + idRecepcionista));

            // Buscar o agendamento pelo ID
            AgendamentoEntity agendamento = agendamentoRepository.findById(idAgendamento)
                    .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + idAgendamento));

            // Buscar o aluno pelo RA
            AlunoEntity aluno = alunoRepository.findByRa(raAluno)
                    .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com RA: " + raAluno));

            // Criar um novo CadastroEntity
            CadastroEntity cadastro = CadastroEntity.builder()
                    .recepcionista(recepcionista)
                    .agendamento(agendamento)
                    .aluno(aluno)
                    .statusCadastro("Pendente") // Define o status como "Pendente"
                    .dataCadastro(LocalDate.now()) // Define a data de cadastro como a data atual
                    .build();

            // Salvar no banco de dados
            return cadastroRepository.save(cadastro);
        } catch (Exception e) {
            System.err.println("Erro ao criar relacionamento: " + e.getMessage());
            throw e;
        }
    }

    // Listar cadastros pendentes
    public List<CadastroEntity> listarPendentes() {
        return cadastroRepository.findByStatusCadastro("Pendente");
    }

    // Aceitar cadastro
    public CadastroEntity aceitarCadastro(Long id) {
        CadastroEntity cadastro = cadastroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cadastro não encontrado."));
        cadastro.setStatusCadastro("Ativo"); // Atualiza o status para "Ativo"
        return cadastroRepository.save(cadastro); // Salva as alterações no repositório
    }

    // Deletar um cadastro
    public void deletar(Long id) {
        cadastroRepository.deleteById(id);
    }

    // Rejeitar cadastro
    public void rejeitarCadastro(Long id) {
        CadastroEntity cadastro = cadastroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cadastro não encontrado."));
        cadastro.setStatusCadastro("Rejeitado"); // Atualiza o status para "Rejeitado"
        cadastroRepository.save(cadastro); // Salva as alterações no repositório
    }

    // Buscar cadastros por RA do aluno
    public List<CadastroEntity> buscarPorAluno(String alunoRa) {
        return cadastroRepository.findByAlunoRa(alunoRa);
    }

    // CONTAR TOTAL PENDENTE
    public int contarTotalAgendamentosPendentes() {
        return cadastroRepository.fn_TotalAgendamentosPendentes(); // Chama o método do repositório
    }

    // CONTAR TOTAL ATIVO
    public int contarTotalAgendamentosAtivos() {
        return cadastroRepository.fn_TotalAgendamentosAtivos(); // Chama o método do repositório
    }

}
