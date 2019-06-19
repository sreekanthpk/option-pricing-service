var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ticker-web-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/stock', function (tick) {
            showTick(JSON.parse(tick.body).stock, JSON.parse(tick.body).price, JSON.parse(tick.body).strike, JSON.parse(tick.body).putPrice, JSON.parse(tick.body).callPrice);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendStock() {
    if (stompClient == null) {
        connect()
    }
    stompClient.send("/app/price", {}, JSON.stringify({'stock': $("#stock").val(),
    'strike': $("#strike").val(),
    'interestRate': $("#interestRate").val(),
    'impliedVolatility': $("#impliedVolatility").val(),
    'time': $("#time").val()}));
}

function showTick(stock, price, strike, putPrice, callPrice) {
    var e1;

    var name=stock.replace(".","");

    if( $("#tab"+name+strike).length ) {
        e1 = $("#tab"+name+strike).empty().append("<tr><td>" + stock+":" +price + "</td></tr>")
        .append("<tr><td>Put @ " +strike+ " :" +putPrice + "</td></tr>")
        .append("<tr><td>Call @ " +strike+ " :" +callPrice + "</td></tr>");

    } else {
         var k = "tab"+name+strike;
         e1 = document.createElement("div");
         e1.setAttribute("id",k);
         $("#tab"+name+strike).empty().append("<tr><td>" + stock+":" +price + "</td></tr><tr><td>Put @"+strike+ ":" +putPrice  + "</td></tr><tr><td>Call @"+strike+ ":" +callPrice + "</td></tr>");

    }

    $("#greetings").append(e1)
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendStock(); });
});