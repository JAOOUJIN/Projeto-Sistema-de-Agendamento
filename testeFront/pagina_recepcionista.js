import { fetchAutenticado } from './utils.js';


// Funções para abrir e fechar modais
const modals = {
    addAluno: document.getElementById("modalAddAluno"),
    addCurso: document.getElementById("modalAddCurso"),
    addDisciplina: document.getElementById("modalAddDisciplina"),
    deleteAluno: document.getElementById("modalDeleteAluno"),
    deleteCurso: document.getElementById("modalDeleteCurso"),
    deleteDisciplina: document.getElementById("modalDeleteDisciplina"),
    buscarGeral: document.getElementById("modalBuscarGeral"),
};

const openModalButtons = {
    addAluno: document.getElementById("openModalAddAluno"),
    addCurso: document.getElementById("openModalAddCurso"),
    addDisciplina: document.getElementById("openModalAddDisciplina"),
    deleteAluno: document.getElementById("openModalDeleteAluno"),
    deleteCurso: document.getElementById("openModalDeleteCurso"),
    deleteDisciplina: document.getElementById("openModalDeleteDisciplina"),
    buscarGeral: document.getElementById("openModalBuscarGeral"),
};

const closeModalButtons = document.querySelectorAll(".close");

// Abrir modais
openModalButtons.addAluno.onclick = () => modals.addAluno.style.display = "block";
openModalButtons.addCurso.onclick = () => modals.addCurso.style.display = "block";
openModalButtons.addDisciplina.onclick = () => modals.addDisciplina.style.display = "block";
openModalButtons.deleteAluno.onclick = () => modals.deleteAluno.style.display = "block";
openModalButtons.deleteCurso.onclick = () => modals.deleteCurso.style.display = "block";
openModalButtons.deleteDisciplina.onclick = () => modals.deleteDisciplina.style.display = "block";
openModalButtons.buscarGeral.onclick = () => modals.buscarGeral.style.display = "block";

// Fechar modais
closeModalButtons.forEach(button => {
    button.onclick = () => {
        Object.values(modals).forEach(modal => modal.style.display = "none");
    };
});

// Fechar modais ao clicar fora
window.onclick = function (event) {
    if (event.target.classList.contains("modal")) {
        Object.values(modals).forEach(modal => modal.style.display = "none");
    }
};

// Função de logout
document.getElementById("logoutButton").onclick = function () {
    localStorage.removeItem('raAluno');
    localStorage.removeItem('token');
    window.location.href = 'index.html';
};

// Função para recarregar a página (Home)
document.getElementById("homeButton").onclick = function () {
    location.reload();
};

// Função para buscar geral
document.getElementById("buscarGeralForm").onsubmit = function (event) {
    event.preventDefault();
    const tipoBusca = document.getElementById("tipoBusca").value;
    const termoBusca = document.getElementById("termoBusca").value;
    alert(`Busca realizada:\nTipo: ${tipoBusca}\nTermo: ${termoBusca}`);
    modals.buscarGeral.style.display = "none";
};

// Função para adicionar aluno
document.getElementById("addStudentForm").onsubmit = async function (event) {
    event.preventDefault();

    const alunoData = {
        ra: document.getElementById("ra").value,
        nomeAluno: document.getElementById("nome").value,
        emailAluno: document.getElementById("email").value,
        senhaAluno: document.getElementById("senha").value
    };

    try {
        await fetchAutenticado("http://localhost:8887/aluno/cadastro", "POST", alunoData);

        alert("Aluno adicionado com sucesso!");
        modals.addAluno.style.display = "none";
        document.getElementById("addStudentForm").reset();
    } catch (error) {
        console.error("Erro ao adicionar aluno:", error);
        alert("Erro ao adicionar aluno.");
    }
};

// Função para adicionar curso
document.getElementById("addCursoForm").onsubmit = async function (event) {
    event.preventDefault();

    const cursoData = {
        nomeCurso: document.getElementById("nomeCurso").value
    };

    try {
        await fetchAutenticado("http://localhost:8887/cursos/cadastrar", "POST", cursoData);

        alert("Curso adicionado com sucesso!");
        modals.addCurso.style.display = "none";
        document.getElementById("addCursoForm").reset();

    } catch (error) {
        console.error("Erro ao adicionar curso:", error);
        alert("Erro ao adicionar curso.");
    }
};

