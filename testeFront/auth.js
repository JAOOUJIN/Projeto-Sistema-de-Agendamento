// Lógica de Login usando JWT
$('#form-login').on('submit', async function (event) {
    event.preventDefault();

    const raAluno = $('#RA-login').val();
    const senha = $('#password-login').val();
    const userType = $('#user-type').val();

    if (!raAluno || !senha || !userType) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const endpoint = "http://localhost:8887/auth/login";

    try {
        const response = await fetch(endpoint, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username: raAluno, senha: senha, userType: userType })
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro: ${response.status} - ${errorText}`);
        }

        const data = await response.json();

        localStorage.setItem('token', data.token);
        localStorage.setItem('userType', userType);
        localStorage.setItem('raAluno', raAluno);

        alert("Login realizado com sucesso!");

        if (userType === "aluno") {
            window.location.href = "pagina_aluno.html";
        } else {
            window.location.href = "pagina_recepcionista.html";
        }
    } catch (error) {
        alert("Erro no login. " + error.message);
        console.error(error);
    }
});
