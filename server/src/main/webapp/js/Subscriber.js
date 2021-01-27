/**
 * Created by szpt_user045 on 11.07.2019.
 */
const address = Settings.getAddress();
let subscriber;
let subscribes = {};


function Connect(){
    locker.reconnect = true;
    subscriber = new WebSocket(address);
    subscriber.onerror = function(){
        console.log('Error on socket connection');
    };

    subscriber.onclose = function(cause){
        console.log('Close socket connection');
        console.log(cause);
        if(cause.code === 1000 || cause.code === 1006) {
            restart(10);
        }
    };
    subscriber.onmessage = function(env){
        let json = JSON.parse(env.data);
        let type = json['type'];
        let data = json['data'];
        if (typeof subscribes[type] === 'function') {
            subscribes[type](data, type);
        } else{
            console.log('Subscribe \'' + type + '\' = ' + typeof subscribes[type]);
        }
    };
    subscriber.onopen = function () {
        if (locker.show){
            location.reload();
        }
        console.log('Connection successfully');

        let hello = {
            action:'hello',
            token:Settings.token
        };
        send(JSON.stringify(hello));
    }
}

function subscribe(sub, on){
    if (typeof subscribes === "undefined"){
        subscribes = {};
    } else {
        subscribes[sub] = on;
        send(JSON.stringify({action: 'subscribe', subscriber: sub, worker: Settings.worker}));
    }
}
function unSubscribe(sub){
    send(JSON.stringify({action:'unsubscribe', subscriber:sub}));
    delete subscribes[sub];
}
function send(msg){
    if (typeof subscriber === "undefined"){
        setTimeout(function () {
            send(msg)
        }, 1000)
    } else {
        if (subscriber.readyState === WebSocket.OPEN){
            subscriber.send(msg);
        } else if (subscriber.readyState === WebSocket.CONNECTING) {
            setTimeout(function(){
                send(msg);
            }, 500);
        } else {
            console.log(subscriber.readyState);
            restart(5);
        }
    }

}
function restart(sec){
    locker.reconnect = false;
    locker.show = true;
    locker.timeLeft = sec;
    if (sec > 0) {
        locker.timeLeft = sec;
        console.log('Reconnect after ' + sec + ' seconds...');
        setTimeout(function(){
            restart(--sec)
        }, 1000)
    } else {
        console.log('Reconnect...');
        Connect();
        //location.reload()
    }

}
function closeConnection(){
    console.log('Close subscriber connection');
    subscriber.close();
}
