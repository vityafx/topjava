<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<%
    List<MealTo> list = MealsUtil.getMealsTo(MealsUtil.getMeals(), 2000);
    String[] TableColumns = {"Date/Time", "Description", "Calories"};
    request.setAttribute("TableColumns", TableColumns);
    request.setAttribute("list", list);
%>
<table width="40%" border="2" style="left: 36px; position: relative">

    <tr>
        <c:forEach items="${TableColumns}" var="row">
            <th>
                    ${row}
            </th>
        </c:forEach>
    </tr>

    <c:forEach items="${list}" var="mealTo">

    <c:choose>
    <c:when test="${mealTo.isExcess()}">
    <tr style="color: #ff0000">
        </c:when>
        <c:when test="${!mealTo.isExcess()}">
    <tr style="color: #00ff00">
        </c:when>
        </c:choose>
        <td>
            <c:out value="${mealTo.LDTWithoutT(mealTo.dateTime)}"/>
        </td>
        <td>
            <c:out value="${mealTo.description}"/>
        </td>
        <td>
            <c:out value="${mealTo.calories}"/>
        </td>
        </c:forEach>

</table>
</body>
</html>