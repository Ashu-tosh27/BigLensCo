import java.sql.*;
import java.util.*;

public class ManageProduct {
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

            // Menu for managing products
            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nProduct Management Menu");
                System.out.println("1. Add a Product");
                System.out.println("2. Update a Product");
                System.out.println("3. Display Products");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a new product
                        addProduct(conn);
                        break;
                    case 2:
                        // Update a product
                        updateProduct(conn);
                        break;
                    case 3:
                        // Display products
                        displayProducts(conn);
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

    public static void addProduct(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Frame Material:");
        String frameMaterial = scanner.nextLine();

        System.out.println("Enter Lens Type:");
        String lensType = scanner.nextLine();

        System.out.println("Enter Power:");
        double power = scanner.nextDouble();

        System.out.println("Enter Stock Availability :");
        int stockAvailable = scanner.nextInt();

        scanner.nextLine(); // Consume newline character

        System.out.println("Enter Product Name:");
        String productName = scanner.nextLine();

        System.out.println("Enter Rated:");
        int rated = scanner.nextInt();

        String sql = "INSERT INTO product (frame_material, lens_type, power, stock_available, product_name, rated) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, frameMaterial);
        pstmt.setString(2, lensType);
        pstmt.setDouble(3, power);
        pstmt.setInt(4, stockAvailable);
        pstmt.setString(5, productName);
        pstmt.setInt(6, rated);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Product added successfully!");
    }

    public static void updateProduct(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the product to update:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.println("Which detail do you want to update?");
        System.out.println("1. Frame Material");
        System.out.println("2. Lens Type");
        System.out.println("3. Power");
        System.out.println("4. Stock Availability");
        System.out.println("5. Product Name");
        System.out.println("6. Rated");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String column = "";
        switch (choice) {
            case 1:
                column = "frame_material";
                break;
            case 2:
                column = "lens_type";
                break;
            case 3:
                column = "power";
                break;
            case 4:
                column = "stock_available";
                break;
            case 5:
                column = "product_name";
                break;
            case 6:
                column = "rated";
                break;
            default:
                System.out.println("Invalid choice. Aborting update.");
                return;
        }

        System.out.println("Enter the new value:");
        String newValue = scanner.nextLine();

        String sql = "UPDATE product SET " + column + " = ? WHERE product_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newValue);
        pstmt.setInt(2, productId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Product updated successfully!");
    }

    public static void displayProducts(Connection conn) throws SQLException {
        System.out.println("List of Products:");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-5s %-20s   %-15s %-15s %-15s %-10s %-20s%n",
                "ID", "Product Name", "Frame Material", "Lens Type", "Power", "Rated", "Stock Available");
        System.out.println("-----------------------------------------------------");
        String sql = "SELECT * FROM product";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int productId = rs.getInt("product_id");
            String productName = rs.getString("product_name");
            String frameMaterial = rs.getString("frame_material");
            String lensType = rs.getString("lens_type");
            double power = rs.getDouble("power");
            int rated = rs.getInt("rated");
            int stockAvailable = rs.getInt("stock_available");
            System.out.printf("%-5s %-20s %-15s %-15s %-15s %-10s %-20s%n",
                    productId, productName, frameMaterial, lensType, power, rated, stockAvailable);
        }
        System.out.println("-----------------------------------------------------");
        rs.close();
        stmt.close();
    }
}
