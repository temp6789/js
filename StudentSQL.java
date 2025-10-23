import java.sql.*;
import java.util.Scanner;

public class StudentSQL {
    // Database connection details
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/stu"; // your DB name
    static final String USER = "root"; // your MySQL username
    static final String PASSWORD = ""; // your MySQL password (if any)

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Student Management System (MySQL + Java) ===\n");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create table if not exists
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS students (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100),
                    roll_no VARCHAR(50),
                    subject1 INT,
                    subject2 INT,
                    subject3 INT,
                    average DOUBLE
                )
            """;
            stmt.executeUpdate(createTableSQL);
            System.out.println(" Table 'students' is ready.\n");

            // Ask how many students to add
            System.out.print("Enter number of students to add: ");
            int n = Integer.parseInt(sc.nextLine());

            String insertSQL = "INSERT INTO students (name, roll_no, subject1, subject2, subject3, average) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            for (int i = 0; i < n; i++) {
                System.out.println("\nEnter details for student " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Roll Number: ");
                String rollNo = sc.nextLine();
                System.out.print("Marks for Subject 1: ");
                int s1 = Integer.parseInt(sc.nextLine());
                System.out.print("Marks for Subject 2: ");
                int s2 = Integer.parseInt(sc.nextLine());
                System.out.print("Marks for Subject 3: ");
                int s3 = Integer.parseInt(sc.nextLine());

                double avg = (s1 + s2 + s3) / 3.0;
                pstmt.setString(1, name);
                pstmt.setString(2, rollNo);
                pstmt.setInt(3, s1);
                pstmt.setInt(4, s2);
                pstmt.setInt(5, s3);
                pstmt.setDouble(6, avg);
                pstmt.executeUpdate();

                System.out.println(" Student added successfully! Average: " + String.format("%.2f", avg));
            }

            // Option to update marks
            System.out.print("\nDo you want to update any student's marks? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                System.out.print("Enter Roll Number to update: ");
                String rollNo = sc.nextLine();
                System.out.print("Enter new marks for Subject 1: ");
                int s1 = Integer.parseInt(sc.nextLine());
                System.out.print("Enter new marks for Subject 2: ");
                int s2 = Integer.parseInt(sc.nextLine());
                System.out.print("Enter new marks for Subject 3: ");
                int s3 = Integer.parseInt(sc.nextLine());
                double avg = (s1 + s2 + s3) / 3.0;

                String updateSQL = "UPDATE students SET subject1=?, subject2=?, subject3=?, average=? WHERE roll_no=?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setInt(1, s1);
                    updateStmt.setInt(2, s2);
                    updateStmt.setInt(3, s3);
                    updateStmt.setDouble(4, avg);
                    updateStmt.setString(5, rollNo);
                    int rows = updateStmt.executeUpdate();
                    if (rows > 0)
                        System.out.println(" Student marks updated successfully!");
                    else
                        System.out.println("⚠ Roll number not found!");
                }
            }

            // Display all records
            String selectSQL = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.println("\n Student Records:");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-5s %-15s %-10s %-8s %-8s %-8s %-8s%n",
                    "ID", "Name", "Roll No", "Sub1", "Sub2", "Sub3", "Avg");
            System.out.println("----------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10s %-8d %-8d %-8d %-8.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getInt("subject1"),
                        rs.getInt("subject2"),
                        rs.getInt("subject3"),
                        rs.getDouble("average"));
            }
            System.out.println("----------------------------------------------------------------");

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
