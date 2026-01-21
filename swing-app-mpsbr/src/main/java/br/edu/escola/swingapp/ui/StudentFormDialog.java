package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.Student;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

public class StudentFormDialog extends JDialog {

    private final StudentUseCase studentUseCase;
    private final Student editingStudent; // null if it is a new registration

    private JTextField txtRegistration;
    private JTextField txtName;

    public StudentFormDialog(Frame owner, StudentUseCase studentUseCase, Student editingStudent) {
        super(owner, true);
        this.studentUseCase = studentUseCase;
        this.editingStudent = editingStudent;

        setTitle(editingStudent == null ? "Cadastrar Aluno" : "Editar Aluno");
        configureWindow();
        initializeComponents();
        if (editingStudent != null) {
            loadStudentData();
        }
    }

    private void configureWindow() {
        setSize(400, 200);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(0, 0));
    }

    private void initializeComponents() {
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        fieldsPanel.add(new JLabel("Matrícula:"));
        txtRegistration = new JTextField();
        fieldsPanel.add(txtRegistration);

        fieldsPanel.add(new JLabel("Nome:"));
        txtName = new JTextField();
        fieldsPanel.add(txtName);

        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> saveStudent());
        btnCancel.addActionListener(e -> dispose());

        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadStudentData() {
        txtRegistration.setText(editingStudent.getRegistration());
        txtName.setText(editingStudent.getName());
        txtRegistration.setEditable(false); // do not allow changing registration when editing
    }

    private void saveStudent() {
        String registration = txtRegistration.getText().trim();
        String name = txtName.getText().trim();

        Student student = (editingStudent == null) ? new Student() : editingStudent;
        student.setRegistration(registration);
        student.setName(name);

        try {
            // For simplicity, we use the same use case for create and update.

            if (editingStudent == null) {
                // CREATE
                studentUseCase.registerStudent(student);
            } else {
                // UPDATE
                studentUseCase.updateStudent(student);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Aluno salvo com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar aluno:\n" + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}