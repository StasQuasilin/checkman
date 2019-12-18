var commentator = {
  props:{
    fields:Object,
    target:Object,
    editField:String
  },
    data:function(){
      return {
          edit:false,
          editTarget:function(t){
              console.log(t);
              event.preventDefault();
          }
      }
    },
    methods:{
        openInput:function(){
            this.edit = !this.edit;
        },

        saveTarget:function(){
            console.log(target)
        }
    },
    template:
    '<div style="display: inline-block">' +
        '<div v-if="edit">' +
            '<input v-on:keyup.enter="saveTarget()" v-on:keyup.escape="openInput()">' +
            '<span class="mini-close" v-on:click="openInput()">&times;</span>' +
        '</div>' +
        '<a v-else v-on:click="openInput()">' +
            '{{fields.header}}' +
        '</a>' +
    '</div>'
};