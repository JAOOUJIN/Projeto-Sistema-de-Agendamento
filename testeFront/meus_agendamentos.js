// Função para abrir o modal de detalhes
function abrirModalDetalhes(agendamento) {
    const modalDetalhes = document.getElementById("modalDetalhes");
    const detalhesAgendamento = document.getElementById("detalhesAgendamento");

    // Preenche o modal com as informações do agendamento
    detalhesAgendamento.innerHTML = `
        <p><strong>ID:</strong> ${agendamento.id}</p>
        <p><strong>RA do Aluno:</strong> ${agendamento.aluno.ra}</p>
        <p><strong>Nome do Aluno:</strong> ${agendamento.aluno.nomeAluno}</p>
        <p><strong>Email do Aluno:</strong> ${agendamento.aluno.emailAluno}</p>
        <p><strong>Data do Agendamento:</strong> ${agendamento.agendamento.data}</p>
        <p><strong>Horário:</strong> ${agendamento.agendamento.horario}</p>
        <p><strong>Disciplina:</strong> ${agendamento.agendamento.disciplinas.map(d => d.nomeDisciplina).join(", ")}</p>
        <p><strong>Status:</strong> ${agendamento.statusCadastro}</p>
    `;

    // Exibe o modal
    modalDetalhes.style.display = "block";
}

// Função para fechar o modal de detalhes
function fecharModalDetalhes() {
    const modalDetalhes = document.getElementById("modalDetalhes");
    modalDetalhes.style.display = "none";
}

// Fechar o modal ao clicar no botão de fechar (×)
document.querySelector("#modalDetalhes .close").onclick = fecharModalDetalhes;

// Fechar o modal ao clicar fora dele
window.onclick = function (event) {
    const modalDetalhes = document.getElementById("modalDetalhes");
    if (event.target === modalDetalhes) {
        fecharModalDetalhes();
    }
};

// Função de logout
document.getElementById("logoutButton").onclick = function () {
    localStorage.clear(); // Limpa o localStorage
    window.location.href = 'index.html'; // Redireciona para a página de login
};

// Função para carregar os agendamentos do aluno
async function carregarMeusAgendamentos() {
    const raAluno = localStorage.getItem('raAluno'); // Recupera o RA do aluno do localStorage
    if (!raAluno) {
        alert("Por favor, faça login.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8887/api/cadastro/aluno/${raAluno}`);

        // Verifica se a resposta da API é válida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }

        const agendamentos = await response.json();
        const agendamentosList = document.getElementById("agendamentosList");
        agendamentosList.innerHTML = ""; // Limpa a lista

        // Verifica se há agendamentos
        if (agendamentos.length === 0) {
            agendamentosList.innerHTML = `
                <tr>
                    <td colspan="6" style="text-align: center;">Nenhum agendamento encontrado.</td>
                </tr>
            `;
            return;
        }

        // Preenche a tabela com os agendamentos
        agendamentos.forEach(agendamento => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${agendamento.id}</td>
                <td>${agendamento.aluno.nomeAluno}</td>
                <td>${agendamento.agendamento.disciplinas.map(d => d.nomeDisciplina).join(", ")}</td>
                <td>${agendamento.dataCadastro}</td>
                <td>${agendamento.statusCadastro}</td>
                <td>
                    <button onclick="abrirModalDetalhes(${JSON.stringify(agendamento).replace(/"/g, '&quot;')})">Detalhes</button>
                </td>
            `;
            agendamentosList.appendChild(tr);
        });
    } catch (error) {
        console.error("Erro ao carregar agendamentos:", error);
        alert("Erro ao carregar agendamentos. Verifique o console para mais detalhes.");
    }
}

// Carregar agendamentos ao carregar a página
window.onload = function () {
    carregarMeusAgendamentos();
};