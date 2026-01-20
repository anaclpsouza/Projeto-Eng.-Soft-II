package br.edu.escola.swingapp.domain.model;

public class ReportCard {
    private Double exam1Grade;
    private Double exam2Grade;
    private Double assignmentGrade;
    private Double projectGrade;

    public Double getExam1Grade() {
        return exam1Grade;
    }

    public void setExam1Grade(Double exam1Grade) {
        this.exam1Grade = exam1Grade;
    }

    public Double getExam2Grade() {
        return exam2Grade;
    }

    public void setExam2Grade(Double exam2Grade) {
        this.exam2Grade = exam2Grade;
    }

    public Double getAssignmentGrade() {
        return assignmentGrade;
    }

    public void setAssignmentGrade(Double assignmentGrade) {
        this.assignmentGrade = assignmentGrade;
    }

    public Double getProjectGrade() {
        return projectGrade;
    }

    public void setProjectGrade(Double projectGrade) {
        this.projectGrade = projectGrade;
    }
}

