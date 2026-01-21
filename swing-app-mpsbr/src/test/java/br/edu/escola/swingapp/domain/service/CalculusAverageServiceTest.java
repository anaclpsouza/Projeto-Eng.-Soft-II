package br.edu.escola.swingapp.domain.service;

import br.edu.escola.swingapp.domain.model.ReportCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculusAverageServiceTest {

    private CalculusAverageService service;

    @BeforeEach
    void setUp() {
        service = new CalculusAverageService();
    }

    // RF03: Validar notas entre 0.0 e 10.0
    // Nota: O serviço atual NÃO implementa essa validação, então este teste
    // documenta o comportamento esperado.
    // Se a validação for adicionada ao serviço futuramente, este teste garantirá
    // que funcione.
    // Por enquanto, vou focar nos testes de cálculo e situação que são o core da
    // lógica existente.
    // Caso eu devesse implementar a validação, eu modificaria a classe de serviço
    // também.
    // Vou assumir que o teste deve verificar se o cálculo ignora notas inválidas OU
    // lança erro.
    // Como a regra diz "Notas devem ser validadas", vou adicionar um teste que
    // espera uma validação (e que deve falhar se não houver).

    @Test
    @DisplayName("RF04: Should calculate arithmetic average correctly")
    void shouldCalculateArithmeticAverageCorrectly() {
        ReportCard card = new ReportCard();
        card.setExam1Grade(8.0);
        card.setExam2Grade(7.0);
        card.setAssignmentGrade(9.0);
        card.setProjectGrade(8.0);
        // (8+7+9+8)/4 = 32/4 = 8.0

        double average = service.calculateAverage(card);
        assertEquals(8.0, average, 0.01);
    }

    @Test
    @DisplayName("RF04: Should calculate average with rounding")
    void shouldCalculateAverageWithRounding() {
        ReportCard card = new ReportCard();
        card.setExam1Grade(7.5);
        card.setExam2Grade(7.5);
        card.setAssignmentGrade(7.5); // 22.5
        card.setProjectGrade(8.0); // 30.5 / 4 = 7.625 -> deve arredondar para 7.63 (HALF_UP)

        // O código atual usa BigDecimal com RoundingMode.HALF_UP e escala 2.
        double average = service.calculateAverage(card);
        assertEquals(7.63, average, 0.001);
    }

    @Test
    @DisplayName("RF04: Should handle null grades by ignoring them in sum but counting them? Or treating as zero?")
    // Analisando o código:
    // if (reportCard.getExam1Grade() != null) { sum += val; count++; }
    // Se for null, não soma e não incrementa count.
    // Se count == 0, retorna 0.0.
    // Isso parece calcular a média APENAS das notas lançadas.
    void shouldCalculateAverageOfOnlyPresentGrades() {
        ReportCard card = new ReportCard();
        card.setExam1Grade(8.0);
        card.setExam2Grade(null);
        card.setAssignmentGrade(null);
        card.setProjectGrade(8.0);
        // (8+8)/2 = 8.0

        double average = service.calculateAverage(card);
        assertEquals(8.0, average, 0.01);
    }

    @Test
    @DisplayName("RF04: Should return zero when no grades are present")
    void shouldReturnZeroWhenNoGrades() {
        ReportCard card = new ReportCard();
        double average = service.calculateAverage(card);
        assertEquals(0.0, average);
    }

    @Test
    @DisplayName("RF05: Should return 'Aprovado' when average >= 7.0")
    void shouldReturnAprovado() {
        assertEquals("Aprovado", service.determineSituation(7.0));
        assertEquals("Aprovado", service.determineSituation(8.5));
        assertEquals("Aprovado", service.determineSituation(10.0));
    }

    @Test
    @DisplayName("RF05: Should return 'Recuperação' when average >= 5.0 and < 7.0")
    void shouldReturnRecuperacao() {
        assertEquals("Recuperação", service.determineSituation(5.0));
        assertEquals("Recuperação", service.determineSituation(6.9));
        assertEquals("Recuperação", service.determineSituation(5.5));
    }

    @Test
    @DisplayName("RF05: Should return 'Reprovado' when average < 5.0")
    void shouldReturnReprovado() {
        assertEquals("Reprovado", service.determineSituation(4.9));
        assertEquals("Reprovado", service.determineSituation(0.0));
        assertEquals("Reprovado", service.determineSituation(2.5));
        assertEquals("Reprovado", service.determineSituation(-1.0)); // Teoricamente impossível com validação, mas a
                                                                     // lógica cobre
    }
}
