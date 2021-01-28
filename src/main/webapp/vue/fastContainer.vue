const fast = new Vue({
    el:'#fast',
    data:{
        windowWidth:0
    },
    created:function(){
        window.addEventListener('resize', this.handleResize);
        this.handleResize();
    },
    methods:{
        handleResize:function(){
            this.windowWidth = window.innerWidth;
        },
        showIt:function(){
            return this.windowWidth > Settings.switchWidth;
        }
    }
});