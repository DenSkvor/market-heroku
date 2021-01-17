var stomp = null;

// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connect() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/products', function (cartDto) {
            renderItem(cartDto);
        });
    });
}
/*
// хук на интерфейс
$(function () {
    $("#cartAddForm").on('submit', function (e) {
        e.preventDefault();
    });
    $("#add").click(function() {sendContent(idFront);});
});*/

// отправка сообщения на сервер
function sendContent(id) {
    stomp.send("/app/add", {}, JSON.stringify({
        'id': id
        }));
}

// рендер сообщения, полученного от сервера
function renderItem(cartDtoJson) {
    var cartDto = JSON.parse(cartDtoJson.body);
    $("#cart").html("");
    $("#cart").append(
        "<tr>" +
        "<td><a class=\"nav-link\" href='/cart'>Корзина (" + cartDto.totalQuantity + " | " + cartDto.totalCost + ")</a></td>" +
        "</tr>");
}