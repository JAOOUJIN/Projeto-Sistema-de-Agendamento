CREATE DATABASE sistema_agendamento

USE sistema_agendamento

-- Tabela AGENDAMENTO
CREATE TABLE AGENDAMENTO (
    id_rep  INT IDENTITY PRIMARY KEY,
    horario TIME NOT NULL,
    data    DATE NOT NULL
);
GO

-- Tabela ALUNO
CREATE TABLE ALUNO (
    id_aluno     INT IDENTITY PRIMARY KEY,
    ra           NVARCHAR(20)  NOT NULL UNIQUE,
    nome_aluno   NVARCHAR(50)  NOT NULL,
    email_aluno  NVARCHAR(45)  NOT NULL UNIQUE,
    status_aluno NVARCHAR(45)  NOT NULL,
    senha        NVARCHAR(255) NOT NULL
);
GO

-- Tabela CURSO
CREATE TABLE CURSO (
    id_curso   INT IDENTITY PRIMARY KEY,
    nome_curso NVARCHAR(45) NOT NULL UNIQUE
);
GO

-- Tabela DISCIPLINA
CREATE TABLE DISCIPLINA (
    id_disciplina   INT IDENTITY PRIMARY KEY,
    nome_disciplina NVARCHAR(45) NOT NULL UNIQUE,
    carga_horaria   INT          NOT NULL,
    numero_alunos   INT          NOT NULL
);
GO

-- Tabela de relacionamento CURSO_DISCIPLINA
CREATE TABLE CURSO_DISCIPLINA (
    id_curso           INT NOT NULL,
    id_disciplina      INT NOT NULL,
    PRIMARY KEY (id_curso, id_disciplina),
    FOREIGN KEY (id_curso) REFERENCES CURSO(id_curso),
    FOREIGN KEY (id_disciplina) REFERENCES DISCIPLINA(id_disciplina)
);
GO

-- Tabela de relacionamento DISC_REP (Disciplina e Agendamento)
CREATE TABLE DISC_REP (
    id_disciplina INT NOT NULL,
    id_rep        INT NOT NULL,
    PRIMARY KEY (id_disciplina, id_rep),
    FOREIGN KEY (id_disciplina) REFERENCES DISCIPLINA(id_disciplina),
    FOREIGN KEY (id_rep) REFERENCES AGENDAMENTO(id_rep)
);
GO

-- Tabela PROFESSOR
CREATE TABLE PROFESSOR (
    id_prof     INT IDENTITY PRIMARY KEY,
    nome_prof   NVARCHAR(50) NOT NULL,
    email_prof  NVARCHAR(45) NOT NULL UNIQUE,
    status_prof NVARCHAR(45)
);
GO

-- Tabela RECEPCIONISTA
CREATE TABLE RECEPCIONISTA (
    id_recep    INT IDENTITY PRIMARY KEY,
    cd_recep    NVARCHAR(20)  NOT NULL UNIQUE,
    nome_recep  NVARCHAR(50)  NOT NULL,
    email_recep NVARCHAR(45)  NOT NULL UNIQUE,
    senha       NVARCHAR(255) NOT NULL
);
GO

-- Tabela CADASTRO
CREATE TABLE CADASTRO (
    id_cadastro     INT IDENTITY PRIMARY KEY,
    id_recep        INT          NOT NULL,
    id_rep          INT          NOT NULL,
    ra              NVARCHAR(20) NOT NULL,
    data_cadastro   DATE,
    status_cadastro NVARCHAR(20) DEFAULT 'Pendente' NOT NULL,
    FOREIGN KEY (id_recep) REFERENCES RECEPCIONISTA(id_recep),
    FOREIGN KEY (id_rep) REFERENCES AGENDAMENTO(id_rep),
    FOREIGN KEY (ra) REFERENCES ALUNO(ra)
);
GO


-- Função para contar o total de alunos
CREATE FUNCTION fn_TotalAlunos()
RETURNS INT
AS
BEGIN
    DECLARE @total INT;

    SELECT @total = COUNT(*) FROM ALUNO;

    RETURN @total;
END;

-- Função para contar o total de cursos
CREATE FUNCTION fn_TotalCursos()
RETURNS INT
AS
BEGIN
    DECLARE @total INT;

    SELECT @total = COUNT(*) FROM Curso;

    RETURN @total;
END;

-- Função para contar o total de disciplinas
CREATE FUNCTION fn_TotalDisciplinas()
RETURNS INT
AS
BEGIN
    DECLARE @total INT;

    SELECT @total = COUNT(*) FROM DISCIPLINA;

    RETURN @total;
END;

-- Função para contar o total de agendamentos pendentes
CREATE FUNCTION fn_TotalAgendamentosPendentes()
RETURNS INT
AS
BEGIN
    DECLARE @total INT;

    SELECT @total = COUNT(*) FROM CADASTRO WHERE status_cadastro = 'Pendente';

    RETURN @total;
END;

