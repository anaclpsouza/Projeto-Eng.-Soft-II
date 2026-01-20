package br.edu.escola.swingapp.domain.service;
import br.edu.escola.swingapp.domain.model.Student;

public class StudentValidator {
    
    public void validate(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo.");
        }

        if (student.getRegistration() == null || student.getRegistration().trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula é obrigatória.");
        }

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (student.getRegistration().length() > 20) {
            throw new IllegalArgumentException("Matrícula não pode ter mais que 20 caracteres.");
        }

        if (student.getName().length() > 100) {
            throw new IllegalArgumentException("Nome não pode ter mais que 100 caracteres.");
        }
    }
}