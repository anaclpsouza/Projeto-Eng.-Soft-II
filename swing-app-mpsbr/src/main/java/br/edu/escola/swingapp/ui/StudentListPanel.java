package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.Student;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class StudentListPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public StudentListPanel() {
        setLayout(new BorderLayout());
        initializeTable();
    }

    private void initializeTable() {
        tableModel = new DefaultTableModel(
                new Object[]{"Matrícula", "Nome"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only table
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }

    public void loadStudents(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getRegistration(),
                    student.getName()
            });
        }
    }

    public Student getSelectedStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        String registration = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        // Return a "simple" Student. MainFrame/UseCase can load the full object if needed.
        Student student = new Student();
        student.setRegistration(registration);
        student.setName(name);
        return student;
    }

    public void showSelectStudentMessage(java.awt.Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                "Selecione um aluno na tabela.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public void confirmAndRemoveStudent(java.awt.Component parent,
                                        StudentUseCase studentUseCase,
                                        Student selectedStudent) {
        int option = JOptionPane.showConfirmDialog(
                parent,
                "Deseja realmente remover o aluno " + selectedStudent.getName() + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );
        if (option == JOptionPane.YES_OPTION) {
            studentUseCase.deleteStudent(selectedStudent.getRegistration());
            JOptionPane.showMessageDialog(
                    parent,
                    "Aluno removido com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}