-- Função para contar o total de agendamentos ativos
CREATE FUNCTION fn_TotalAgendamentosAtivos()
RETURNS INT
AS
BEGIN
    DECLARE @total INT;

    SELECT @total = COUNT(*) FROM CADASTRO WHERE status_cadastro = 'Ativo';

    RETURN @total;
END;

-- Procedimento para adicionar uma nova disciplina
CREATE PROCEDURE sp_AddDisciplina (
    @nomeDisciplina NVARCHAR(45),
    @cargaHoraria INT,
    @numeroAlunos INT
)
AS
BEGIN
    -- Verifica se a disciplina já existe
    IF EXISTS (SELECT 1 FROM DISCIPLINA WHERE nome_disciplina = @nomeDisciplina)
    BEGIN
        RAISERROR('Disciplina já existe.', 16, 1); -- Lança um erro se a disciplina já existir
        RETURN; -- Sai da procedure
    END

    -- Insere a nova disciplina
    INSERT INTO DISCIPLINA (nome_disciplina, carga_horaria, numero_alunos)
    VALUES (@nomeDisciplina, @cargaHoraria, @numeroAlunos);
    
    RETURN; -- Sai da procedure sem erro
END;

-- Procedimento para adicionar um novo curso
CREATE PROCEDURE sp_AddCurso (
    @nomeCurso NVARCHAR(45)
)
AS
BEGIN
    -- Verifica se o curso já existe
    IF EXISTS (SELECT 1 FROM Curso WHERE nome_curso = @nomeCurso)
    BEGIN
        RAISERROR('Curso já existe.', 16, 1); -- Lança um erro se o curso já existir
        RETURN; -- Sai da procedure
    END

    -- Insere o novo curso
    INSERT INTO Curso (nome_curso) VALUES (@nomeCurso);
    
    RETURN; -- Sai da procedure sem erro
END;

INSERT INTO ALUNO (ra, nome_aluno, email_aluno, status_aluno, senha) VALUES
('SP3134229', 'João Silva', 'joao.silva@example.com', 'Ativo', 'senha123'),
('SP3131230', 'Maria Oliveira', 'maria.oliveira@example.com', 'Ativo', 'senha123'),
('SP3154231', 'Carlos Santos', 'carlos.santos@example.com', 'Ativo', 'senha123'),
('SP3109232', 'Ana Costa', 'ana.costa@example.com', 'Ativo', 'senha123'),
('SP3653233', 'Lucas Pereira', 'lucas.pereira@example.com', 'Ativo', 'senha123');

INSERT INTO CURSO (nome_curso) VALUES
('Engenharia de Software'),
('Sistemas de Informação'),
('Ciência da Computação');

INSERT INTO DISCIPLINA (nome_disciplina, carga_horaria, numero_alunos) VALUES
('Matemática', 60, 30),
('Programação I', 45, 25),
('Banco de Dados', 45, 20),
('Redes de Computadores', 45, 15),
('Engenharia de Requisitos', 60, 10);

DECLARE @i INT = 0;
DECLARE @data DATE = GETDATE(); 

WHILE @i < 8
BEGIN
    INSERT INTO AGENDAMENTO (horario, data) VALUES
    (CAST('08:00:00' AS TIME), DATEADD(WEEK, @i, @data)), 
    (CAST('09:00:00' AS TIME), DATEADD(WEEK, @i, @data)), 
    (CAST('10:00:00' AS TIME), DATEADD(WEEK, @i, @data)); 
    SET @i = @i + 1;
END;

INSERT INTO CURSO_DISCIPLINA (id_curso, id_disciplina) VALUES
(1, 1), -- Engenharia de Software com Matemática
(1, 2), -- Engenharia de Software com Programação I
(2, 3), -- Sistemas de Informação com Banco de Dados
(2, 4), -- Sistemas de Informação com Redes de Computadores
(3, 5); -- Ciência da Computação com Engenharia de Requisitos

INSERT INTO DISC_REP (id_disciplina, id_rep) VALUES
(1, 1), -- Matemática com o primeiro agendamento
(2, 2), -- Programação I com o segundo agendamento
(3, 3), -- Banco de Dados com o terceiro agendamento
(4, 4), -- Redes de Computadores com o quarto agendamento
(5, 5); -- Engenharia de Requisitos com o quinto agendamento

INSERT INTO PROFESSOR (nome_prof, email_prof, status_prof) VALUES
('Dr. Fernando Almeida', 'fernando.almeida@example.com', 'Ativo'),
('Profa. Juliana Mendes', 'juliana.mendes@example.com', 'Ativo'),
('Dr. Ricardo Lima', 'ricardo.lima@example.com', 'Ativo');

INSERT INTO RECEPCIONISTA (cd_recep, nome_recep, email_recep, senha) VALUES
('RP001', 'Patrícia Souza', 'patricia.souza@example.com', 'adm123');

-- id_recep = 1 (recepcionista)
-- id_rep = 1 (agendamento)
-- ra = 'SP3134229' (aluno)

INSERT INTO CADASTRO (id_recep, id_rep, ra, data_cadastro, status_cadastro) VALUES
(1, 1, 'SP3134229', GETDATE(), 'Pendente');

