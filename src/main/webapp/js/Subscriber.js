/**
 * Created by szpt_user045 on 11.07.2019.
 */
var address = Settings.getAddress();
console.log('Subscribe api ' + address);
const subscriber = new WebSocket(address);

subscriber.onmessage = function(env){
    var json = JSON.parse(env.data);
    var type = json['type'];
    var data = JSON.parse(json['data']);
    console.log('--------');
    console.log('Receive type:' + type + ', data');
    console.log(data);
    console.log('--------');
    if (typeof subscribes[type] === 'function') {
        subscribes[type](data);
    } else{
        console.log(typeof subscribes[type])
    }
};
var subscribes = {};
function subscribe(sub, on){
    console.log('Subscribe on ' + sub);
    send(JSON.stringify({action:'subscribe', subscriber:sub}));
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
        console.log('Subscriber are closed')
    }
}
function closeConnection(){
    subscriber.close();
}
$(window).unload(function(){
    closeConnection();
});