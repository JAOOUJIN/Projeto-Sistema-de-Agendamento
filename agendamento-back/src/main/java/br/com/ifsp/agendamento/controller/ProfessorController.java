package br.com.ifsp.agendamento.controller;

import br.com.ifsp.agendamento.dto.ProfessorEntity;
import br.com.ifsp.agendamento.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    //BUSCAR TODOS
    @GetMapping("/listar")
    public List<ProfessorEntity> listarTodos() {
        return professorService.listarTodos();
    }

    //CADASTRAR
    @PostMapping("/cadastrar")
    public ProfessorEntity cadastrar(@RequestBody ProfessorEntity professor) {
        return professorService.salvarProfessor(professor);
    }

    //ATUALIZAR
    @PutMapping("/atualizar/{id}")
    public ProfessorEntity atualizar(@PathVariable("id") Long id, @RequestBody ProfessorEntity professorAtualizado) {
        return professorService.atualizarProfessor(id, professorAtualizado);
    }

    //DELETE
    @DeleteMapping("/deletar/{id}")
    public void deletar(@PathVariable("id") Long id) {
        professorService.deletarProfessor(id);
    }
}
