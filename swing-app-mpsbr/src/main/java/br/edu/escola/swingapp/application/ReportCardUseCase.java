package br.edu.escola.swingapp.application;

import br.edu.escola.swingapp.domain.model.*;
import br.edu.escola.swingapp.infra.repository.*;
import br.edu.escola.swingapp.domain.service.*;

public class ReportCardUseCase {

    private final StudentRepository studentRepository;
    private final CalculusAverageService calculusAverageService;

    public ReportCardUseCase(StudentRepository studentRepository, CalculusAverageService calculusAverageService) {
        this.studentRepository = studentRepository;
        this.calculusAverageService = calculusAverageService;
    }

    public void updateReportCard(String registration, ReportCard newReportCard) {
        Student student = studentRepository.findByRegistration(registration)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        student.setReportCard(newReportCard);
        studentRepository.save(student);
    }

      public double getAverage(String registration) {
        Student student = studentRepository.findByRegistration(registration)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (student.getReportCard() == null) {
            return 0.0;
        }
        return calculusAverageService.calculateAverage(student.getReportCard());
    }

    public String getSituation(String registration) {
        double average = getAverage(registration);
        return calculusAverageService.determineSituation(average);
    }
}
