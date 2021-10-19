package ru.nsu.vadim;

import java.util.ArrayList;
import java.util.HashMap;

public class Gradebook {
    private Person student = new Person(null, null, null);
    final int MAX_SEMESTER = 8;
    private ArrayList<HashMap<String, Grade>> semesterGrades = new ArrayList<>(MAX_SEMESTER);
    private int qualifyingWorkGrade;

    Gradebook(String f, String i, String o) {
        this.student.setSurname(f);
        this.student.setName(i);
        this.student.setPatronymic(o);
        for (int k = 0; k < MAX_SEMESTER; k++) {
            semesterGrades.add(new HashMap<>());
        }
    }

    public void setQualifyingWorkGrade(int qualifyingWorkGrade) {
        this.qualifyingWorkGrade = qualifyingWorkGrade;
    }

    public int getQualifyingWorkGrade() {
        return qualifyingWorkGrade;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public void addGrade(int semester, String subject, Grade grade) {
        semesterGrades.get(semester - 1).put(subject, grade); // idk why it should work but it actually works
    }

    public Grade getGrade(int semester, String subject) {
        return semesterGrades.get(semester - 1).get(subject);
    }

    public ArrayList<Integer> getJustGrades(int semester) {
        ArrayList<Grade> got = new ArrayList<>(semesterGrades.get(semester - 1).values());
        ArrayList<Integer> res = new ArrayList<>();
        for (Grade item : got) {
            res.add(item.getGrade());
        }
        return res;
    }

    public double avgGradesAll() {
        ArrayList<Integer> all = new ArrayList<>();
        for (int i = 0; i < MAX_SEMESTER; i++) {
            all.addAll(getJustGrades(i + 1));
        }

        return all.stream().mapToDouble(n -> n).average().orElse(0);
    }

    public int currentSemester() {
        int i;
        for (i = 0; !semesterGrades.get(i).isEmpty(); i++) ;
        return i;
    }

    public boolean isIncreasedScholarshipReady() {
        int badCnt = (int) getJustGrades(currentSemester())
                .stream()
                .filter(g -> g < 5)
                .count();

        return badCnt == 0;
    }

    public boolean isRedDiplomaReady() {
        for (int i = 0; i < currentSemester(); i++) {
            for (int grade : getJustGrades(i + 1)) {
                if (grade < 4) {
                    return false;
                }
            }
        }

        int excLastCnt = (int) getJustGrades(currentSemester())
                .stream()
                .filter(g -> g == 5)
                .count();

        int allLastCnt = getJustGrades(currentSemester()).size();

        if (excLastCnt / (double) allLastCnt < 0.75) {
            return false;
        }

        return qualifyingWorkGrade >= 5;
    }
}
