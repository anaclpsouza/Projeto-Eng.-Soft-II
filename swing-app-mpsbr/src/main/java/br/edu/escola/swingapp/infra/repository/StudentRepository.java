package br.edu.escola.swingapp.infra.repository;

import java.util.List;
import java.util.Optional;

import br.edu.escola.swingapp.domain.model.Student;

public interface StudentRepository {
    void save(Student student);

    List<Student> findAll();

    Optional<Student> findByRegistration(String registration);

    void deleteByRegistration(String registration);

    boolean existsByRegistration(String registration);
}
