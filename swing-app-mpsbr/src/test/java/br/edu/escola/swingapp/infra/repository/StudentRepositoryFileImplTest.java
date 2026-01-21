package br.edu.escola.swingapp.infra.repository;

import br.edu.escola.swingapp.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentRepositoryFileImplTest {

    @Mock
    private FileStorage fileStorage;

    private StudentRepositoryFileImpl repository;

    @BeforeEach
    void setUp() {
        repository = new StudentRepositoryFileImpl(fileStorage);
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenFileIsEmpty() {
        when(fileStorage.readAll()).thenReturn("");

        List<Student> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_ShouldReturnStudents_WhenFileHasContent() {
        String csvContent = "123;John Doe;;;;\n456;Jane Doe;10.0;9.0;8.0;7.0\n";
        when(fileStorage.readAll()).thenReturn(csvContent);

        List<Student> result = repository.findAll();

        assertEquals(2, result.size());

        Student s1 = result.get(0);
        assertEquals("123", s1.getRegistration());
        assertEquals("John Doe", s1.getName());
        assertNull(s1.getReportCard());
        // Depending on implementation, empty grades might mean null reportCard or
        // existing reportCard with nulls.
        // Looking at code: if all grades are null/empty, reportCard is NOT set on
        // student only if grades are actually parsed as null.
        // Let's check implementation behavior:
        // toCsvLine writes ";;;" if reportCard is null.
        // fromCsvLine splits by ";" and calls parseGrade.
        // If sExam1 is empty string, parseGrade returns null.
        // If ALL grades are null, the `if` block for setting reportCard is skipped?
        // Code: if (reportCard.getExam1Grade() != null || ... )
        // student.setReportCard(...)
        // So s1 should NOT have a report card.

        Student s2 = result.get(1);
        assertEquals("456", s2.getRegistration());
        assertEquals("Jane Doe", s2.getName());
        assertNotNull(s2.getReportCard());
        assertEquals(10.0, s2.getReportCard().getExam1Grade());
    }

    @Test
    void save_ShouldAddNewStudent_AndWriteToFile() {
        when(fileStorage.readAll()).thenReturn("");

        Student newStudent = new Student();
        newStudent.setRegistration("999");
        newStudent.setName("New Student");

        repository.save(newStudent);

        verify(fileStorage).writeAll(contains("999;New Student"));
    }

    @Test
    void save_ShouldUpdateExistingStudent_AndWriteToFile() {
        String csvContent = "123;Old Name;;;;\n";
        when(fileStorage.readAll()).thenReturn(csvContent);

        Student updatedStudent = new Student();
        updatedStudent.setRegistration("123");
        updatedStudent.setName("New Name"); // changed name

        repository.save(updatedStudent);

        // Expectation: The file content written should replace the old line with the
        // new one
        verify(fileStorage)
                .writeAll(argThat(content -> content.contains("123;New Name") && !content.contains("Old Name")));
    }

    @Test
    void deleteByRegistration_ShouldRemoveStudent_AndSave() {
        String csvContent = "123;To Delete;;;;\n456;To Keep;;;;\n";
        when(fileStorage.readAll()).thenReturn(csvContent);

        repository.deleteByRegistration("123");

        verify(fileStorage)
                .writeAll(argThat(content -> !content.contains("123;To Delete") && content.contains("456;To Keep")));
    }

    @Test
    void findByRegistration_ShouldReturnStudent_WhenExists() {
        String csvContent = "123;Target;;;;\n";
        when(fileStorage.readAll()).thenReturn(csvContent);

        Optional<Student> result = repository.findByRegistration("123");

        assertTrue(result.isPresent());
        assertEquals("Target", result.get().getName());
    }

    @Test
    void findByRegistration_ShouldReturnEmpty_WhenNotExists() {
        String csvContent = "123;Target;;;;\n";
        when(fileStorage.readAll()).thenReturn(csvContent);

        Optional<Student> result = repository.findByRegistration("999");

        assertTrue(result.isEmpty());
    }
}
