noteEdit = new Vue({
   el:'#noteEdit',
   data:{
       api:{},
       note:{
           id:-1,
           note:''
       },
       transportation:-1
   },
    methods:{
       save:function () {
           PostApi(this.api.save, {note:this.note,transportation:this.transportation}, function (a) {
               if(a.status === 'success'){
                   closeModal();
               }
           })
       }
    }
});