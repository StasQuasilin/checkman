/**
 * Created by szpt_user045 on 11.07.2019.
 */
var Settings = {
    protocol:'ws',
    address:'localhost:8080',
    context:'',
    api:'/api/subscribes',
    getAddress:function(){
        return this.protocol + '://' + this.address + this.context + this.api;
    }
};

const subscriber = new WebSocket(Settings.getAddress());
subscriber.onmessage = function(env){
    var json = JSON.parse(env.data);
    var type = json['type'];
    var data = JSON.parse(json['data']);
    console.log('--------');
    console.log('Receive type:'+type+', data');
    console.log(data);
    console.log('--------');
    subscribes[type] ( data );
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
    } else{
        console.log('subscriber state is ' + subscriber.readyState);
        setTimeout(function(){
            send(msg);
        }, 500);
    }
}