addressEditor = new Vue({
    el:'#addressEditor',
    components:{
        'object-input':objectInput
    },
    data:{
        api:{},
        counterpartyProps:{
            header:'<fmt:message key="deal.organisation"/>',
            show:['value']
        },
        addressTypes:[],
        addressType:'',

        counterparty:{
            id:-1
        },
        address:{
            index:'',
            region:'',
            district:'',
            city:'',
            street:'',
            build:'',
            block:'',
            flat:''
        }
    },
    methods:{
        save:function(){
            PostApi(this.api.save, {counterparty:this.counterparty.id, address:this.address, type:this.addressType}, function(a){
                if(a.status === 'success'){
                    saveModal(a.result);
                    closeModal();
                }
            })
        }
    }
});