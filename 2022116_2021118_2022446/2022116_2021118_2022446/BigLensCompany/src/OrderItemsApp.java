import java.util.Scanner;
import java.sql.*;

public class OrderItemsApp {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/BigLensCompany";
    static final String USER = "root";
    static final String PASS = "YES";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        Scanner scanner = new Scanner(System.in);

        boolean returnToMainMenu = true;

        while (returnToMainMenu) {
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // Display available items for ordering
                System.out.println("Available Items for Ordering:");
                System.out.println("-----------------------------------------------------");
                System.out.printf("%-10s %-20s %-15s%n", "Product ID", "Product Name", "Quantity Available");
                System.out.println("-----------------------------------------------------");

                stmt = conn.createStatement();
                String query = "SELECT product_id, product_name, stock_available FROM product WHERE stock_available > 0 ORDER BY product_id";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String productName = rs.getString("product_name");
                    int quantityAvailable = rs.getInt("stock_available");
                    System.out.printf("%-10s %-20s %-15s%n", productId, productName, quantityAvailable);
                }
                rs.close();

                // Order process
                System.out.println("\nEnter the product ID you want to order:");
                int productIdInput = scanner.nextInt();

                System.out.println("Enter the quantity you want to order:");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Update database with the order
                String updateQuery = "UPDATE product SET stock_available = stock_available - ? WHERE product_id = ?";
                pstmt = conn.prepareStatement(updateQuery);
                pstmt.setInt(1, quantity);
                pstmt.setInt(2, productIdInput);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Order placed successfully!");
                } else {
                    System.out.println("Failed to place order. Product may be out of stock or not found.");
                }

                // Ask if the user wants to go back to the main menu or exit
                System.out.println("\nDo you want to go back to the main menu? (yes/no)");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("yes")) {
                    returnToMainMenu = false;
                }

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
}
