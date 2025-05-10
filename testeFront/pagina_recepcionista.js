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
    localStorage.clear();
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
        const response = await fetch("http://localhost:8887/aluno/cadastro", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(alunoData)
        });

        if (response.ok) {
            alert("Aluno adicionado com sucesso!");
            modals.addAluno.style.display = "none";
            document.getElementById("addStudentForm").reset(); // Limpa o formulário
        } else {
            const errorMessage = await response.text();
            alert("Erro ao adicionar aluno: " + errorMessage);
        }
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
        const response = await fetch("http://localhost:8887/cursos/cadastrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(cursoData)
        });

        if (response.ok) {
            alert("Curso adicionado com sucesso!");
            modals.addCurso.style.display = "none"; // Fecha o modal
            document.getElementById("addCursoForm").reset(); // Limpa o formulário
        } else {
            const errorMessage = await response.text();
            alert("Erro ao adicionar curso: " + errorMessage);
        }
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
        const response = await fetch("http://localhost:8887/disciplinas/cadastrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(disciplinaData)
        });

        if (response.ok) {
            alert("Disciplina adicionada com sucesso!");
            modals.addDisciplina.style.display = "none"; // Fecha o modal
            document.getElementById("addDisciplinaForm").reset(); // Limpa o formulário
        } else {
            const errorMessage = await response.text();
            alert("Erro ao adicionar disciplina: " + errorMessage);
        }
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
        const response = await fetch(`http://localhost:8887/disciplinas/${idDisciplina}`, {
            method: "DELETE"
        });

        if (response.ok) {
            alert("Disciplina deletada com sucesso!");
            modals.deleteDisciplina.style.display = "none"; // Fecha o modal
            document.getElementById("deleteDisciplinaForm").reset(); // Limpa o formulário
        } else {
            const errorMessage = await response.text();
            alert("Erro ao deletar disciplina: " + errorMessage);
        }
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
        const response = await fetch(`http://localhost:8887/cursos/deletar/${idCurso}`, {
            method: "DELETE"
        });

        if (response.ok) {
            alert("Curso deletado com sucesso!");
            modals.deleteCurso.style.display = "none";
            document.getElementById("deleteCursoForm").reset(); // Limpa o formulário
        } else {
            const errorMessage = await response.text();
            alert("Erro ao deletar curso: " + errorMessage);
        }
    } catch (error) {
        console.error("Erro ao deletar curso:", error);
        alert("Erro ao deletar curso.");
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
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Erro ao buscar " + tipoBusca);
        }

        const resultados = await response.json();

        exibirResultados(tipoBusca, resultados);

        // Fechar o modal após a busca
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

    alert(mensagem); // Exibe os resultados em um alerta
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
