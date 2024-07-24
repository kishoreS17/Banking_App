<!DOCTYPE html>
<html>
<head>
    <title>Delete Customer</title>
    <style>
      .body{
      text-align:center;
      }
    </style>
</head>
<body>
    <h1>Delete Customer</h1>
    <form action="deletecustomerservlet" method="post">
        <label for="accountNo">Account Number:</label>
        <input type="text" id="accountNo" name="accountNo" required><br><br>
        
        <button type="submit">Delete Customer</button>
    </form>
    <br>
    <a href="adminpage.jsp">Back to Admin</a>
</body>
</html>
