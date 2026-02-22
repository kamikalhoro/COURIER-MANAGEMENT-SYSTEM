package controller;

import model.Employee;
import DAO.EmployeeIO;
import java.util.ArrayList;

public class SignupController {
    private final EmployeeIO employeeIO = new EmployeeIO();

    public boolean registerNewEmployee(String username, String password, 
                                     String fullName, String phoneNumber) {

        if (username.isBlank() || password.isBlank() || fullName.isBlank() || phoneNumber.isBlank()) {
            return false;
        }

        if (employeeIO.findByUsername(username) != null) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }
        
        int EmployeeID = generateID();
        
        Employee newEmployee = new Employee(EmployeeID, username, password, fullName, phoneNumber);
        employeeIO.saveOrUpdateEmployee(newEmployee); 

        return true;
    }
    
    
    public int generateID () {
        ArrayList<Employee> employees = employeeIO.getAllEmployees();
        int id = 0;
        for (Employee employee : employees) {
            if (employee.getEmployeeId() > id) {
                id+=employee.getEmployeeId();
            }
        }
        return id+1;
    }
}
