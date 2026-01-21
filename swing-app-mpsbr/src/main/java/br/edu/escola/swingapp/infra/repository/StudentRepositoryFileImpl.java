package br.edu.escola.swingapp.infra.repository;

import br.edu.escola.swingapp.domain.model.Student;
import br.edu.escola.swingapp.domain.model.ReportCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * StudentRepository implementation that persists data in a CSV file,
 * using FileStorage for file read/write.
 *
 * CSV format (per line):
 *   registration;name;exam1Grade;exam2Grade;assignmentGrade;projectGrade
 */
public class StudentRepositoryFileImpl implements StudentRepository {

    private final FileStorage fileStorage;

    public StudentRepositoryFileImpl(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public void save(Student student) {
        List<Student> students = findAll();

        // Update if already exists with same registration
        students.removeIf(s -> s.getRegistration().equals(student.getRegistration()));

        students.add(student);

        String csv = toCsv(students);
        fileStorage.writeAll(csv);
    }

    @Override
    public List<Student> findAll() {
        String content = fileStorage.readAll();
        if (content == null || content.isBlank()) {
            return new ArrayList<>();
        }

        List<Student> students = new ArrayList<>();
        String[] lines = content.split("\\R");

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            Student student = fromCsvLine(line);
            students.add(student);
        }

        return students;
    }

    @Override
    public Optional<Student> findByRegistration(String registration) {
        return findAll()
                .stream()
                .filter(s -> s.getRegistration().equals(registration))
                .findFirst();
    }

    @Override
    public void deleteByRegistration(String registration) {
        List<Student> students = findAll();
        boolean removed = students.removeIf(s -> s.getRegistration().equals(registration));

        if (removed) {
            String csv = toCsv(students);
            fileStorage.writeAll(csv);
        }
    }

    @Override
    public boolean existsByRegistration(String registration) {
        return findByRegistration(registration).isPresent();
    }

    // ------------- CSV helpers -------------

    private String toCsv(List<Student> students) {
        StringBuilder sb = new StringBuilder();

        for (Student student : students) {
            sb.append(toCsvLine(student)).append(System.lineSeparator());
        }

        return sb.toString();
    }

    private String toCsvLine(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(escape(student.getRegistration())).append(";");
        sb.append(escape(student.getName())).append(";");

        ReportCard reportCard = student.getReportCard();
        if (reportCard != null) {
            sb.append(formatGrade(reportCard.getExam1Grade())).append(";");
            sb.append(formatGrade(reportCard.getExam2Grade())).append(";");
            sb.append(formatGrade(reportCard.getAssignmentGrade())).append(";");
            sb.append(formatGrade(reportCard.getProjectGrade()));
        } else {
            sb.append(";;;").append("");
        }

        return sb.toString();
    }

    private Student fromCsvLine(String line) {
        String[] parts = line.split(";", -1);
        String registration = unescape(getPart(parts, 0));
        String name = unescape(getPart(parts, 1));

        String sExam1 = getPart(parts, 2);
        String sExam2 = getPart(parts, 3);
        String sAssignment = getPart(parts, 4);
        String sProject = getPart(parts, 5);

        ReportCard reportCard = new ReportCard();
        reportCard.setExam1Grade(parseGrade(sExam1));
        reportCard.setExam2Grade(parseGrade(sExam2));
        reportCard.setAssignmentGrade(parseGrade(sAssignment));
        reportCard.setProjectGrade(parseGrade(sProject));

        Student student = new Student();
        student.setRegistration(registration);
        student.setName(name);

        if (reportCard.getExam1Grade() != null ||
            reportCard.getExam2Grade() != null ||
            reportCard.getAssignmentGrade() != null ||
            reportCard.getProjectGrade() != null) {
            student.setReportCard(reportCard);
        }

        return student;
    }

    private String getPart(String[] parts, int index) {
        if (index < parts.length) {
            return parts[index];
        }
        return "";
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(";", ",");
    }

    private String unescape(String value) {
        return value;
    }

    private String formatGrade(Double grade) {
        if (grade == null) {
            return "";
        }
        return String.valueOf(grade);
    }

    private Double parseGrade(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}