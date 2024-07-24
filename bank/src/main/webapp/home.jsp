<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
     <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }
        header {
            background-color: #004080;
            color: white;
            padding: 10px 0;
            text-align: center;
        }
        main {
            margin: 20px;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .account-summary {
            margin-bottom: 20px;
        }
        .actions {
            display: flex;
            gap: 10px;
           
        }
        .action {
            flex: 1;
            padding: 10px;
            background-color: #004080;
            color: white;
            text-align: center;
            border-radius: 5px;
            text-decoration: none;
        }
        .action:hover {
            background-color: #003060;
        }
        .logout {
            background-color: #d9534f;
            margin-top: 20px;
        }
        .logout:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
    <header>
        <h1>Your Bank</h1>
    </header>
     <main>
     <div class="account-summary">
    <%
        
        if (session != null) {
            String fullName = (String) session.getAttribute("fullName");
            String accountNumber = (String) session.getAttribute("accountNo");
            Double balance = (Double) session.getAttribute("balance");
            
            if (fullName != null && accountNumber != null && balance != null) {
    %>
                <h1>Welcome, <%= fullName %></h1>
                <p>Account Holder: <%= fullName %></p>
                <p>Account Number: <%= accountNumber %></p>
                <p>Balance: $<%= String.format("%.2f", balance) %></p>
    <%
            } else {
    %>
                <p>Error: User details not found.</p>
    <%
            }
        } else {
    %>
            <p>Error: No session found.</p>
    <%
        }
    %>
     </div>
     <div class="actions">
            <a href="deposit.jsp" class="action">Deposit</a>
            <a href="withdraw.jsp" class="action">Withdraw</a>
            <a href="transferfunds.jsp" class="action">Transfer Funds</a>
            <a href="transactionHistory.jsp" class="action">View Transaction History</a>
     </div>
      <a href="CloseAccountServlet" class="action logout">Close Account</a>
      <a href="LogoutServlet" class="action logout">Logout</a>
     </main>
</body>
</html>
