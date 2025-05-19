import { fetchAutenticado } from './utils.js';

$(document).ready(function () {
    // Função para carregar cursos
    async function carregarCursos() {
        try {
            const cursos = await fetchAutenticado("http://localhost:8887/cursos/listar");
            const cursoSelect = $('#curso');

            cursoSelect.empty().append(new Option("Selecione um curso", ""));
            cursos.forEach(curso => {
                cursoSelect.append(new Option(curso.nomeCurso, curso.idCurso));
            });
        } catch (error) {
            console.error("Erro ao carregar cursos:", error);
            alert("Erro ao carregar cursos.");
        }
    }

    // Carregar disciplinas com base no curso selecionado
    $('#curso').on('change', async function () {
        const cursoId = $(this).val();
        if (!cursoId) return;

        try {
            const disciplinas = await fetchAutenticado(`http://localhost:8887/api/curso-disciplina/curso/${cursoId}`);
            const disciplinaSelect = $('#disciplina');

            disciplinaSelect.empty().append(new Option("Selecione uma disciplina", ""));
            disciplinas.forEach(disciplina => {
                disciplinaSelect.append(new Option(disciplina.nomeDisciplina, disciplina.idDisciplina));
            });
        } catch (error) {
            console.error("Erro ao carregar disciplinas:", error);
            alert("Erro ao carregar disciplinas.");
        }
    });

    // Carregar horários disponíveis com base na disciplina e data selecionadas
    $('#data').on('change', async function () {
        const disciplinaId = $('#disciplina').val();
        const data = $(this).val();

        if (!disciplinaId || !data) {
            alert("Por favor, selecione uma disciplina e uma data.");
            return;
        }

        try {
            const horarios = await fetchAutenticado(`http://localhost:8887/agenda/horarios/${disciplinaId}/${data}`);
            const horarioSelect = $('#horario');

            horarioSelect.empty().append(new Option("Selecione um horário", ""));
            horarios.forEach(horario => {
                horarioSelect.append(new Option(horario.horario, horario.idRep));
            });
        } catch (error) {
            console.error("Erro ao carregar horários:", error);
            alert("Erro ao carregar horários.");
        }
    });

    // Lógica para o botão "Confirmar Agendamento"
    $('#confirmar').on('click', async function () {
        const disciplinaId = $('#disciplina').val();
        const data = $('#data').val();
        const horarioId = $('#horario').val();
        const raAluno = localStorage.getItem('raAluno');
        const idRecepcionista = 1;

        // Verifica se todos os campos estão preenchidos
        if (!disciplinaId || !data || !horarioId || !raAluno) {
            alert("Por favor, preencha todos os campos e faça login.");
            return;
        }

        // Monta o objeto de agendamento
        const agendamentoData = {
            idRecepcionista: idRecepcionista,
            idAgendamento: parseInt(horarioId),
            raAluno: raAluno
        };

        try {
            await fetchAutenticado("http://localhost:8887/api/cadastro/agendar", "POST", agendamentoData);
            alert("Agendamento confirmado com sucesso!");
            $('#form-agendamento')[0].reset();
        } catch (error) {
            console.error("Erro ao confirmar o agendamento:", error);
            alert("Erro ao confirmar o agendamento.");
        }
    });

    // Carregar cursos ao iniciar a página
    carregarCursos();
});