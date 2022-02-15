"use strict"
$(document).ready(function () {
    isLogged();
    loadCarBrands();
    loadBodyTypes();
    loadTransmissions();
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
                    $('<a id="message" href="http://localhost:8080/job4j_cars/addCarAd.html"> Добавить новое объявление </a>'
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

function loadCarBrands() {
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_cars/brands.do',
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            for (var brand of data) {
                $('#selectCarBrandId').append('<option value="' + brand.id + '">' + brand.carBrand + '</option>');
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function loadCarModels() {
    var carBrandId = document.getElementById('selectCarBrandId').value;
    console.log(carBrandId);
    removeOptions(document.getElementById('selectCarModelId'));
    $(function () {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_cars/models.do',
            data: {carBrandId},
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            for (var model of data) {
                $('#selectCarModelId').append('<option value="' + model.id + '">' + model.carModel + '</option>');
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function removeOptions(selectElement) {
    var i, L = selectElement.options.length - 1;
    for(i = L; i >= 1 ; i--) {
        selectElement.remove(i);
    }
}

function loadBodyTypes() {
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_cars/bodyTypes.do',
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            for (var bodytype of data) {
                $('#selectBodyTypeId').append('<option value="' + bodytype.id + '">' + bodytype.bodyType + '</option>');
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function loadTransmissions() {
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_cars/transmissions.do',
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            for (var transmission of data) {
                $('#selectTransmissionId').append('<option value="' + transmission.id + '">' + transmission.transmission + '</option>');
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}