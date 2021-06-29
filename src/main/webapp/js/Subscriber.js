/**
 * Created by szpt_user045 on 11.07.2019.
 */
const address = Settings.getAddress();
let subscriber;
let subscribes = {};
let temp = [];
let time = 10;


function Connect(){
    locker.reconnect = true;
    subscriber = new WebSocket(address);
    subscriber.onerror = function(e){
        console.log('Error on socket connection :' + e);
    };

    subscriber.onclose = function(cause){
        console.log('Close socket connection');
        console.log(cause);
        if(cause.code === 1000 || cause.code === 1006) {
            restart(time++);
        }
    };
    subscriber.onmessage = function(env){
        try {
            let json = JSON.parse(env.data);
            let type = json['type'];
            let data = json['data'];

            let v = subscribes[type];
            for (let i in v){
                if (v.hasOwnProperty(i)){
                    let f = v[i];
                    let t = typeof f;
                    if (t === 'function') {
                        f(data, type);
                    } else {
                        console.log('Subscribe \'' + type + '\' = ' + t);
                    }
                }
            }

        } catch (e) {
            console.log(e);
            console.log(env)
        }
    };
    subscriber.onopen = function () {
        time = 10;
        if (locker.show){
            loginer.autologin(function (a) {
                if(a.status === 'success'){
                    locker.show = false;
                    for (let sub in subscribes){
                        if (subscribes.hasOwnProperty(sub)){
                            subscribe(sub, subscribes[sub]);
                        }
                    }
                }
            })
        }
        console.log('Connection successfully');

        let hello = {
            action:'hello',
            token:Settings.token
        };
        send(JSON.stringify(hello));
    }
}

function subscribe(sub, on, attributes){
    if (typeof subscribes === "undefined"){
        subscribes = {};
    } else if (!subscribes[sub]){
        subscribes[sub] = [];
    }
    temp.push(sub);
    subscribes[sub].push(on);
    let args = {
        action: 'subscribe',
        subscriber: sub,
        worker: Settings.worker,
        view:Settings.view
    };
    if (attributes != null){
        args = Object.assign(args, attributes);
    }
    send(JSON.stringify(args));
}
function unSubscribe(){
    for (let i in temp){
        if(temp.hasOwnProperty(i)){
            closeSubscribe(temp[i])
        }
    }
    temp = [];
}
function closeAllSubscribes() {
    let keys = Object.keys(subscribes);
    for (let i in keys){
        if (keys.hasOwnProperty(i)){
            closeSubscribe(keys[i]);
        }
    }
}
function closeSubscribe(sub) {
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
            // restart(5);
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
