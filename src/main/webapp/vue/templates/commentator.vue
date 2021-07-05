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
        // '{{item.noteMap}}' +
        '<span v-for="(notes, key) in item.noteMap" style="padding-right: 2pt">' +
            '<template v-if="key !== \'null\'">' +
                '{{key}}: ' +
            '</template>' +
            '<template v-for="(note, noteIdx) in notes">' +
                '<b style="font-style: italic">' +
                    '{{note.note}}' +
                '</b>' +
                '<template v-if="noteIdx < notes.length - 1">, </template>' +
            '</template>' +
        '</span>' +
        '<span class="mini-close" v-on:click.stop="editNote(-1)">' +
            '{{props.fields.addNote}}' +
        '</span>' +
    '</div>'
};