package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.ReportCard;
import br.edu.escola.swingapp.domain.model.Student;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

public class GradeBookFrame extends JFrame {

    private final StudentUseCase studentUseCase;
    private final ReportCardUseCase reportCardUseCase;

    private JTable table;
    private DefaultTableModel tableModel;

    public GradeBookFrame(StudentUseCase studentUseCase, ReportCardUseCase reportCardUseCase) {
        super("Diário de Classe");
        this.studentUseCase = studentUseCase;
        this.reportCardUseCase = reportCardUseCase;

        configureWindow();
        initializeTable();
        loadData();
    }

    private void configureWindow() {
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initializeTable() {
        tableModel = new DefaultTableModel(
                new Object[]{
                        "Matrícula",
                        "Nome",
                        "Prova 1",
                        "Prova 2",
                        "Trabalho",
                        "Projeto",
                        "Média",
                        "Situação"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // diário só para visualização
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 2 && columnIndex <= 6) { // colunas de notas + média
                    return Double.class;
                }
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);

        // Renderer para colorir a coluna de situação
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                super.setValue(value);

                if (value == null) {
                    setForeground(Color.BLACK);
                    return;
                }

                String status = value.toString();
                if ("Aprovado".equalsIgnoreCase(status)) {
                    setForeground(new Color(0, 128, 0)); // verde
                } else if ("Reprovado".equalsIgnoreCase(status)) {
                    setForeground(Color.RED);
                } else if ("Recuperação".equalsIgnoreCase(status)) {
                    setForeground(new Color(255, 140, 0)); // laranja
                } else {
                    setForeground(Color.BLACK);
                }
            }
        };

        table.getColumnModel().getColumn(7).setCellRenderer(statusRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<Student> students = studentUseCase.listAll();
        for (Student student : students) {
            ReportCard reportCard = student.getReportCard();
            Double exam1 = null;
            Double exam2 = null;
            Double assignment = null;
            Double project = null;
            Double average = null;
            String situation = "";

            if (reportCard != null) {
                exam1 = reportCard.getExam1Grade();
                exam2 = reportCard.getExam2Grade();
                assignment = reportCard.getAssignmentGrade();
                project = reportCard.getProjectGrade();

                // Usa o use case para garantir regra única de cálculo
                average = reportCardUseCase.getAverage(student.getRegistration());
                situation = reportCardUseCase.getSituation(student.getRegistration());
            }

            tableModel.addRow(new Object[]{
                    student.getRegistration(),
                    student.getName(),
                    exam1,
                    exam2,
                    assignment,
                    project,
                    average,
                    situation
            });
        }
    }
}