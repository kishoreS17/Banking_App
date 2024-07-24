package a1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registercustomerservlet")
public class registercustomerservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String email = request.getParameter("email");
        String accountType = request.getParameter("accountType");
        String initialBalance = request.getParameter("initialBalance");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("idProof");

        // Validate the input parameters
        if (fullName == null || address == null || mobileNo == null || email == null ||
            accountType == null || initialBalance == null || dob == null || idProof == null ||
            fullName.isEmpty() || address.isEmpty() || mobileNo.isEmpty() || email.isEmpty() ||
            accountType.isEmpty() || initialBalance.isEmpty() || dob.isEmpty() || idProof.isEmpty()) {
            response.sendRedirect("error.jsp?message=Invalid input parameters");
            return;
        }

        // Generate unique account number and temporary password
        String accountNo = generateNumericAccountNumber(12);
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        String username = generateUsername(fullName, mobileNo);
        
        String balance = generateBalance(initialBalance);

        String dbUrl = "jdbc:mysql://localhost:3306/db2";
        String dbUser = "root";
        String dbPassword = "2004";

        String sql = "INSERT INTO customers (accountNo, fullName, address, mobileNo, email, accountType, initialBalance, dob, idProof, password, username, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Set the values for the PreparedStatement
            stmt.setString(1, accountNo);
            stmt.setString(2, fullName);
            stmt.setString(3, address);
            stmt.setString(4, mobileNo);
            stmt.setString(5, email);
            stmt.setString(6, accountType);
            stmt.setString(7, initialBalance);
            stmt.setString(8, dob);
            stmt.setString(9, idProof);
            stmt.setString(10, tempPassword);
            stmt.setString(11, username);
            stmt.setString(12, balance);

            // Execute the SQL statement
            stmt.executeUpdate();

            // Pass the account number and temporary password to the success page
            request.setAttribute("accountNo", accountNo);
            request.setAttribute("tempPassword", tempPassword);
            request.setAttribute("username", username);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Database error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Unexpected error: " + e.getMessage());
        }

    }

    private static String generateNumericAccountNumber(int length) {
        Random random = new Random();
        StringBuilder accountNo = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generates a random number between 0 and 9
            accountNo.append(digit);
        }

        return accountNo.toString();
    }

    private static String generateUsername(String fullName, String mobileNo) {
        String[] nameParts = fullName.split(" ");
        String namePart = nameParts[0]; // Take the first part of the name
        String mobilePart = mobileNo.length() >= 4 ? mobileNo.substring(mobileNo.length() - 4) : mobileNo;
        return namePart + mobilePart;
    }
    private static String generateBalance(String initialBalance) {
        return initialBalance;
    }
}
