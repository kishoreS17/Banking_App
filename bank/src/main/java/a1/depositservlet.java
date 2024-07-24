package a1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/depositservlet")
public class depositservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("first.jsp");
            return;
        }

        String accountNumber = (String) session.getAttribute("accountNo");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load your database driver and establish a connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "root", "2004");

            // Update balance
            pstmt = conn.prepareStatement("UPDATE customers SET balance = balance + ? WHERE accountNo = ?");
            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();

            // Record the transaction
            pstmt = conn.prepareStatement("INSERT INTO transactions (account_number, type, amount, balance_after) VALUES (?, 'DEPOSIT', ?, (SELECT balance FROM customers WHERE accountNo = ?))");
            pstmt.setString(1, accountNumber);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, accountNumber);
            pstmt.executeUpdate();

            // Update session balance
            session.setAttribute("balance", (Double) session.getAttribute("balance") + amount);

            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
