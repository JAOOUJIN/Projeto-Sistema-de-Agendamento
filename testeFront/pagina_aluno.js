import { fetchAutenticado } from './utils.js';

// Abrir modal de cursos
document.getElementById("openModalCursos").onclick = function () {
    document.getElementById("modalCursos").style.display = "block";
    carregarCursos(); // Carregar cursos ao abrir o modal
}

// Abrir modal de disciplinas
document.getElementById("openModalDisciplinas").onclick = function () {
    document.getElementById("modalDisciplinas").style.display = "block";
    carregarDisciplinas(); // Carregar disciplinas ao abrir o modal
}

// Abrir modal de alteração de senha
document.getElementById("openModalAlterarSenha").onclick = function () {
    document.getElementById("modalAlterarSenha").style.display = "block";
}

// Fechar modais ao clicar no botão de fechar
document.querySelectorAll(".close").forEach(function (closeBtn) {
    closeBtn.onclick = function () {
        this.closest(".modal").style.display = "none";
    }
});

// Fechar modais ao clicar fora deles
window.onclick = function (event) {
    if (event.target.classList.contains("modal")) {
        event.target.style.display = "none";
    }
}

// Inicializar o calendário
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'pt-br',
        events: [

            {
                title: 'Aula de Matemática',
                start: '2023-10-10T10:00:00',
                end: '2023-10-10T12:00:00'
            },
            {
                title: 'Aula de História',
                start: '2023-10-12T14:00:00',
                end: '2023-10-12T16:00:00'
            }
        ]
    });
    calendar.render();

    carregarDadosAluno();
});

// Função para carregar os dados do aluno
async function carregarDadosAluno() {
    const raAluno = localStorage.getItem("raAluno");
    if (!raAluno) {
        alert("RA não encontrado. Faça login novamente.");
        window.location.href = "index.html";
        return;
    }

    try {
        const aluno = await fetchAutenticado(`http://localhost:8887/aluno/${raAluno}`);
        document.getElementById("nomeAluno").textContent = aluno.nomeAluno;
        document.getElementById("emailAluno").textContent = aluno.emailAluno;
        document.getElementById("raAluno").textContent = aluno.ra;
        document.getElementById("statusAluno").textContent = aluno.statusAluno;
    } catch (error) {
        console.error("Erro ao carregar dados do aluno:", error);
        alert("Erro ao carregar dados do aluno. Verifique o console para mais detalhes.");
        localStorage.clear();
        window.location.href = "index.html";
    }
}
// Função para carregar os cursos
async function carregarCursos() {
    try {
        const cursos = await fetchAutenticado("http://localhost:8887/cursos/listar-nomes");

        const listaCursos = document.getElementById("listaCursos");
        listaCursos.innerHTML = "";

        cursos.forEach(nomeCurso => {
            const item = document.createElement("li");
            item.textContent = nomeCurso;
            listaCursos.appendChild(item);
        });
    } catch (error) {
        console.error("Erro ao carregar cursos:", error);
        alert(error.message);
    }
}

// Função para carregar as disciplinas
async function carregarDisciplinas() {
    try {
        const disciplinas = await fetchAutenticado("http://localhost:8887/disciplinas/listar-nomes");

        const listaDisciplinas = document.getElementById("listaDisciplinas");
        listaDisciplinas.innerHTML = "";

        disciplinas.forEach(nomeDisciplina => {
            const item = document.createElement("li");
            item.textContent = nomeDisciplina;
            listaDisciplinas.appendChild(item);
        });
    } catch (error) {
        console.error("Erro ao carregar disciplinas:", error);
        alert(error.message);
    }
}

// Função para alterar senha
document.getElementById("formAlterarSenha").onsubmit = async function (event) {
    event.preventDefault();

    const raAluno = localStorage.getItem("raAluno");
    const senhaAtual = document.getElementById("senhaAtual").value;
    const novaSenha = document.getElementById("novaSenha").value;
    const confirmarSenha = document.getElementById("confirmarSenha").value;

    if (novaSenha !== confirmarSenha) {
        alert("As senhas não coincidem.");
        return;
    }

    try {
        await fetchAutenticado(`http://localhost:8887/aluno/alterar-senha/${raAluno}`, "PUT", {
            senhaAtual: senhaAtual,
            novaSenha: novaSenha
        });

        alert("Senha alterada com sucesso!");
        document.getElementById("modalAlterarSenha").style.display = "none";
    } catch (error) {
        console.error("Erro ao alterar senha:", error);
        alert(error.message);
    }
};


// Carregar dados do aluno ao carregar a página
document.addEventListener('DOMContentLoaded', carregarDadosAluno);

// Função de logout
document.getElementById("logoutButton").onclick = function () {
    if (confirm("Tem certeza que deseja sair?")) {
        localStorage.removeItem('raAluno');
        localStorage.removeItem('token');
        window.location.href = 'index.html';
    }
};