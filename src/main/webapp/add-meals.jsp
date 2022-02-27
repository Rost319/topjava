<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.02.2022
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add</title>
</head>
<body>
<h2>Добавить еду</h2> <br>


<br>
<form method="post" action="<c:url value='/add_meal'/>">

    <label> Дата/Время <input type="datetime-local" name="dateTime"></label><br>

    <label> Описание <input type="text" name="description"></label><br>

    <label> Калории <input type="number" name="calories"></label><br>

    <input type="submit" value="Ok" name="Ok"><br>

</form>

</body>
</html>
