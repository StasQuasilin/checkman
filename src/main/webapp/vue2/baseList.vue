bl = {
    data:{
        api:{},
        items:{},
        filter:null
    },
    mounted:function(){
    },
    methods:{
        handle:function(data){
            console.log(data);
            if (data.add){
                for (let a in data.add){
                    if (data.add.hasOwnProperty(a)){
                        let update = data.add[a];
                        this.items[update.id] = update;
                    }
                }
            }
            if(data.update){
                for (let u in data.update){
                    if (data.update.hasOwnProperty(u)){
                        let update = data.update[u];
                        this.items[update.id] = update;
                    }
                }
            }
            if (data.remove){
                for (let r in data.remove){
                    if (data.remove.hasOwnProperty(r)){
                        let remove = data.remove[r];
                        delete this.items[remove];
                    }
                }
            }
            if (data.add || data.update || data.remove){
                this.$forceUpdate();
                if(this.filter) {
                    this.filter.items = Object.assign({}, this.items);
                    this.filter.$forceUpdate();
                }
            }
        },
        getItems:function () {
            let items = (this.filter && typeof this.filter.getFilteredItems === "function") ? this.filter.getFilteredItems() : Object.values(this.items);

            if (typeof this.sort === "function"){
                return items.sort(this.sort);
            } else {
                console.log('Function \'Sort\' not realized!');
            }
            return items;
        }
    }
};