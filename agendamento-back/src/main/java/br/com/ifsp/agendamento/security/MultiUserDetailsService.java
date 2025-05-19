package br.com.ifsp.agendamento.security;

import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import br.com.ifsp.agendamento.repository.AlunoRepository;
import br.com.ifsp.agendamento.dto.AlunoEntity;
import br.com.ifsp.agendamento.repository.RecepcionistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultiUserDetailsService implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AlunoEntity> alunoOpt = alunoRepository.findByRa(username);
        if (alunoOpt.isPresent()) {
            AlunoEntity aluno = alunoOpt.get();
            return User.builder()
                    .username(aluno.getRa())
                    .password(aluno.getSenhaAluno())
                    .roles("ALUNO")
                    .build();
        }

        Optional<RecepcionistaEntity> recepOpt = recepcionistaRepository.findByCdRecep(username);
        if (recepOpt.isPresent()) {
            RecepcionistaEntity recep = recepOpt.get();
            return User.builder()
                    .username(recep.getCdRecep())
                    .password(recep.getSenha())
                    .roles("RECEPCIONISTA")
                    .build();
        }

        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}