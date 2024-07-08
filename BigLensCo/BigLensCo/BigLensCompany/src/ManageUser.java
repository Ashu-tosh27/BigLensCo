import java.sql.*;
import java.util.*;

public class ManageUser {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/BigLensCompany";
    static final String USER = "root";
    static final String PASS = "YES";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Menu for managing users
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nUser Management Menu");
                System.out.println("1. Add a User");
                System.out.println("2. Update a User");
                System.out.println("3. Display Users");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a new user
                        addUser(conn);
                        break;
                    case 2:
                        // Update a user
                        updateUser(conn);
                        break;
                    case 3:
                        // Display users
                        displayUsers(conn);
                        break;
                    case 4:
                        // Exit
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 4);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void addUser(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Middle Name:");
        String middleName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Email:");
        String email = scanner.nextLine();

        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        String sql = "INSERT INTO users (user_first_name, user_middle_name, user_last_name, user_address, user_email, user_password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, firstName);
        pstmt.setString(2, middleName);
        pstmt.setString(3, lastName);
        pstmt.setString(4, address);
        pstmt.setString(5, email);
        pstmt.setString(6, password);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("User added successfully!");
    }

    public static void updateUser(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the user to update:");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.println("Which detail do you want to update?");
        System.out.println("1. First Name");
        System.out.println("2. Middle Name");
        System.out.println("3. Last Name");
        System.out.println("4. Address");
        System.out.println("5. Email");
        System.out.println("6. Password");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String column = "";
        switch (choice) {
            case 1:
                column = "user_first_name";
                break;
            case 2:
                column = "user_middle_name";
                break;
            case 3:
                column = "user_last_name";
                break;
            case 4:
                column = "user_address";
                break;
            case 5:
                column = "user_email";
                break;
            case 6:
                column = "user_password";
                break;
            default:
                System.out.println("Invalid choice. Aborting update.");
                return;
        }

        System.out.println("Enter the new value:");
        String newValue = scanner.nextLine();

        String sql = "UPDATE users SET " + column + " = ? WHERE user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newValue);
        pstmt.setInt(2, userId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("User updated successfully!");
    }

    public static void displayUsers(Connection conn) throws SQLException {
        System.out.println("List of Users:");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-5s %-15s %-15s %-15s %-30s %-20s%n", "ID", "First Name", "Middle Name", "Last Name", "Email", "Address");
        System.out.println("-----------------------------------------------------");
        String sql = "SELECT * FROM users";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            String firstName = rs.getString("user_first_name");
            String middleName = rs.getString("user_middle_name");
            String lastName = rs.getString("user_last_name");
            String address = rs.getString("user_address");
            String email = rs.getString("user_email");
            System.out.printf("%-5s %-15s %-15s %-15s %-30s %-20s%n", userId, firstName, middleName, lastName, email, address);
        }
        System.out.println("-----------------------------------------------------");
        rs.close();
        stmt.close();
    }
}
