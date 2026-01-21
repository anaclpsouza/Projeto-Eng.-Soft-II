package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.ReportCard;
import br.edu.escola.swingapp.domain.model.Student;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.util.List;

public class GradeBookDialog extends JDialog {

    private final StudentUseCase studentUseCase;
    private final ReportCardUseCase reportCardUseCase;

    private JTable table;
    private DefaultTableModel tableModel;

    public GradeBookDialog(Frame owner,
                           StudentUseCase studentUseCase,
                           ReportCardUseCase reportCardUseCase) {
        super(owner, "Diário de Classe", true); // true = modal
        this.studentUseCase = studentUseCase;
        this.reportCardUseCase = reportCardUseCase;

        configureWindow();
        initializeTable();
        loadData();
    }

    private void configureWindow() {
        setSize(900, 500);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(0, 0));
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
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 2 && columnIndex <= 6) {
                    return Double.class;
                }
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(new Color(224, 224, 224));

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
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
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