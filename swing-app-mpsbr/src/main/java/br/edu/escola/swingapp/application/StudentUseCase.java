package br.edu.escola.swingapp.application;

import java.util.List;
import java.util.Optional;

import br.edu.escola.swingapp.domain.model.Student;
import br.edu.escola.swingapp.domain.service.StudentValidator;
import br.edu.escola.swingapp.infra.repository.StudentRepository;

public class StudentUseCase {

    private final StudentRepository studentRepository;
    private final StudentValidator studentValidator;

    public StudentUseCase(StudentRepository studentRepository, StudentValidator studentValidator) {
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
    }

    // CREATE
    public void registerStudent(Student student) {
        studentValidator.validate(student);

        if (studentRepository.existsByRegistration(student.getRegistration())) {
            throw new RuntimeException("Matrícula já cadastrada");
        }

        studentRepository.save(student);
    }

    // UPDATE 
    public void updateStudent(Student student) {
        studentValidator.validate(student);

        if (!studentRepository.existsByRegistration(student.getRegistration())) {
            throw new RuntimeException("Aluno não encontrado para atualização");
        }

        studentRepository.save(student);
    }

    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    public void deleteStudent(String registration) {
        studentRepository.deleteByRegistration(registration);
    }

    public Optional<Student> findByRegistration(String registration) {
        return studentRepository.findByRegistration(registration);
    }
}