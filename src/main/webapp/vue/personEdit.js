var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            saveDriverAPI:''
        },
        person:{
            id:-1,
            forename:'',
            surname:'',
            patronymic:'',
            phones:{}
        },
        transportationId:''

    },methods:{
        newPhone:function(){
            this.addPhone({id:-1, value:''})
        },
        addPhone:function(phone){
            Vue.set(this.phones, this.phones.length, phone)
        },
        removePhone:function(id){
            Vue.delete(this.phones, id)
        },
        save:function(){
            var result = {};
            if (this.person.id > 0) {
                result.id = this.person.id;
            }
            result.forename = this.person.forename;
            result.surname = this.person.surname;
            result.patronymic = this.person.patronymic;
            if (this.transporterId) {
                result.transportation_id = this.transportationId;
            }
            console.log(result);

            PostApi(this.api.saveDriverAPI, result, function(a){
                saveModal(a);
                closeModal();
            })
        }
    }

});