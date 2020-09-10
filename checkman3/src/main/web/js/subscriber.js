subscriber = {
    address:'',
    handlers:{},
    closableSubscribes:[],
    socket:null,
    connect(){
        const self = this;
        this.socket = new WebSocket(this.address);
        this.socket.onmessage = function (env) {
            let json = JSON.parse(env.data);
            console.log(json);
            let type = json['subscriber'];
            let data = json['data'];
            let handler = self.handlers[type];
            if (typeof  handler === 'function') {
                handler(data);
            } else{
                console.log('Subscribe \'' + type + '\' = ' + typeof handler);
            }
        }
    },
    subscribe(subscribe, handler) {
        this.foreverSubscribe(subscribe, handler);
        this.closableSubscribes.push(subscribe);
        this.send(JSON.stringify({
            action:'subscribe',
            subscriber:subscribe,
            worker:worker
        }));
    },
    unsubscribe(){
        for (let i = 0; i < this.closableSubscribes.length;i++){
            let sub = this.closableSubscribes[i];
            this.closeSubscribe(sub);
        }
        this.closableSubscribes = [];
    },
    closeSubscribe(sub){
        this.send(JSON.stringify({
            action:'unsubscribe',
            subscriber:sub,
            worker:worker
        }));
        delete this.handlers[sub];
    },
    foreverSubscribe(subscribe, handler){
        this.handlers[subscribe] = handler;
    },
    send(msg){
        if (this.socket.readyState === WebSocket.OPEN){
            this.socket.send(msg);
        } else if (this.socket.readyState === WebSocket.CONNECTING) {
            console.log('wait');
            const self = this;
            setTimeout(function(){
                self.send(msg);
            }, 500);
        }
    },
    closeAll(){
        let keys = Object.keys(this.handlers);
        for(let key in keys){
            if (keys.hasOwnProperty(key)){
                this.closeSubscribe(key);
            }
        }
    }
};