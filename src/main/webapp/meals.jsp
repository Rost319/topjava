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
    </tr>

    <c:forEach var="meal" items="${mealToList}">


        <tr style="color: ${meal.excess ? "red" : "green"}">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>

    </c:forEach>

</table>


</body>
</html>