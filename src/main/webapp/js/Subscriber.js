/**
 * Created by szpt_user045 on 11.07.2019.
 */
var Settings = {
    protocol:'ws',
    address:'localhost:3332',
    context:'/che',
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
    subscribes[type] ( data );
};
var subscribes = {};
function subscribe(sub, on){
    console.log('Subscribe on ' + sub);
    subscriber.send(JSON.stringify({action:'subscribe', subscriber:sub}));
    subscribes[sub] = on;
}
function unSubscribe(sub){
    console.log('Unsubscribe ' + sub);
    subscriber.send(JSON.stringify({action:'unsubscribe', subscriber:sub}));
    delete subscribes[sub];
}