"use strict"

$(document).ready(function () {
    isLogged();
    showUserAds();
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
                $('<a id="message" href="http://localhost:8080/job4j_cars/"> На главную </a>'
                    + '<div class="rightFloat">'
                    + '<span>Вы вошли как: ' + user + ' | </span>'
                    + '<a id="message" href="' + linkToExit() + '"> Выйти </a>'
                    + '</div>')
                    .insertAfter(".username");
                $('<h2 style="text-align: center">Изменение статуса объявлений</h2>\n')
                    .insertAfter(".updateheader");
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function linkToExit() {
    return 'http://localhost:8080/job4j_cars/logout.do?rand' + Math.random();
}

function showUserAds() {
    console.log('in begin of show user ads and call clearAds')
        clearAds();
        $(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/job4j_cars/changeStatus.do',
                cache: false,
                dataType: 'json'
            }).done(function (data) {
                for (const ad of data) {
                    if(!ad.isSold) {
                        $('.adsList').append('<div class="changeStatus">\n' +
                            '                <p id="adCreated" style="float: right">\n' +
                            '                    Объявление создано:' + ad.created +
                            '                </p>\n' +
                            '                <p id="adInfo">\n' +
                            '                ' + ad.carBrand.carBrand + ' ' + ad.carModel.carModel +
                            '                ' + ad.bodyType.bodyType + ' ' + ad.transmission.transmission + '<br>' +
                            'Год выпуска: '  + ad.manufactureYear + 'г.; Пробег: ' + ad.mileage + 'км.' +
                            '                </p>\n' +
                            '                <p id="adPrice">\n' +
                            '                    Цена:' + ad.price + 'руб.' +
                            '                </p>\n' +
                            '                <p id="adDecs">\n' +
                            '                ' + ad.description +
                            '                </p>\n' +
                            '            <input class="carChange" type="button" value="Автомобиль продан" onclick="updateAdStatus(' + ad.id + ')">' +
                            '            </div>');
                    }
                }
            }).fail(function (err) {
                console.log(err);
            });
        })

}

function clearAds() {
    console.log("in clear")
    document.getElementById('adsListId').innerHTML = "";
}

function updateAdStatus(adId) {
    $(function () {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_cars/changeStatus.do',
            cache: false,
            data: {adId}
        }).done(function () {
            console.log("updateStatus and call showUserAds")
            showUserAds();
        }).fail(function (err) {
            console.log(err);
        });
    })
}