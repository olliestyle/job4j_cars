"use strict"

// let slideIndex = 1;

let slideIndex = [1, 1, 1];
/* Class the members of each slideshow group with different CSS classes */
const slideId = ["mySlides1", "mySlides2", "mySlides3"];

$(document).ready(function () {
    isLogged();
    loadCarBrands();
    loadBodyTypes();
    loadTransmissions();
    showAllAds();
    showSlides(1, 0);
    showSlides(1, 1);
    showSlides(1, 2);
    // showSlides(slideIndex);
});

function showAllAds() {
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_cars/ads.do',
            cache: false,
            dataType: 'json'
        }).done(function (data) {
            let i = 1;
            for (const ad of data) {
                $('#ads').append('<div class="advertisement">\n' +
                    '                <p id="adCreated" style="float: right">\n' +
                    '                    Объявление создано:' + ad.created +
                    '                </p>\n' +
                    '                <p id="adInfo">\n' +
                    '                ' + ad.carBrand.carBrand + ' ' + ad.carModel.carModel +
                    '                ' + ad.bodyType.bodyType + ' ' + ad.transmission.transmission +
                    '                ' + ad.manufactureYear + 'г.' + ad.mileage + 'км.' +
                    '                </p>\n' +
                    '                <p id="adPrice">\n' +
                    '                    Цена:' + ad.price + 'руб.' +
                    '                </p>\n' +
                    '                <p id="adDecs">\n' +
                    '                ' + ad.description +
                    '                </p>\n' +
                    '                    <div class="slideshow-container">\n' +
                                                fillPhotos(ad.photos, i) +

                                                     +
                    '                    </div>\n' +
                    '                    <div style="text-align:center">\n' +
                                            fillDots(ad.photos.length, i) +
                    '                    </div>\n' +
                    '            </div>');
                i = i + 1;
                console.log(i);
                // $('#selectCarBrandId').append('<option value="' + brand.id + '">' + brand.carBrand + '</option>');
            }
        }).fail(function (err) {
            console.log(err);
        });
    })
}

function fillPhotos(photos, i) {
    let toReturn = '';
    for(const photo of photos) {
        toReturn = toReturn + '<div class="mySlides' + i  + '">\n' +
            '<img class="carPhoto" src="/job4j_cars/download.do?name=' + photo + '"/>\n' +
            '</div>\n';
    }
    toReturn = toReturn + '<a class="prev" onclick="plusSlides(-1,' + (i - 1) + ')">&#10094;</a> +\n' +
                            '<a class="next" onclick="plusSlides(1,' + (i - 1) + ')">&#10095;</a>n\''
    console.log(toReturn);
    return toReturn;
}

function fillDots(length) {
    var toReturn = '';
    for (var i = 1; i <= length; i++) {
        toReturn = toReturn + '<span class="dot" onclick="currentSlide(' + i + ', ' + (i - 1) +')"></span>\n'
    }
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

// // Next/previous controls
// function plusSlides(n) {
//     showSlides(slideIndex += n);
// }
//
// // Thumbnail image controls
function currentSlide(n, no) {
    showSlides(slideIndex = n, no);
}

// function showSlides(n) {
//     let i;
//     const slides = document.getElementsByClassName("mySlides");
//     const dots = document.getElementsByClassName("dot");
//     if (n > slides.length) {slideIndex = 1}
//     if (n < 1) {slideIndex = slides.length}
//     for (i = 0; i < slides.length; i++) {
//         slides[i].style.display = "none";
//     }
//     for (i = 0; i < dots.length; i++) {
//         dots[i].className = dots[i].className.replace(" active", "");
//     }
//     slides[slideIndex-1].style.display = "block";
//     dots[slideIndex-1].className += " active";
// }

function plusSlides(n, no) {
    showSlides(slideIndex[no] += n, no);
}

function showSlides(n, no) {
    let i;
    const x = document.getElementsByClassName(slideId[no]);
    console.log(x)
    if (n > x.length) {slideIndex[no] = 1}
    if (n < 1) {slideIndex[no] = x.length}
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex[no]-1].style.display = "block";
}