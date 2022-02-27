<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.02.2022
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Update</title>
</head>
<body>
<h2>Редактировать еду</h2> <br>


<br>
<form method="post" action="<c:url value='/update'/>">

    <input type="number" hidden name="id" value="${requestScope.meal.id}"/>

    <label> Дата/Время <input type="datetime-local" name="dateTime" value="${requestScope.meal.dateTime}"></label><br>

    <label> Описание <input type="text" name="description" value="${requestScope.meal.description}"></label><br>

    <label> Калории <input type="number" name="calories" value="${requestScope.meal.calories}"></label><br>

    <input type="submit" value="Ok" name="Ok"><br>

</form>

</body>
</html>
