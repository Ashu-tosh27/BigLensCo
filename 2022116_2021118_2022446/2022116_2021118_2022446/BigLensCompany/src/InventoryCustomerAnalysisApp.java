import java.sql.*;

public class InventoryCustomerAnalysisApp {
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
            stmt = conn.createStatement();

            // Example: Inventory or Customer Analysis
            String sql = "SELECT COUNT(*) AS totalProducts FROM product";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int totalProducts = rs.getInt("totalProducts");
                System.out.println("Total Products in Inventory: " + totalProducts);
            }
            rs.close();

            sql = "SELECT COUNT(*) AS totalCustomers FROM users";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int totalCustomers = rs.getInt("totalCustomers");
                System.out.println("Total Customers: " + totalCustomers);
            }
            rs.close();

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
