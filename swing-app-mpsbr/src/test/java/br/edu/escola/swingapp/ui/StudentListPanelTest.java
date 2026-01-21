package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentListPanelTest {

    private StudentListPanel panel;

    @BeforeEach
    void setUp() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            panel = new StudentListPanel();
        });
    }

    @Test
    void loadStudents_ShouldPopulateTable() throws InterruptedException, InvocationTargetException {
        Student s1 = new Student();
        s1.setRegistration("111");
        s1.setName("Alice");

        Student s2 = new Student();
        s2.setRegistration("222");
        s2.setName("Bob");

        List<Student> students = List.of(s1, s2);

        SwingUtilities.invokeAndWait(() -> {
            panel.loadStudents(students);

            // Access table via reflection or getComponent logic since it's private,
            // OR checks generic behavior.
            // But we can check behavior via getSelectedStudent logic if we select a row.

            JTable table = (JTable) panel.getComponent(1); // 0 is header, 1 is table usually in BorderLayout?
            // Wait, StudentListPanel adds ScrollPane? No:
            // add(table.getTableHeader(), BorderLayout.NORTH);
            // add(table, BorderLayout.CENTER);

            // So component 1 should be table.
            assertEquals(2, table.getRowCount());
            assertEquals("111", table.getValueAt(0, 0));
            assertEquals("Alice", table.getValueAt(0, 1));
        });
    }

    @Test
    void getSelectedStudent_ShouldReturnNull_WhenNoSelection() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            Student result = panel.getSelectedStudent();
            assertNull(result);
        });
    }

    @Test
    void getSelectedStudent_ShouldReturnStudent_WhenRowSelected()
            throws InterruptedException, InvocationTargetException {
        Student s1 = new Student();
        s1.setRegistration("111");
        s1.setName("Alice");
        List<Student> students = List.of(s1);

        SwingUtilities.invokeAndWait(() -> {
            panel.loadStudents(students);

            JTable table = (JTable) panel.getComponent(1);
            table.setRowSelectionInterval(0, 0);

            Student result = panel.getSelectedStudent();
            assertNotNull(result);
            assertEquals("111", result.getRegistration());
            assertEquals("Alice", result.getName());
        });
    }
}
