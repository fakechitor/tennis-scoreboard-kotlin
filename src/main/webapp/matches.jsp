<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%><!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Matches</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/matches" method="GET">
    <label for="filter_by_player_name">Игрок:</label>
    <input type="hidden" name="page" value="1">
    <input type="text" id="filter_by_player_name" name="filter_by_player_name" placeholder="Введите игрока">
    <button type="submit">Искать</button>
</form>
<br>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Игрок 1</th>
        <th>Игрок 2</th>
        <th>Победитель</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${matches}" var="match" varStatus="status">
        <tr>
            <td>${(status.index + 1) + additional }</td>
            <td>${match.name1}</td>
            <td>${match.name2}</td>
            <td>${match.winner}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">
<c:if test="${page > 1}">
    <a href="${pageContext.request.contextPath}/matches?page=${page -1}&filter_by_player_name=${filterName}">&laquo; Previous</a>
</c:if>

<c:forEach begin="1" end="${totalPages}" var="i">
    <c:choose>
        <c:when test="${i == page}">
            <span class="current">${i}</span>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/matches?page=${i}&filter_by_player_name=${filterName}">${i}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>

<c:if test="${page < totalPages}">
    <a href="${pageContext.request.contextPath}/matches?page=${page + 1}&filter_by_player_name=${filterName}">Next &raquo;</a>
</c:if>
</div>

</body>
</html>
