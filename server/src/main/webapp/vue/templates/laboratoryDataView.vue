var laboratoryView = {
    props:{
        item:Object,
        fields:Object
    },
    template:'<div class="right-field-content">' +
        '<div v-if="item.analyses.sun.id" style="width: 104pt">' +
            '<div>{{fields.sun.humidityA}}:{{item.analyses.sun.humidity1}}</div>' +
            '<div>{{fields.sun.humidityB}}:{{item.analyses.sun.humidity2}}</div>' +
            '<div>{{fields.sun.soreness}}:{{item.analyses.sun.soreness}}</div>' +
            '<div>{{fields.sun.impurity}}:{{item.analyses.sun.oilImpurity}}</div>' +
            '<div>{{fields.sun.oiliness}}:{{item.analyses.sun.oiliness}}</div>' +
        '</div>' +
        '<div v-else-if="item.analyses.oil && item.analyses.oil.id">' +
            'Oil' +
        '</div>' +
        '<div v-else-if="item.analyses.cake && item.analyses.cake.id">' +
            'Cake' +
        '</div>' +
    '</div>'
};