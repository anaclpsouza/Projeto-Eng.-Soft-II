# Plano de Projeto – Gerenciador de Notas Escolares (Swing)

## 1. Objetivo do Projeto

Este projeto tem como objetivo desenvolver uma aplicação desktop em Java Swing para gerenciar alunos e suas notas escolares, atendendo a requisitos de qualidade de software e às práticas recomendadas pelo modelo MPS.Br (nível F).  
O sistema deverá permitir o cadastro de alunos, registro de notas, cálculo de média e situação (aprovado/recuperação/reprovado), além de evidenciar o uso de boas práticas de engenharia de software, como SOLID, testes automatizados e versionamento com Git/GitHub.

---

## 2. Escopo

### 2.1. Funcionalidades principais

- **Cadastro de alunos**
  - Criar, listar, editar e remover alunos (CRUD).
  - Garantir unicidade da matrícula.

- **Cadastro de notas**
  - Registrar/editar notas de:
    - Prova 1
    - Prova 2
    - Trabalho
    - Projeto

- **Cálculo de desempenho**
  - Calcular a média do aluno a partir das notas cadastradas.
  - Determinar a situação:
    - Aprovado
    - Recuperação
    - Reprovado

- **Diário de classe**
  - Apresentar uma grade em forma de tabela:
    - Linhas: alunos
    - Colunas: avaliações, média e situação
    - Situação exibida com cores (verde para “Aprovado”, vermelho para “Reprovado”, laranja para “Recuperação”).

### 2.2. Itens fora de escopo

- Autenticação de usuários (login/senha).
- Persistência em banco de dados relacional (é usado arquivo CSV).
- Integração com sistemas externos.

---

## 3. Papéis e Responsabilidades

- **Desenvolvedor(es) / Equipe de Projeto**
  - Implementar funcionalidades do sistema (UI, domínio, infraestrutura).
  - Escrever testes automatizados e configurar ferramentas de qualidade.
  - Registrar issues, branches, commits e PRs no GitHub.

- **Professor / Orientador**
  - Definir critérios de avaliação do trabalho.
  - Acompanhar o andamento do projeto (repositório, documentação, apresentação).

---

## 4. Cronograma (macro)

| Fase                                   | Período (estimado)                  | Entregas principais                            |
|----------------------------------------|-------------------------------------|-----------------------------------------------|
| Planejamento e definição de requisitos | Semana 1                            | Plano de projeto, lista de requisitos, issues |
| Implementação do CRUD de alunos        | Semana 2                            | Telas de aluno, `StudentUseCase`, repositório |
| Implementação de notas e cálculo       | Semana 3                            | `GradesFormDialog`, `GradeCalculationService` |
| Diário de classe e refinamentos        | Semana 4                            | `GradeBookDialog`, melhorias de UI             |
| Testes, qualidade e documentação       | Semana 5                            | Testes JUnit, JaCoCo, relatório final (PDF)   |

Os períodos podem ser ajustados conforme o calendário da disciplina.

---

## 5. Estratégia de Desenvolvimento

### 5.1. Arquitetura em camadas

O projeto será organizado nos seguintes pacotes:

- `domain.model` – Entidades de domínio: `Student`, `ReportCard`.
- `domain.service` – Serviços de regras de negócio: `StudentValidator`, `GradeCalculationService`.
- `application` – Casos de uso: `StudentUseCase`, `ReportCardUseCase`.
- `infra.repository` – Persistência em arquivo: `FileStorage`, `StudentRepository`, `StudentRepositoryFileImpl`.
- `ui` – Interface Swing: `MainFrame`, `StudentListPanel`, `StudentFormDialog`, `GradesFormDialog`, `GradeBookDialog`.

Essa separação facilita a aplicação dos princípios SOLID e a manutenção do código.

### 5.2. Padrões de código

- Identificadores (classes, métodos, variáveis) em **inglês**.
- Textos da interface (labels, mensagens) em **português**.
- Convenção de nomes:
  - Classes: `PascalCase`
  - Métodos e variáveis: `camelCase`

