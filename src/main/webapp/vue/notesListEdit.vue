var notes = new Vue({
    el:'#notes',
    data:{
        api:{
            update:'',
            save:'',
            remove:''
        },
        plan:-1,
        notes:[],
        timer:-1,
        worker:{},
        noteInput:''
    },
    methods:{
        doRequest:function(){
            this.stop();
            var notes = {};
            for (var i in this.notes){
                if (this.notes.hasOwnProperty(i)){
                    var note = this.notes[i];
                    notes[note.id] = note.note;
                }
            }
            const self = this;
            PostApi(this.api.update, {plan:this.plan, notes:notes},function(a){
                if (a.add.length || a.update.length || a.remove.length){
                    for (var i in a.add){
                        if (a.add.hasOwnProperty(i)){
                            self.add(a.add[i]);
                        }
                    }
                    for (var u in a.update){
                        if (a.update.hasOwnProperty(u)){
                            self.update(a.update[u]);
                        }
                    }
                    for (var r in a.remove){
                        if (a.remove.hasOwnProperty(r)){
                            self.remove(a.remove[r]);
                        }
                    }
                }
                self.timer = setTimeout(function(){
                    self.doRequest()
                }, 1000)
            });
        },
        add:function(note){
            var found = false;
            this.notes.forEach(function(item){
                if (item.id === note.id){
                    found = true;
                }
            });
            if (!found){
                this.notes.push(note)
            }
        },
        update:function(note){
            this.remove(note.id);
            this.add(note);
        },
        remove:function(id){
            for (var i in this.notes){
                if (this.notes.hasOwnProperty(i)){
                    var n = this.notes[i];
                    if (n.id == id){
                        this.notes.splice(i, 1);
                        break;
                    }
                }
            }
        },
        stop:function(){
            clearTimeout(this.timer);
        },
        saveNote:function(){
            const self =this;
            PostApi(this.api.save, {plan:this.plan, note:this.noteInput}, function(a){
                if (a.status === 'success'){
                    self.noteInput = '';
                    self.doRequest();
                }
            })
        },
        removeNote:function(id){
            const self = this;
            PostApi(this.api.remove, {id:id}, function(a){
                if(a.status == 'success'){
                    self.doRequest();
                }
            })
        }
    }
})