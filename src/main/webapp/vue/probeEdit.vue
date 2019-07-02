/**
 * Created by quasilin on 01.04.2019.
 */
var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            findManagerAPI:'',
            findOrganisationAPI:'',
            saveAPI:''
        },
        probe:{},
        laborants:[],
        managerInput:'',
        foundManagers:[],
        organisationInput:'',
        foundOrganisations:[],
        fnd:-1
    },
    methods:{
        findManager:function(){
            clearTimeout(this.fnd);
            const self = this;
            this.fnd = setTimeout(function(){
                var parameters = {};
                parameters.key = self.managerInput;
                PostApi(self.api.findManagerAPI, parameters, function(a){
                    self.foundManagers = a;
                })
            }, 500);
        },
        setManager:function(manager){
            this.probe.manager = manager.id;
            this.managerInput = manager.person.value;
            this.foundManagers = [];
        },
        findOrganisation:function(){
            clearTimeout(this.fnd);
            const self = this;
            this.fnd = setTimeout(function(){
                var parameters = {};
                parameters.key = self.organisationInput;
                PostApi(self.api.findOrganisationAPI, parameters, function(a){
                    self.foundOrganisations = a;
                })
            }, 500);
        },
        setOrganisation:function(organisation){
            this.probe.organisation = organisation.value;
            this.organisationInput = organisation.value;
            this.foundOrganisations = [];
        },
        save:function(){
            PostApi(this.api.saveAPI, this.probe, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});