---

## 6. Estratégia de Versionamento (Git/GitHub)

- Repositório hospedado em:  
  `https://github.com/<seu-usuario>/<seu-repositorio>`

### 6.1. Branches

- `main` – branch estável, com versões integradas e revisadas.
- `feature/...` – branches de funcionalidade, uma por requisito ou grupo de requisitos. Exemplos:
  - `feature/student-crud`
  - `feature/grades-screen`
  - `feature/grade-book`

### 6.2. Commits

- Mensagens padronizadas inspiradas em Conventional Commits, por exemplo:
  - `feat: implement student screens and CRUD`
  - `feat(ui): add grades form dialog`
  - `fix: validate student registration on update`

### 6.3. Issues e Pull Requests

- Cada requisito é registrado como **issue** no GitHub.
- Para implementar uma issue, é criada uma branch `feature/...`.
- Ao finalizar, é aberto um **Pull Request** da branch `feature/...` para `main`, referenciando a issue correspondente (ex.: `Closes #1`).
- Após revisão e merge do PR, a issue é automaticamente encerrada.

---

## 7. Estratégia de Testes e Qualidade

### 7.1. Testes automatizados

- **Framework:** JUnit 5 (`junit-jupiter`).
- **Escopo de testes:**
  - `GradeCalculationService`: cálculo de média e situação em diferentes cenários.
  - `StudentValidator`: validação de matrícula e nome (campos obrigatórios).

- **Execução:**
  - Via Maven: `mvn test`.
  - Via VS Code: executando testes pela aba **Testing**.

### 7.2. Cobertura de testes

- **Ferramenta:** JaCoCo (plugin Maven).
- **Objetivo:** obter evidência de cobertura de código para classes de serviço e de domínio.
- **Relatório:** gerado em `target/site/jacoco/index.html` após `mvn test`.

### 7.3. Análise estática e estilo

- **Ferramentas:**
  - SonarLint (extensão VS Code) para alertas de qualidade.
  - (Opcional) Checkstyle configurado no `pom.xml`.
- **Uso:**
  - Corrigir code smells, avisos de complexidade, possíveis bugs e problemas de estilo.

---

## 8. Riscos e Mitigações

| Risco                                         | Prob. | Impacto | Mitigação                                                                 |
|----------------------------------------------|:-----:|:-------:|----------------------------------------------------------------------------|
| Atraso na implementação das telas Swing      |  Média|   Alto  | Priorizar CRUD de aluno e tela de notas nas primeiras semanas             |
| Baixa cobertura de testes                    |  Média|  Médio  | Reservar tempo específico para criação de testes e uso de JaCoCo          |
| Dificuldade com ferramentas (Maven, GitHub)  |  Baixa|  Médio  | Seguir tutoriais oficiais e registrar problemas nas issues do repositório |
| Falta de alinhamento com MPS.Br              |  Baixa|   Alto  | Relacionar claramente requisitos, issues, plano de projeto e métricas     |

---

## 9. Métricas de Acompanhamento

Algumas métricas simples serão acompanhadas ao longo do projeto:

- Número de **issues** abertas x fechadas.
- Número de **branches** criadas e **pull requests** concluídos.
- Percentual de **cobertura de testes** (JaCoCo).
- Quantidade de alertas do **SonarLint** (antes/depois das correções).

Essas métricas serão consolidadas no relatório final, evidenciando as práticas de **MED**, **GQA** e **GPR**.

---

## 10. Entregáveis

- Código-fonte completo da aplicação Java Swing.
- Arquivo `pom.xml` com dependências e plugins configurados.
- Testes automatizados (JUnit) e relatório de cobertura (JaCoCo).
- Relatório final em PDF contendo:
  - Descrição do sistema, arquitetura, telas.
  - Relação com processos do MPS.Br.
  - Evidências de versionamento, testes e qualidade.
- Este plano de projeto em `docs/plano-de-projeto.md`.