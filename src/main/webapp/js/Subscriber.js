/**
 * Created by szpt_user045 on 11.07.2019.
 */
var address = Settings.getAddress();
console.log('Subscribe api ' + address);
var subscriber;
Connect();

function Connect(){
    subscriber = new WebSocket(address);
    subscriber.onmessage = function(env){
        var json = JSON.parse(env.data);
        var type = json['type'];
        var data = JSON.parse(json['data']);
        console.log('--------');
        console.log('Receive type:' + type + ', data');
        console.log(data);
        if (typeof subscribes[type] === 'function') {
            subscribes[type](data);
        } else{
            console.log('Subscribe \'' + type + '\' = ' + typeof subscribes[type]);
        }
    };
}

var subscribes = {};
function subscribe(sub, on){
    console.log('Subscribe on ' + sub);
    send(JSON.stringify({action:'subscribe', subscriber:sub, worker:Settings.worker}));
    subscribes[sub] = on;
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
        restart(5);
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