// Função para adicionar disciplina
document.getElementById("addDisciplinaForm").onsubmit = async function (event) {
    event.preventDefault();
    const disciplinaData = {
        nomeDisciplina: document.getElementById("nomeDisciplina").value,
        cargaHoraria: document.getElementById("cargaHoraria").value,
        numeroAlunos: document.getElementById("numeroAlunos").value
    };

    try {
        await fetchAutenticado("http://localhost:8887/disciplinas/cadastrar", "POST", disciplinaData);

        alert("Disciplina adicionada com sucesso!");
        modals.addDisciplina.style.display = "none";
        document.getElementById("addDisciplinaForm").reset();

    } catch (error) {
        console.error("Erro ao adicionar disciplina:", error);
        alert("Erro ao adicionar disciplina.");
    }
};

// Função para deletar disciplina
document.getElementById("deleteDisciplinaForm").onsubmit = async function (event) {
    event.preventDefault();

    const idDisciplina = document.getElementById("idDisciplina").value;

    try {
        await fetchAutenticado(`http://localhost:8887/disciplinas/deletar/${idDisciplina}`, "DELETE");

        alert("Disciplina deletada com sucesso!");
        modals.deleteDisciplina.style.display = "none";
        document.getElementById("deleteDisciplinaForm").reset();

    } catch (error) {
        console.error("Erro ao deletar disciplina:", error);
        alert("Erro ao deletar disciplina.");
    }
};

// Função para deletar curso
document.getElementById("deleteCursoForm").onsubmit = async function (event) {
    event.preventDefault();

    const idCurso = document.getElementById("idCurso").value;

    try {
        await fetchAutenticado(`http://localhost:8887/cursos/deletar/${idCurso}`, "DELETE");

        alert("Curso deletado com sucesso!");
        modals.deleteCurso.style.display = "none";
        document.getElementById("deleteCursoForm").reset();

    } catch (error) {
        console.error("Erro ao deletar curso:", error);
        alert("Erro ao deletar curso.");
    }
};

document.getElementById("deleteStudentForm").onsubmit = async function (event) {
    event.preventDefault();

    const raAluno = document.getElementById("raDelete").value;

    try {
        const aluno = await fetchAutenticado(`http://localhost:8887/aluno/${raAluno}`, "GET");
        console.log("Aluno recebido:", aluno);

        if (!aluno || !aluno.idAluno) {
            alert("Aluno não encontrado.");
            return;
        }

        await fetchAutenticado(`http://localhost:8887/aluno/delete/${aluno.idAluno}`, "DELETE");

        alert("Aluno deletado com sucesso!");
        modals.deleteAluno.style.display = "none";
        document.getElementById("deleteStudentForm").reset();
    } catch (error) {
        console.error("Erro ao deletar aluno:", error);
        alert("Erro ao deletar aluno.");
    }
};

// Função para buscar geral
document.getElementById("buscarGeralForm").onsubmit = async function (event) {
    event.preventDefault();

    const tipoBusca = document.getElementById("tipoBusca").value;
    const termoBusca = document.getElementById("termoBusca").value;

    let url;
    if (tipoBusca === "aluno") {
        url = `http://localhost:8887/aluno/buscar/nome/${termoBusca}`;
    } else if (tipoBusca === "curso") {
        url = `http://localhost:8887/cursos/buscar/nome/${termoBusca}`;
    } else if (tipoBusca === "disciplina") {
        url = `http://localhost:8887/disciplinas/buscar/nome/${termoBusca}`;
    }

    try {
        const resultados = await fetchAutenticado(url);
        exibirResultados(tipoBusca, resultados);
        modals.buscarGeral.style.display = "none";
    } catch (error) {
        console.error("Erro ao buscar:", error);
        alert("Erro ao buscar: " + error.message);
    }
};

