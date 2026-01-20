package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.application.ReportCardUseCase;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class MainFrame extends JFrame {

    private final StudentUseCase studentUseCase;
    private final ReportCardUseCase reportCardUseCase;

    private StudentListPanel studentListPanel;

    public MainFrame(StudentUseCase studentUseCase, ReportCardUseCase reportCardUseCase) {
        super("Gerenciador de Notas Escolares");
        this.studentUseCase = studentUseCase;
        this.reportCardUseCase = reportCardUseCase;

        configureWindow();
        initializeComponents();
        loadInitialData();
    }

    private void configureWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initializeComponents() {
        // Menu
        setJMenuBar(createMenuBar());

        // Top button panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRegister = new JButton("Cadastrar Aluno");
        JButton btnEdit = new JButton("Editar Aluno");
        JButton btnShowGrades = new JButton("Ver/Editar Notas");
        JButton btnDelete = new JButton("Remover Aluno");

        btnRegister.addActionListener(e -> openStudentRegistrationForm());
        btnEdit.addActionListener(e -> openStudentEditForm());
        btnShowGrades.addActionListener(e -> openGradesForm());
        btnDelete.addActionListener(e -> deleteSelectedStudent());

        topPanel.add(btnRegister);
        topPanel.add(btnEdit);
        topPanel.add(btnShowGrades);
        topPanel.add(btnDelete);

        add(topPanel, BorderLayout.NORTH);

        // Student list panel
        studentListPanel = new StudentListPanel();
        JScrollPane scrollPane = new JScrollPane(studentListPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu studentMenu = new JMenu("Aluno");
        JMenuItem miRegister = new JMenuItem("Cadastrar");
        JMenuItem miEdit = new JMenuItem("Editar");
        JMenuItem miDelete = new JMenuItem("Remover");

        miRegister.addActionListener(e -> openStudentRegistrationForm());
        miEdit.addActionListener(e -> openStudentEditForm());
        miDelete.addActionListener(e -> deleteSelectedStudent());

        studentMenu.add(miRegister);
        studentMenu.add(miEdit);
        studentMenu.add(miDelete);

        JMenu gradesMenu = new JMenu("Notas");
        JMenuItem miShowGrades = new JMenuItem("Ver/Editar Notas");
        miShowGrades.addActionListener(e -> openGradesForm());

        JMenuItem miGradeBook = new JMenuItem("Diário de Classe");
        miGradeBook.addActionListener(e -> openGradeBook());

        gradesMenu.add(miShowGrades);
        gradesMenu.add(miGradeBook);
        menuBar.add(studentMenu);
        menuBar.add(gradesMenu);

        return menuBar;
    }

    private void loadInitialData() {
        studentListPanel.loadStudents(studentUseCase.listAll());
    }

    private void openStudentRegistrationForm() {
        StudentFormDialog dialog = new StudentFormDialog(this, studentUseCase, null);
        dialog.setVisible(true);
        studentListPanel.loadStudents(studentUseCase.listAll());
    }

    private void openStudentEditForm() {
        var selectedStudent = studentListPanel.getSelectedStudent();
        if (selectedStudent == null) {
            studentListPanel.showSelectStudentMessage(this);
            return;
        }
        var fullStudentOpt = studentUseCase.findByRegistration(selectedStudent.getRegistration());
        var studentToEdit = fullStudentOpt.orElse(selectedStudent);

        StudentFormDialog dialog = new StudentFormDialog(this, studentUseCase, studentToEdit);
        dialog.setVisible(true);

        studentListPanel.loadStudents(studentUseCase.listAll());
    }

    private void openGradesForm() {
        var selectedStudent = studentListPanel.getSelectedStudent();
        if (selectedStudent == null) {
            studentListPanel.showSelectStudentMessage(this);
            return;
        }
        var fullStudentOpt = studentUseCase.findByRegistration(selectedStudent.getRegistration());
        var fullStudent = fullStudentOpt.orElse(selectedStudent);

        GradesFormDialog dialog = new GradesFormDialog(this, reportCardUseCase, fullStudent);
        dialog.setVisible(true);
        studentListPanel.loadStudents(studentUseCase.listAll());
    }

    private void deleteSelectedStudent() {
        var selectedStudent = studentListPanel.getSelectedStudent();
        if (selectedStudent == null) {
            studentListPanel.showSelectStudentMessage(this);
            return;
        }
        studentListPanel.confirmAndRemoveStudent(this, studentUseCase, selectedStudent);
        studentListPanel.loadStudents(studentUseCase.listAll());
    }

    private void openGradeBook() {
        GradeBookFrame frame = new GradeBookFrame(studentUseCase, reportCardUseCase);
        frame.setVisible(true);
    }
}