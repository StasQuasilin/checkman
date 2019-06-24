var editor = new Vue({
    el: '#personEditor',
    data:{
        api:{
            saveDriverAPI:''
        },
        person:{
            id:-1,
            forename:'',
            surname:'',
            patronymic:'',
            phones:[]
        },
        transportationId:''

    },methods:{
        addPhone:function(phone){
            this.person.phones.push(phone)
        },
        removePhone:function(id){
            this.person.phones.splice(id, 1);
        },
        save:function(){
            var result = {};
            if (this.transportationId){
                result.transportation_id = this.transportationId
            }
            if (this.person.id > 0) {
                result.id = this.person.id;
            }
            result.forename = this.person.forename;
            result.surname = this.person.surname;
            result.patronymic = this.person.patronymic;
            if (this.transporterId) {
                result.transportation_id = this.transportationId;
            }
            PostApi(this.api.saveDriverAPI, result, function(a){
                console.log(a);
                saveModal(a);
                closeModal();
            })
        },
        close:function(){
            closeModal();
        }
    }

});