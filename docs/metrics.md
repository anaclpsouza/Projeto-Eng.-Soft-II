# Métricas do Projeto – Gerenciador de Notas Escolares (Swing)

Este documento registra as principais métricas acompanhadas ao longo do projeto, com o objetivo de evidenciar práticas de **Medição (MED)**, **Gerência da Qualidade (GQA)** e **Gerência de Projetos (GPR)** conforme o modelo MPS.Br.

---

## 1. Métricas de Requisitos e Issues

As funcionalidades e correções foram registradas como **issues** no GitHub. Foram acompanhadas as seguintes métricas:

### 1.1. Quantidade de issues abertas x fechadas

- **Métrica:**  
  - Número total de issues abertas.
  - Número total de issues fechadas.
- **Objetivo:**  
  - Acompanhar o andamento da implementação dos requisitos.
- **Coleta:**  
  - Obtida diretamente na aba **Issues** do GitHub, com filtros “Open” e “Closed”.

**Exemplo de registro (preencha com seus valores reais):**

| Data       | Issues abertas | Issues fechadas | Observações                              |
|-----------|----------------|-----------------|------------------------------------------|
| 2026-01-10| 5              | 0               | Criação inicial das issues de requisitos |
| 2026-01-15| 3              | 4               | CRUD de aluno e tela de notas concluídos |
| 2026-01-21| 1              | 8               | Diário de classe e ajustes finais        |

---

## 2. Métricas de Versionamento (Branches e Pull Requests)

### 2.1. Branches de funcionalidade

- **Métrica:**
  - Quantidade de branches do tipo `feature/...` criadas.
- **Objetivo:**
  - Verificar se as funcionalidades estão sendo desenvolvidas de forma isolada da `main`.
- **Coleta:**
  - Aba **Branches** do repositório no GitHub.

**Exemplo de registro:**

| Branch                    | Tipo     | Status   | Issue relacionada | Observações                         |
|---------------------------|----------|----------|-------------------|-------------------------------------|
| feature/student-crud      | feature  | merged   | #1                | CRUD de aluno                       |
| feature/grades-screen     | feature  | merged   | #2                | Tela de cadastro/edição de notas    |
| feature/grade-book        | feature  | merged   | #3                | Tela de diário de classe            |

### 2.2. Pull Requests

- **Métrica:**
  - Número de Pull Requests abertos e concluídos.
  - Vínculo entre PRs e issues.
- **Objetivo:**
  - Controlar integração de funcionalidades e rastreabilidade entre requisitos e código.
- **Coleta:**
  - Aba **Pull requests** do GitHub.

**Exemplo de registro:**

| PR # | Branch origem          | Destino | Issue ligada | Status   | Observações                      |
|------|------------------------|---------|-------------|----------|----------------------------------|
| #1   | feature/student-crud   | main    | #1          | merged   | Implementa CRUD de aluno         |
| #2   | feature/grades-screen  | main    | #2          | merged   | Implementa tela de notas         |
| #3   | feature/grade-book     | main    | #3          | merged   | Implementa diário de classe      |

---

## 3. Métricas de Testes e Cobertura (MED, GQA)

### 3.1. Execução de testes automatizados

- **Métrica:**
  - Quantidade de testes executados.
  - Quantidade de testes aprovados e falhos.
- **Objetivo:**
  - Garantir que as principais regras de negócio (cálculo de média, situação, validação de aluno) estejam cobertas por testes automatizados.
- **Coleta:**
  - Saída do `mvn test` ou painel de testes do VS Code.

**Exemplo de registro:**

| Data       | Testes executados | Sucesso | Falhas | Classes testadas                         |
|-----------|-------------------|---------|--------|------------------------------------------|
| 2026-01-18| 6                 | 6       | 0      | GradeCalculationService, StudentValidator|
| 2026-01-21| 10                | 10      | 0      | + ReportCardUseCase                      |

### 3.2. Cobertura de código (JaCoCo)

- **Métrica:**
  - Percentual de linhas cobertas por testes.
  - Percentual de branches cobertos (quando aplicável).
- **Ferramenta:**
  - JaCoCo (`jacoco-maven-plugin`) integrada ao Maven.
- **Coleta:**
  - Gerando relatório com `mvn test` e abrindo `target/site/jacoco/index.html`.

**Exemplo de registro:**

| Data       | Cobertura de linhas (%) | Cobertura de branches (%) | Classes com foco em testes                    |
|-----------|--------------------------|----------------------------|-----------------------------------------------|
| 2026-01-18| 45%                      | 30%                        | GradeCalculationService                       |
| 2026-01-21| 65%                      | 50%                        | + StudentValidator, ReportCardUseCase         |

---

## 4. Métricas de Qualidade de Código (SonarLint / Checkstyle)

### 4.1. Alertas de análise estática

- **Métrica:**
  - Quantidade de alertas detectados pelo SonarLint (ou outra ferramenta semelhante).
  - Quantidade de alertas após correções.
- **Objetivo:**
  - Reduzir code smells, possíveis bugs e problemas de estilo.
- **Coleta:**
  - Painel do SonarLint no VS Code, antes e depois das refatorações.

**Exemplo de registro:**

| Data       | Alertas SonarLint (antes) | Alertas (depois) | Ações tomadas                                    |
|-----------|---------------------------|------------------|--------------------------------------------------|
| 2026-01-17| 15                        | 8                | Renomear métodos, remover código duplicado      |
| 2026-01-20| 8                         | 3                | Melhorar nomes e tratamento de exceções         |

---

## 5. Interpretação das Métricas

Com base nas métricas coletadas:

- **Requisitos:**  
  O acompanhamento de issues abertas/fechadas mostrou a evolução da implementação das funcionalidades planejadas, permitindo verificar se o escopo definido no plano de projeto estava sendo cumprido.

- **Versionamento:**  
  O uso de branches `feature/...` e PRs vinculados a issues garantiu rastreabilidade entre requisitos, implementações e integrações, reduzindo o risco de mudanças diretas na `main`.

- **Testes e Cobertura:**  
  O aumento da cobertura de testes ao longo do tempo indica uma preocupação crescente com a confiabilidade do sistema. As principais regras de negócio (cálculo de média, situação e validação de aluno) foram priorizadas nos testes.

- **Qualidade de código:**  
  A redução do número de alertas de análise estática ao longo do projeto evidencia a adoção de boas práticas de codificação, refatorações e melhorias contínuas de qualidade.

Essas métricas, combinadas, suportam as práticas de **MED (Medição)**, **GQA (Gerência da Qualidade)** e **GPR (Gerência de Projetos)** exigidas pelo trabalho.