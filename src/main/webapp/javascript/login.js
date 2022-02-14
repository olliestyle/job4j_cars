"use strict"

function tryLogin() {
    $(function () {
        var email = document.getElementById('emailId').value;
        console.log(email);
        var password = document.getElementById('passwordId').value;
        console.log(password)
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/job4j_cars/login.do',
            data: {email, password},
            dataType: 'json'
        }).done(function (message) {
            if (message === 'Не верное имя или пароль') {
                if (document.getElementById('message') === null) {
                    $('<p style="color: red" id="message">' + message + '</p>').insertAfter(".message");
                }
            } else {
                window.location.href = "http://localhost:8080/job4j_cars";
            }
        }).fail(function (err) {
            console.log(err);
        })
    });
}