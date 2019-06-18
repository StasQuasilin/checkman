var referenceList = new Vue({
    el:'#referencesList',
    data:{
        api:{
            update:'',
            edit:''
        },
        items:[]
    },
    methods:{
        update:function(){
            const self = this;
            PostApi(this.api.update, null, function(a){
                self.items = a;
                self.sort();
            })
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