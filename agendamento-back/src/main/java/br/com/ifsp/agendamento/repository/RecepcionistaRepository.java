package br.com.ifsp.agendamento.repository;

import br.com.ifsp.agendamento.dto.RecepcionistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RecepcionistaRepository extends JpaRepository<RecepcionistaEntity, Long> {

    //codigo e senha
    @Query(nativeQuery = true, value = "SELECT * FROM RECEPCIONISTA WHERE cd_recep = :cd_recep AND senha = :senha")
    Optional<RecepcionistaEntity> findByCodigoAndSenha(@Param("cd_recep") String cd_recep, @Param("senha") String senha);


}