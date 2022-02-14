"use strict"
$(document).ready(function () {
    isLogged();
});
function isLogged() {
    $(function () {
        $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/job4j_cars/login.do',
                dataType: 'json'
            }).done(function (user) {
                if(user === 'UserIsEmpty') {
                    if(document.getElementById('username') === null) {
                        $('<div class="rightFloat">'
                          +'<a id="message" href="http://localhost:8080/job4j_cars/login.html"> Войдите </a>'
                          + ' или '
                          + '<a id="message" href="http://localhost:8080/job4j_cars/reg.html"> Зарегистрируйтесь</a>'
                          + '<span> если хотите создать объявление</span>'
                          + '</div>')
                            .insertAfter(".username");
                    }
                } else {
                    $('<a id="message" href="http://localhost:8080/job4j_cars/addCarAd.do"> Добавить новое объявление </a>'
                        + '<div class="rightFloat">'
                        + '<span>Вы вошли как: ' + user + ' | </span>'
                        + '<a id="message" href="' + linkToExit() + '"> Выйти </a>'
                        + '</div>')
                        .insertAfter(".username");
                }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function linkToExit() {
    return 'http://localhost:8080/job4j_cars/logout.do?rand' + Math.random();
}