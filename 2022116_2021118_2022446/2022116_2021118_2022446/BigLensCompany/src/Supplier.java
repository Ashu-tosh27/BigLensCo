import java.sql.*;
import java.util.Scanner;

public class Supplier {
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

            // Menu for managing suppliers
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nSupplier Management Menu");
                System.out.println("1. Add a Supplier");
                System.out.println("2. Display Suppliers");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a new supplier
                        addSupplier(conn);
                        break;
                    case 2:
                        // Display suppliers
                        displaySuppliers(conn);
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

    public static void addSupplier(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Supplier First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Supplier Middle Name:");
        String middleName = scanner.nextLine();

        System.out.println("Enter Supplier Last Name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter Supplier Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Supplier Email:");
        String email = scanner.nextLine();

        System.out.println("Enter Product ID:");
        int productId = scanner.nextInt();

        String sql = "INSERT INTO supplier (sup_first_name, sup_middle_name, sup_last_name, sup_address, sup_email, product_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, firstName);
        pstmt.setString(2, middleName);
        pstmt.setString(3, lastName);
        pstmt.setString(4, address);
        pstmt.setString(5, email);
        pstmt.setInt(6, productId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Supplier added successfully!");
    }

    public static void displaySuppliers(Connection conn) throws SQLException {
        System.out.println("List of Suppliers:");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-20s %-15s %-15s %-30s%n",
                "Supplier Name", "Address", "Email", "Product ID");
        System.out.println("-----------------------------------------------------");
        String sql = "SELECT * FROM supplier";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String supName = rs.getString("sup_first_name") + " " + rs.getString("sup_middle_name") + " " + rs.getString("sup_last_name");
            String supAddress = rs.getString("sup_address");
            String supEmail = rs.getString("sup_email");
            int productId = rs.getInt("product_id");
            System.out.printf("%-20s %-15s %-15s %-30s%n",
                    supName, supAddress, supEmail, productId);
        }
        System.out.println("-----------------------------------------------------");
        rs.close();
        stmt.close();
    }
}
