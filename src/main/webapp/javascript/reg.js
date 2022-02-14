"use strict"

function tryRegister() {
    $(function () {
        var name = document.getElementById('nameId').value;
        var email = document.getElementById('emailId').value;
        var password = document.getElementById('passwordId').value;
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_cars/reg.do',
            data: {name, email, password},
            dataType: 'json'
        }).done(function (message) {
            if (message === 'Пользователь с таким именем уже зарегистрирован') {
                if (document.getElementById('message') === null) {
                    $('<p style="color: red" id="message">' + message + '</p>').insertAfter(".message");
                }
            } else if (message === 'Пользователь добавлен') {
                window.location.href = "http://localhost:8080/job4j_cars/login.html";
            }
        }).fail(function (err) {
            console.log(err);
        })
    });
}