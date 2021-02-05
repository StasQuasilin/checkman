list = new Vue({
    el: '#container',
    components:{
        'transport-view':transportView,
        'laboratory-view':laboratoryView,
        'price-view':priceView,
        'commentator':commentator
    },
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
        limit:-1,
        loading:false,
        filter:null
    },
    mounted:function(){
        this.filter = transportFilter;
    },
    methods:{
        clean:function(){
            if (this.items.length > this.limit){
                this.items.splice(this.items.length - 1, 1);
                this.clean();
            }
        }
        ,
        update:function(item){
            let found = false;
            for(let i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    if (this.items[i].item.id === item.id) {
                        found = true;
                        this.items[i].item = item;
                        break;
                    }
                }
            }
            if (!found){
                this.items.push({item: item});
                this.sort();
                if (this.limit > 0){
                    this.clean();
                }
            }

            this.sort();

        },
        drop:function(id){
            for(let i in this.items){
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
                for(let a in e.add){
                    if (e.add.hasOwnProperty(a)) {
                        self.update(e.add[a]);
                    }
                }
            }
            if (e.update) {
                for (let u in e.update) {
                    if (e.update.hasOwnProperty(u)) {
                        self.update(e.update[u]);
                    }
                }
            }
            if (e.remove){
                for(let r in e.remove){
                    if (e.remove.hasOwnProperty(r)) {
                        self.drop(e.remove[r])
                    }
                }
            }
            this.filter.items = {};
            if (typeof this.filter !== 'undefined'){
                for (let i in this.items){
                    if (this.items.hasOwnProperty(i)){
                        let item = this.items[i].item;
                        this.filter.items[item.id] = item;
                    }
                }
            }
        },
        sort:function(){
            this.items.sort(function(a, b){
                return new Date(b.item.date) - new Date(a.item.date);
            })
        },
        contextMenu:function(item){
            this.menu.id = item.id;
            this.menu.item = item;
            this.menu.x = event.pageX;
            this.menu.y = event.pageY;
            this.menu.show = true;
            const self = this;

            let menu = this.$refs.contextMenu;
            if (menu){
                setTimeout(function(){
                    let menuBottom = menu.getBoundingClientRect().bottom;
                    let screenBottom = document.body.getBoundingClientRect().bottom;
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
            console.log('archive ' + id);
            if (this.api.archive) {
                PostApi(this.api.archive, {id: id});
            }
        },
        getItems:function(){
            let items = this.items;
            if (this.filter){
                const self = this;
                return items.filter(function (item) {
                    return self.filter.doFilter(item.item);
                })
            }
            return items;
        },
        openModal:function(url, attr){
            loadModal(url, attr)
        },
        canArchiveThis:function (item) {
            let date = new Date(item.date);
            let now = new Date();
            return item.any && now - date > 24 * 60 * 60 * 1000;
        }
    }
});