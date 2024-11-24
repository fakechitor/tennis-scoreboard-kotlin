<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TennisScoreboard</title>
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
    <section>
        <form method="POST" action="${pageContext.request.contextPath}/new-match" class="form-container">
            <h3>Имя игрока 1</h3>
            <label>
                <input type="text" name="firstPlayer" placeholder="Игрок 1" required class="input-field">
            </label>
            <h3>Имя игрока 2</h3>
            <label>
                <input type="text" name="secondPlayer" placeholder="Игрок 2" required class="input-field">
            </label>
            <button type="submit" class="form-button">Начать</button>
        </form>

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
    </section>
</div>
</body>
</html>