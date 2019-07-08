<%--
  Created by IntelliJ IDEA.
  User: SerGo
  Date: 23.06.2019
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- jstl зависимость добавляем в maven (pom.xml)--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meals</title>
    <style>
        .excess {color: red;}
        .normal {color: green;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<%-- meals?actoin=create - meals аттрибут передаваемый в jsp (http) --%>
<a href="meals?action=create">Add Meal</a>
<hr>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Callories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <%-- items="${meals}" список значений пришедший из параметра http
     В параметр передается meals--%>
    <c:forEach var="meal" items="${meals}">
        <%-- Тег <jsp:useBean> позволяет ассоциировать экземпляр Java-класса,
        определенный в данной области видимости scope
        scope - scope - область видимости (page, request, session, application).
        используя атрибут type, задать локальное имя объекту, определенном в другой странице
         JSP или сервлете (атрибуты class или beanName при этом не используются).--%>
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"></jsp:useBean>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>
                <%-- <%=java вставка%>
                 в 8 java date time java потокобезопасный
                 DateTimeFormatter - https://jon.com.ua/blog/vvedenie-v-rabotu-s-datoj-i-vremenem-v-java-8/--%>
                <%=TimeUtil.toString(meal.getDateTime())%>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
