<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2>${param.action == "create" ? 'Create' : 'Edit'}</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.getId() != null ? meal.getId() : ''}" />
    <label for="dateTime">DateTime:</label>
    <input type="datetime-local" name="dateTime" id="dateTime"
           value="${meal.dateTime != null ? meal.dateTime.toString().replace(' ', 'T') : ''}" required />
    <br/>

    <label for="description">Description</label>
    <input type="text" name="description" id="description" value="${meal.description}" required />
    <br/>

    <label for="calories">Calories</label>
    <input type="number" name="calories" id="calories" value="${meal.calories}" required />
    <br/>

    <button type="submit">Save</button>
    <a href="meals">Back</a>
</form>
</body>
</html>
