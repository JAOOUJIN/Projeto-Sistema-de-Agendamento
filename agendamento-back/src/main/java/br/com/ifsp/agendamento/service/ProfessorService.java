package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.ProfessorEntity;
import br.com.ifsp.agendamento.repository.ProfessorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todos os professores
    public List<ProfessorEntity> listarTodos() {
        return professorRepository.findAll();
    }

    // Salvar um novo professor
    public ProfessorEntity salvarProfessor(ProfessorEntity professor) {
        // Verifica se já existe um professor com o mesmo email
        if (professorRepository.existsByEmailProf(professor.getEmailProf())) {
            throw new RuntimeException("Já existe um professor cadastrado com este email.");
        }
        return professorRepository.save(professor);
    }

    // Atualizar um professor existente
    public ProfessorEntity atualizarProfessor(Long id, ProfessorEntity professorAtualizado) {
        // Busca o professor existente pelo ID
        Optional<ProfessorEntity> professorExistente = professorRepository.findById(id);
        // Atualiza os campos do professor existente
        if (professorExistente.isEmpty()) {
            throw new RuntimeException("Professor não encontrado para o ID: " + id);
        }
        ProfessorEntity professor = professorExistente.get();
        professor.setNomeProf(professorAtualizado.getNomeProf());
        professor.setEmailProf(professorAtualizado.getEmailProf());
        professor.setStatusProf(professorAtualizado.getStatusProf());
        // Salva as alterações no repositório e retorna a entidade atualizada
        return professorRepository.save(professor);
    }

    // Deletar um professor pelo ID
    public void deletarProfessor(Long id) {
        // Verifica se o professor existe antes de tentar deletá-lo
        if (!professorRepository.existsById(id)) {
            throw new RuntimeException("Professor não encontrado para o ID: " + id);
        }
        // Deleta o professor pelo ID
        professorRepository.deleteById(id);
    }
}
