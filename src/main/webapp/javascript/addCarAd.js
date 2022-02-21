"use strict"

$(document).ready(function () {
    isLogged();
    loadCarBrands();
    loadBodyTypes();
    loadTransmissions();
});

function addCarAd() {
    let carBrand = document.getElementById("selectCarBrandId").value;
    let carModel = document.getElementById("selectCarModelId").value;
    let bodyType = document.getElementById("selectBodyTypeId").value;
    let transmission = document.getElementById("selectTransmissionId").value;
    let year = document.getElementById("yearId").value;
    let mileage = document.getElementById("mileageId").value;
    let price = document.getElementById("priceId").value;
    let desc = document.getElementById("descId").value;

    $(function () {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_cars/addCarAd.do',
            data: {carBrand, carModel, bodyType, transmission, year, mileage, price, desc},
            cache: false,
            dataType: 'json'
        }).done(function (carAdId) {
            addPhotosToCarAd(carAdId);
            window.location.href = "http://localhost:8080/job4j_cars";
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function addPhotosToCarAd(carAdId) {
    let photos = document.getElementById("photosId");
    let files = photos.files;
    let formData = new FormData();
    for (let file of files) {
        formData.append("photos", file);
    }

    $(function () {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_cars/addPhotosToCarAd.do?carAdId=' + carAdId,
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            enctype: 'multipart/form-data'
        }).done(function () {
            console.log("hello");
        }).fail(function (err) {
            console.log(err);
        });
    })
}


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
                $('<h2 style="text-align: center">Фильтры</h2>\n' +
                    '        <select id="selectCarBrandId" onchange="loadCarModels()">\n' +
                    '            <option value="0" selected>Выберите брэнд</option>\n' +
                    '        </select>\n' +
                    '        <select id="selectCarModelId">\n' +
                    '            <option value="0" selected>Выберите модель</option>\n' +
                    '        </select>\n' +
                    '        <select id="selectBodyTypeId">\n' +
                    '            <option value="0" selected>Выберите тип кузова</option>\n' +
                    '        </select>\n' +
                    '        <select id="selectTransmissionId">\n' +
                    '            <option value="0" selected>Выберите коробку передач</option>\n' +
                    '        </select>\n' +
                    '        <input type="button" value="Добавить объявление" onclick="addCarAd()">')
                    .insertAfter('.selects');
                $('<h2 style="text-align: center; margin-bottom: 26px" >Заполните информацию об автомобиле</h2>\n' +
                    '        <p>Год выпуска</p>\n' +
                    '        <input id="yearId" type="text" placeholder="Год Выпуска" required><br>\n' +
                    '        <p>Пробег</p>\n' +
                    '        <input id="mileageId" type="text" placeholder="Пробег" required><br>\n' +
                    '        <p>Цена</p>\n' +
                    '        <input id="priceId" type="text" placeholder="Цена" required><br>\n' +
                    '        <p>Описание</p>\n' +
                    '        <input id="descId" type="text" placeholder="Описание автомобиля" required style="width: 500px"><br><br>\n' +
                    '        <input id="photosId" type="file" multiple>')
                    .insertAfter('.infos');
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