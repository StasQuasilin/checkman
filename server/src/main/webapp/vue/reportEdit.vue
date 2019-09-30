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
            turn:false,
            categoryName:false,
            newFieldName:false
        },
        commentInput:'',
        newField:false,
        newCategory:false,
        field:{},
        category:{}
    },
    methods:{
        selectDate:function(){
            const self = this;
            datepicker.show(function(a){
                self.report.date = a;
            }, this.report.date)
        },
        addField:function(field){

            if (!field.value) {
                field.value = 0;
            }
            if (!field.comment){
                field.comment = '';
            }

            field.editComment = false;

            console.log(field);

            this.fields.push(field);
            this.sortFields();
        },
        sortFields:function(){
            this.fields.sort(function(a, b){
                if (a.category && b.category){
                    return a.category.number - b.category.number;
                } else if (a.category){
                    return 1;
                } else {
                    return -1;
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
                var fields = this.fields;
                fields.forEach(function(item){
                    delete item['editComment'];

                    if (item.category == null){
                        delete item['category'];
                    }
                    if (item.storage == null){
                        delete item['storage'];
                    }
                });
                PostApi(this.api.save, {report: this.report, fields: this.fields}, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        },
        addNewField:function(){
            var f = this.field;
            var err = this.errors.newFieldName = f.title == '' && f.storage == -1;
            if (!err) {
                var category = null;
                if (f.category != -1) {
                    category = this.categories[f.category];
                }
                var storage = null;

                var field = {
                    category: category,
                    title: f.title,
                    unit: f.unit,
                    once: f.once
                };

                if (f.storage != -1) {
                    field.storage = f.storage;
                }

                this.addField(field);
                this.newField = false;
                this.initField();
            }
        },
        initField:function(){
            this.field = {
                category:-1,
                title:'',
                unit:this.units[0].id,
                storage:-1,
                once:false
            }
        },
        initCategory:function(){
            this.category = {
                id:-1,
                title:'',
                once:false
            }
        },
        saveCategory:function(){
            var err = this.errors.categoryName = this.category.title == '';
            if (!err){
                this.categories.push(this.category);
                this.initCategory();
                this.newCategory = false;
            }
        }
    }
});