package br.com.ifsp.agendamento.service;

import br.com.ifsp.agendamento.dto.CursoEntity;
import br.com.ifsp.agendamento.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    // Metodo para listar todos os cursos
    public List<CursoEntity> listarTodos() {
        return cursoRepository.findAll();
    }

    public List<String> listarNomes() {
        return cursoRepository.findAll().stream()
                .map(CursoEntity::getNomeCurso)
                .collect(Collectors.toList());
    }

    // Metodo para buscar curso por ID
    public Optional<CursoEntity> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    // Metodo para deletar curso por ID
    public void deletarPorId(Long id) {
        cursoRepository.deleteById(id);
    }

    //Metodo para buscar curso por Nome
    public List<CursoEntity> buscarPorNome(String nomeCurso) {
        return cursoRepository.findByNomeCursoContainingIgnoreCase(nomeCurso);
    }

    //Metodo para buscar TOTAL
    public int contarTotalCursos() {
        return cursoRepository.fn_TotalCursos();
    }

    // Metodo para salvar curso
    public void adicionarCurso(String nomeCurso) {
        cursoRepository.addCurso(nomeCurso);
    }

}
