package br.edu.escola.swingapp.application;

import br.edu.escola.swingapp.domain.model.Student;
import br.edu.escola.swingapp.domain.service.StudentValidator;
import br.edu.escola.swingapp.infra.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentUseCaseTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentValidator studentValidator;

    @InjectMocks
    private StudentUseCase studentUseCase;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setRegistration("123");
        student.setName("Student Test");
    }

    @Test
    @DisplayName("Should register student successfully")
    void shouldRegisterStudentSuccessfully() {
        // Arrange
        when(studentRepository.existsByRegistration("123")).thenReturn(false);

        // Act
        studentUseCase.registerStudent(student);

        // Assert
        verify(studentValidator).validate(student);
        verify(studentRepository).existsByRegistration("123");
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("Should throw exception when student already exists")
    void shouldThrowExceptionWhenStudentAlreadyExists() {
        // Arrange
        when(studentRepository.existsByRegistration("123")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentUseCase.registerStudent(student));

        verify(studentValidator).validate(student);
        verify(studentRepository).existsByRegistration("123");
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Should update student successfully")
    void shouldUpdateStudentSuccessfully() {
        // Arrange
        when(studentRepository.existsByRegistration("123")).thenReturn(true);

        // Act
        studentUseCase.updateStudent(student);

        // Assert
        verify(studentValidator).validate(student);
        verify(studentRepository).existsByRegistration("123");
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent student")
    void shouldThrowExceptionWhenUpdatingNonExistentStudent() {
        // Arrange
        when(studentRepository.existsByRegistration("123")).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentUseCase.updateStudent(student));

        verify(studentValidator).validate(student);
        verify(studentRepository).existsByRegistration("123");
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Should delete student successfully")
    void shouldDeleteStudentSuccessfully() {
        // Act
        studentUseCase.deleteStudent("123");

        // Assert
        verify(studentRepository).deleteByRegistration("123");
    }
}
