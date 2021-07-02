commentator = {
  props:{
    fields:Object,
    target:Object,
    editField:String,
    item:Object,
    props:Object
  },
    data:function(){
      return {

      }
    },
    methods:{
      editNote:function (id) {
        loadModal(this.props.api, {note:id,transportation:this.item.id}, function (a) {
          console.log(a);
        })
      }
    },
    template:
    '<div style="display: inline-block">' +
        '<span v-for="(note, noteIdx) in item.notes" style="padding-right: 2pt">' +
            '<template v-if="note.creator">' +
                '{{note.creator}}: ' +
            '</template>' +
            '<b style="font-style: italic">' +
                '\"{{note.note}}\"' +
            '</b>' +
            '<template v-if="noteIdx < item.notes.length - 1">, </template>' +
        '</span>' +
        // '<span class="mini-close" v-on:click.stop="editNote(-1)">' +
        //     '{{props.fields.addNote}}' +
        // '</span>' +
    '</div>'
};