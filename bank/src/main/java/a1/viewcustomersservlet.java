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

@WebServlet("/viewcustomersservlet")
public class viewcustomersservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "root", "2004");

            String sql = "SELECT accountNo, fullName, address, mobileNo, email, accountType, dob, idProof FROM customers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder customers = new StringBuilder();
            customers.append("<table border='1'><tr><th>Account No</th><th>Full Name</th><th>Address</th><th>Mobile No</th><th>Email</th><th>Account Type</th><th>Date of Birth</th><th>ID Proof</th></tr>");
            while (rs.next()) {
                customers.append("<tr>")
                         .append("<td>").append(rs.getString("accountNo")).append("</td>")
                         .append("<td>").append(rs.getString("fullName")).append("</td>")
                         .append("<td>").append(rs.getString("address")).append("</td>")
                         .append("<td>").append(rs.getString("mobileNo")).append("</td>")
                         .append("<td>").append(rs.getString("email")).append("</td>")
                         .append("<td>").append(rs.getString("accountType")).append("</td>")
                         .append("<td>").append(rs.getDate("dob")).append("</td>")
                         .append("<td>").append(rs.getString("idProof")).append("</td>")
                         .append("</tr>");
            }
            customers.append("</table>");

            rs.close();
            stmt.close();
            conn.close();

            request.setAttribute("customers", customers.toString());
            request.getRequestDispatcher("viewcustomers.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
