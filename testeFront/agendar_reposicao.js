$(document).ready(function () {
    // Função para carregar cursos
    async function carregarCursos() {
        try {
            const response = await fetch("http://localhost:8887/cursos/listar");
            if (!response.ok) throw new Error("Erro ao carregar cursos");
            const cursos = await response.json();
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
            const response = await fetch(`http://localhost:8887/api/curso-disciplina/curso/${cursoId}`);
            if (!response.ok) throw new Error("Erro ao carregar disciplinas");
            const disciplinas = await response.json();
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
            const response = await fetch(`http://localhost:8887/agenda/horarios/${disciplinaId}/${data}`);
            if (!response.ok) throw new Error("Erro ao carregar horários");
            const horarios = await response.json();
            const horarioSelect = $('#horario');

            horarioSelect.empty().append(new Option("Selecione um horário", ""));
            horarios.forEach(horario => {
                // Agora, estamos usando o idRep como valor da opção
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
        const raAluno = localStorage.getItem('raAluno'); // Recupera o RA do aluno do localStorage
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
            raAluno: raAluno // RA do aluno
        };

        try {
            const response = await fetch("http://localhost:8887/api/cadastro/agendar", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(agendamentoData)
            });

            if (response.ok) {
                alert("Agendamento confirmado com sucesso!");
                $('#form-agendamento')[0].reset(); // Limpa o formulário
            } else {
                const errorMessage = await response.text();
                alert(`Erro ao confirmar o agendamento: ${errorMessage}`);
            }
        } catch (error) {
            console.error("Erro na requisição:", error);
            alert("Erro na requisição.");
        }
    });

    // Carregar cursos ao iniciar a página
    carregarCursos();
});