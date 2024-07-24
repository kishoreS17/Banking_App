<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Withdraw</title>
</head>
<body>
    <h1>Withdraw</h1>
    <form action="withdrawservlet" method="post">
        <label for="amount">Amount to Withdraw:</label>
        <input type="number" id="amount" name="amount" step="0.01" required>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
