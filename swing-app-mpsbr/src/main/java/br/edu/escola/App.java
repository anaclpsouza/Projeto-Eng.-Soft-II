package br.edu.escola;

import br.edu.escola.swingapp.application.ReportCardUseCase;
import br.edu.escola.swingapp.application.StudentUseCase;
import br.edu.escola.swingapp.domain.service.CalculusAverageService;
import br.edu.escola.swingapp.domain.service.StudentValidator;
import br.edu.escola.swingapp.infra.repository.FileStorage;
import br.edu.escola.swingapp.infra.repository.StudentRepository;
import br.edu.escola.swingapp.infra.repository.StudentRepositoryFileImpl;
import br.edu.escola.swingapp.ui.MainFrame;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configureLookAndFeel();

            // Infra
            FileStorage fileStorage = new FileStorage("students.csv");
            StudentRepository studentRepository = new StudentRepositoryFileImpl(fileStorage);

            // Domain
            StudentValidator studentValidator = new StudentValidator();
            CalculusAverageService calculusAverageService = new CalculusAverageService();

            // Application
            StudentUseCase studentUseCase = new StudentUseCase(studentRepository, studentValidator);
            ReportCardUseCase reportCardUseCase = new ReportCardUseCase(studentRepository, calculusAverageService);

            // UI
            MainFrame mainFrame = new MainFrame(studentUseCase, reportCardUseCase);
            mainFrame.setVisible(true);
        });
    }

    private static void configureLookAndFeel() {
        Map<String, String> defaults = new HashMap<>();
        defaults.put("Component.focusWidth", "1");
        defaults.put("Button.arc", "12");
        defaults.put("Component.arc", "12");
        defaults.put("Panel.background", "#F5F5F5");

        FlatLaf.setGlobalExtraDefaults(defaults);

        FlatLightLaf.setup();

        UIManager.put("Component.focusColor", new java.awt.Color(33, 150, 243));
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 12);
    }
}