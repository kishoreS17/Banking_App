package a1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/transferfundsservlet")
public class transferfundsservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db2/customers";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2004";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String accountNumber = request.getParameter("accountNumber");
        String amountStr = request.getParameter("amount");
        String password = request.getParameter("password");

        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid amount.");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load database driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check password (simple example, should use proper security)
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE password = ?");
            pstmt.setString(1, password);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Invalid password.");
                return;
            }

            // Perform the transfer
            // Deduct amount from the user's account
            pstmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
            pstmt.setDouble(1, amount);
            pstmt.setString(2, "your-account-number"); // Replace with the logged-in user's account number
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                response.getWriter().println("Transfer failed. Insufficient funds or account not found.");
                return;
            }

            // Add amount to the recipient's account
            pstmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();

            response.getWriter().println("Transfer successful.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
