<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 5px;
        }
        .calories-true {
            color: green;
            font-weight: bold;
        }
        .calories-false {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h2>List Meals</h2>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr>
            <td>${meal.getDateTime()}</td>
            <td>${meal.getDescription()}</td>
            <td class="calories-${meal.isExcess()}">${meal.getCalories()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
