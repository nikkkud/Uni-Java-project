const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/game-table'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body));
    });

    stompClient.subscribe('/user/queue/get_user_interface', (greeting2) => {
        showUserInterface(JSON.parse(greeting2.body));
    });

    // getInfo();
    sendStep(1);
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};
////////////////////


function connect() {
    stompClient.activate();
}

function sendStep(test) {

    if (test == 1) {
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({'Act': "bet", 'Bet': 100})
        });
    }
    if (test == 2) {
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({'Act': "call", 'Bet': 0})
        });
    }
    if (test == 3){
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({'Act': "check",'Bet': 0})
        });
    }
    if (test == 4){
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({'Act': "start",'Bet': 0})
        });
    }
}
function getInfo() {
    stompClient.publish({
        destination: "/app/getinfo"
    });
}

function showGreeting(message) {
    getInfo();


    // $("#greetings").append("<tr><td>" + message + "</td></tr>");
    console.log(message);
    $("#ontable").empty();
    $("#onhall").empty();
    $("#bank").empty();
    $("#grop2").empty();
    $("#userinterface").empty();

    for (let i = 0; i < message.PlayersOnTable.length; i++) {
        if (message.StepId == i) {
            $("#ontable").append(
                '<li><div id = "grop"><p>' + 
                message.PlayersOnTable[i].name +
                message.PlayersOnTable[i].balance +
                "(bet:" +
                message.PlayersBet[i] + ")" +
                '(S)' +
                '</p></div">'
                
                // + '<div id = "grop"><p>dg' + message.Bank + '</p></div"></li>'
            );
        }else {
            $("#ontable").append(
                '<li><div id = "grop"><p>' + 
                message.PlayersOnTable[i].name +
                message.PlayersOnTable[i].balance +
                "(bet:" +
                message.PlayersBet[i] + ")" +
                '</p></div">'
                
                // + '<div id = "grop"><p>dg' + message.Bank + '</p></div"></li>'
            );   
        }

    }
    for (let i = 0; i < message.PlayersOnHall.length; i++) {
        $("#onhall").append(
            '<li><div id = "grop"><p>' + 
            message.PlayersOnHall[i].name +
            message.PlayersOnHall[i].balance +
            '</p></div">'
            
        ); 
    }



    $("#bank").append(
        "Bank: " + message.Bank
    );
    for (let i = 0; i < message.CardsOnTable.length; i++) {
        $("#grop2").append(
            "<h1>" + message.CardsOnTable[i].Number + message.CardsOnTable[i].Suit + "</h1>"
        );  
    }


}
function showUserInterface(message) {
    console.log(message);
    $("#userinterface").empty();

    $("#userinterface").append(
        "<h3>" + message.name + message.balance +"</h3>"
    );
    $("#userinterface").append(
        '<input id = "bet" type="submit" value="bet"></form>'
    );
    $("#userinterface").append(
        '<input id = "call" type="submit" value="call"></form>'
    );
    $("#userinterface").append(
        '<input id = "check" type="submit" value="check"></form>'
    );
    $("#userinterface").append(
        '<input id = "start" type="submit" value="Start"></form>'
    );
    
    $( "#bet" ).click(() => sendStep(1));
    $( "#call" ).click(() => sendStep(2));
    $( "#check" ).click(() => sendStep(3));
    $( "#start" ).click(() => sendStep(4));

} 

$(function () {
    connect();

    $("form").on('submit', (e) => e.preventDefault());///фигня шобы не обновлять страницу
    // $( "#send" ).click(() => sendName());
    
});
