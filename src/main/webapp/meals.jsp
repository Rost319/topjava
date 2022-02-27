<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>All Meals</h2>
<br>

<table cellspacing="10">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>  </th>
        <th>  </th>

    </tr>

    <c:forEach var="meal" items="${mealToList}">


        <tr style="color: ${meal.excess ? "red" : "green"}">
            <td>${meal.id}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>

            <td>
                <form method="post" action="<c:url value='/delete'/>">
                    <input type="number" hidden name="id" value="${meal.id}" />
                    <input type="submit" name="delete" value="Удалить"/>
                </form>
            </td>

            <td>
                <form method="get" action="<c:url value='/update'/>">
                    <input type="number" hidden name="id" value="${meal.id}" />
                    <input type="submit" value="Редактировать"/>
                </form>
            </td>
        </tr>

    </c:forEach>


</table><br>

<h4>Создание новой записи</h4>

<form method="get" action="<c:url value='/add_meal'/>">
    <input type="number" hidden name="id" value="${meal.id}" />
    <input type="submit" value="Добавить"/>
</form>


</body>
</html>