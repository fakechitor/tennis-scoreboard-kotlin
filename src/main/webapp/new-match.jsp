<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/flavicone.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TennisScoreboard</title>
</head>
<body>
<br/>
<section>
    <form method="POST" action="${pageContext.request.contextPath}/new-match">
        <h3>Имя игрока 1</h3>
        <label>
            <input type="text" name="firstPlayer" placeholder="Игрок 1" required>
        </label>
        <h3>Имя игрока 2</h3>
        <label>
            <input type="text" name="secondPlayer" placeholder="Игрок 2" required>
        </label>
        <button type="submit">Начать</button>
    </form>
</section>
</body>
</html>