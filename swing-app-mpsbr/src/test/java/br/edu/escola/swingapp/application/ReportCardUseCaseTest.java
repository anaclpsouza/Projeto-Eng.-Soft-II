package br.edu.escola.swingapp.application;

import br.edu.escola.swingapp.domain.model.ReportCard;
import br.edu.escola.swingapp.domain.model.Student;
import br.edu.escola.swingapp.domain.service.CalculusAverageService;
import br.edu.escola.swingapp.infra.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportCardUseCaseTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CalculusAverageService calculusAverageService;

    @InjectMocks
    private ReportCardUseCase reportCardUseCase;

    private Student student;
    private ReportCard reportCard;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setRegistration("123");
        student.setName("Student Test");

        reportCard = new ReportCard();
        reportCard.setExam1Grade(8.0);
    }

    @Test
    @DisplayName("Should update report card successfully")
    void shouldUpdateReportCardSuccessfully() {
        // Arrange
        when(studentRepository.findByRegistration("123")).thenReturn(Optional.of(student));

        // Act
        reportCardUseCase.updateReportCard("123", reportCard);

        // Assert
        verify(studentRepository).findByRegistration("123");
        verify(studentRepository).save(student);
        assertEquals(reportCard, student.getReportCard());
    }

    @Test
    @DisplayName("Should throw exception when student not found for report card update")
    void shouldThrowExceptionWhenStudentNotFoundForUpdate() {
        // Arrange
        when(studentRepository.findByRegistration("123")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reportCardUseCase.updateReportCard("123", reportCard));

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Should get average correctly")
    void shouldGetAverageCorrectly() {
        // Arrange
        student.setReportCard(reportCard);
        when(studentRepository.findByRegistration("123")).thenReturn(Optional.of(student));
        when(calculusAverageService.calculateAverage(reportCard)).thenReturn(8.0);

        // Act
        double average = reportCardUseCase.getAverage("123");

        // Assert
        assertEquals(8.0, average);
        verify(calculusAverageService).calculateAverage(reportCard);
    }

    @Test
    @DisplayName("Should return 0.0 when student has no report card")
    void shouldReturnZeroExpectedWhenNoReportCard() {
        // Arrange
        student.setReportCard(null);
        when(studentRepository.findByRegistration("123")).thenReturn(Optional.of(student));

        // Act
        double average = reportCardUseCase.getAverage("123");

        // Assert
        assertEquals(0.0, average);
        verify(calculusAverageService, never()).calculateAverage(any());
    }

    @Test
    @DisplayName("Should get situation correctly")
    void shouldGetSituationCorrectly() {
        // Arrange
        student.setReportCard(reportCard);
        when(studentRepository.findByRegistration("123")).thenReturn(Optional.of(student));
        when(calculusAverageService.calculateAverage(reportCard)).thenReturn(8.0);
        when(calculusAverageService.determineSituation(8.0)).thenReturn("Aprovado");

        // Act
        String situation = reportCardUseCase.getSituation("123");

        // Assert
        assertEquals("Aprovado", situation);
    }
}
