package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import br.com.ifsp.agendamento.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    // Listar todas as disciplinas
    public List<DisciplinaEntity> listarTodas() {
        return disciplinaRepository.findAll();
    }

    // Listar nomes de todas as disciplinas
    public List<String> listarNomes() {
        return disciplinaRepository.findAll().stream()
                .map(DisciplinaEntity::getNomeDisciplina)
                .collect(Collectors.toList());
    }

    // Buscar uma disciplina por ID
    public Optional<DisciplinaEntity> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    // Deletar uma disciplina por ID
    public void deletar(Long id) {
        disciplinaRepository.deleteById(id);
    }

    // Atualizar uma disciplina existente
    public DisciplinaEntity atualizarDisciplina(Long id, DisciplinaEntity disciplinaAtualizada) {
        Optional<DisciplinaEntity> disciplinaExistente = disciplinaRepository.findById(id);

        if (disciplinaExistente.isPresent()) {
            DisciplinaEntity disciplina = disciplinaExistente.get();
            disciplina.setNomeDisciplina(disciplinaAtualizada.getNomeDisciplina());
            disciplina.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
            disciplina.setNumeroAlunos(disciplinaAtualizada.getNumeroAlunos());
            return disciplinaRepository.save(disciplina);
        } else {
            throw new IllegalArgumentException("Disciplina com ID " + id + " não encontrada.");
        }
    }

    // Buscar disciplinas por nome
    public List<DisciplinaEntity> buscarPorNome(String nomeDisciplina) {
        return disciplinaRepository.findByNomeDisciplinaContainingIgnoreCase(nomeDisciplina);
    }

    //CONTAR TODOS
    public int contarTotalDisciplinas() {
        return disciplinaRepository.fn_TotalDisciplinas();
    }

    // Adicionar uma disciplina
    public void adicionarDisciplina(String nomeDisciplina, Long cargaHoraria, Long numeroAlunos) {
        disciplinaRepository.addDisciplina(nomeDisciplina, cargaHoraria, numeroAlunos);
    }

}
