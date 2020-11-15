<template>
    <div class="table" ref="installList">
        <!-- <el-card style="height:100%"> -->
            <img :src="require('static/img/leave/load.png')" alt="" style="display:block;margin:0 auto">
        <!-- </el-card> -->

        
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px">&copy;2019-2022&emsp;南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    data() {
        return {
        }
    },
    mounted() {
        // this.$refs.installList.style.height = $('.content').height() - 60 + 'px';//滚动条高度
    },
   
    methods: {
        
    }
}

</script>

<style scoped>

</style>
<style>

</style>

