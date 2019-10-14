var referenceList = new Vue({
    el:'#referencesList',
    data:{
        api:{
            update:'',
            edit:''
        },
        items:{}
    },
    methods:{
        update:function(){
            const self = this;
            PostApi(this.api.update, null, function(a){
                self.items = a;
                self.getKeys();
            })
        },
        getKeys:function(){
            var keys = Object.keys(this.items);
            keys.sort(function(a, b){
                return a.localeCompare(b);
            });

            return keys;
        },
        sort:function(){
            this.items.sort(function (a, b) {
                return a.localeCompare(b);
            })
        },
        edit:function(id){
            loadModal(this.api.edit + '?id=' + id);
        }
    }
});