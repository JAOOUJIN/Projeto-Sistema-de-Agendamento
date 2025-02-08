package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.CursoEntity;
import br.com.ifsp.agendamento.dto.CursoHasDisciplinaEntity;
import br.com.ifsp.agendamento.dto.CursoHasDisciplinaId;
import br.com.ifsp.agendamento.dto.DisciplinaEntity;
import br.com.ifsp.agendamento.repository.CursoHasDisciplinaRepository;
import br.com.ifsp.agendamento.repository.CursoRepository;
import br.com.ifsp.agendamento.repository.DisciplinaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoHasDisciplinaService {

    @Autowired
    private CursoHasDisciplinaRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    // Salvar a relação entre curso e disciplina
    public CursoHasDisciplinaEntity salvar(Long idCurso, Long idDisciplina) {
        CursoHasDisciplinaId id = criarCursoHasDisciplinaId(idCurso, idDisciplina);
        CursoEntity curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + idCurso));
        DisciplinaEntity disciplina = disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + idDisciplina));

        CursoHasDisciplinaEntity relacao = CursoHasDisciplinaEntity.builder()
                .id(id)
                .curso(curso)
                .disciplina(disciplina)
                .build();

        return repository.save(relacao);
    }

    // Buscar todas as relações
    public List<CursoHasDisciplinaEntity> buscarTodos() {
        return repository.findAll();
    }

    // Buscar relação por ID
    public Optional<CursoHasDisciplinaEntity> buscarPorId(CursoHasDisciplinaId id) {
        return repository.findById(id);
    }

    // Remover relação
    public void remover(CursoHasDisciplinaId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Relação não encontrada!");
        }
    }

    // Criar ID para a relação entre curso e disciplina
    public CursoHasDisciplinaId criarCursoHasDisciplinaId(Long idCurso, Long idDisciplina) {
        return new CursoHasDisciplinaId(idCurso, idDisciplina);
    }

    // Listar disciplinas por curso
    public List<DisciplinaEntity> listarDisciplinasPorCurso(Long cursoId) {
        return repository.findDisciplinasByCursoId(cursoId);
    }

}