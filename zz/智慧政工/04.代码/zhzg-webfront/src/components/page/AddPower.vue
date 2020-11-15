<template>
    <div class="AddSonAdmin">
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
                            <aaa :tabledate="tableData"></aaa>

                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
    import aaa from './SelectUser.vue'
    export default {
        name: 'user',
        data() {
            return {
                class1: 'el-icon-arrow-down el-icon--right',
                class2: 'el-icon-arrow-up el-icon--right',
                stepType:0,
                minHeight:0,
                tableData: [
                    {
                        userName: '王明源',
                        loginName: '0102332',
                        workingCompany: '江苏省南京市建邺分局政治处',
                        department: '治安大队',
                        characterID: '320925199001020020',
                        phone: '18299299910',
                        info: '无'
                    },{
                        userName: '张兆康',
                        loginName: '1017308',
                        workingCompany: '江苏省南京市玄武分局政治处',
                        department: '治安大队',
                        characterID: '320925199001020020',
                        phone: '18299299910'
                    }
                ],
                Form:{},
                SelectedUerData: [],
                pageShow1:true,
                pageShow2:false,
                cur_page: 1,//表格当前页
                cur_page2: 1,
                pagesize:10,//表格分页条数
                pagesize2:10,
                data_length:0,
                data_length2:0,
            }
        },
        components:{aaa},
        created() {
            localStorage.setItem('roleId',parseInt(this.$route.query.id));
        },
        methods: {
            init(){
                const obj = {
                    url:this.global.serverPath8 + "/user-mgmt/user/list",
                    postData:{
                        userName:this.userForm.userName,
                        policeNo:this.userForm.loginName,
                        idNumber:this.userForm.characterID,
                        companyId:this.userForm.workingCompany,
                        pageNum:this.cur_page,
                        pageSize:"10"
                    }
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.tableData=res.data;
                        this.totalRecords=res.totalRecords;
                    }else if(res.code != 1){
                        this.$message.error("系统错误")
                    }
                })
            },
            // 表格分页导航
            handleCurrentChange(val) {
                // this.loading = true;
                this.cur_page = val;
                this.getTabData();
            },
            // 查询分页导航
            handleCurrentChange2(val) {
                // this.loading = true;
                this.cur_page2 = val;
                // this.getSearchData();
            },
            //提交

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
</style>

