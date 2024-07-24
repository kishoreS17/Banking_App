<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Customer Management</title>
    <script type="text/javascript">
        function validateForm() {
            var initialBalance = document.forms["customerForm"]["initialBalance"].value;
            if (initialBalance < 1000) {
                alert("Initial balance must be at least 1000");
                return false;
            }
            return true;
        }
    </script>
    <style>
        body {
        text-align:center;
          
        } </style>
</head>
<body>
    <h1>Admin - Customer Management</h1>

    <!-- Form to Register a New Customer -->
    <h2>Register New Customer</h2>
    <form name="customerForm" action="registercustomerservlet" method="post" onsubmit="return validateForm();">
        <label for="fullName">Full Name:</label><br>
        <input type="text" id="fullName" name="fullName" required><br><br>

        <label for="address">Address:</label><br>
        <input type="text" id="address" name="address" required><br><br>

        <label for="mobileNo">Mobile No:</label><br>
        <input type="text" id="mobileNo" name="mobileNo" required pattern="\d{10}"><br><br>

        <label for="email">Email ID:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="accountType">Type of Account:</label><br>
        <select id="accountType" name="accountType" required>
            <option value="Saving">Saving Account</option>
            <option value="Current">Current Account</option>
        </select><br><br>

        <label for="initialBalance">Initial Balance:</label><br>
        <input type="number" id="initialBalance" name="initialBalance" required min="1000"><br><br>

        <label for="dob">Date of Birth:</label><br>
        <input type="date" id="dob" name="dob" required><br><br>

        <label for="idProof">ID Proof:</label><br>
        <input type="text" id="idProof" name="idProof" required><br><br>

        <input type="submit" value="Register">
    </form>

    <!-- Links to Perform Other Operations -->
    <h2>Manage Customers</h2>
    <a href="viewcustomersservlet">View All Customers</a><br>
    <a href="entercustomerdetails.jsp">Modify Customer</a><br>
    <a href="deletecustomer.jsp">Delete Customer</a><br>

</body>
</html>
