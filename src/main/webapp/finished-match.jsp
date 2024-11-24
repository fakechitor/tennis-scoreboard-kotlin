<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
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
        <caption>Финальный счет</caption>
        <thead>
        <tr>
            <th>Игрок</th>
            <th>${columnNames[0]}</th>
            <th>${columnNames[1]}</th>
            <th>${columnNames[2]}</th>
            <th>${columnNames[3]}</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${firstPlayer}</td>
            <td>${scoreData["setsPlayer1"]}</td>
            <td>${scoreData["firstSetPlayer1"]}</td>
            <td>${scoreData["secondSetPlayer1"]}</td>
            <td>${scoreData["thirdSetPlayer1"]}</td>
        </tr>
        <tr>
            <td>${secondPlayer}</td>
            <td>${scoreData["setsPlayer2"]}</td>
            <td>${scoreData["firstSetPlayer2"]}</td>
            <td>${scoreData["secondSetPlayer2"]}</td>
            <td>${scoreData["thirdSetPlayer2"]}</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>