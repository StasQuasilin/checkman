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
        products:[],
        calculators:{},
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
        addCategory:function(category){
            this.categories.push(category);
            this.categories.sort(function(a, b){
                return a.number - b.number;
            })
        },
        getUnits:function(fields){
            var units = {};
            for(var i in fields){
                if (fields.hasOwnProperty(i)){
                    var unit = fields[i].unit;
                    if (!units[unit]){
                        units[unit] = this.getUnit(unit);
                    }
                }
            }

            return Object.values(units);
        },
        getUnit:function(id){
            for(var i in this.units){
                if (this.units.hasOwnProperty(i)){
                    if (this.units[i].id === id){
                        return this.units[i];
                    }
                }
            }
        },
        selectDate:function(){
            const self = this;
            datepicker.show(function(a){
                self.report.date = a;
            }, this.report.date)
        },
        getCategory:function(categoryId){
            for (var i in this.categories){
                if(this.categories.hasOwnProperty(i)){
                    var cat = this.categories[i];
                    if (cat.id === categoryId){
                        return cat;
                    }
                }
            }
        },
        addField:function(field, categoryId, categoryName, categoryNumber){

            if (!field.value) {
                field.value = 0;
            }
            if (!field.comment){
                field.comment = '';
            }

            field.editComment = false;
            var category = this.getCategory(categoryId);
            if (category == null){
                category = {
                    id:categoryId,
                    title:categoryName,
                    number:categoryNumber,
                    summary:false,
                    fields:[]
                };
                this.categories.push(category);
            }
            category.fields.push(field);
            this.sortFields();
        },
        sortFields:function(){
            this.categories.forEach(function(category){
               category.fields.sort(function(a, b){
                   return a.number - b.number;
               })
            });
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
                    this.fields[i].editComment = this.fields[i] === field;
                    if (this.fields[i].editComment){
                        this.commentInput = this.fields[i].comment;
                        setTimeout(function(){
                            self.$refs.commentInput[0].select()
                        }, 100)
                    }
                }
            }
        },
        editField:function(field){
            console.log('Edit');
            console.log(field);
            this.newField = true;
            this.field.category = field.category ? field.category.id : null;
            this.field.title = field.title;
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
            this.errors.turn = this.report.turn === -1;
            if (!this.errors.turn){
                var fields = [];
                this.categories.forEach(function(category){
                    category.fields.forEach(function(item){
                        delete item['editComment'];
                        var cat = Object.assign({}, category);
                        delete cat.fields;
                        item.category = cat;
                        fields.push(item);
                    });
                });

                const self = this;
                PostApi(this.api.save, {report: this.report, fields: fields}, function(a){
                    console.log(a);
                    if (a.status === 'success'){
                        closeModal();
                        loadModal(self.api.preview, {id: a.id})
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
                product:-1,
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
        },
        summary:function(category, unit){
            var sum = 0;
            for (var i in category.fields){
                if (category.fields.hasOwnProperty(i)) {
                    if (category.fields[i].unit == unit.id) {
                        sum += parseFloat(category.fields[i].value);
                    }
                }
            }
            return sum;
        }
    }
});