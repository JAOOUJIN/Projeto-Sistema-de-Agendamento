export async function fetchAutenticado(url, metodo = "GET", corpo = null, incluirAuth = true) {
    const headers = {
        "Content-Type": "application/json"
    };

    if (incluirAuth) {
        const token = localStorage.getItem("token");
        if (token) {
            headers["Authorization"] = `Bearer ${token}`;
        } else {
            alert("Token não encontrado. Faça login novamente.");
            window.location.href = "index.html";
            throw new Error("Token ausente");
        }
    }

    const config = {
        method: metodo,
        headers: headers
    };

    if (corpo) {
        config.body = JSON.stringify(corpo);
    }

    const response = await fetch(url, config);

    if (response.status === 401) {
        alert("Sessão expirada. Faça login novamente.");
        localStorage.clear();
        window.location.href = "index.html";
        throw new Error("Não autorizado");
    }

    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        const mensagem = errorData.message || "Erro ao processar a requisição.";
        throw new Error(mensagem);
    }

    if (response.status === 204) return null;

    return response.json();
}
