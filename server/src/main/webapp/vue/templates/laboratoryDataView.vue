var laboratoryView = {
    props:{
        item:Object,
        fields:Object
    },
    template:'<div class="right-field-content" style="width: 132pt">' +
        '<div v-if="item.analyses.sun.id">' +
            '<div>{{fields.sun.humidityA}}: {{item.analyses.sun.humidity1}}</div>' +
            '<div>{{fields.sun.humidityB}}: {{item.analyses.sun.humidity2}}</div>' +
            '<div>{{fields.sun.soreness}}: {{item.analyses.sun.soreness}}</div>' +
            '<div>{{fields.sun.impurity}}: {{item.analyses.sun.oilImpurity}}</div>' +
            '<div>{{fields.sun.oiliness}}: {{item.analyses.sun.oiliness}}</div>' +
        '</div>' +
        '<div v-else-if="item.analyses.oil && item.analyses.oil.id">' +
            '<div>{{fields.oil.acid}}: {{item.analyses.oil.acid}}</div>' +
            '<div>{{fields.oil.peroxide}}: {{item.analyses.oil.peroxide}}</div>' +
            '<div>{{fields.oil.phosphorus}}: {{item.analyses.oil.phosphorus}}</div>' +
        '</div>' +
        '<div v-else-if="item.analyses.cake && item.analyses.cake.id">' +
            '<div>{{fields.meal.humidity}}: {{item.analyses.cake.humidity}}</div>' +
            '<div>{{fields.meal.protein}}: {{item.analyses.cake.protein}}</div>' +
            '<div>{{fields.meal.cellulose}}: {{item.analyses.cake.cellulose}}</div>' +
            '<div>{{fields.meal.oiliness}}: {{item.analyses.cake.oiliness}}</div>' +
        '</div>' +
    '</div>'
};