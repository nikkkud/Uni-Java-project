var user_name = "";

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/game-table'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body));
    });

    stompClient.subscribe('/user/queue/get_user_interface', (greeting2) => {
        user_name = showUserInterface(JSON.parse(greeting2.body));

    });

    //getInfo();
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
            body: JSON.stringify({ 'Act': "bet", 'Bet': 100 })
        });
    }
    if (test == 2) {
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({ 'Act': "call", 'Bet': 0 })
        });
    }
    if (test == 3) {
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({ 'Act': "check", 'Bet': 0 })
        });
    }
    if (test == 4) {
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({ 'Act': "start", 'Bet': 0 })
        });
    }
    if (test == 5) {
        stompClient.publish({
            destination: "/app/hello",
            body: JSON.stringify({ 'Act': "fold", 'Bet': 0 })
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

    console.log(message);

    console.log(user_name);

    // $("#greetings").append("<tr><td>" + message + "</td></tr>");
    //console.log(message.Bank);
    $("#ontable").empty();
    $("#onhall").empty();
    $("#bank").empty();
    $("#grop2").empty();

    for (let i = 0; i < message.PlayersOnTable.length; i++) {
        //var plaeyr_step = message.StepId;

        if (message.StepId == i) {
            let ontabaleelem = document.querySelector('#ontable');
            let onhallelem = document.querySelector('#onhall');

            ontabaleelem.classList.remove('clean_hall_table');
            onhallelem.classList.add('clean_hall_table');

            if (user_name != message.PlayersOnTable[i].name) {
                $("#ontable").append(
                    '<li><div class="players_profile player_step"><div class="userprofile_first_block"><h3 class="playerdata userinterface_nickname">' +
                    message.PlayersOnTable[i].name +
                    '</h3><h3 class="playerdata userinterface_balance">' +
                    message.PlayersOnTable[i].balance +
                    '</div>' +
                    '<div class="userprofile_third_block"><h3 class="playerdata userinterface_nickname">Bet</h3><h3 class="playerdata userinterface_balance">' +
                    message.PlayersBet[i] +
                    '</h3></div></div></li>'
                );

            }

            if ((message.OpenCards[i][0].Number == 0 || message.OpenCards[i][0].Suit == 'N') || (message.OpenCards[i][1].Number == 0 || message.OpenCards[i][1].Suit == 'N')) {

                $("#ontable li").append(
                    `<div class="player_card_block"><img class="card_image first_card" src="images/cards/back_side.svg" alt=""><img class="card_image" src="images/cards/back_side.svg" alt=""></div>`
                );
            } else {
                $("#ontable li").append(
                    `<div class="player_card_block"><img class="card_image first_card" src="images/cards/${message.OpenCards[i][0].Number}${message.OpenCards[i][0].Suit}.svg"><img class="card_image" src="images/cards/${message.OpenCards[i][1].Number}${message.OpenCards[i][1].Suit}.svg"></div>`
                );
            }

        } else {

            let ontabaleelem = document.querySelector('#ontable');
            let onhallelem = document.querySelector('#onhall');

            ontabaleelem.classList.remove('clean_hall_table');
            onhallelem.classList.add('clean_hall_table');

            if (user_name != message.PlayersOnTable[i].name) {
                $("#ontable").append(
                    '<li><div class="players_profile"><div class="userprofile_first_block"><h3 class="playerdata userinterface_nickname">' +
                    message.PlayersOnTable[i].name +
                    '</h3><h3 class="playerdata userinterface_balance">' +
                    message.PlayersOnTable[i].balance +
                    '</div>' +
                    '<div class="userprofile_third_block"><h3 class="playerdata userinterface_nickname">Bet</h3><h3 class="playerdata userinterface_balance">' +
                    message.PlayersBet[i] +
                    '</h3></div></div></li>'
                );

                if ((message.OpenCards[i][0].Number == 0 || message.OpenCards[i][0].Suit == 'N') || (message.OpenCards[i][1].Number == 0 || message.OpenCards[i][1].Suit == 'N')) {
                    $("#ontable li").append(
                        `<div class="player_card_block"><img class="card_image first_card" src="images/cards/back_side.svg" alt=""><img class="card_image" src="images/cards/back_side.svg" alt=""></div>`
                    );
                } else {
                    $("#ontable li").append(
                        `<div class="player_card_block"><img class="card_image first_card" src="images/cards/${message.OpenCards[i][0].Number}${message.OpenCards[i][0].Suit}.svg"><img class="card_image" src="images/cards/${message.OpenCards[i][1].Number}${message.OpenCards[i][1].Suit}.svg"></div>`
                    );
                }

            }


        }

    }

    if (message.GameOver == true) {
        for (let i = 0; i < message.PlayersOnHall.length; i++) {

            let ontabaleelem = document.querySelector('#ontable');
            ontabaleelem.classList.add('clean_hall_table');

            if (user_name != message.PlayersOnHall[i].name) {
                $("#onhall").append(
                    '<li><div class="players_profile"><div class="userprofile_first_block"><h3 class="playerdata userinterface_nickname">' +
                    message.PlayersOnHall[i].name +
                    '</h3><h3 class="playerdata userinterface_balance">' +
                    message.PlayersOnHall[i].balance +
                    '</div><div class="userprofile_third_block"><h3 class="playerdata userinterface_nickname">Online</h3></div></li>'
                );
            }

        }
    }

    $("#bank").append(
        "Total Pot: $" + message.Bank
    );
    for (let i = 0; i < message.CardsOnTable.length; i++) {
        $("#grop2").append(
            `<img class="card_image" src="images/cards/${message.CardsOnTable[i].Number}${message.CardsOnTable[i].Suit}.svg">`
        );
    }

}

function showUserInterface(message) {
    var card_flag = true;
    console.log(message);
    $(".userprofile .userinterface_nickname").empty();
    $('.userprofile .userinterface_balance').empty();
    $('.userprofile .userinterface_bet_value').empty();

    $('.userprofile .userinterface_nickname').append(message.Player.name);
    $('.userprofile .userinterface_balance').append(message.Player.balance);
    $('.userprofile .userinterface_bet_value').append(message.Bet);

    if (message.StepTest == true) {
        $('.userprofile').addClass('player_step');
    } else {
        $('.userprofile').removeClass('player_step');
    }


    for (let i = 0; i < message.Cards.length; i++) {
        if (message.Cards[i].Number == 0 || message.Cards[i].Suit == 'N') {
            card_flag = false;
        }
    }

    if (card_flag == true) {
        $('.usercard_block').empty();

        $('.usercard_block').append(
            `<img class="card_image" src="images/cards/${message.Cards[0].Number}${message.Cards[0].Suit}.svg"><img class="card_image last_card" src="images/cards/${message.Cards[1].Number}${message.Cards[1].Suit}.svg">`
        );
    } else if (card_flag == false) {
        $('.usercard_block').empty();
    }

    $("#userinterface").empty();

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

    $("#bet").click(() => sendStep(1));
    $("#call").click(() => sendStep(2));
    $("#check").click(() => sendStep(3));
    $("#start").click(() => sendStep(4));

    return message.Player.name;
}


$(function () {
    connect();

    $("form").on('submit', (e) => e.preventDefault());
    // $( "#send" ).click(() => sendName());

});

