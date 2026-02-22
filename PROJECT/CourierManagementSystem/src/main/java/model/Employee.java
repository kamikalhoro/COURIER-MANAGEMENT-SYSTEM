package model;

import java.io.Serializable;

public class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int employeeId;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    
    public Employee (int employeeId, String username,
            String password, String fullName, String phoneNumber) {
        this.employeeId = employeeId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }
    
    // 1. Employee ID
    public int getEmployeeId() {
        return employeeId;
    }
    
    // 2. Username
    public String getUsername() {
        return username;
    }

    // 3. Password
    public String getPassword() {
        return password;
    }

    // 4. Full Name
    public String getFullName() {
        return fullName;
    }

    // 6. Phone Number
    public String getPhoneNumber() {
        return phoneNumber;
    }
  
}
