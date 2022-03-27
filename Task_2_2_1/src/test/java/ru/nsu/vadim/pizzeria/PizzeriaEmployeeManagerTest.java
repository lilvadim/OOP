package ru.nsu.vadim.pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.vadim.employee.Employee;
import ru.nsu.vadim.employee.EmployeeManager;
import ru.nsu.vadim.employee.WorkExperience;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class PizzeriaEmployeeManagerTest {

    static EmployeeManager manager;

    @BeforeAll
    static void init() {
        List<Employee> employees = List.of(
                new PizzaCooker(1, WorkExperience.PROBATIONER),
                new PizzaDeliverer(2, WorkExperience.LESS_THAN_YEAR, 1),
                new PizzaCooker(3, WorkExperience.EXPERT),
                new PizzaCooker(4, WorkExperience.EXPERT),
                new PizzaCooker(5, WorkExperience.EXPERT),
                new PizzaDeliverer(6, WorkExperience.EXPERT, 2),
                new PizzaDeliverer(7, WorkExperience.EXPERT, 3)
        );
        manager = new PizzeriaEmployeeManager(employees);
    }

    @Test
    void getEmployees() {
        List<PizzaCooker> cookers = manager.getEmployees(PizzaCooker.class);
        Assertions.assertEquals(4, cookers.size());

        List<PizzaDeliverer> deliverers = manager.getEmployees(PizzaDeliverer.class);
        Assertions.assertEquals(3, deliverers.size());

        List<Employee> employeeList = manager.getEmployees(Employee.class);
        Assertions.assertEquals(7, employeeList.size());
    }

    @Test
    void saveEmployeesToJson() throws Exception {
        File file = new File("employees.json");
        manager.saveEmployeesToJson(file);

        String json = Files.readString(file.toPath());
        Assertions.assertTrue(json.contains(WorkExperience.LESS_THAN_YEAR.toString()));
        Assertions.assertTrue(json.contains("\"id\" : 6"));
        Assertions.assertTrue(json.contains("\"capacity\" : 3"));
    }

    @Test
    void addEmployeesFromJson() throws Exception {
        EmployeeManager employeeManager = new PizzeriaEmployeeManager();
        employeeManager.addEmployeesFromJson(new File("employees.json"));

        Assertions.assertEquals(manager.getAllEmployees().size(), employeeManager.getAllEmployees().size());
    }
}
