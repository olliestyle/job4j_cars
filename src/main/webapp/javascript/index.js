"use strict"

let slideIndex;
/* Class the members of each slideshow group with different CSS classes */
let slideId;

$(document).ready(function () {
    isLogged();
    loadCarBrands();
    loadBodyTypes();
    loadTransmissions();
    showAllAds();
});

function clearAds() {
    document.getElementById('ads').innerHTML = "";
}

function showAllAds() {
    clearAds();
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_cars/ads.do',
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            let i = 1;
            slideIndex = new Array(data.length).fill(1);
            slideId = new Array(data.length);
            for (var k = 0; k < slideId.length; k++) {
                slideId[k] = 'mySlides' + (k + 1);
                console.log(slideId[k]);
            }
            for (const ad of data) {
                let sold = '<p style="color: green; size: 20px; float: right; margin-right: 300px">' + 'В продаже!' + '</p>';
                if (ad.isSold){
                    sold = '<p style="color: red; size: 20px; float: right; margin-right: 300px">' + 'Продано!' + '</p>';
                }
                $('#ads').append('<div class="advertisement">\n' +
                    '                <p id="adCreated" style="float: right">\n' +
                    '                    Объявление создано:' + ad.created +
                    '                </p>\n' +
                    '                <p id="adInfo">\n' +
                    '                ' + ad.carBrand.carBrand + ' ' + ad.carModel.carModel +
                    '                ' + ad.bodyType.bodyType + ' ' + ad.transmission.transmission +
                    '                ' + ad.manufactureYear + 'г.' + ad.mileage + 'км.' +
                    '                </p>\n' + sold +
                    '                <p id="adPrice">\n' +
                    '                    Цена:' + ad.price + 'руб.' +
                    '                </p>\n' +
                    '                <p id="adDecs">\n' +
                    '                ' + ad.description +
                    '                </p>\n' +
                    '                    <div class="slideshow-container">\n' +
                                                fillPhotos(ad.photos, i) +
                    '                    </div>\n' +
                    '            </div>');
                i = i + 1;
                // $('#selectCarBrandId').append('<option value="' + brand.id + '">' + brand.carBrand + '</option>');
            }
            for (let j = 0; j < data.length; j++) {
                showSlides(1, j);
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function showAdsByFilters() {
    clearAds();
    let carBrandId = document.getElementById('selectCarBrandId').value;
    let carModelId = document.getElementById('selectCarModelId').value;
    let bodyTypeId = document.getElementById('selectBodyTypeId').value;
    let transmissionId = document.getElementById('selectTransmissionId').value;
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_cars/search.do',
            data: {carBrandId, carModelId, bodyTypeId, transmissionId},
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            let i = 1;
            slideIndex = new Array(data.length).fill(1);
            slideId = new Array(data.length);
            for (var k = 0; k < slideId.length; k++) {
                slideId[k] = 'mySlides' + (k + 1);
                console.log(slideId[k]);
            }
            for (const ad of data) {
                let sold = '<p style="color: green; size: 20px; float: right; margin-right: 300px">' + 'В продаже!' + '</p>';
                if (ad.isSold){
                    sold = '<p style="color: red; size: 20px; float: right; margin-right: 300px">' + 'Продано!' + '</p>';
                }
                $('#ads').append('<div class="advertisement">\n' +
                    '                <p id="adCreated" style="float: right">\n' +
                    '                    Объявление создано:' + ad.created +
                    '                </p>\n' +
                    '                <p id="adInfo">\n' +
                    '                ' + ad.carBrand.carBrand + ' ' + ad.carModel.carModel +
                    '                ' + ad.bodyType.bodyType + ' ' + ad.transmission.transmission +
                    '                ' + ad.manufactureYear + 'г.' + ad.mileage + 'км.' +
                    '                </p>\n' + sold +
                    '                <p id="adPrice">\n' +
                    '                    Цена:' + ad.price + 'руб.' +
                    '                </p>\n' +
                    '                <p id="adDecs">\n' +
                    '                ' + ad.description +
                    '                </p>\n' +
                    '                    <div class="slideshow-container">\n' +
                    fillPhotos(ad.photos, i) +
                    '                    </div>\n' +
                    '            </div>');
                i = i + 1;
                // $('#selectCarBrandId').append('<option value="' + brand.id + '">' + brand.carBrand + '</option>');
            }
            for (let j = 0; j < data.length; j++) {
                showSlides(1, j);
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function fillPhotos(photos, i) {
    let toReturn = '';
    for(const photo of photos) {
        toReturn = toReturn + '<div class="mySlides' + i  + ' fade">\n' +
            '<img class="carPhoto" src="/job4j_cars/download.do?name=' + photo + '"/>\n' +
            '</div>\n';
    }
    toReturn = toReturn + '<a class="prev" onclick="plusSlides(-1,' + (i - 1) + ')">&#10094;</a>\n' +
                            '<a class="next" onclick="plusSlides(1,' + (i - 1) + ')">&#10095;</a>'
    return toReturn;
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

function plusSlides(n, no) {
    showSlides(slideIndex[no] += n, no);
}

function showSlides(n, no) {
    let i;
    const x = document.getElementsByClassName(slideId[no]);
    if (n > x.length) {slideIndex[no] = 1}
    if (n < 1) {slideIndex[no] = x.length}
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex[no]-1].style.display = "block";
}