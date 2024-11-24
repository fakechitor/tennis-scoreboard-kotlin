<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tennis scoreboard</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Space+Mono:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
</head>
<body>
<div class="top-panel">
    <a href="${pageContext.request.contextPath}" class="title-link">
        <span>Tennis scoreboard</span>
    </a>
    <div class="matches">
        <a href="${pageContext.request.contextPath}/matches?page=1&filter_by_player_name=">
            <span>Матчи</span>
        </a>
    </div>
</div>
<div class="content">
    <table>
        <caption>Текущий матч</caption>
        <thead>
        <tr>
            <th>Игрок</th>
            <th>${columnNames[0]}</th>
            <th>${columnNames[1]}</th>
            <th>${columnNames[2]}</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${firstPlayer}</td>
            <td>${scoreData["setsPlayer1"]}</td>
            <td>${scoreData["gamesPlayer1"]}</td>
            <td>${scoreData["pointsPlayer1"]}</td>
            <td>
                <form action="${pageContext.request.contextPath}/match-score?uuid=${uuid}" method="POST">
                    <input type="hidden" name="player" value="player1">
                    <button type="submit">Добавить очко</button>
                </form>
            </td>
        </tr>
        <tr>
            <td>${secondPlayer}</td>
            <td>${scoreData["setsPlayer2"]}</td>
            <td>${scoreData["gamesPlayer2"]}</td>
            <td>${scoreData["pointsPlayer2"]}</td>
            <td>
                <form action="${pageContext.request.contextPath}/match-score?uuid=${uuid}" method="POST">
                    <input type="hidden" name="player" value="player2">
                    <button type="submit">Добавить очко</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
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