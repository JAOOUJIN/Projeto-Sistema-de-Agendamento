# 📅 Sistema de Agendamento Acadêmico

Este projeto foi desenvolvido como parte de uma atividade acadêmica com o objetivo de criar um sistema de gerenciamento para instituições de ensino. A aplicação permite o cadastro, consulta e remoção de **alunos**, **cursos** e **disciplinas**, funcionando como uma ferramenta administrativa simples e eficiente.

## 🧰 Tecnologias Utilizadas

- **Java (Servlets e JDBC)** — Lógica de programação e integração com banco de dados  
- **HTML5 + CSS3** — Estrutura e estilo da interface  
- **JavaScript Puro** — Interações básicas no frontend  
- **SQL Server Management Studio** — Banco de dados relacional utilizado  
- **Postman** — Testes de requisições para as APIs Java

## ✨ Funcionalidades

### 📚 Cursos
- Adicionar novo curso (código + nome)
- Buscar curso pelo nome
- Deletar curso pelo código

### 👨‍🎓 Alunos
- Cadastrar aluno com RA, nome, email e senha
- Buscar aluno pelo nome
- Deletar aluno pelo RA

### 📖 Disciplinas
- Cadastrar disciplina com código, nome e curso associado
- Buscar disciplina pelo nome
- Deletar disciplina pelo código

## 📂 Estrutura do Projeto

```
📁 Projeto-Sistema-de-Agendamento
├── 📁 agendamento-back            # Código Java (Servlets, DAO, conexões)
├── 📁 banco-sistema-agendamento  # Scripts para criação do banco (SQL Server)
│   └── script_banco.sql
├── 📁 documentacao-postman
│   └── Sistema_Agendamento.postman_collection.json
├── 📁 testeFront                 # Frontend da aplicação (HTML/CSS/JS)
└── README.md
```

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**
```bash
git clone https://github.com/JAOOUJIN/Projeto-Sistema-de-Agendamento.git
```

2. **Configure o banco de dados:**
   - Crie um banco de dados no **SQL Server Management Studio**.
   - Execute o script `script_banco.sql` para criar as tabelas e relacionamentos.

3. **Configure e execute o backend Java:**
   - Importe o projeto Java no Eclipse ou IntelliJ.
   - Configure o servidor Apache Tomcat.
   - Atualize a string de conexão com o banco (URL, usuário e senha do SQL Server).

4. **Execute o frontend:**
   - Acesse os arquivos HTML na pasta `testeFront` diretamente no navegador.
   - O frontend consome as rotas criadas no backend Java.

## 🧠 Aprendizados

Durante o desenvolvimento, trabalhei com:

- Programação com Java para web (Servlets)
- Conexão com banco de dados usando JDBC
- Criação e consumo de APIs REST
- Testes de API com Postman
- Integração entre frontend e backend
- Modelagem de banco de dados relacional no SQL Server


