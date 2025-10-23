import java.io.Serializable;

public class Employee implements Serializable {
    private String name;
    private String department;
    private double salary;

    // No-argument constructor
    public Employee() 
    {

    }

    // Getters and Setters
    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getDepartment() { 
        return department; 
    }

    public void setDepartment(String department) { 
        this.department = department; 
    }

    public double getSalary() { 
        return salary; 
    }

    public void setSalary(double salary) { 
        this.salary = salary; 
    }

    @Override
    public String toString()
    {
        return name + " works in " + department + " department with a salary of " + salary;
    }
}
