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
        <p><strong>Disciplina:</strong> ${agendamento.agendamento.disciplinas[0].nomeDisciplina}</p>
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

// Função para abrir o modal de busca geral
document.getElementById("openModalBuscarGeral").onclick = function () {
    const modalBuscarGeral = document.getElementById("modalBuscarGeral");
    modalBuscarGeral.style.display = "block";
};

// Função para fechar o modal de busca geral
document.querySelector("#modalBuscarGeral .close").onclick = function () {
    const modalBuscarGeral = document.getElementById("modalBuscarGeral");
    modalBuscarGeral.style.display = "none";
};

// Fechar o modal de busca geral ao clicar fora dele
window.onclick = function (event) {
    const modalBuscarGeral = document.getElementById("modalBuscarGeral");
    if (event.target === modalBuscarGeral) {
        modalBuscarGeral.style.display = "none";
    }
};

// Função de logout
document.getElementById("logoutButton").onclick = function () {
    localStorage.clear(); // Limpa o localStorage
    window.location.href = 'index.html'; // Redireciona para a página de login
};

// Função para carregar agendamentos pendentes
async function carregarAgendamentosPendentes() {
    try {
        const response = await fetch("http://localhost:8887/api/cadastro/pendentes");

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }

        const agendamentos = await response.json();
        const pendentesList = document.getElementById("pendentesList");
        pendentesList.innerHTML = ""; // Limpa a lista

        // Verifica se há agendamentos pendentes
        if (agendamentos.length === 0) {
            pendentesList.innerHTML = `
                <tr>
                    <td colspan="6" style="text-align: center;">Nenhum agendamento pendente encontrado.</td>
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
                <td>${agendamento.dataCadastro}</td> <!-- Data de cadastro -->
                <td>${agendamento.agendamento.disciplinas[0].nomeDisciplina}</td> <!-- Apenas a disciplina escolhida -->
                <td>
                    <button onclick="abrirModalDetalhes(${JSON.stringify(agendamento).replace(/"/g, '&quot;')})">Detalhes</button>
                    <button onclick="aceitarAgendamento(${agendamento.id})">Aceitar</button>
                    <button class="rejeitar" onclick="rejeitarAgendamento(${agendamento.id})">Rejeitar</button>
                </td>
            `;
            pendentesList.appendChild(tr);
        });
    } catch (error) {
        console.error("Erro ao carregar agendamentos pendentes:", error);
        alert("Erro ao carregar agendamentos pendentes. Verifique o console para mais detalhes.");
    }
}

// Função para aceitar um agendamento
async function aceitarAgendamento(id) {
    try {
        const response = await fetch(`http://localhost:8887/api/cadastro/aceitar/${id}`, {
            method: "POST"
        });

        if (response.ok) {
            alert("Agendamento aceito com sucesso!");
            carregarAgendamentosPendentes(); // Recarrega a lista
        } else {
            throw new Error("Erro ao aceitar o agendamento.");
        }
    } catch (error) {
        console.error("Erro ao aceitar agendamento:", error);
        alert("Erro ao aceitar o agendamento.");
    }
}

// Função para rejeitar um agendamento
async function rejeitarAgendamento(id) {
    try {
        const response = await fetch(`http://localhost:8887/api/cadastro/rejeitar/${id}`, {
            method: "POST"
        });

        if (response.ok) {
            alert("Agendamento rejeitado com sucesso!");
            carregarAgendamentosPendentes(); // Recarrega a lista
        } else {
            throw new Error("Erro ao rejeitar o agendamento.");
        }
    } catch (error) {
        console.error("Erro ao rejeitar agendamento:", error);
        alert("Erro ao rejeitar o agendamento.");
    }
}

// Carregar agendamentos pendentes ao carregar a página
document.addEventListener('DOMContentLoaded', function () {
    carregarAgendamentosPendentes();
});