// Função para exibir os resultados
function exibirResultados(tipo, resultados) {
    let mensagem = `Resultados encontrados para ${tipo}:\n`;

    if (tipo === "aluno") {
        if (resultados.length > 0) {
            resultados.forEach(aluno => {
                mensagem += `RA: ${aluno.ra}, Nome: ${aluno.nomeAluno}, Email: ${aluno.emailAluno}\n`;
            });
        } else {
            mensagem += "Nenhum aluno encontrado.";
        }
    } else if (tipo === "curso") {
        if (resultados.length > 0) {
            resultados.forEach(curso => {
                mensagem += `ID: ${curso.idCurso}, Nome: ${curso.nomeCurso}\n`;
            });
        } else {
            mensagem += "Nenhum curso encontrado.";
        }
    } else if (tipo === "disciplina") {
        if (resultados.length > 0) {
            resultados.forEach(disciplina => {
                mensagem += `ID: ${disciplina.idDisciplina}, Nome: ${disciplina.nomeDisciplina}\n`;
            });
        } else {
            mensagem += "Nenhuma disciplina encontrada.";
        }
    }

    alert(mensagem);
}

// Inicializar o calendário
document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'pt-br',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [
            {
                title: 'Reunião de Professores',
                start: '2023-10-05',
                end: '2023-10-05',
                color: '#2196F3'
            },
            {
                title: 'Prova de Matemática',
                start: '2023-10-10',
                end: '2023-10-10',
                color: '#4CAF50'
            },
            {
                title: 'Feriado Escolar',
                start: '2023-10-12',
                end: '2023-10-12',
                color: '#FF9800'
            }
        ]
    });
    calendar.render();
});

// Função para carregar o total de alunos
async function loadTotalAlunos() {
    try {
        const totalAlunos = await fetchAutenticado('http://localhost:8887/aluno/total');
        document.getElementById('totalAlunos').innerText = totalAlunos;
    } catch (error) {
        console.error('Erro ao carregar total de alunos:', error);
        document.getElementById('totalAlunos').innerText = 'Erro';
    }
}

// Função para carregar o total de cursos
async function loadTotalCursos() {
    try {
        const totalCursos = await fetchAutenticado('http://localhost:8887/cursos/total');
        document.getElementById('totalCursos').innerText = totalCursos;
    } catch (error) {
        console.error('Erro ao carregar total de cursos:', error);
        document.getElementById('totalCursos').innerText = 'Erro';
    }
}

// Função para carregar o total de disciplinas
async function loadTotalDisciplinas() {
    try {
        const totalDisciplinas = await fetchAutenticado('http://localhost:8887/disciplinas/total');
        document.getElementById('totalDisciplinas').innerText = totalDisciplinas;
    } catch (error) {
        console.error('Erro ao carregar total de disciplinas:', error);
        document.getElementById('totalDisciplinas').innerText = 'Erro';
    }
}

// Função para carregar o total de agendamentos pendentes
async function loadTotalAgendamentosPendentes() {
    try {
        const totalPendentes = await fetchAutenticado('http://localhost:8887/api/cadastro/total/pendentes');
        document.getElementById('totalAgendamentosPendentes').innerText = totalPendentes;
    } catch (error) {
        console.error('Erro ao carregar total de agendamentos pendentes:', error);
        document.getElementById('totalAgendamentosPendentes').innerText = 'Erro';
    }
}

// Função para carregar o total de agendamentos ativos
async function loadTotalAgendamentosAtivos() {
    try {
        const totalAtivos = await fetchAutenticado('http://localhost:8887/api/cadastro/total/ativos');
        document.getElementById('totalAgendamentosAtivos').innerText = totalAtivos;
    } catch (error) {
        console.error('Erro ao carregar total de agendamentos ativos:', error);
        document.getElementById('totalAgendamentosAtivos').innerText = 'Erro';
    }
}

// Chama as funções ao carregar a página
window.onload = function () {
    loadTotalAlunos();
    loadTotalCursos();
    loadTotalDisciplinas();
    loadTotalAgendamentosPendentes();
    loadTotalAgendamentosAtivos();
};