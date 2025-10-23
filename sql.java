import java.sql.*;
import java.util.Scanner;

public class sql {
    // Database credentials
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/sample";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Java MySQL Interactive Program ===\n");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create table if not exists
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS employees (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100),
                    position VARCHAR(100),
                    salary DOUBLE
                )
            """;
            stmt.executeUpdate(createTableSQL);
            System.out.println(" Table 'employees' is ready.\n");

            // Ask user how many employees to add
            System.out.print("Enter number of employees to add: ");
            int n = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < n; i++) {
                System.out.println("\nEnter details for employee " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Position: ");
                String position = sc.nextLine();
                System.out.print("Salary: ");
                double salary = Double.parseDouble(sc.nextLine());

                String insertSQL = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, position);
                    pstmt.setDouble(3, salary);
                    pstmt.executeUpdate();
                    System.out.println(" Employee added successfully!");
                }
            }

            // Update employee salary interactively
            System.out.print("\nDo you want to update any employee salary? (yes/no): ");
            String updateChoice = sc.nextLine().trim().toLowerCase();
            if (updateChoice.equals("yes")) {
                System.out.print("Enter employee name to update: ");
                String empName = sc.nextLine();
                System.out.print("Enter new salary: ");
                double newSalary = Double.parseDouble(sc.nextLine());

                String updateSQL = "UPDATE employees SET salary = ? WHERE name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                    pstmt.setDouble(1, newSalary);
                    pstmt.setString(2, empName);
                    int rows = pstmt.executeUpdate();
                    if (rows > 0)
                        System.out.println(" Salary updated successfully!");
                    else
                        System.out.println("⚠ Employee not found!");
                }
            }

            // Display all employee records
            String selectSQL = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.println("\n Employee Records:");
            System.out.println("-------------------------------------------------");
            System.out.printf("%-5s %-15s %-15s %-10s%n", "ID", "Name", "Position", "Salary");
            System.out.println("-------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-15s $%-10.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
            }
            System.out.println("-------------------------------------------------");

        } catch (SQLException e) {
            System.out.println(" Database error occurred!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(" Invalid input!");
            e.printStackTrace();
        }

        System.out.println("\n=== Program Finished ===");
        sc.close();
    }
}