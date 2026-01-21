package br.edu.escola;

import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.domain.service.StudentValidator;
import br.edu.escola.swingapp.domain.service.CalculusAverageService;
import br.edu.escola.swingapp.infra.repository.StudentRepository;
import br.edu.escola.swingapp.infra.repository.StudentRepositoryFileImpl;
import br.edu.escola.swingapp.infra.repository.FileStorage;
import br.edu.escola.swingapp.ui.MainFrame;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Infra    
            FileStorage fileStorage = new FileStorage("Students.csv");
            StudentRepository studentRepository = new StudentRepositoryFileImpl(fileStorage);

            // Domínio
            StudentValidator studentValidator = new StudentValidator();
            CalculusAverageService calculusAverageService = new CalculusAverageService();

            // Aplicação
            StudentUseCase studentUseCase = new StudentUseCase(studentRepository, studentValidator);
            ReportCardUseCase boletimUseCase = new ReportCardUseCase(studentRepository, calculusAverageService);

            // UI
            MainFrame mainFrame = new MainFrame(studentUseCase, boletimUseCase);
            mainFrame.setVisible(true);
        });
    }
}