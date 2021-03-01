probeView = {
    props:{
        item:Object,
        fields:Object
    },
    computed:{
        className:function () {
            if(this.item) {
                return 'container-item-' + new Date(this.item.date).getDay()
            }
        }
    },
    template:'<div class="container-item" :class="className" style="flex-direction: column">' +
            '<div class=""></div>' +
            '<div class="upper-row"">' +
                '{{new Date(item.date).toLocaleDateString()}} ' +
                '{{new Date(item.date).toLocaleTimeString().substring(0, 5)}} ' +
                '{{fields.turn}} #{{item.number}}' +
            '</div>' +
            '<div class="middle-row" style="display: block">' +
                '<div v-if="item.sun.length > 0" style="width: 100%">' +
                    '<b>{{fields.sun.title}}</b>' +
                    '<table class="probe-table">' +
                        '<tr>' +
                            '<th>' +
                                '{{fields.sun.humidity}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.sun.soreness}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.sun.oiliness}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.sun.impurity}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.sun.acid}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.organisation}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.manager}}' +
                            '</th>' +
                        '</tr>' +
                        '<tr v-for="sun in item.sun">' +
                            '<td>' +
                                '{{sun.analyses.humidity1}}' +
                            '</td>' +
                            '<td>' +
                                '{{sun.analyses.soreness}}' +
                            '</td>' +
                            '<td>' +
                                '{{sun.analyses.oiliness}}' +
                            '</td>' +
                            '<td>' +
                                '{{sun.analyses.oilImpurity}}' +
                            '</td>' +
                            '<td>' +
                                '{{sun.analyses.acidValue}}' +
                            '</td>' +
                            '<td>' +
                                '{{sun.organisation}}' +
                            '</td>' +
                            '<td>' +
                                '{{sun.manager}}' +
                            '</td>' +
                        '</tr>' +
                    '</table>' +
                '</div>' +
                '<div v-if="item.oil.length > 0">' +
                    '<b>{{fields.oil.title}}</b>' +
                    '<table class="probe-table">' +
                        '<tr>' +
                            '<th>' +
                                '{{fields.oil.organoleptic}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.oil.color}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.oil.acid}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.oil.peroxide}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.oil.phosphorus}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.oil.wax}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.oil.soap}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.organisation}}' +
                            '</th>' +
                            '<th>' +
                                '{{fields.manager}}' +
                            '</th>' +
                        '</tr>' +
                        '<tr v-for="oil in item.oil">' +
                            '<td>' +
                                '<template v-if="oil.analyses.organoleptic">' +
                                    '{{fields.oil.organolepticYes}}' +
                                '</template>' +
                                '<template v-else>' +
                                    '{{fields.oil.organolepticNo}}' +
                                '</template>' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.color}}' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.acid}}' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.peroxide}}' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.phosphorus}}' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.wax}}' +
                            '</td>' +
                            '<td>' +
                                '<template v-if="oil.analyses.soap">' +
                                    '+' +
                                '</template>' +
                                '<template v-else>' +
                                    '-' +
                                '</template>' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.organisation}}' +
                            '</td>' +
                            '<td>' +
                                '{{oil.analyses.manager}}' +
                            '</td>' +
                        '</tr>' +
                        '</tr>' +
                    '</table>' +
                '</div>' +
            '</div> ' +
        '</div>'
};