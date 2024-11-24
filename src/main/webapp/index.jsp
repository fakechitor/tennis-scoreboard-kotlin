<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<<body>
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
    <div class="button-container">
        <a href="${pageContext.request.contextPath}/new-match" class="button">Новый матч</a>
        <a href="${pageContext.request.contextPath}/matches?page=1&filter_by_player_name=" class="button">Матчи</a>
    </div>
</div>
</body>
</html>