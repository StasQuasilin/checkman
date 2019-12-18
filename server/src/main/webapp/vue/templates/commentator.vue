var commentator = {
  props:{
    save:String,
    header:String
  },
    data:function(){
      return {
          edit:false
      }
    },
    template:
    '<div style="display: inline-block">' +
        '<div v-if="edit">' +
            '<input>' +
        '</div>' +
        '<a v-else>' +
            '{{header}}' +
        '</a>' +
    '</div>'
};