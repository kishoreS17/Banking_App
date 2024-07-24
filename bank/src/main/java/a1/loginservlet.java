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
import javax.servlet.http.HttpSession;

@WebServlet("/loginservlet")
public class loginservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (authenticateadmin(username, password)) {
            response.sendRedirect("adminpage.jsp");
        } else if (authenticatecustomers(request, username, password)) {
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("tryagain.jsp");
        }
    }

    private boolean authenticateadmin(String username, String password) {
        boolean isValid = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1", "root", "2004");
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isValid = rs.next();
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    private boolean authenticatecustomers(HttpServletRequest request, String username, String password) {
        boolean isValid = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "root", "2004");
            String sql = "SELECT fullName, accountNo, balance FROM customers WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isValid = true;
                String fullName = rs.getString("fullName");
                String accountNo = rs.getString("accountNo");
                double balance = rs.getDouble("balance");

                // Store fullName and accountNumber in session
                HttpSession session = request.getSession();
                session.setAttribute("fullName", fullName);
                session.setAttribute("accountNo", accountNo);
                session.setAttribute("balance", balance);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
