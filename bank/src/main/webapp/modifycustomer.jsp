<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="a1.Customer" %>
<%
    Customer customer = (Customer) request.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("error.jsp?message=Customer not found");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modify Customer Details</title>
    
    <style>
       .body{
       text-align:center;
       
       }
    
    </style>
</head>
<body>
    <h2>Modify Customer Details</h2>
    <form action="modifycustomerservlet" method="post">
        <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" value="<%= customer.getFullName() %>" required><br>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%= customer.getAddress() %>" required><br>

        <label for="mobileNo">Mobile No:</label>
        <input type="text" id="mobileNo" name="mobileNo" value="<%= customer.getMobileNo() %>" required><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%= customer.getEmail() %>" required><br>

        <label for="accountType">Account Type:</label>
        <select id="accountType" name="accountType" required>
            <option value="Saving" <%= customer.getAccountType().equals("Saving") ? "selected" : "" %>>Saving Account</option>
            <option value="Current" <%= customer.getAccountType().equals("Current") ? "selected" : "" %>>Current Account</option>
        </select><br>

        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" value="<%= customer.getDob() %>" required><br>

        <label for="idProof">ID Proof:</label>
        <input type="text" id="idProof" name="idProof" value="<%= customer.getIdProof() %>" required><br>

        <input type="submit" value="Update">
    </form>
    <br>
    <a href="adminpage.jsp">Back to Admin</a>
</body>
</html>
