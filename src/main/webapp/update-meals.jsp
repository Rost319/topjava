<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.02.2022
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<h2>Редактировать еду</h2> <br>


<br>
<form method="post" action="meals">

    <input type="number" hidden name="id" value="<%= request.getParameter("mealId") %>">

    <label><input type="datetime-local" name="dateTime"></label><br>

    <label><input type="text" name="description"></label><br>

    <label><input type="number" name="calories"></label><br>

    <input type="submit" value="Ok" name="Ok"><br>

</form>



</body>
</html>
