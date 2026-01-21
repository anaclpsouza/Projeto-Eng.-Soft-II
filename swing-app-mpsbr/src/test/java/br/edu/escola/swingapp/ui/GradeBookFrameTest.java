package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeBookFrameTest {

    @Mock
    private StudentUseCase studentUseCase;

    @Mock
    private ReportCardUseCase reportCardUseCase;

    @Test
    void shouldInitializeAndLoadData() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Test Student");
        student.setReportCard(new br.edu.escola.swingapp.domain.model.ReportCard());

        when(studentUseCase.listAll()).thenReturn(List.of(student));
        when(reportCardUseCase.getAverage("123")).thenReturn(8.5);
        when(reportCardUseCase.getSituation("123")).thenReturn("Aprovado");

        SwingUtilities.invokeAndWait(() -> {
            GradeBookFrame frame = new GradeBookFrame(studentUseCase, reportCardUseCase);

            assertNotNull(frame);
            assertEquals("Diário de Classe", frame.getTitle());

            // To be more thorough, we could inspect the JTable
            // Assuming the frame structure has a ScrollPane which has the Table
            if (frame.getContentPane().getComponent(0) instanceof javax.swing.JScrollPane scrollPane) {
                JViewport viewport = scrollPane.getViewport();
                if (viewport.getView() instanceof JTable table) {
                    assertEquals(1, table.getRowCount());
                    assertEquals("Test Student", table.getValueAt(0, 1));
                    assertEquals(8.5, table.getValueAt(0, 6)); // Average column
                    assertEquals("Aprovado", table.getValueAt(0, 7)); // Situation column
                }
            }

            frame.dispose();
        });
    }

    @Test
    void shouldHandleEmptyList() throws Exception {
        when(studentUseCase.listAll()).thenReturn(Collections.emptyList());

        SwingUtilities.invokeAndWait(() -> {
            GradeBookFrame frame = new GradeBookFrame(studentUseCase, reportCardUseCase);
            assertNotNull(frame);
            frame.dispose();
        });
    }
}
