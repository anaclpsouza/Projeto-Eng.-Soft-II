package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.Student;
import br.edu.escola.swingapp.domain.model.ReportCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeBookDialogTest {

    @Mock
    private StudentUseCase studentUseCase;

    @Mock
    private ReportCardUseCase reportCardUseCase;

    @Test
    void shouldInitializeAndLoadData() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Test Student");
        student.setReportCard(new ReportCard());

        when(studentUseCase.listAll()).thenReturn(List.of(student));
        when(reportCardUseCase.getAverage("123")).thenReturn(8.5);
        when(reportCardUseCase.getSituation("123")).thenReturn("Aprovado");

        SwingUtilities.invokeAndWait(() -> {
            GradeBookDialog dialog = new GradeBookDialog(new JFrame(), studentUseCase, reportCardUseCase);

            assertNotNull(dialog);
            assertEquals("Diário de Classe", dialog.getTitle());

            // Inspect the JTable within the JScrollPane
            if (dialog.getContentPane().getComponent(0) instanceof JScrollPane scrollPane) {
                JViewport viewport = scrollPane.getViewport();
                if (viewport.getView() instanceof JTable table) {
                    assertEquals(1, table.getRowCount());
                    assertEquals("Test Student", table.getValueAt(0, 1));
                    assertEquals(8.5, table.getValueAt(0, 6)); // Average column
                    assertEquals("Aprovado", table.getValueAt(0, 7)); // Situation column
                } else {
                    fail("Viewport view is not a JTable");
                }
            } else {
                fail("First component is not a JScrollPane");
            }

            dialog.dispose();
        });
    }

    @Test
    void shouldHandleEmptyList() throws Exception {
        when(studentUseCase.listAll()).thenReturn(Collections.emptyList());

        SwingUtilities.invokeAndWait(() -> {
            GradeBookDialog dialog = new GradeBookDialog(new JFrame(), studentUseCase, reportCardUseCase);
            assertNotNull(dialog);
            dialog.dispose();
        });
    }
}
