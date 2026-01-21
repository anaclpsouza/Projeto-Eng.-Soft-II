package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentFormDialogTest {

    @Mock
    private StudentUseCase studentUseCase;

    @Test
    void shouldInitializeForNewStudent() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            StudentFormDialog dialog = new StudentFormDialog(null, studentUseCase, null);
            assertNotNull(dialog);
            assertEquals("Cadastrar Aluno", dialog.getTitle());
            dialog.dispose();
        });
    }

    @Test
    void shouldInitializeForExistingStudent() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Test Student");

        SwingUtilities.invokeAndWait(() -> {
            StudentFormDialog dialog = new StudentFormDialog(null, studentUseCase, student);
            assertNotNull(dialog);
            assertEquals("Editar Aluno", dialog.getTitle());
            dialog.dispose();
        });
    }
}
