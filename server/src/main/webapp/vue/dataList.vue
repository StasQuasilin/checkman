var list = new Vue({
    el: '#container',
    data:{
        api:{
            edit:'',
            show:'',
            archive:''
        },
        items:[],
        timeout:-1,
        attributes:{},
        types:{},
        menu:{
            id:-1,
            item:{},
            show:false,
            x:0,
            y:0
        },
        timer :1000,
        limit:-1
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
                        break;
                    }
                }
            }
            if (!doNot) {
                this.items.push({item: item});
                this.sort();
                if (this.limit > 0){
                    this.clean();
                }
            }
        },
        clean:function(){
            if (this.items.length > this.limit){
                this.items.splice(this.items.length - 1, 1);
                this.clean();
            }
        }
        ,
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
            } else {
                this.sort();
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
            if (filter_control.checkFilter) {
                filter_control.checkFilter();
            }
        },
        sort:function(){
            this.items.sort(function(a, b){

                return new Date(a.item.date) - new Date(b.item.date);
            })
        },
        contextMenu:function(item){
            this.menu.id = item.id;
            this.menu.item = item;
            this.menu.x = event.pageX;
            this.menu.y = event.pageY;
            this.menu.show = true;
            const self = this;
            var menu = this.$refs.contextMenu;
            if (menu){
                setTimeout(function(){
                    var menuBottom = menu.getBoundingClientRect().bottom;
                    var screenBottom = document.body.getBoundingClientRect().bottom;
                    if (menuBottom > screenBottom){
                        self.menu.y += self.menu.y - menuBottom;
                    }
                }, 0);
            } else {
                console.log('Context menu not connected... For fix it add attribute \'ref="contextMenu"\'')
            }

            event.preventDefault();
        },
        closeMenu:function(){
            this.menu.show = false;
            //event.preventDefault();
        },
        edit:function(id){
            if (this.api.edit){
                loadModal(this.api.edit + '?id=' + id, {id : id})
            }
        },
        show:function(id){
            if (this.api.show) {
                loadModal(this.api.show + '?id=' + id, {id: id})
            }
        },
        archive:function(id){
            if (this.api.archive) {
                PostApi(this.api.archive, {id: id});
            }
        },
        getItems:function(){
            return this.items;
        },
        openModal:function(url, attr){
            loadModal(url, attr)
        }
    }
});