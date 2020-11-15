<template>
    <div class="sidebar styleSide">
        <!-- 折叠按钮 -->
        <!-- <div class="collapse-btn" @click="collapseChage">
                                            <i class=""></i>
                                        </div> -->
        <div :class="[!collapse?'logo':'logo2']">
            <img :class="[!collapse?'logoBox1':'logoBox2']" alt="" :src="!collapse?img1:img2">
        </div>
        <el-menu class="sidebar-el-menu" :default-active="active" :collapse="collapse" style="padding-top:4px;box-sizing:border-box;border-right:none" background-color="#001529" text-color="rgba(255,255,255,0.65)" active-text-color="#20a0ff" router>
            <template v-for="item in items">
                <template v-if="item.linkTo">
                    <el-menu-item>
                        <a :href="item.links" class="alinkto" target="_black">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </a>
                    </el-menu-item>
                </template>
                <template v-if="item.subs !== undefined &&item.subs.length!=0">

                    <el-submenu  :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-if="item.subs !== undefined &&item.subs.length!=0" v-for="subItem in item.subs">
                            <template v-if="subItem.linkTo">
                                <el-menu-item style="margin-left: 10px;">
                                    <a :href="subItem.links" class="alinkto" target="_black">
                                        <span slot="title">{{ subItem.title }}</span>
                                    </a>
                                </el-menu-item>
                            </template>
                            <el-submenu style="margin-left: 10px;" class="special" v-else-if="!subItem.linkTo&&item.subs !== undefined&&subItem.subs!== null&&subItem.subs.length!=0" :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem,i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item style="margin-left: 10px;" v-else :index="subItem.index" :key="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-if="item.subs !== undefined &&item.subs.length==0&&!item.linkTo">
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '../common/bus';
    import {communication} from "../../data/getAxios";
    export default {
        data() {
            return {
                collapse: false,
                img1: './static/img/long-logo.png',
                img2: './static/img/logo.png',
                items:[],
                active:'home2'
            }
        },
        computed: {
            onRoutes() {
                // self = this;

                // }



            }
        },
        created() {
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })
            this.$nextTick(function () {

                this.active=this.$route.path.replace('/', '');
            })
            if(localStorage.getItem("policeNo")=="999999"){
                this.items = [
                    {"id":200000,"title":"系统管理","index":"system","code":"system","icon":"iconfont icongongwenbao","parentId":"0","linkTo":null,"links":null,"depth":1,"subs":
                            [
                                {"id":1,"title":"菜单管理","index":"menuManager","code":"system:user","icon":null,"parentId":"200000","linkTo":null,"links":null,"depth":2,"subs":null},
                                {"id":2,"title":"角色组管理","index":"rolegroup","code":"system:role","icon":null,"parentId":"200000","linkTo":null,"links":null,"depth":2,"subs":null},
                                {"id":3,"title":"单位管理","index":"companyManager","code":"system:user","icon":null,"parentId":"200000","linkTo":null,"links":null,"depth":2,"subs":null},
                                {"id":5,"title":"用户管理","index":"userManager","code":"system:user","icon":null,"parentId":"200000","linkTo":null,"links":null,"depth":2,"subs":null},
                                {"id":6,"title":"角色管理","index":"roleManager","code":"system:role","icon":null,"parentId":"200000","linkTo":null,"links":null,"depth":2,"subs":null},
                                {"id":6,"title":"使用报告","index":"UseReport","code":"system:user","icon":null,"parentId":"200000","linkTo":null,"links":null,"depth":2,"subs":null}
                            ]
                    }
                ];
                return
            }
            const obj={
                url:this.global.serverPath8 + '/user-mgmt/menu/list?personId='+localStorage.getItem("loginpersonId"),
                isGet:true

            }
            communication(obj).then((res)=>{
                this.items=res.data


            })
        },
        methods: {
            // 侧边栏折叠
            // collapseChage() {
            //     this.collapse = !this.collapse;
            //     bus.$emit('collapse', this.collapse);
            // },

            compare (property) {
                return function (a, b) {
                    var value1 = a[property]
                    var value2 = b[property]
                    return value2 - value1
                }

            }
        },
        mounted() {
            // if (document.body.clientWidth < 1500) {
            // this.collapseChage();
            // }
        }
    }
</script>

<style scoped>
    .alinkto{
        width: 100%;
        height: 100%;
        display: inline-block;
        color: rgba(255, 255, 255, 0.65);

    }
    .sidebar {
        display: block;
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        background-color: #001529;
        overflow-y: scroll;
    }

    .logo {
        height: 62px;
        width: 210px;
        background: #001529;
    }

    .logo2 {
        height: 59px;
        width: 65px;
        background: #001529;
    }

    .logoBox1 {
        width: 100%;
        height: 100%;
        -webkit-transition: width .3s;
        transition: width .3s;
    }

    .logoBox2 {
        width: 35px;
        height: 35px;
        /* -webkit-transition: width .2s;  */
        /* transition: width .2s;  */
        margin: 18px 0 0 15px;
    }

    .collapse-btn {
        /* float: left; */
        /* padding: 0 21px; */
        cursor: pointer;
        color: #909399;
        text-align: center;
        padding-top: 25px;
        background: #001526;
    }

    .sidebar::-webkit-scrollbar {
        width: 0;
    }

    .sidebar-el-menu:not(.el-menu--collapse) {
        width: 210px;
    }

    .sidebar>ul {
        height: calc(100% - 62px);
    }
</style>
<style>
    /*.styleSide .el-submenu .el-menu-item {*/
    /*    padding: 0 0 0 52px!important;*/
    /*}*/
    /*!*.special .el-submenu__title{*!*/
    /*!*    padding-left: 52px!important;*!*/
    /*!*}*!*/





    /*设置菜单文字大小  */

    .styleSide .el-menu-item.is-active,
    .styleSide .el-submenu__title,
    .styleSide .el-menu-item {
        /*菜单文字大小  */
        font-size: 16px!important;
    }

    .styleSide .el-submenu [class^=iconfont],
    .styleSide .el-menu-item [class^=iconfont] {
        vertical-align: middle;
        margin-right: 5px;
        width: 24px;
        text-align: center;
        /*菜单图标大小  */
        font-size: 22px;
        font-weight: 400;
    }

    .styleSide .el-submenu [class^=el-icon-],
    .styleSide .el-menu-item [class^=el-icon-] {
        vertical-align: middle;
        margin-right: 5px;
        width: 24px;
        text-align: center;
        font-size: 20px;
        font-weight: 400;
    }
</style>

