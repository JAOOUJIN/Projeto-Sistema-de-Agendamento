package br.com.ifsp.agendamento.security;

import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.dto.AlunoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AlunoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String ra) throws UsernameNotFoundException {
        AlunoEntity aluno = repository.findByRa(ra)
                .orElseThrow(() -> new UsernameNotFoundException("Aluno não encontrado"));

        return User.builder()
                .username(aluno.getRa())
                .password(aluno.getSenhaAluno())
                .roles("USER")
                .build();
    }
}