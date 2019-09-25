var editor = new Vue({
    el:'#edit',
    data:{
        api:{},
        fields:[],
        edit:[],
        categories:[],
        turns:[],
        units:[],
        report:{},
        storages:{},
        errors:{
            turn:false
        },
        commentInput:''

    },
    methods:{
        addField:function(field){
            field.value = 0;
            field.comment = '';
            field.editComment = false;
            this.fields.push(field);
            this.sortFields();
        },
        sortFields:function(){
            this.fields.sort(function(a, b){
                if (a.category && b.category){
                    return b.category.number - a.category.number;
                } else if (a.category){
                    return 1;
                } else if (b.category){
                    return -1;
                } else {
                    return 0;
                }
            })
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
        openComment:function(field){
            this.commentInput = '';
            const self = this;
            for (var i in this.fields){
                if (this.fields.hasOwnProperty(i)) {
                    this.fields[i].editComment = this.fields[i] == field;
                    if (this.fields[i].editComment){
                        this.commentInput = this.fields[i].comment;
                        setTimeout(function(){
                            self.$refs.commentInput[0].select()
                        }, 100)
                    }
                }
            }
        },
        editComment:function(id){
            var field = this.fields[id];
            field.comment = this.commentInput;
            field.editComment = false;
            this.commentInput = '';
        },
        getSettings:function(field){
            return this.edit[field.setting];
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