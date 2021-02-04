bl = {
    data:{
        api:{},
        items:{},
        filter:null
    },
    methods:{
        handle:function(data){
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
            if (data.update || data.remove){
                this.$forceUpdate();
                if(this.filter) {
                    this.filter.items = this.items;
                    this.filter.$forceUpdate();
                }
            }
        },
        getItems:function () {
            let items = Object.values(this.items);
            if(this.filter && typeof this.filter.doFilter === "function"){
                items = items.filter(this.filter.doFilter);
            }
            if (typeof this.sort === "function"){
                return items.sort(this.sort);
            } else {
                console.log('no sort');
            }
            return items;
        }
    }
};