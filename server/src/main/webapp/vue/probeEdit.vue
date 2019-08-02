/**
 * Created by quasilin on 01.04.2019.
 */
var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            findManager:'',
            findOrganisation:'',
            save:''
        },
        probe:{},
        laborants:[],
        foundManagers:[],
        foundOrganisations:[],
        fnd:-1,
        manager:'',
        organisation:''
    },
    methods:{
        findManager:function(){
            clearTimeout(this.fnd);
            var inp = this.probe.manager.value;
            if (inp) {
                const self = this;
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findManager, {key: inp}, function (a) {
                        self.foundManagers = a;
                    })
                }, 500);
            } else {
                self.foundManagers = [];
            }
        },
        setManager:function(manager){
            this.probe.manager = {
                id:manager.id,
                value:manager.person.value
            };
            this.manager = manager.person.value;
            this.foundManagers = [];
        },
        findOrganisation:function(){
            clearTimeout(this.fnd);
            var inp = this.probe.organisation.value;
            if (inp) {
                const self = this;
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findOrganisation, {key : inp}, function (a) {
                        self.foundOrganisations = a;
                    })
                }, 500);
            } else {
                this.foundOrganisations = [];
            }
        },
        setOrganisation:function(organisation){
            this.probe.organisation = {
                id:organisation.id,
                value:organisation.value
            };
            this.organisation = organisation.value;
            this.foundOrganisations = [];
        },
        save:function(){
            if (this.manager !== this.probe.manager.value){
                this.probe.manager.id = -1;
            }
            if (this.organisation !== this.probe.organisation.value){
                this.probe.organisation.id = -1;
            }
            PostApi(this.api.save, this.probe, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});