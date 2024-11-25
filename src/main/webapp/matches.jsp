<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Matches</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Space+Mono:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
</head>
<body>
<div class="top-panel">
    <a href="${pageContext.request.contextPath}/" class="title-link">
        <span>Tennis scoreboard</span>
    </a>
    <div class="matches">
        <a href="${pageContext.request.contextPath}/matches?page=1&filter_by_player_name=">
            <span>Матчи</span>
        </a>
    </div>
</div>
<form action="${pageContext.request.contextPath}/matches" method="GET" class="search-form">
    <label for="filter_by_player_name" class="search-label">Игрок:</label>
    <div class="input-container">
        <input type="hidden" name="page" value="1">
        <input type="text" id="filter_by_player_name" name="filter_by_player_name" value="${filterName}" placeholder="Введите игрока" class="search-input">
        <button type="submit" class="search-button">Искать</button>
    </div>
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
        <a href="${pageContext.request.contextPath}/matches?page=${page - 1}&filter_by_player_name=${filterName}" class="pagination-button">&laquo; Пред</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == page}">
                <span class="pagination-button current">${i}</span>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/matches?page=${i}&filter_by_player_name=${filterName}" class="pagination-button">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${page < totalPages}">
        <a href="${pageContext.request.contextPath}/matches?page=${page + 1}&filter_by_player_name=${filterName}" class="pagination-button">След &raquo;</a>
    </c:if>
</div>
<c:if test="${not empty showError}">
    <input type="checkbox" id="error-toggle" checked style="display:none">
    <div class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('error-toggle').checked = false;">&times;</span>
            <h2>Ошибка</h2>
            <p>${errorMessage}</p>
        </div>
    </div>
</c:if>
</body>
</html>
