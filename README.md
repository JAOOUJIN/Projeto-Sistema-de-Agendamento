# Projeto-Sistema-de-Agendamento
Sistema de Agendamento

Descrição do Sistema
O sistema desenvolvido é uma aplicação web que permite a gestão de alunos, cursos e disciplinas em uma instituição de ensino. A aplicação segue o padrão CRUD (Criar, Ler, Atualizar, Deletar) e oferece uma interface amigável para os usuários, permitindo que recepcionistas realizem operações de gerenciamento de forma eficiente.

Funcionalidades Principais

1. Gerenciamento de Alunos
  - Adicionar Aluno: Permite que o recepcionista cadastre novos alunos no sistema, fornecendo informações como RA, nome, email e senha.
  - Buscar Aluno: O recepcionista pode buscar alunos pelo nome, visualizando detalhes como RA, nome e email.
  - Deletar Aluno: Permite que o recepcionista remova alunos do sistema utilizando o RA.

2. Gerenciamento de Cursos
  - Adicionar Curso: O recepcionista pode cadastrar novos cursos, informando o nome do curso.
  - Buscar Curso: Permite a busca de cursos pelo nome, exibindo informações relevantes.
  - Deletar Curso: O recepcionista pode remover cursos do sistema utilizando o ID do curso.

3. Gerenciamento de Disciplinas
  - Adicionar Disciplina: O recepcionista pode cadastrar novas disciplinas, informando o nome, carga horária e número de alunos.
  - Buscar Disciplina: Permite a busca de disciplinas pelo nome, exibindo informações relevantes.
  - Deletar Disciplina: O recepcionista pode remover disciplinas do sistema utilizando o ID da disciplina.

Processos e Funções

1. Adicionar Aluno
  - Processo: O recepcionista preenche um formulário com os dados do aluno e envia para o backend.
  - Função: cadastrarAluno(alunoRequest) no AlunoController que chama alunoService.cadastrarAluno(alunoRequest).

2. Buscar Aluno
  - Processo: O recepcionista insere o nome do aluno no campo de busca e envia a solicitação.
  - Função: buscarPorNome(nome) no AlunoController que chama alunoService.buscarPorNome(nome).

3. Deletar Aluno
  - Processo: O recepcionista insere o RA do aluno que deseja remover e confirma a ação.
  - Função: deletarAluno(ra) no AlunoController que chama alunoService.deletarPorRa(ra).

4. Adicionar Curso
  - Processo: O recepcionista preenche um formulário com o nome do curso e envia para o backend.
  - Função: cadastrarCurso(curso) no CursoController que chama cursoService.salvarOuAtualizar(curso).

5. Buscar Curso
  - Processo: O recepcionista insere o nome do curso no campo de busca e envia a solicitação.
  - Função: buscarPorNome(nome) no CursoController que chama cursoService.buscarPorNome(nome).

6. Deletar Curso
  - Processo: O recepcionista insere o ID do curso que deseja remover e confirma a ação.
  - Função: deletarCurso(id) no CursoController que chama cursoService.deletarPorId(id).

7. Adicionar Disciplina
  - Processo: O recepcionista preenche um formulário com os dados da disciplina e envia para o backend.
  - Função: cadastrarDisciplina(disciplina) no DisciplinaController que chama disciplinaService.salvarDisciplina(disciplina).

8. Buscar Disciplina
  - Processo: O recepcionista insere o nome da disciplina no campo de busca e envia a solicitação.
  - Função: buscarPorNome(nome) no DisciplinaController que chama disciplinaService.buscarPorNome(nome).

9. Deletar Disciplina
  - Processo: O recepcionista insere o ID da disciplina que deseja remover e confirma a ação.
  - Função: deletarDisciplina(id) no DisciplinaController que chama disciplinaService.deletar(id).


Funcionalidades do Aluno

1. Agendar Reposição
  - Descrição: O aluno pode solicitar o agendamento de uma reposição de aula.
  - Processo: O aluno preenche um formulário com os detalhes da reposição (data, disciplina, etc.) e envia a solicitação.

2. Visualizar Agendamentos
  - Descrição: O aluno pode visualizar todos os seus agendamentos.
  - Processo: O aluno clica em um botão para ver seus agendamentos, que são recuperados do backend.

3. Ver Disciplinas
  - Descrição: O aluno pode visualizar todas as disciplinas disponíveis.
  - Processo: O aluno clica em um botão para ver as disciplinas, que são recuperadas do backend.

4. Ver Cursos
  - Descrição: O aluno pode visualizar todos os cursos disponíveis.
  - Processo: O aluno clica em um botão para ver os cursos, que são recuperados do backend.

5. Alterar Senha
  - Descrição: O aluno pode alterar sua senha.
  - Processo: O aluno preenche um formulário com a senha atual e a nova senha, e envia a solicitação.
