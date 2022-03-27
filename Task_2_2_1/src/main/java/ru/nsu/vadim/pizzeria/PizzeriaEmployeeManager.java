package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import ru.nsu.vadim.employee.Employee;
import ru.nsu.vadim.employee.EmployeeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PizzeriaEmployeeManager implements EmployeeManager {

    private record EmployeesWrapper(Collection<Employee> employees) {}

    private static final PolymorphicTypeValidator typeValidator =
            BasicPolymorphicTypeValidator.builder()
                    .allowIfSubType(PizzaCooker.class)
                    .allowIfSubType(PizzaDeliverer.class)
                    .allowIfSubType(ArrayList.class)
                    .build();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .activateDefaultTyping(typeValidator);

    private final Collection<Employee> employees;

    public PizzeriaEmployeeManager() {
        this(new ArrayList<>());
    }

    public PizzeriaEmployeeManager(Collection<Employee> employees) {
        this.employees = employees;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Employee> List<E> getEmployees(Class<E> typeOfEmployee) {
        return (List<E>) employees.stream().filter(typeOfEmployee::isInstance).toList();
    }

    @Override
    public Optional<Employee> getEmployee(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }


    @Override
    public List<Employee> getAllEmployees() {
        return employees.stream().toList();
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void addEmployees(Collection<? extends Employee> employees) {
        this.employees.addAll(employees);
    }

    @Override
    public void addEmployeeFromJson(File jsonFile, Class<? extends Employee> typeOfEmployee) throws Exception {
        employees.add(objectMapper.readValue(jsonFile, typeOfEmployee));
    }

    @Override
    public void addEmployeesFromJson(File jsonFile) throws Exception {
        employees.addAll(objectMapper.readValue(jsonFile, EmployeesWrapper.class).employees);
    }

    @Override
    public void saveAllEmployeesToJson(File jsonFile) throws Exception {
        objectMapper.writeValue(jsonFile, new EmployeesWrapper(new ArrayList<>(employees)));
    }

    @Override
    public boolean saveEmployeeToJson(File jsonFile, long id) throws Exception {
        Optional<Employee> employee = getEmployee(id);
        if (employee.isEmpty()) {
            return false;
        } else {
            objectMapper.writeValue(jsonFile, employee.get());
            return true;
        }
    }

}
