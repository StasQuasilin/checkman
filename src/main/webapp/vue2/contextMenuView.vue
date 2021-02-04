contextMenu = {
    props:{
        menu:Object,
        items:Array
    },
    methods:{
        closeMenu:function () {
            this.menu.show = false;
        },
        clickOn:function (index) {
            let item = this.items[index];
            let onClick = item.onClick;
            if (onClick){
                onClick(this.menu.item.id)
            } else {
                console.warn('No click action for ' + item.title);
            }
        }
    },
    template:'<div v-if="menu.show && items.length > 0" v-on:click="closeMenu" class="menu-wrapper">' +
        '      <div ref="contextMenu" :style="{ top: menu.y + \'px\', left:menu.x + \'px\'}" class="context-menu">' +
        '        <div v-for="(item,idx) in items" class="custom-data-list-item" v-on:click="clickOn(idx)">' +
                    '{{item.title}}' +
                '</div>' +
        '      </div>' +
        '    </div>'
};
contextMenuView = {
    components:{
        'context-menu':contextMenu
    },
    data:function () {
        return{
            menu:{
                item:null,
                x:0,
                y:0,
                show:false
            },
            menuItems:[]
        }
    },
    methods:{
        showMenu:function (item) {
            this.menu.item = item;
            let e = event;
            this.menu.x = e.pageX;
            this.menu.y = e.pageY;
            this.menu.show = true;
            e.preventDefault();
            const self= this;
            setTimeout(function () {
                let menu = self.$refs.contextMenu.$refs.contextMenu;
                if (menu){
                    let menuBottom = menu.getBoundingClientRect().bottom;
                    let screenBottom = document.body.getBoundingClientRect().bottom;
                    if (menuBottom > screenBottom){
                        self.menu.y += self.menu.y - menuBottom;
                    }

                }else {
                    console.log('Context menu not connected... For fix it add attribute \'ref="contextMenu"\'')
                }
            }, 0);
        },

    }

};
