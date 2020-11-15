<template>
    <div class="AddSonAdmin2">
        <div class="main-container">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>管理员设置</el-breadcrumb-item>
                                        <el-breadcrumb-item>子管理员设置</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>子管理员设置</span>
                                </div>-->
                                <div class="stepdiv"></div>
                                <el-steps :active="stepType" align-center>
                                    <el-step title="设置用户" icon="el-icon-location"></el-step>
                                    <el-step title="管理范围" icon="el-icon-location"></el-step>
                                    <el-step title="分配权限" icon="el-icon-location"></el-step>
                                </el-steps>
                            </el-card>
                            <!--组件-->
                            
                            <el-card shadow="" class="card2">
                                <el-row :gutter="20">
                                    <el-col :span="12" :offset="6">
                                        <div style="text-align: center;margin-top:20px">
                                            <div style="float: left;">
                                                设置管理员范围
                                            </div>

                                            <el-checkbox-group style="float: left;text-align: left" v-model="checkList">
                                                <el-checkbox :key="item.id" style="display: block;margin-left: 30px;margin-right: 0px;margin-bottom: 20px;"  v-for="item in initdata" :label="item.companyId">{{item.name}}</el-checkbox>

                                            </el-checkbox-group>
                                        </div>


                                    </el-col>
                                </el-row>
                            </el-card>
                            <el-form :label-position="'right'" :model="Form" ref="Form" style="text-align: center;margin-top:20px" class="manage-form">
                                <el-form-item>
                                    <el-button type="primary" @click.native="Submit">下一步</el-button>
                                </el-form-item>
                            </el-form>    
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import {communication} from "../../data/getAxios";

export default {
    name: 'user',
    data() {
        return {
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            stepType:1,
            Form:{
                radio:1
            },
            minHeight:0,
            initdata:[],
            checkList:[]
        }
    },
    components:{},
    created() {
       console.log(JSON.parse(localStorage.getItem('SelectedUerData')))
        this.init()
    },
    methods: {
        init(){
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/admin/managelimit",
                isGet:true
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.initdata=res.data
                }
            })
        },
        //提交
        Submit() {
            console.log(this.checkList)
            localStorage.setItem('deptIds',JSON.stringify(this.checkList));
            this.$router.push({ path: '/addSonAdmin3'})
        },
    },
    computed: {
        
    }
}

</script>
<style scoped>
    .addDepartBtn a{
        color:white
    }
    .card2 {
        margin: 0 20px;
    }

    .AddSonAdmin .search{
        height: 40px;
        border-bottom: 1px solid #ddd;
        margin-left: 0;
        margin-right: 0;
    }

    .separateline{
        float: left;
        padding:0;
        width: 36px;
        /* background: url('../../../static/img/separateline.png') no-repeat center; */
        width: 12px;
        height: 60px;
    }
    .SelecteduserList{
        height: 30px;
        margin: 10px;
        margin-left: 0;
        text-align: left;
        font-size: 14px;
    }

    .manage-form .el-radio {
        width: 100%;
        text-align: left;
        margin: 10px 0;
    }
</style>

