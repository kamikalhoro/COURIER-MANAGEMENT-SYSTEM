package controller;

import model.Employee;
import DAO.EmployeeIO;

public class LoginController {
    private final EmployeeIO employeeIO = new EmployeeIO();
    
    public boolean Authenticate (String username, String password) {
        if (username.isBlank() || password.isBlank()) {
            return false;
        }
        
        Employee User = employeeIO.findByUsername(username);
        
        if (User != null) {
            if ((User.getPassword()).equals(password)) {
                return true;
            }
        }
        
        return false;
    }
}