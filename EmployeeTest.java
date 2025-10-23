import java.util.Scanner;

public class EmployeeTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create Employee object
        Employee emp = new Employee();

        // Get input from user
        System.out.print("Enter Employee Name: ");
        emp.setName(sc.nextLine());

        System.out.print("Enter Department: ");
        emp.setDepartment(sc.nextLine());

        System.out.print("Enter Salary: ");
        emp.setSalary(sc.nextDouble());

        // Display data using getters
        System.out.println("\nEmployee Details:");
        System.out.println("Name : " + emp.getName());
        System.out.println("Department : " + emp.getDepartment());
        System.out.println("Salary : " + emp.getSalary());

        // Or simply print the object
        System.out.println("\n" + emp);

        sc.close();
    }
}