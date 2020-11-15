<template>
    <div id="app">
    <!-- <div id="app" @click="isDo()"> -->
        <router-view v-if="isRouterAlive"></router-view>
    </div>
</template>
<style>
    @import "../static/css/main.css";/*引入css*/
    /* @import "../static/css/iconfont.css";  引入css   */
    @import "../static/css/color-dark.css";     /*深色主题*/
    /*@import "../static/css/theme-green/color-green.css";   浅绿色主题*/
</style>
<script>
export default {
    provide(){
        return{
            reload:this.reload
        }
    },
    data(){
        return {//Vue判断用户长时间未操作
            lastTime: null, //最后一次点击的时间
            isRouterAlive: true,
            currentTime: null, //当前点击的时间
            timeOut: 10 * 60 * 1000 //设置超时时间： 10分钟
        };
    },
    created() {
        // this.isOut();
        // this.$router.push('/goods/goods'); // 页面加载时跳转
        this.lastTime = new Date().getTime();   //网页第一次打开时，记录当前时间
    },
    methods: {
        reload () {
            this.isRouterAlive = false;
            this.$nextTick(() => (this.isRouterAlive = true))
        }
        // isDo(){
        //     this.currentTime = new Date().getTime(); //记录这次点击的时间
        //     if(this.currentTime - this.lastTime > this.timeOut) {  //判断上次最后一次点击的时间和这次点击的时间间隔是否大于10分钟
        //         // 这里写状态已过期后执行的操作
        //         location.reload('/login')
        //         // this.$router.push('/login');
        //         this.$message.error('登录超时，请重新登录');
        //     } else {
        //         this.lastTime = new Date().getTime(); //如果在10分钟内点击，则把这次点击的时间记录覆盖掉之前存的最后一次点击的时间
        //     }
            
        // },
        // isOut(){
        //     setTimeout(()=>{
        //         this.$router.push('/login');
        //         this.$message.error('登录超时，请重新登录');
        //     // },5000)//一小时
        //     },360000)//一小时
        // }
    }
}
</script>
