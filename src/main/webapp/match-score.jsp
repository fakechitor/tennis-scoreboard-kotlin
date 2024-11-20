<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tennis scoreboard</title>
</head>
<body>
<table>
    <caption>Текущий матч</caption>
    <thead>
    <tr>
        <th>Игрок</th>
        <th>${columnNames[0]}</th>
        <th>${columnNames[1]}</th>
        <th>${columnNames[2]}</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${firstPlayer}</td>
        <td>${scoreData["setsPlayer1"]}</td>
        <td>${scoreData["gamesPlayer1"]}</td>
        <td>${scoreData["pointsPlayer1"]}</td>
        <td><form action="${pageContext.request.contextPath}/match-score?uuid=${uuid}" method="POST">
            <input type="hidden" name="player" value="player1">
            <button type="submit">Добавить очко</button>
        </form></td>
    </tr>
    <tr>
        <td>${secondPlayer}</td>
        <td>${scoreData["setsPlayer2"]}</td>
        <td>${scoreData["gamesPlayer2"]}</td>
        <td>${scoreData["pointsPlayer2"]}</td>
        <td><form action="${pageContext.request.contextPath}/match-score?uuid=${uuid}" method="POST">
            <input type="hidden" name="player" value="player2">
            <button type="submit">Добавить очко</button>
        </form></td>
    </tr>
    </tbody>
</table>

</body>
</html>