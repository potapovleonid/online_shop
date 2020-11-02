
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
        stomp.subscribe('/topic/bucket', function (bucketDto) {
            renderItem(bucketDto);
        });
    });
}


// рендер сообщения, полученного от сервера
function renderItem(bucketDTOJson) {
    var bucketDTO = JSON.parse(bucketDTOJson.body);
    $("#bucketinfo").append("<h5>In bucket of goods: </h5>>" +
                            bucketDTO.amountProducts + "\n" +
                            "Summ: " + bucketDTO.sum);
}
