# 🎓 Gerenciador de Notas Escolares (Swing App)

Sistema desktop para gestão de alunos, lançamento de notas e controle de diário de classe, desenvolvido como projeto prático para a disciplina de **Engenharia de Software II**.

O projeto adota práticas recomendadas de engenharia de software e processos alinhados ao modelo **MPS.Br (Nível F)**, incluindo arquitetura em camadas, princípios **SOLID**, testes automatizados com cobertura de código e persistência desacoplada.

---

## 🚀 Funcionalidades

- **CRUD de Alunos**: Cadastro, listagem, edição e exclusão de alunos com validação de dados e unicidade de matrícula.
- **Boletim de Notas**: Lançamento e edição de avaliações:
  - Prova 1 (P1)
  - Prova 2 (P2)
  - Trabalho
  - Projeto
- **Cálculo Automático de Desempenho**:
  - Média aritmética calculada com precisão (`BigDecimal`, 2 casas decimais).
  - Classificação automática da situação acadêmica:
    - **Aprovado**: Média $\ge 7.0$
    - **Recuperação**: $5.0 \le$ Média $< 7.0$
    - **Reprovado**: Média $< 5.0$
- **Diário de Classe Visual**: Tabela geral exibindo notas de todos os alunos e a situação destacada por cores (verde para Aprovado, laranja para Recuperação e vermelho para Reprovado).
- **Persistência em Arquivo**: Armazenamento local dos dados em arquivo `CSV` (`students.csv`). Um exemplo de modelo está disponível em `students.csv.example`.
- **Interface Gráfica Moderna**: Desenvolvida em Java Swing estilizada com a biblioteca **FlatLaf** (Light Theme).

---

## 🛠️ Tecnologias e Dependências

### Requisitos de Ambiente
- **Java JDK 17** ou superior (testado com OpenJDK).
- **Apache Maven 3.8+** (opcional se for executar diretamente pelo VS Code / Eclipse / IntelliJ).

### Principais Dependências (Maven)
- **[FlatLaf 3.3](https://www.formdev.com/flatlaf/)**: Look and Feel moderno e limpo para Swing.
- **[JUnit 5 (Jupiter 5.9.3)](https://junit.org/junit5/)**: Framework para criação e execução de testes unitários.
- **[Mockito 5.3.1](https://site.mockito.org/)**: Framework para criação de mocks nos testes de camadas.
- **[JaCoCo 0.8.8](https://www.eclemma.org/jacoco/)**: Plugin Maven para análise e relatório de cobertura de código.

---

## 🏛️ Arquitetura do Projeto

O código está estruturado em camadas no pacote `br.edu.escola.swingapp`:

```
swing-app-mpsbr/src/main/java/br/edu/escola/
├── App.java                               # Ponto de entrada (Main) e injeção de dependências
└── swingapp/
    ├── application/                       # Casos de uso da aplicação
    │   ├── StudentUseCase.java
    │   └── ReportCardUseCase.java
    ├── domain/
    │   ├── model/                         # Entidades de negócio (Student, ReportCard)
    │   └── service/                       # Regras de cálculo e validações (CalculusAverageService, StudentValidator)
    ├── infra/repository/                  # Persistência em arquivo CSV (FileStorage, StudentRepositoryFileImpl)
    └── ui/                                # Telas e componentes visuais Swing (MainFrame, Dialogs, Panels)
```

---

## 📦 Como Executar

### Opção 1: Pelo Terminal via Maven

1. Abra o terminal e entre na pasta do módulo:
   ```bash
   cd swing-app-mpsbr
   ```

2. Compile o projeto e execute os testes unitários:
   ```bash
   mvn clean test
   ```

3. Execute a aplicação gráfica:
   ```bash
   mvn compile exec:java -Dexec.mainClass="br.edu.escola.App"
   ```

---

### Opção 2: Pelo Visual Studio Code

1. Instale a extensão **Extension Pack for Java** no VS Code.
2. Abra o arquivo [`swing-app-mpsbr/src/main/java/br/edu/escola/App.java`](swing-app-mpsbr/src/main/java/br/edu/escola/App.java).
3. Clique na opção **Run** ou pressione `F5` para iniciar o programa com interface gráfica.

---

## 🧪 Testes e Métricas de Qualidade

Para gerar o relatório visual de cobertura de código pelo JaCoCo:

```bash
cd swing-app-mpsbr
mvn test jacoco:report
```

O relatório HTML detalhado será gerado em:
`swing-app-mpsbr/target/site/jacoco/index.html`

Documentos adicionais sobre o ciclo de vida e planejamento estão disponíveis em:
- [Plano de Projeto](docs/plano-de-projeto.md)
- [Métricas de Qualidade (MPS.Br)](docs/metrics.md)
