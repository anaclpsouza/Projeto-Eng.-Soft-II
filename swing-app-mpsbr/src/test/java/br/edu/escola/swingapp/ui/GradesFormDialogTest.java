package br.edu.escola.swingapp.ui;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.domain.model.ReportCard;
import br.edu.escola.swingapp.domain.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradesFormDialogTest {

    @Mock
    private ReportCardUseCase reportCardUseCase;

    // Test subclass to suppress dialogs and capture messages if needed
    static class TestGradesFormDialog extends GradesFormDialog {
        final List<String> messages = new ArrayList<>();

        public TestGradesFormDialog(Frame owner, ReportCardUseCase reportCardUseCase, Student student) {
            super(owner, reportCardUseCase, student);
        }

        @Override
        protected void showMessage(String message, String title, int messageType) {
            // Suppress dialog and record message
            messages.add(message);
        }
    }

    @Test
    void shouldInitializeWithExistingGrades() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Student Name");

        ReportCard rc = new ReportCard();
        rc.setExam1Grade(8.0);
        student.setReportCard(rc);

        SwingUtilities.invokeAndWait(() -> {
            TestGradesFormDialog dialog = new TestGradesFormDialog(null, reportCardUseCase, student);
            try {
                JTextField txtGrade1 = (JTextField) getField(dialog, "txtGrade1");
                assertEquals("8.0", txtGrade1.getText());
            } catch (Exception e) {
                fail("Failed to access field: " + e.getMessage());
            } finally {
                dialog.dispose();
            }
        });
    }

    @Test
    void shouldSaveValidGrades() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Student Name");

        when(reportCardUseCase.getAverage("123")).thenReturn(9.0);
        when(reportCardUseCase.getSituation("123")).thenReturn("Aprovado");

        SwingUtilities.invokeAndWait(() -> {
            TestGradesFormDialog dialog = new TestGradesFormDialog(null, reportCardUseCase, student);

            try {
                // Set values
                setFieldValue(dialog, "txtGrade1", "10");
                setFieldValue(dialog, "txtGrade2", "9");
                setFieldValue(dialog, "txtWorkGrade", "8");
                setFieldValue(dialog, "txtProjectGrade", "9");

                // Invoke private method directly
                invokePrivateMethod(dialog, "saveGrades");

                // Assert message captured
                if (dialog.messages.isEmpty()) {
                    fail("No messages captured (saveGrades might have crashed silently)");
                }
                String msg = dialog.messages.get(0);
                if (!msg.contains("sucesso")) {
                    fail("Expected success message but got: " + msg);
                }

            } catch (Exception e) {
                fail("Failed during saveGrades invocation: " + e.getMessage());
            } finally {
                dialog.dispose();
            }
        });

        // Verify use case call
        verify(reportCardUseCase, times(1)).updateReportCard(eq("123"), any(ReportCard.class));
    }

    @Test
    void shouldNotSaveInvalidNumber() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Student Name");

        SwingUtilities.invokeAndWait(() -> {
            TestGradesFormDialog dialog = new TestGradesFormDialog(null, reportCardUseCase, student);

            try {
                setFieldValue(dialog, "txtGrade1", "invalid");

                invokePrivateMethod(dialog, "saveGrades");

                // Assert error message captured
                if (dialog.messages.isEmpty())
                    fail("No error message captured");
                assertTrue(dialog.messages.stream().anyMatch(m -> m.contains("valores numéricos")),
                        "Message was: " + dialog.messages);

            } catch (Exception e) {
                // Expected exception handling inside method
            } finally {
                dialog.dispose();
            }
        });

        verify(reportCardUseCase, never()).updateReportCard(anyString(), any());
    }

    @Test
    void shouldNotSaveStartOutOfRangeGrade() throws Exception {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("Student Name");

        SwingUtilities.invokeAndWait(() -> {
            TestGradesFormDialog dialog = new TestGradesFormDialog(null, reportCardUseCase, student);

            try {
                setFieldValue(dialog, "txtGrade1", "11");

                invokePrivateMethod(dialog, "saveGrades");

                // Assert error message captured
                if (dialog.messages.isEmpty())
                    fail("No error message captured");
                assertTrue(dialog.messages.stream().anyMatch(m -> m.contains("0 a 10")),
                        "Message was: " + dialog.messages);

            } catch (Exception e) {
                // Expected exception handling inside method
            } finally {
                dialog.dispose();
            }
        });

        verify(reportCardUseCase, never()).updateReportCard(anyString(), any());
    }

    // Reflection Helpers
    private void setFieldValue(Object target, String fieldName, String value) throws Exception {
        JTextField field = (JTextField) getField(target, fieldName);
        field.setText(value);
    }

    private Object getField(Object target, String fieldName) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    private void invokePrivateMethod(Object target, String methodName) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(methodName);
                method.setAccessible(true);
                method.invoke(target);
                return;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException(methodName);
    }
}
