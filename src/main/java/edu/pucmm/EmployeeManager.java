package edu.pucmm;

import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author me@fredpena.dev
 * @created 01/06/2024  - 23:43
 */
public class EmployeeManager {

    private final List<Employee> employees;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        double minAllowed = employee.getPosition().getMinSalary() * 0.9;

        for (Employee existing : employees) {
            if (existing.getId().equals(employee.getId()) || existing.getName().equals(employee.getName())) {
                throw new DuplicateEmployeeException("Duplicate employee by ID or name");
            }
        }

        if (employees.contains(employee)) {
            throw new DuplicateEmployeeException("Duplicate employee");
        }
        if (!isSalaryValidForPosition(employee.getPosition(), employee.getSalary())) {
            throw new InvalidSalaryException("Invalid salary for position");
        }

        if (employee.getSalary() < minAllowed) {
            throw new InvalidSalaryException("Salary is below 10% threshold of minimum salary");
        }

        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        employees.remove(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            totalSalary += employee.getSalary();
        }
        return totalSalary;
    }

    public void updateEmployeeSalary(Employee employee, double newSalary) {
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        if (!isSalaryValidForPosition(employee.getPosition(), newSalary)) {
            throw new InvalidSalaryException("Salary is not within the range for the position");
        }
        employee.setSalary(newSalary);
    }

    public void updateEmployeePosition(Employee employee, Position newPosition) {
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        double salary = employee.getSalary();
        double min = newPosition.getMinSalary();
        double max = newPosition.getMaxSalary();

        if (salary >= min && salary <= max) {
            employee.setPosition(newPosition);
        } else if (salary >= min * 0.9 && salary < min) {
            employee.setSalary(min);
            employee.setPosition(newPosition);
        } else {
            throw new InvalidSalaryException("Salary is invalid for new position");
        }
    }

    public boolean isSalaryValidForPosition(Position position, double salary) {
        if (position == null || salary < 0) {
            return false;
        }
        return salary >= position.getMinSalary() && salary <= position.getMaxSalary();
    }

}
