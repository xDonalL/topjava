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
<a href="meals?action=create">Add Meal</a>
<br><br>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <td> ${meal.dateTime != null ? meal.dateTime.toString().replace('T', ' ') : ''}</td>
        <td>${meal.getDescription()}</td>
        <td class="calories-${meal.isExcess()}">${meal.getCalories()}</td>
        <td><a href="meals?action=update&id=${meal.getId()}">Update</a></td>
        <td><a href="meals?action=delete&id=${meal.getId()}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
