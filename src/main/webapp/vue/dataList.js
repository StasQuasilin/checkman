var list = new Vue({
    el: '#container',
    data:{
        api:{
            update:'',
            edit:'',
            show:''
        },
        items:[],
        timeout:-1,
        attributes:{},
        types:{},
        menu:{
            id:-1,
            show:false,
            x:0,
            y:0
        },
        timer :1000
    },
    mounted:function(){
        console.log('list mounted')
        console.log(typeof (filter_control));
        if (typeof filter_control !== 'undefined'){
            console.log('do filter')
            filter_control.items = this.items;
            if (typeof filter_control.filteredItems === 'function') {
                console.log('do function')
                this.getItems = function () {
                    return filter_control.filteredItems();
                }
            }
        }
    },
    methods:{
        add:function(item){
            var doNot = false;
            for (var a in this.items){
                if (this.items.hasOwnProperty(a)){
                    if (this.items[a].item.id == item.id){
                        doNot = true;
                    }
                }
            }
            if (!doNot) {
                this.items.push({item: item});
            }
        },
        update:function(item){
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    if (this.items[i].item.id == item.id) {
                        this.items[i].item = item;
                        break;
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
        doRequest:function(){
            const self = this;
            var parameters = {};
            for (var k in self.items){
                if (self.items.hasOwnProperty(k)) {
                    var item = self.items[k].item;
                    parameters[item.id] = item.hash;
                }
            }
            for (var a in this.attributes){
                if (this.attributes.hasOwnProperty(a)){
                    parameters[a] = this.attributes[a];
                }
            }
            PostApi(this.api.update, parameters, function(e){
                if (e.add.length || e.update.length || e.remove.length) {
                    console.log(e);
                    for(var a in e.add){
                        self.add(e.add[a])
                    }
                    for(var u in e.update){
                        self.update(e.update[u])
                    }
                    for(var d in e.remove){
                        self.drop(e.remove[d])
                    }
                    self.sort();
                }
            })
            self.timeout = setTimeout(function(){
                self.doRequest();
            }, self.timer)
        },
        sort:function(){
            this.items.sort(function(a, b){
                return new Date(b.item.date) - new Date(a.item.date);
            })
        },
        stop:function(){
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
        },
        edit:function(id){
            const self = this;
            loadModal(this.api.edit + '?id=' + id, {id : id}, function(){
                self.doRequest();
            })
        },
        show:function(id){
            loadModal(this.api.show + '?id=' + id, {id : id})
        },
        getItems:function(){
            return this.items;
        }
    }
});