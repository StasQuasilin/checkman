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
        },
        filter:filter_control
    },
    mounted:function(){
        this.filter.items = this.items;
    },
    beforeDestroy:function(){
        console.log('before destroy')
    },
    methods:{
        filteredItems:function(){
            if (typeof this.filter.filteredItems === 'function') {
                return this.filter.filteredItems();
            } else {
                return this.items;
            }
        },
        setUrls:function(url, show){
            this.url = url;
            this.showLink = show;
            this.doRequest()
        },
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
        show:function(id, type){
            loadModal(this.showLink + '?id=' + id)
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
            PostApi(this.url, parameters, function(e){
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
            }, 1000)
        },
        sort:function(){
            this.items.sort(function(a, b){
                return new Date(b.item.date) - new Date(a.item.date);
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
        },
        rowName:function(date){
            return 'container-item-' + new Date(date).getDay();
        },

    }
});