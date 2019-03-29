var deamon = new Vue({
    el: '#container',
    data:{
        url:'',
        showLink:'',
        itemClass: function() {
            return 'custom-item-'
        },
        items:[],
        timeout:-1,
        types:[],
        menu:{
            id:-1,
            show:false,
            x:0,
            y:0
        }
    },
    methods:{
        setUrls:function(url, show){
            this.url = url;
            this.showLink = show;
            this.doRequest()
        },
        add:function(item){
            var data = {};
            data.className = 'container-item-' + new Date(item.date).getDay();
            data.item = item;
            this.items.push(data);
        },
        update:function(item){
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    if (this.items[i].item.id === item.id) {
                        this.items[i].className = 'container-item-' + new Date(item.date).getDay();
                        this.items[i].item=item;
                        break
                    }
                }
            }

        },
        drop:function(id){
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    if (this.items[i].item.id === id) {
                        this.items.splice(i, 1);
                        break
                    }
                }
            }
        },
        show:function(id, type){
            loadModal(this.showLink + '?id=' + id)
        },
        doRequest:function(){
            const self = this;
            var parameters = {};
            for (var k in this.items){
                if (this.items.hasOwnProperty(k)) {
                    var item = this.items[k];
                    parameters[item.item.id] = item.item.hash;
                }
            }
            PostApi(this.url, parameters, function(e){
                if (e.add.length || e.update.length || e.delete.length) {
                    console.log(e);
                    for(var a in e.add){
                        self.add(e.add[a])
                    }
                    for(var u in e.update){
                        self.update(e.update[u])
                    }
                    for(var d in e.delete){
                        self.drop(e.delete[d])
                    }
                    self.sort();
                }
                self.timeout = setTimeout(function(){
                    self.doRequest();
                }, 1000)
            })
        },
        sort:function(){
            this.items.sort(function(a, b){
                return new Date(a.item.date) - new Date(b.item.date);
            })
        },
        stop:function(){
            console.log('Stop deamon list');
            clearTimeout(this.timeout)
        },
        contextMenu:function(id){
            this.menu.id = id;
            this.menu.x = event.pageX;
            this.menu.y = event.pageY;
            this.menu.show = true;
            event.preventDefault();
        },
        closeMenu:function(){
            this.menu.show = false;
            //event.preventDefault();
        }

    }
});