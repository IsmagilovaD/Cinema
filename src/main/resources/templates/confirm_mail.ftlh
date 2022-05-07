<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Confirm</title>
</head>
<body>
<h1 style="font-family: sans-serif">${.data_model["username"]}!</h1>
<h2 style="font-family: sans-serif"> Пройдите для подтверждения регистрации по ссылке <a style="color: lightseagreen; font-size: larger" href=localhost/confirm/${.data_model["confirm_code"]}>localhost/confirm/${.data_model["confirm_code"]}</a></h2>
</body>
</html>