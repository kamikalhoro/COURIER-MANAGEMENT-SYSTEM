package DAO;

import model.Employee;
import java.util.ArrayList;

public class EmployeeIO {
    
    private static final String file_path = "data/employees.ser";
    private final ObjectFileIO<Employee> serializer = new ObjectFileIO<>(file_path);

    ArrayList<Employee> employees = new ArrayList<>();

    public EmployeeIO() {
        // Load data into the Program from file if initially any
        this.employees = serializer.load();
        
        if (this.employees.isEmpty()) {
            this.employees.add(new Employee(1, "test", "test", "Test_Name", "0300000090"));
            serializer.save(this.employees);
        }
    }

    public Employee findByUsername(String username) {
        for (Employee e : employees) {
            if (e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }

    public void saveOrUpdateEmployee(Employee newEmployee) {
        // Check if employee already exists by ID (specifically when updating)
        employees.removeIf(e -> e.getEmployeeId() == newEmployee.getEmployeeId());

        employees.add(newEmployee);
        
        serializer.save(employees);
    }
    
    public ArrayList<Employee> getAllEmployees() {
        return employees;
    }
    
    public void deleteEmployee (int employeeID)  {
      
            employees.removeIf (e -> e.getEmployeeId() == employeeID);
            
            serializer.save(employees);
    }
}