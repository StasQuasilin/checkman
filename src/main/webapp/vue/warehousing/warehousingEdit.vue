edit = new Vue({
    el:'#editor',
    data:{
        api:{},
        document:-1,
        shipper:-1,
        netto:0,
        storages:[],
        shippers:[],
        relations:[]
    },
    methods:{
        remove:function(key){
            this.relations.splice(key, 1);
        },
        add:function(){
            var amount = this.netto - this.total();
            this.relations.push({
                storage:this.storages[0].id,
                shipper:this.shipper,
                amount:amount
            })
        },
        total:function(){
            var total = 0;
            for (var i in this.relations){
                if (this.relations.hasOwnProperty(i)){
                    total += this.relations[i].amount;
                }
            }
            return total;
        },
        save:function(){
            if (this.api.save){
                PostApi(this.api.save, {document: this.document, relations:  this.relations}, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        },
        different:function(){
            var diff = (this.total() - this.netto);

            if (diff == 0){
                return '';
            } else if (diff > 0){
                return '(+' + diff.toLocaleString() + ')?';
            } else {
                return '(' + diff.toLocaleString() + ')!';
            }
        }
    }
});