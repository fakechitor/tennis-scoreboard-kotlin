<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <link rel="icon" href="${pageContext.request.contextPath}/flavicone.ico" type="image/x-icon" />
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
        <th>${columnNames[0]}</th>
        <th>${columnNames[1]}</th>
        <th>${columnNames[2]}</th>
        <th>${columnNames[3]}</th>
        <
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
        <td>${scoreData["thirdSetPlayer2    "]}</td>
    </tr>
    </tbody>
</table>
</body>
</html>