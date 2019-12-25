/**
 * Created by szpt_user045 on 11.07.2019.
 */
var address = Settings.getAddress();
console.log('Subscribe api ' + address);
var subscriber;
Connect();

function Connect(){
    subscriber = new WebSocket(address);
    //subscriber.onerror = function(){
    //    console.log('Error on socket connection');
    //};
    subscriber.onсlose = function(cause){
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
    console.log('Subscribe on ' + sub);
    subscribes[sub]=on;
    send(JSON.stringify({action:'subscribe', subscriber:sub, worker:Settings.worker}));
}
function unSubscribe(sub){
    console.log('Unsubscribe ' + sub);
    send(JSON.stringify({action:'unsubscribe', subscriber:sub}));
    delete subscribes[sub];
}
function send(msg){
    if (subscriber.readyState == WebSocket.OPEN){
        subscriber.send(msg);
    } else if (subscriber.readyState == WebSocket.CONNECTING) {
        console.log('subscriber state is ' + subscriber.readyState);
        setTimeout(function(){
            send(msg);
        }, 500);
    } else {
        console.log('Subscriber are closed');
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
