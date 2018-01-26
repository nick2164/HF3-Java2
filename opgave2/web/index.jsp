<%--
  Created by IntelliJ IDEA.
  User: HF
  Date: 09/01/2018
  Time: 09.47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information</title>
</head>
<body>
    <section class="container">
        <div class="login">
            <h1>Login</h1>
            <form method="post" action="login">
                <p><input type="text" name="FirstName" value="" placeholder="First name"></p>
                <p><input type="text" name="PhoneNumber" value="" placeholder="PhoneNumber"></p>
                <p class="submit"><input type="submit" name="commit" value="Login"></p>
            </form>
            <form method="get" action="login">
                <h3>Psst.. Don't you know any passwords?</h3>
                <p>Try one of theses! :D</p>
                <p class="submit"><input type="submit" name="commit" value="Use backdoor"></p>
            </form>
        </div>
    </section>
</body>
</html>
