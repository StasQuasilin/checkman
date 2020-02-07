/**
 * Created by szpt_user045 on 11.07.2019.
 */
var address = Settings.getAddress();
var subscriber;
Connect();

function Connect(){
    console.log('Connect to ' + address);
    subscriber = new WebSocket(address);
    subscriber.onerror = function(){
        console.log('Error on socket connection');
    };

    subscriber.onclose = function(cause){
        console.log('Close socket connection');
        console.log(cause);
        if(cause.code == 1000 || cause.code == 1006) {
            restart(3);
        }
    };
    subscriber.onmessage = function(env){
        var json = JSON.parse(env.data);
        var type = json['type'];
        var data = json['data'];
        if (typeof subscribes[type] === 'function') {
            subscribes[type](data, type);
        } else{
            console.log('Subscribe \'' + type + '\' = ' + typeof subscribes[type]);
        }
    };
}

var subscribes = {};
function subscribe(sub, on){
    subscribes[sub]=on;
    send(JSON.stringify({action:'subscribe', subscriber:sub, worker:Settings.worker}));
}
function unSubscribe(sub){
    send(JSON.stringify({action:'unsubscribe', subscriber:sub}));
    delete subscribes[sub];
}
function send(msg){
    if (subscriber.readyState == WebSocket.OPEN){
        subscriber.send(msg);
    } else if (subscriber.readyState == WebSocket.CONNECTING) {
        setTimeout(function(){
            send(msg);
        }, 500);
    } else {
        restart(1);
    }
}
function restart(sec){
    if (sec > 0) {
        console.log('Restart after ' + sec + ' seconds...');
        setTimeout(function(){
            restart(--sec)
        }, 1000)
    } else {
        location.reload()
    }

}
function closeConnection(){
    console.log('Close subscriber connection');
    subscriber.close();
}
