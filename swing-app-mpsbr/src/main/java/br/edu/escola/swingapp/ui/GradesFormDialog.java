package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.domain.model.Student;
import br.edu.escola.swingapp.domain.model.ReportCard;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

public class GradesFormDialog extends JDialog {

    private final ReportCardUseCase reportCardUseCase;
    private final Student student;

    private JTextField txtGrade1;
    private JTextField txtGrade2;
    private JTextField txtWorkGrade;
    private JTextField txtProjectGrade;

    public GradesFormDialog(Frame owner, ReportCardUseCase reportCardUseCase, Student student) {
        super(owner, true);
        this.reportCardUseCase = reportCardUseCase;
        this.student = student;

        setTitle("Notas do Aluno - " + student.getName());
        configureWindow();
        initializeComponents();
        loadExistingGrades();
    }

    private void configureWindow() {
        setSize(400, 250);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());
    }

    private void initializeComponents() {
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 8, 8));

        fieldsPanel.add(new JLabel("Nota Prova 1:"));
        txtGrade1 = new JTextField();
        fieldsPanel.add(txtGrade1);

        fieldsPanel.add(new JLabel("Nota Prova 2:"));
        txtGrade2 = new JTextField();
        fieldsPanel.add(txtGrade2);

        fieldsPanel.add(new JLabel("Nota Trabalho:"));
        txtWorkGrade = new JTextField();
        fieldsPanel.add(txtWorkGrade);

        fieldsPanel.add(new JLabel("Nota Projeto:"));
        txtProjectGrade = new JTextField();
        fieldsPanel.add(txtProjectGrade);

        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton btnSave = new JButton("Salvar Notas");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> saveGrades());
        btnCancel.addActionListener(e -> dispose());

        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadExistingGrades() {
        if (student.getReportCard() != null) {
            ReportCard reportCard = student.getReportCard();
            if (reportCard.getExam1Grade() != null) {
                txtGrade1.setText(String.valueOf(reportCard.getExam1Grade()));
            }
            if (reportCard.getExam2Grade() != null) {
                txtGrade2.setText(String.valueOf(reportCard.getExam2Grade()));
            }
            if (reportCard.getAssignmentGrade() != null) {
                txtWorkGrade.setText(String.valueOf(reportCard.getAssignmentGrade()));
            }
            if (reportCard.getProjectGrade() != null) {
                txtProjectGrade.setText(String.valueOf(reportCard.getProjectGrade()));
            }
        }
    }

    private void saveGrades() {
        try {
            Double grade1 = parseGrade(txtGrade1.getText().trim());
            Double grade2 = parseGrade(txtGrade2.getText().trim());
            Double workGrade = parseGrade(txtWorkGrade.getText().trim());
            Double projectGrade = parseGrade(txtProjectGrade.getText().trim());

            validateGradeRange(grade1);
            validateGradeRange(grade2);
            validateGradeRange(workGrade);
            validateGradeRange(projectGrade);

            ReportCard reportCard = new ReportCard();
            reportCard.setExam1Grade(grade1);
            reportCard.setExam2Grade(grade2);
            reportCard.setAssignmentGrade(workGrade);
            reportCard.setProjectGrade(projectGrade);

            reportCardUseCase.updateReportCard(student.getRegistration(), reportCard);

            double average = reportCardUseCase.getAverage(student.getRegistration());
            String situation = reportCardUseCase.getSituation(student.getRegistration());

            JOptionPane.showMessageDialog(
                    this,
                    "Notas salvas com sucesso.\nMédia: " + average + "\nSituação: " + situation,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe valores numéricos válidos para as notas.",
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(
                    this,
                    iae.getMessage(),
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar notas:\n" + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Double parseGrade(String value) {
        if (value.isEmpty()) {
            return 0.0; // você pode optar por retornar null e tratar diferente
        }
        return Double.parseDouble(value.replace(",", ".")); // aceita vírgula como separador
    }

    private void validateGradeRange(Double grade) {
        if (grade < 0.0 || grade > 10.0) {
            throw new IllegalArgumentException("As notas devem estar no intervalo de 0 a 10.");
        }
    }
}