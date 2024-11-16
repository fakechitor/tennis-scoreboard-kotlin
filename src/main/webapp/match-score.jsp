<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tennis scoreboard</title>
</head>
<body>
<table>
    <caption>Текущий матч</caption>
    <thead>
    <tr>
        <th>Игрок</th>
        <th>Сет</th>
        <th>Игра</th>
        <th>Очки</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${firstPlayerName}</td>
        <td>${setsPlayer1}</td>
        <td>${gamesPlayer1}</td>
        <td>${pointsPlayer1}</td>
        <td><form action="http://localhost:8080/ROOT_war_exploded/match-score/${uuid}" method="POST">
            <input type="hidden" name="player" value="player1">
            <button type="submit">Добавить очко</button>
        </form></td>
    </tr>
    <tr>
        <td>${secondPlayerName}</td>
        <td>${setsPlayer2}</td>
        <td>${gamesPlayer2}</td>
        <td>${pointsPlayer2}</td>
        <td><form action="http://localhost:8080/ROOT_war_exploded/match-score/${uuid}" method="POST">
            <input type="hidden" name="player" value="player2">
            <button type="submit">Добавить очко</button>
        </form></td>
    </tr>
    </tbody>
</table>

</body>
</html>