package br.edu.escola.swingapp.domain.service;

import br.edu.escola.swingapp.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {

    private StudentValidator validator;

    @BeforeEach
    void setUp() {
        validator = new StudentValidator();
    }

    @Test
    @DisplayName("Should throw exception when student is null")
    void shouldThrowExceptionWhenStudentIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(null);
        });
        assertEquals("Aluno não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("RF01: Should throw exception when registration is null")
    void shouldThrowExceptionWhenRegistrationIsNull() {
        Student student = new Student();
        student.setName("John Doe");
        student.setRegistration(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(student);
        });
        assertEquals("Matrícula é obrigatória.", exception.getMessage());
    }

    @Test
    @DisplayName("RF01: Should throw exception when registration is empty")
    void shouldThrowExceptionWhenRegistrationIsEmpty() {
        Student student = new Student();
        student.setName("John Doe");
        student.setRegistration("   ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(student);
        });
        assertEquals("Matrícula é obrigatória.", exception.getMessage());
    }

    @Test
    @DisplayName("RF01: Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        Student student = new Student();
        student.setRegistration("123");
        student.setName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(student);
        });
        assertEquals("Nome é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("RF01: Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        Student student = new Student();
        student.setRegistration("123");
        student.setName("   ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(student);
        });
        assertEquals("Nome é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Should pass validation when student is valid")
    void shouldPassValidationWhenStudentIsValid() {
        Student student = new Student();
        student.setRegistration("12345");
        student.setName("John Doe");

        assertDoesNotThrow(() -> validator.validate(student));
    }
}
