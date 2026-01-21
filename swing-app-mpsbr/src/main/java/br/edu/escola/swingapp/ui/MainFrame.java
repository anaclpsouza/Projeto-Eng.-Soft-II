package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.Student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

public class MainFrame extends JFrame {

    private final StudentUseCase studentUseCase;
    private final ReportCardUseCase reportCardUseCase;

    private StudentListPanel studentListPanel;

    public MainFrame(StudentUseCase studentUseCase, ReportCardUseCase reportCardUseCase) {
        super("Gerenciador de Notas Escolares");
        this.studentUseCase = studentUseCase;
        this.reportCardUseCase = reportCardUseCase;

        configureWindow();
        initializeMenuBar();
        initializeComponents();
        loadStudents();
    }

    private void configureWindow() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu studentMenu = new JMenu("Aluno");
        JMenuItem miNewStudent = new JMenuItem("Novo Aluno");
        miNewStudent.addActionListener(e -> openStudentCreateForm());

        JMenuItem miEditStudent = new JMenuItem("Editar Aluno");
        miEditStudent.addActionListener(e -> openStudentEditForm());

        JMenuItem miRemoveStudent = new JMenuItem("Remover Aluno");
        miRemoveStudent.addActionListener(e -> removeSelectedStudent());

        studentMenu.add(miNewStudent);
        studentMenu.add(miEditStudent);
        studentMenu.add(miRemoveStudent);

        JMenu gradesMenu = new JMenu("Notas");
        JMenuItem miEditGrades = new JMenuItem("Ver/Editar Notas");
        miEditGrades.addActionListener(e -> openGradesForm());

        JMenuItem miGradeBook = new JMenuItem("Diário de Classe");
        miGradeBook.addActionListener(e -> openGradeBook());

        gradesMenu.add(miEditGrades);
        gradesMenu.add(miGradeBook);

        menuBar.add(studentMenu);
        menuBar.add(gradesMenu);

        setJMenuBar(menuBar);
    }

    private void initializeComponents() {
        // Header com título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 16, 10, 16));

        JLabel titleLabel = new JLabel(" Alunos Cadastrados no Sistema");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        JLabel subtitleLabel = new JLabel(" Gerencie matrículas, notas e situação escolar de forma simples.");
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setFont(subtitleLabel.getFont().deriveFont(Font.PLAIN, 12f));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Painel central com a tabela de alunos
        studentListPanel = new StudentListPanel();
        studentListPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(studentListPanel, BorderLayout.CENTER);

        // Painel de ações na parte inferior
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        actionsPanel.setBorder(new EmptyBorder(5, 10, 10, 10));

        JButton btnNewStudent = new JButton("Novo Aluno");
        JButton btnEditStudent = new JButton("Editar");
        JButton btnViewGrades = new JButton("Ver/Editar Notas");
        JButton btnRemoveStudent = new JButton("Remover");
        JButton btnGradeBook = new JButton("Diário de Classe");

        stylePrimaryButton(btnNewStudent);
        styleSecondaryButton(btnEditStudent);
        styleSecondaryButton(btnViewGrades);
        styleDangerButton(btnRemoveStudent);

        stylePrimaryButton(btnGradeBook);
        btnGradeBook.setText("📘 Diário de Classe");

        btnNewStudent.addActionListener(e -> openStudentCreateForm());
        btnEditStudent.addActionListener(e -> openStudentEditForm());
        btnViewGrades.addActionListener(e -> openGradesForm());
        btnRemoveStudent.addActionListener(e -> removeSelectedStudent());
        btnGradeBook.addActionListener(e -> openGradeBook());

        actionsPanel.add(btnNewStudent);
        actionsPanel.add(btnEditStudent);
        actionsPanel.add(btnViewGrades);
        actionsPanel.add(btnRemoveStudent);

        actionsPanel.add(new javax.swing.JSeparator(javax.swing.SwingConstants.VERTICAL));
        actionsPanel.add(btnGradeBook);

        add(actionsPanel, BorderLayout.SOUTH);
    }

    private void stylePrimaryButton(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
    }

    private void styleSecondaryButton(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.setForeground(new Color(33, 33, 33));
    }

    private void styleDangerButton(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.setBackground(new Color(244, 67, 54));
        button.setForeground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
    }

    private void loadStudents() {
        studentListPanel.loadStudents(studentUseCase.listAll());
    }

    private void openStudentCreateForm() {
        StudentFormDialog dialog = new StudentFormDialog(this, studentUseCase, null);
        dialog.setVisible(true);
        loadStudents();
    }

    private void openStudentEditForm() {
        Student selectedStudent = studentListPanel.getSelectedStudent();
        if (selectedStudent == null) {
            studentListPanel.showSelectStudentMessage(this);
            return;
        }

        var fullStudentOpt = studentUseCase.findByRegistration(selectedStudent.getRegistration());
        Student fullStudent = fullStudentOpt.orElse(selectedStudent);

        StudentFormDialog dialog = new StudentFormDialog(this, studentUseCase, fullStudent);
        dialog.setVisible(true);
        loadStudents();
    }

    private void removeSelectedStudent() {
        Student selectedStudent = studentListPanel.getSelectedStudent();
        if (selectedStudent == null) {
            studentListPanel.showSelectStudentMessage(this);
            return;
        }
        studentListPanel.confirmAndRemoveStudent(this, studentUseCase, selectedStudent);
        loadStudents();
    }

    private void openGradesForm() {
        Student selectedStudent = studentListPanel.getSelectedStudent();
        if (selectedStudent == null) {
            studentListPanel.showSelectStudentMessage(this);
            return;
        }

        var fullStudentOpt = studentUseCase.findByRegistration(selectedStudent.getRegistration());
        Student fullStudent = fullStudentOpt.orElse(selectedStudent);

        GradesFormDialog dialog = new GradesFormDialog(this, reportCardUseCase, fullStudent);
        dialog.setVisible(true);
        loadStudents();
    }

    private void openGradeBook() {
        GradeBookDialog dialog = new GradeBookDialog(this, studentUseCase, reportCardUseCase);
        dialog.setVisible(true);
    }
}