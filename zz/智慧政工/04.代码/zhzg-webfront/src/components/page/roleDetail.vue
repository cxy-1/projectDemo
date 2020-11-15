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
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>角色管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>角色管理详情</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>子管理员设置</span>
                                </div>-->
                                <div class="stepdiv"></div>
                            </el-card>
                            <!--组件-->
<!--                            <aaa :tabledate="tableData"></aaa>-->
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row :gutter="10">
                <el-col :span="8"><el-card style="padding-bottom: 20px;margin-bottom: 20px;height: 700px;overflow-y: auto;" class="card2 myCard" >
                    <div slot="header" class="clearfix">
                        <span>用户权限</span>
                    </div>
                    <div class="userTable" >
                        <el-table :data="tableData.user" style="width: 100%" >
                            <el-table-column
                                label="序号"
                                type="index"
                                width="50">
                            </el-table-column>
                            <el-table-column prop="userName" label="用户名" ></el-table-column>
                            <el-table-column prop="policeNo" label="警号"></el-table-column>
                            <el-table-column width="120" label="操作" align="left">
                                <template slot-scope="scope">
                                    <el-button type="text" @click="handleDel(1, scope.row.id)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>

                    </div>
                </el-card></el-col>
                <el-col :span="8"> <el-card style="padding-bottom: 20px;margin-bottom: 20px;height: 700px;overflow-y: auto;" class="card2 myCard" >
                    <div slot="header" class="clearfix">
                        <span>菜单权限</span>
                    </div>
                    <div class="userTable" style="padding-bottom: 20px;">
                        <el-table :data="tableData.perm" style="width: 100%">
                            <el-table-column
                                label="序号"
                                type="index"
                                width="50">
                            </el-table-column>
                            <el-table-column prop="title" label="菜单"></el-table-column>
                            <el-table-column width="120" label="操作" align="left">
                                <template slot-scope="scope">
                                    <el-button type="text" @click="handleDel(2, scope.row.id)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>

                    </div>
                </el-card></el-col>
                <el-col :span="8"> <el-card style="padding-bottom: 20px;margin-bottom: 20px;height: 700px;overflow-y: auto;" class="card2 myCard" >
                    <div slot="header" class="clearfix">
                        <span>单位权限</span>
                    </div>
                    <div class="userTable" style="padding-bottom: 20px;">
                        <el-table :data="tableData.company" style="width: 100%">
                            <el-table-column
                                label="序号"
                                type="index"
                                width="50">
                            </el-table-column>
                            <el-table-column  prop="name" label="单位名称"></el-table-column>
                            <el-table-column width="120" label="操作" align="left">
                                <template slot-scope="scope">
                                    <el-button type="text" @click="handleDel(3, scope.row.companyId)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>

                    </div>
                </el-card></el-col>
            </el-row>
        </div>





        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
    import aaa from './SelectUser.vue'
    export default {
        name: 'roleDetail',
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
                    },
                    ,{
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
            this.init()
        },
        methods: {
            init(){
                const obj = {
                    url:this.global.serverPath8 + "/user-mgmt/getRole?id="+this.$route.query.id,
                    isGet:true
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.tableData=res.data;
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
            handleDel(index, row){
                this.idx = index;
                // this.roleName=row.roleName;//传入角色名称
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRow(index,row)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            // 确定删除
            deleteRow(index,id) {
                if(index==1){
                    var obj = {
                        url:this.global.serverPath8 + "/user-mgmt/delUserRole",
                        postData:{
                            roleId:this.$route.query.id,
                            userId:id
                        }
                    }
                }else if(index==2){
                    var obj = {
                        url:this.global.serverPath8 + "/user-mgmt/delRolePerm",
                        postData:{
                            roleId:this.$route.query.id,
                            permId:id
                        }
                    }
                }else{
                    var obj = {
                        url:this.global.serverPath8 + "/user-mgmt/updateRole",
                        postData:{
                            id:this.$route.query.id,
                            companyId:id
                        }

                    }
                }

                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.$message.success("删除成功");
                        this.init();
                    }else if(res.code != 1){
                        this.$message.error("系统错误")
                    }
                })
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
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
</style>

