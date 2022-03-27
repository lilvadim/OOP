package ru.nsu.vadim.employee;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface EmployeeManager {
    <E extends Employee> List<E> getEmployees(Class<E> typeOfEmployee);

    List<? extends Employee> getAllEmployees();

    void addEmployee(Employee employee);

    void addEmployees(Collection<? extends Employee> employees);

    void addEmployeeFromJson(File jsonFile, Class<? extends Employee> typeOfEmployee) throws Exception;

    void addEmployeesFromJson(File jsonFile) throws Exception;

    void saveEmployeesToJson(File jsonFile) throws Exception;
}
