var editor = new Vue({
    el:'#edit',
    data:{
        api:{},
        fields:{},
        settings:{},
        categories:[],
        turns:[],
        units:[],
        report:{},
        storages:{},
        errors:{
            turn:false
        }
    },
    methods:{
        addField:function(field, category){
            var categoryId = null;
            if (category){
                categoryId = category.id;
            }
            if (!this.fields[categoryId]){
                this.fields[categoryId] = [];
                this.categories.push(category);
            }
            field.value = 0;
            field.comment = '';
            this.fields[categoryId].push(field);
            this.settings[field.setting] = {
                comment: false
            }
        },
        getFields:function(category){
            if (category){
                return this.fields[category.id];
            } else{
                return this.fields[null];
            }
        },
        getCategories:function(){
            return this.categories;
        },
        editComment:function(field, edit){
            for (var i in this.settings){
                if (this.settings.hasOwnProperty(i)) {
                    if (i == field) {
                        this.settings[i].comment = edit;
                    } else {
                        this.settings[i].comment = false;
                    }
                }
            }
        },
        getSettings:function(field){
            return this.settings[field.id];
        },
        save:function(){
            console.log(this.report);
            this.errors.turn = this.report.turn == -1;
            if (!this.errors.turn){
                PostApi(this.api.save, {report: this.report, fields: this.fields}, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        }
    }
});