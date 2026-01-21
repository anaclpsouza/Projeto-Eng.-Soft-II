package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MainFrameTest {

    @Mock
    private StudentUseCase studentUseCase;

    @Mock
    private ReportCardUseCase reportCardUseCase;

    @Test
    void shouldInitializeMainFrameCurrently() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            MainFrame mainFrame = new MainFrame(studentUseCase, reportCardUseCase);

            assertNotNull(mainFrame);
            assertEquals("Gerenciador de Notas Escolares", mainFrame.getTitle());
            assertNotNull(mainFrame.getJMenuBar());
            assertEquals(2, mainFrame.getJMenuBar().getMenuCount()); // Aluno, Notas
        });
    }
}
