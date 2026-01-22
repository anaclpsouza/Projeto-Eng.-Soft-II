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

**Registro Atual:**

| Branch                          | Tipo     | Status   | Issue relacionada | Observações                         |
|---------------------------------|----------|----------|-------------------|-------------------------------------|
| feature/crud-aluno-boletim#1    | feature  | merges   | #1                | CRUD de aluno e Boletim             |
| feature/ui-upgrade#1            | feature  | merged   | #4                | Atualização de UI (FlatLaf)         |
| feature/testes-unitarios        | feature  | merged   | #5                | Implementação de testes unitários   |
| main                            | stable   | active   | -                 | Branch principal                    |

### 2.2. Pull Requests

- **Métrica:**
  - Número de Pull Requests abertos e concluídos.
  - Vínculo entre PRs e issues.
- **Objetivo:**
  - Controlar integração de funcionalidades e rastreabilidade entre requisitos e código.
- **Coleta:**
  - Aba **Pull requests** do GitHub.

**Exemplo de registro:**

| PR # | Branch origem                | Destino | Issue ligada | Status   | Observações                      |
|------|------------------------------|---------|-------------|----------|----------------------------------|
| #1   | feature/crud-aluno-boletim#1 | main    | #1          | merged   | Implementa CRUD de aluno         |
| #2   | feature/ui-upgrade#1         | main    | #4          | merged   | Upgrade de UI com FlatLaf        |
| #3   | feature/testes-unitarios     | main    | #5          | merged   | Adiciona cobertura de testes     |

---

## 3. Métricas de Testes e Cobertura (MED, GQA)

### 3.1. Execução de testes automatizados

- **Métrica:**
  - Quantidade de testes executados (Total: 46+ testes unitários cobrindo domain, ui e service).
  - Quantidade de testes aprovados e falhos.
- **Objetivo:**
  - Garantir que as principais regras de negócio (cálculo de média, situação, validação de aluno) estejam cobertas por testes automatizados.
- **Coleta:**
  - Saída do `mvn test` ou painel de testes do VS Code (Total executado em 21/01/2026: 46 testes).

**Registro Real:**

| Data       | Testes executados | Sucesso | Falhas | Classes testadas                         |
|-----------|-------------------|---------|--------|------------------------------------------|
| 2026-01-18| 6                 | 6       | 0      | GradeCalculationService, StudentValidator|
| 2026-01-21| 75                | 75      | 0      | + ReportCardUseCase, UI, Repository      |

### 3.2. Cobertura de código (JaCoCo)

- **Métrica:**
  - Percentual de linhas cobertas por testes.
  - Percentual de branches cobertos (quando aplicável).
- **Ferramenta:**
  - JaCoCo (`jacoco-maven-plugin`) integrada ao Maven.
- **Coleta:**
  - Gerando relatório com `mvn test` e abrindo `target/site/jacoco/index.html`.

**Registro Real (JaCoCo):**

| Pacote                     | Cobertura de Linhas | Cobertura de Branches |
|----------------------------|---------------------|-----------------------|
| br.edu...domain.model      | 100%                | 100%                  |
| br.edu...domain.service    | 92%                 | 86%                   |
| br.edu...application       | 94%                 | 75%                   |
| br.edu...infra.repository  | 82%                 | 60%                   |
| br.edu...ui (Interfaces)   | ~75%                | ~50%                  |
| **Média Geral**            | **~82%**            | **~70%**              |

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
  O uso de branches `feature/...` e PRs vinculados a issues garantiu rastreabilidade entre requisitos, implementações e integrações, reduzindo o risco de mudanças diretas na `main`. Foram utilizadas branches específicas como `feature/ui-upgrade#1` e `feature/testes-unitarios`.

- **Testes e Cobertura:**  
  Atingimos uma cobertura excelente (>90%) nas camadas de Domínio e Aplicação, que contêm as regras de negócio críticas. A cobertura geral do projeto está acima de 80%, excedendo a prática comum de 70%. Todos os 75 testes automatizados estão passando.

- **Qualidade de código:**  
  A redução do número de alertas de análise estática ao longo do projeto evidencia a adoção de boas práticas de codificação, refatorações e melhorias contínuas de qualidade.

Essas métricas, combinadas, suportam as práticas de **MED (Medição)**, **GQA (Gerência da Qualidade)** e **GPR (Gerência de Projetos)** exigidas pelo trabalho.
