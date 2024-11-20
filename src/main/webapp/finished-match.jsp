<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <link rel="icon" href="flavicone.ico" type="image/x-icon" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Tennis scoreboard</title>
</head>
<body>
<table>
    <caption>
        Текущий матч
    </caption>
    <thead>
    <tr>
        <th>Игрок</th>
        <th>Сет 1</th>
        <th>Сет 2</th>
        <th>Сет 3</th>
        <th>Победитель</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${firstPlayerName}</td>
        <td>${firstSetPlayer1}</td>
        <td>${gamesPlayer1}</td>
        <td>${pointsPlayer1}</td>
        <td></td>
    </tr>
    <tr>
        <td>${secondPlayerName}</td>
        <td>${setsPlayer2}</td>
        <td>${gamesPlayer2}</td>
        <td>${pointsPlayer2}</td>
        <td></td>
    </tr>
    </tbody>
</table>
</body>
</html>