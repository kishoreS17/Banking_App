<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Deposit</title>
</head>
<body>
    <h1>Deposit</h1>
    <form action="depositservlet" method="post">
        <label for="amount">Amount to Deposit:</label>
        <input type="number" id="amount" name="amount" step="0.01" required>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
