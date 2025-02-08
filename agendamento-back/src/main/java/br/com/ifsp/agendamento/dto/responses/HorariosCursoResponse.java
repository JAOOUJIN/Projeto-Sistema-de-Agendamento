package br.com.ifsp.agendamento.dto.responses;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorariosCursoResponse(Long idRep, LocalTime horario, LocalDate data) {
}
