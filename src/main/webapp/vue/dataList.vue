var list = new Vue({
    el: '#container',
    data:{
        api:{
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
        if (typeof filter_control !== 'undefined'){
            filter_control.items = this.items;
            if (typeof filter_control.filteredItems === 'function') {
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
            this.sort();
        },
        update:function(item){
            var found = false;
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    if (this.items[i].item.id == item.id) {
                        found = true;
                        this.items[i].item = item;
                        break;
                    }
                }
            }
            if (!found){
                this.add(item);
            }
            this.sort();
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
            this.sort();
        },
        handler:function(e){
            const self = this;
            if (e.add){
                for(var a in e.add){
                    if (e.add.hasOwnProperty(a)) {
                        self.add(e.add[a])
                    }
                }
            }
            if (e.update) {
                for (var u in e.update) {
                    if (e.update.hasOwnProperty(u)) {
                        self.update(e.update[u]);
                    }
                }
            }
            if (e.remove){
                for(var r in e.remove){
                    if (e.remove.hasOwnProperty(r)) {
                        self.drop(e.remove[r])
                    }
                }
            }
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
        },
        openModal:function(url, attr){
            loadModal(url, attr)
        }
    }
});