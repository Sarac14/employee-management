package edu.pucmm;


import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author me@fredpena.dev
 * @created 02/06/2024  - 00:47
 */

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private Position juniorDeveloper;
    private Position seniorDeveloper;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setUp() {
        employeeManager = new EmployeeManager();
        juniorDeveloper = new Position("1", "Junior Developer", 30000, 50000);
        seniorDeveloper = new Position("2", "Senior Developer", 60000, 90000);
        employee1 = new Employee("1", "John Doe", juniorDeveloper, 40000);
        employee2 = new Employee("2", "Jane Smith", seniorDeveloper, 70000);
        employeeManager.addEmployee(employee1);
    }

    @Test
    public void testAddEmployee() {
        // TODO: Agregar employee2 al employeeManager y verificar que se agregó correctamente.
        // - Verificar que el número total de empleados ahora es 2.
        // - Verificar que employee2 está en la lista de empleados.
        employeeManager.addEmployee(employee2);
        Assertions.assertEquals(2, employeeManager.getEmployees().size());
        assertTrue(employeeManager.getEmployees().contains(employee2));
    }

    @Test
    public void testRemoveEmployee() {
        // TODO: Eliminar employee1 del employeeManager y verificar que se eliminó correctamente.
        // - Agregar employee2 al employeeManager.
        // - Eliminar employee1 del employeeManager.
        // - Verificar que el número total de empleados ahora es 1.
        // - Verificar que employee1 ya no está en la lista de empleados.
        employeeManager.addEmployee(employee2);
        employeeManager.removeEmployee(employee1);
        Assertions.assertEquals(1, employeeManager.getEmployees().size());
        Assertions.assertFalse(employeeManager.getEmployees().contains(employee1));
    }

    @Test
    public void testCalculateTotalSalary() {
        // TODO: Agregar employee2 al employeeManager y verificar el cálculo del salario total.
        // - Agregar employee2 al employeeManager.
        // - Verificar que el salario total es la suma de los salarios de employee1 y employee2.
        employeeManager.addEmployee(employee2);
        double totalSalary = employeeManager.calculateTotalSalary();
        Assertions.assertEquals(employee1.getSalary() + employee2.getSalary(), totalSalary);
    }

    @Test
    public void testUpdateEmployeeSalaryValid() {
        // TODO: Actualizar el salario de employee1 a una cantidad válida y verificar la actualización.
        // - Actualizar el salario de employee1 a 45000.
        // - Verificar que el salario de employee1 ahora es 45000.
        employeeManager.updateEmployeeSalary(employee1, 45000);
        Assertions.assertEquals(45000, employee1.getSalary());
    }

    @Test
    public void testUpdateEmployeeSalaryInvalid() {
        // TODO: Intentar actualizar el salario de employee1 a una cantidad inválida y verificar la excepción.
        // - Intentar actualizar el salario de employee1 a 60000 (que está fuera del rango para Junior Developer).
        // - Verificar que se lanza una InvalidSalaryException.
        assertThrows(InvalidSalaryException.class, () -> {
            employeeManager.updateEmployeeSalary(employee1, 60000);
        });
    }

    @Test
    public void testUpdateEmployeeSalaryEmployeeNotFound() {
        // TODO: Intentar actualizar el salario de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar el salario de employee2 a 70000.
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeManager.updateEmployeeSalary(employee2, 70000);
        });
    }

    @Test
    public void testUpdateEmployeePositionValid() {
        // TODO: Actualizar la posición de employee2 a una posición válida y verificar la actualización.
        // - Agregar employee2 al employeeManager.
        // - Actualizar la posición de employee2 a seniorDeveloper.
        // - Verificar que la posición de employee2 ahora es seniorDeveloper.
        employeeManager.addEmployee(employee2);
        employeeManager.updateEmployeePosition(employee2, seniorDeveloper);
        Assertions.assertEquals(seniorDeveloper, employee2.getPosition());
    }

    @Test
    public void testUpdateEmployeePositionInvalidDueToSalary() {
        // TODO: Intentar actualizar la posición de employee1 a seniorDeveloper y verificar la excepción.
        // - Intentar actualizar la posición de employee1 a seniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException porque el salario de employee1 no está dentro del rango para Senior Developer.
        assertThrows(InvalidSalaryException.class, () -> {
            employeeManager.updateEmployeePosition(employee1, seniorDeveloper);
        });
    }

    @Test
    public void testUpdateEmployeePositionEmployeeNotFound() {
        // TODO: Intentar actualizar la posición de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar la posición de employee2 a juniorDeveloper.
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeManager.updateEmployeePosition(employee2, juniorDeveloper);
        });
    }

    @Test
    public void testIsSalaryValidForPosition() {
        // TODO: Verificar la lógica de validación de salario para diferentes posiciones.
        // - Verificar que un salario de 40000 es válido para juniorDeveloper.
        // - Verificar que un salario de 60000 no es válido para juniorDeveloper.
        // - Verificar que un salario de 70000 es válido para seniorDeveloper.
        // - Verificar que un salario de 50000 no es válido para seniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(juniorDeveloper, 40000));
        Assertions.assertFalse(employeeManager.isSalaryValidForPosition(juniorDeveloper, 60000));
        assertTrue(employeeManager.isSalaryValidForPosition(seniorDeveloper, 70000));
        Assertions.assertFalse(employeeManager.isSalaryValidForPosition(seniorDeveloper, 50000));
    }

    @Test
    public void testAddEmployeeWithInvalidSalary() {
        // TODO: Intentar agregar empleados con salarios inválidos y verificar las excepciones.
        // - Crear un empleado con un salario de 60000 para juniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        // - Crear otro empleado con un salario de 40000 para seniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        Employee invalid1 = new Employee("3", "Invalid Junior", juniorDeveloper, 60000);
        Employee invalid2 = new Employee("4", "Invalid Senior", seniorDeveloper, 40000);

        assertThrows(InvalidSalaryException.class, () -> employeeManager.addEmployee(invalid1));
        assertThrows(InvalidSalaryException.class, () -> employeeManager.addEmployee(invalid2));
    }

    @Test
    public void testRemoveExistentEmployee() {
        // TODO: Eliminar un empleado existente y verificar que no se lanza una excepción.
        // - Eliminar employee1 del employeeManager.
        // - Verificar que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> employeeManager.removeEmployee(employee1));
        Assertions.assertFalse(employeeManager.getEmployees().contains(employee1));
    }

    @Test
    public void testRemoveNonExistentEmployee() {
        // TODO: Intentar eliminar un empleado no existente y verificar la excepción.
        // - Intentar eliminar employee2 (no agregado al manager).
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> employeeManager.removeEmployee(employee2));
    }

    @Test
    public void testAddDuplicateEmployee() {
        // TODO: Intentar agregar un empleado duplicado y verificar la excepción.
        // - Intentar agregar employee1 nuevamente al employeeManager.
        // - Verificar que se lanza una DuplicateEmployeeException.
        assertThrows(DuplicateEmployeeException.class, () -> employeeManager.addEmployee(employee1));

    }

    @ParameterizedTest
    @CsvSource({
            "30000, true",
            "50000, true",
            "29999, false",
            "50001, false",
            "-1000, false"
    })
    public void testIsSalaryValidForJuniorDeveloper(double salary, boolean expected) {
        Assertions.assertEquals(expected, employeeManager.isSalaryValidForPosition(juniorDeveloper, salary));
    }

    /// VERIFICANDO REGLAS DE NEGOCIOS
    @Test
    public void testAddDuplicateEmployeeByName() {
        // Empleado duplicado (ya existe un empleado con el mismo nombre)
        Employee duplicateName = new Employee("3", "John Doe", seniorDeveloper, 70000);
        assertThrows(DuplicateEmployeeException.class, () -> employeeManager.addEmployee(duplicateName));
    }

    @Test
    public void testIsSalaryValidForPositionWithInvalidInputs() {
        // Posicion nula y salario negativo
        Assertions.assertFalse(employeeManager.isSalaryValidForPosition(null, 50000));
        Assertions.assertFalse(employeeManager.isSalaryValidForPosition(juniorDeveloper, -1000));
    }

    @Test
    public void testAddEmployeeWithSalaryBelow10Percent() {
        // Salrio por debajo del 10% minimo
        double invalidSalary = juniorDeveloper.getMinSalary() * 0.89;
        Employee invalidEmployee = new Employee("3", "Sara", juniorDeveloper, invalidSalary);
        assertThrows(InvalidSalaryException.class, () -> employeeManager.addEmployee(invalidEmployee));
    }

    @Test
    public void testUpdatePositionWithSalaryWithin10PercentRange() {
        // Ajuste automático si salario esta dentro del 10% del mínimo al cambiar de posicion

        Employee lowSalaryEmployee = new Employee("3", "Carlos", juniorDeveloper, 45000);
        employeeManager.addEmployee(lowSalaryEmployee);
        lowSalaryEmployee.setSalary(seniorDeveloper.getMinSalary() * 0.91);

        employeeManager.updateEmployeePosition(lowSalaryEmployee, seniorDeveloper);

        Assertions.assertEquals(seniorDeveloper, lowSalaryEmployee.getPosition());
        Assertions.assertEquals(seniorDeveloper.getMinSalary(), lowSalaryEmployee.getSalary());
    }



    @Test
    public void testUpdatePositionFailsIfSalaryTooLow() {
        // Cambio de posición lanza exepcion por salario fuera del 10%
        double veryLowSalary = seniorDeveloper.getMinSalary() * 0.5;
        Employee lowSalaryEmployee = new Employee("3", "Brigibel", juniorDeveloper, veryLowSalary);
        employeeManager.addEmployee(lowSalaryEmployee);

        assertThrows(InvalidSalaryException.class, () -> employeeManager.updateEmployeePosition(lowSalaryEmployee, seniorDeveloper));
    }



}
