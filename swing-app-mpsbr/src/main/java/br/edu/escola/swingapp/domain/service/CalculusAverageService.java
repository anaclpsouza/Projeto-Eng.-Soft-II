package br.edu.escola.swingapp.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.edu.escola.swingapp.domain.model.ReportCard;

public class CalculusAverageService {
    public double calculateAverage(ReportCard reportCard) {
        double sum = 0.0;
        int count = 0;

        if (reportCard.getExam1Grade() != null) {
            sum += reportCard.getExam1Grade();
            count++;
        }
        if (reportCard.getExam2Grade() != null) {
            sum += reportCard.getExam2Grade();
            count++;
        }
        if (reportCard.getAssignmentGrade() != null) {
            sum += reportCard.getAssignmentGrade();
            count++;
        }
        if (reportCard.getProjectGrade() != null) {
            sum += reportCard.getProjectGrade();
            count++;
        }

        if (count == 0) {
            return 0.0;
        }
        
        Double average = sum / count;
        BigDecimal bd = BigDecimal.valueOf(average);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public String determineSituation(double average) {
        if (average >= 7.0) {
            return "Aprovado";
        }
        if (average >= 5.0) {
            return "Recuperação";
        }
        return "Reprovado";
    }
}
