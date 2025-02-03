$(document).ready(function () {
    // Alternância entre Login e Cadastro
    $('#goRight').on('click', function () {
        $('#slideBox').animate({ 'marginLeft': '0' });
        $('.topLayer').animate({ 'marginLeft': '100%' });
    });

    $('#goLeft').on('click', function () {
        if (window.innerWidth > 769) {
            $('#slideBox').animate({ 'marginLeft': '50%' });
        } else {
            $('#slideBox').animate({ 'marginLeft': '20%' });
        }
        $('.topLayer').animate({ 'marginLeft': '0' });
    });

    // Lógica de Login
    $('#form-login').on('submit', async function (event) {
        event.preventDefault();
        const ra = $('#RA-login').val();
        const senha = $('#password-login').val();
        const userType = $('#user-type').val();

        try {
            const response = await fetch("http://localhost:8887/aluno/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username: ra, senha: senha, userType: userType })
            });

            if (!response.ok) throw new Error(`Erro: ${response.status} - ${await response.text()}`);
            alert("Login realizado com sucesso!");

            localStorage.setItem('raAluno', ra);

            // Redireciona com base no tipo de usuário
            if (userType === "aluno") window.location.href = "pagina_aluno.html";
            else window.location.href = "pagina_recepcionista.html";
        } catch (error) {
            alert("Erro no login. " + error.message);
        }
    });

    $('#form-signup').on('submit', async function (event) {
        event.preventDefault();

        const ra = $('#RA-signup').val();
        const nomeAluno = $('#nome-completo').val();
        const emailAluno = $('#email').val();
        const senhaAluno = $('#password-signup').val();
        const confirmarSenha = $('#confirm-password').val();

        console.log("RA:", ra);
        console.log("Nome:", nomeAluno);
        console.log("Email:", emailAluno);
        console.log("Senha:", senhaAluno);

        // Verifica se as senhas coincidem
        if (senhaAluno !== confirmarSenha) {
            alert("As senhas não coincidem. Por favor, tente novamente.");
            return;
        }

        // Cria o objeto de cadastro
        const alunoRequest = {
            ra: ra,
            nomeAluno: nomeAluno,
            emailAluno: emailAluno,
            senhaAluno: senhaAluno
        };

        try {
            const response = await fetch("http://localhost:8887/aluno/cadastro", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(alunoRequest)
            });

            const data = await response.text();

            if (response.ok) {
                alert("Cadastro realizado com sucesso!");
                $('#form-signup')[0].reset(); // Limpa o formulário
                $('#goLeft').click(); // Volta para a tela de login
            } else {
                alert("Erro no cadastro: " + data);
            }
        } catch (error) {
            alert("Erro na requisição: " + error.message);
        }
    });
    // Animação do Canvas
    paper.install(window);
    paper.setup(document.getElementById("canvas"));

    const shapeGroup = new Group();
    const positionArray = [];

    function getCanvasBounds() {
        const canvasWidth = view.size.width;
        const canvasHeight = view.size.height;
        const canvasMiddleX = canvasWidth / 2;
        const canvasMiddleY = canvasHeight / 2;

        positionArray.length = 0;
        positionArray.push(
            { x: (canvasMiddleX - 50) + (canvasMiddleX / 2), y: 150 },
            { x: 200, y: canvasMiddleY },
            { x: canvasWidth - 130, y: canvasHeight - 75 },
            { x: 0, y: canvasMiddleY + 100 },
            { x: (canvasMiddleX / 2) + 100, y: 100 },
            { x: canvasMiddleX + 80, y: canvasHeight - 50 },
            { x: canvasWidth + 60, y: canvasMiddleY - 50 },
            { x: canvasMiddleX + 100, y: canvasMiddleY + 100 }
        );
    }

    function initializeShapes() {
        getCanvasBounds();
        const shapePathData = [
            'M231,352l445-156L600,0L452,54L331,3L0,48L231,352',
            'M0,0l64,219L29,343l535,30L478,37l-133,4L0,0z',
            'M0,65l16,138l96,107l270-2L470,0L337,4L0,65z',
            'M333,0L0,94l64,219L29,437l570-151l-196-42L333,0',
            'M331.9,3.6l-331,45l231,304l445-156l-76-196l-148,54L331.9,3.6z',
            'M389,352l92-113l195-43l0,0l0,0L445,48l-80,1L122.7,0L0,275.2L162,297L389,352',
            'M 50 100 L 300 150 L 550 50 L 750 300 L 500 250 L 300 450 L 50 100',
            'M 700 350 L 500 350 L 700 500 L 400 400 L 200 450 L 250 350 L 100 300 L 150 50 L 350 100 L 250 150 L 450 150 L 400 50 L 550 150 L 350 250 L 650 150 L 650 50 L 700 150 L 600 250 L 750 250 L 650 300 L 700 350 '
        ];

        shapePathData.forEach((pathData, i) => {
            const headerShape = new Path({
                strokeColor: 'rgba(255, 255, 255, 0.5)',
                strokeWidth: 2,
                parent: shapeGroup,
            });
            headerShape.pathData = pathData;
            headerShape.scale(2);
            headerShape.position = positionArray[i];
        });
    }

    initializeShapes();

    view.onFrame = function (event) {
        if (event.count % 4 === 0) {
            shapeGroup.children.forEach((child, i) => {
                child.rotate(i % 2 === 0 ? -0.1 : 0.1);
            });
        }
    };

    view.onResize = function () {
        getCanvasBounds();
        shapeGroup.children.forEach((child, i) => {
            child.position = positionArray[i];
            child.opacity = view.size.width < 700 && [2, 3, 5].includes(i) ? 0 : 1;
        });
    };
});