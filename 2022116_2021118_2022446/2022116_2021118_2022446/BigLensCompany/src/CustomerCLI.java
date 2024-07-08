import java.sql.*;
import java.util.Scanner;

public class CustomerCLI {
    public static void main(String[] args) {

        // JDBC driver name, database URL, username, and password
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/BigLensCompany";
        String USER = "root";
        String PASS = "YES";

        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            ResultSet rs = null;
            int choice = 0;
            Scanner scanner = new Scanner(System.in);

            do {
                System.out.println("\nWelcome to BigLensCompany");
                System.out.println("Login Role");
                System.out.println("1. Admin");
                System.out.println("2. User");
                System.out.println("3. Exit");
                System.out.println("Enter choice");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Admin Login
                        System.out.print("Enter Email(Admin): ");
                        String adminEmail = scanner.next();
                        System.out.print("Enter Password: ");
                        String adminPassword = scanner.next();

                        // Check credentials
                        sql = "SELECT * FROM admin WHERE admin_email = ? AND admin_password = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, adminEmail);
                        pstmt.setString(2, adminPassword);
                        rs = pstmt.executeQuery();

                        if (rs.next()) {
                            String adminUsername = rs.getString("admin_first_name");
                            System.out.println("Welcome " + adminUsername);
                            System.out.println("\nMain Menu");

                            do {
                                System.out.println("1. Manage Lens");
                                System.out.println("2. Manage User");
                                System.out.println("3. Manage Supplier");
                                System.out.println("4. Order Items");
                                System.out.println("5. Inventory Analysis");
                                System.out.println("6. Exit");
                                System.out.print("Enter your choice: ");
                                choice = scanner.nextInt();

                                switch (choice) {
                                    case 1:
                                        ManageProduct.main(args);
                                        // Add your code here for managing lenses
                                        break;
                                    case 2:
                                        ManageUser.main(args);
                                        // Add your code here for managing users
                                        break;
                                    case 3:
                                        Supplier.main(args);
                                        // Add your code here for managing suppliers
                                        break;
                                    case 4:
                                        OrderItemsApp.main(args); // Redirect to OrderItemsApp class
                                        break;
                                    case 5:
                                        InventoryCustomerAnalysisApp.main(args); // Redirect to InventoryCustomerAnalysisApp class
                                        break;
                                    case 6:
                                        // Exit
                                        System.out.println("Exiting...");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } while (choice != 6);
                        } else {
                            System.out.println("Invalid email or password. Please try again.");
                        }

                        rs.close();
                        pstmt.close();
                        break;
                    case 2:

                        // User Login
                        System.out.print("Enter Email(User): ");
                        String userEmail = scanner.next();
                        System.out.print("Enter Password: ");
                        String userPassword = scanner.next();

                        // Check credentials
                        sql = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
                        PreparedStatement pstmt2 = conn.prepareStatement(sql);
                        pstmt2.setString(1, userEmail);
                        pstmt2.setString(2, userPassword);
                        rs = pstmt2.executeQuery();

                        if (rs.next()) {
                            String adminUsername = rs.getString("user_first_name");
                            System.out.println("Welcome " + adminUsername);
                            System.out.println("\nMain Menu");

                            do {

                                System.out.println("1. Order Items");
                                System.out.println("2. Inventory Analysis");
                                System.out.println("3. Exit");
                                System.out.print("Enter your choice: ");
                                choice = scanner.nextInt();

                                switch (choice) {

                                    case 1:
                                        OrderItemsApp.main(args); // Redirect to OrderItemsApp class
                                        break;
                                    case 2:
                                        InventoryCustomerAnalysisApp.main(args); // Redirect to InventoryCustomerAnalysisApp class
                                        break;
                                    case 3:


                                        System.out.println("Exiting...");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } while (choice != 3);
                        } else {
                            System.out.println("Invalid email or password. Please try again.");
                        }

                        rs.close();
                        pstmt2.close();
                        break;
                    case 3:
                        // Exit
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 3);
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
}
