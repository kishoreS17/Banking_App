package a1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/modifycustomerservlet")
public class modifycustomerservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String email = request.getParameter("email");
        String accountType = request.getParameter("accountType");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("idProof");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "root", "2004");

            String sql = "UPDATE customers SET fullName = ?, address = ?, mobileNo = ?, email = ?, accountType = ?, dob = ?, idProof = ? WHERE accountNo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, address);
            stmt.setString(3, mobileNo);
            stmt.setString(4, email);
            stmt.setString(5, accountType);
            stmt.setString(6, dob);
            stmt.setString(7, idProof);
            stmt.setString(8, accountNo);

            stmt.executeUpdate();

            response.sendRedirect("entercustomerdetails.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message= error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
