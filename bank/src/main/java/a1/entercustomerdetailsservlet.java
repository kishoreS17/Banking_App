package a1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/entercustomerdetailsservlet")
public class entercustomerdetailsservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");

        String dbUrl = "jdbc:mysql://localhost:3306/db2";
        String dbUser = "root";
        String dbPassword = "2004";

        String sql = "SELECT accountNo, fullName, address, mobileNo, email, accountType, dob, idProof FROM customers WHERE accountNo = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Set the value for the PreparedStatement
            stmt.setString(1, accountNo);

            // Execute the SQL statement and get the result set
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Create a customer object to hold the details
                Customer customer = new Customer();
                customer.setAccountNo(rs.getString("accountNo"));
                customer.setFullName(rs.getString("fullName"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobileNo"));
                customer.setEmail(rs.getString("email"));
                customer.setAccountType(rs.getString("accountType"));
                customer.setDob(rs.getString("dob"));
                customer.setIdProof(rs.getString("idProof"));

                // Set the customer object as a request attribute and forward to modify page
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("modifycustomer.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.jsp?message=Customer not found");
            }
        } catch (ClassNotFoundException e) {
            log("JDBC Driver not found", e);
            response.sendRedirect("error.jsp?message=JDBC Driver not found");
        } catch (SQLException e) {
            log("Database error", e);
            response.sendRedirect("error.jsp?message=Database error: " + e.getMessage());
        } catch (Exception e) {
            log("Unexpected error", e);
            response.sendRedirect("error.jsp?message=Unexpected error: " + e.getMessage());
        }
    }
}
