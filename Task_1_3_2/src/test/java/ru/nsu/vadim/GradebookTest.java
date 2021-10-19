package ru.nsu.vadim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class GradebookTest {
    @Test
    void gradebook_testAll() {
        Gradebook myBook = new Gradebook(
                "Мостовой",
                "Вадиммм",
                "Евгеньевич");

        String myFullName = myBook.getStudent().getFullName();
        Assertions.assertEquals("Мостовой Вадиммм Евгеньевич", myFullName);

        myBook.getStudent().setName("Вадим");
        myFullName = myBook.getStudent().getFullName();
        Assertions.assertEquals("Мостовой Вадим Евгеньевич", myFullName);

        double myAvg = myBook.avgGradesAll();
        Assertions.assertEquals(0.0, myAvg);

        myBook.addGrade(
                1,
                "Иностранный язык",
                new Grade(5,
                        LocalDate.of(2020, 12, 23),
                        Grade.formType.credit,
                        new Person(
                                "Хоцкина",
                                "Ольга",
                                "Валерьевна")));
        myBook.addGrade(
                1,
                "Введение в алгебру и анализ",
                new Grade(4,
                        LocalDate.of(2021, 1, 12),
                        Grade.formType.exam,
                        new Person(
                                "Васкевич",
                                "Владимир",
                                "Леонтьевич")));
        myBook.addGrade(
                1,
                "История",
                new Grade(5,
                        LocalDate.of(2020, 12, 28),
                        Grade.formType.diffCredit,
                        new Person(
                                "Оплаканская",
                                "Рената",
                                "Валерьевна")));

        myBook.addGrade(
                1,
                "Введение в дискретную математику и математическую логику",
                new Grade(5,
                        LocalDate.of(2021, 1, 19),
                        Grade.formType.exam,
                        new Person(
                                "Власов",
                                "Дмитрий",
                                "Юрьевич")));

        Assertions.assertEquals(5, myBook.getGrade(1, "Иностранный язык").getGrade());

        Assertions.assertEquals((5 + 5 + 5 + 4) / 4d, myBook.avgGradesAll());

        Assertions.assertEquals(1, myBook.currentSemester());

        Assertions.assertFalse(myBook.isIncreasedScholarshipReady());

        myBook.setQualifyingWorkGrade(5);

        Assertions.assertTrue(myBook.isRedDiplomaReady());

        myBook.addGrade(1, "Декларативное программирование", new Grade(5));
        myBook.addGrade(1, "Физическая культура и спорт", new Grade(5));
        myBook.addGrade(1, "Основы культуры и речи", new Grade(5));
        myBook.addGrade(1, "Императивное программирование", new Grade(4));
        myBook.addGrade(1, "Цифровые платформы", new Grade(5));

        myBook.addGrade(2, "Введение в алгебру и анализ", new Grade(3));
        myBook.addGrade(2, "Императивное программирование", new Grade(5));
        myBook.addGrade(2, "Цифровые платформы", new Grade(5));
        myBook.addGrade(2, "Введение в дискретную математику и математическую логику", new Grade(4));
        myBook.addGrade(2, "Иностранный язык", new Grade(5));
        myBook.addGrade(2, "Декларативное программирование", new Grade(5));
        myBook.addGrade(2, "Физическая культура и спорт", new Grade(5));
        myBook.addGrade(2, "Измерительный практикум", new Grade(5));

        Assertions.assertFalse(myBook.isIncreasedScholarshipReady());

        Assertions.assertFalse(myBook.isRedDiplomaReady());
    }
}
