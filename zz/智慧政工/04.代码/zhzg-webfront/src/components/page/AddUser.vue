<template>
    <div class="adduser">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col> 
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>角色管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>添加用户</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>添加用户</span>
                                </div>-->
                            </el-card>

                            <el-row  :gutter="20">
                                <el-col :span="18" style="padding-right: 0">
                                    <el-card  class="card2 myCard" style="height: 700px">
                                        <div class="user-info">
                                            <!-- 表单  -->
                                            <div class="handle-box4">
                                                <el-form :inline="true" :label-position="'right'" label-width="75px" ref='userForm' :model="userForm" class="demo-form-inline">
                                                    <el-row :gutter="6">
                                                        <el-col :span="10">
                                                            <el-form-item prop="userName" label="用户名：">
                                                                <el-input v-model="userForm.userName" placeholder="请输入"></el-input>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="11">
                                                            <el-form-item prop="workingCompany" label="部门：">
                                                                <el-select v-model="userForm.workingCompany" placeholder="请选择">
                                                                    <el-option
                                                                        v-for="item in department"
                                                                        :key="item.id"
                                                                        :label="item.name"
                                                                        :value="item.companyId">
                                                                    </el-option>
                                                                </el-select>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="3">
                                                            <el-form-item>
                                                                <el-button type="primary" @click="inquiry()">查询</el-button>
                                                            </el-form-item>
                                                        </el-col>
                                                    </el-row>
                                                </el-form>
                                            </div>
                                            <!--表格  -->
                                            <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                            <div class="userTable" v-show="tableData.length>0?true:false">
                                                <el-table ref="LegalCustomerOrderTable"  :data="tableData" style="width: 100%">
                                                    <el-table-column  width="55">
                                                        <template slot-scope="scope">
                                                            <el-checkbox v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>
                                                        </template>
                                                    </el-table-column>
                                                    <el-table-column prop="userName" label="用户名" width="80"></el-table-column>
                                                    <el-table-column prop="policeNo" width="80" label="警号"></el-table-column>
                                                    <el-table-column prop="company" label="工作单位"></el-table-column>
                                                    <el-table-column prop="dept" label="部门"></el-table-column>
                                                    <el-table-column prop="idNumber" label="身份证号" width="174"></el-table-column>
                                                    <el-table-column prop="phone" label="手机"></el-table-column>
                                                </el-table>
                                                <!--表格分页  -->
                                                <div class="pagination">
                                                    <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length">
                                                    </el-pagination>
                                                </div>

                                            </div>
                                        </div>
                                    </el-card>
                                </el-col>
                                <div class="separateline myCard"></div>
                                <el-col :span="5"  >
                                    <el-card class="myCard" style="height: 700px">
                                        <div class="Selecteduser">
                                            <el-row :gutter="20" class="search">
                                                <el-col :span="24">已选人员</el-col>
                                            </el-row>
                                            <div class="SelecteduserList" v-for="(item,index) in multipleSelection" :key="index">
                                                <span>{{item.userName}}</span>
                                            </div>
                                        </div>
                                    </el-card>
                                </el-col>
                            </el-row>

                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <div class="demo-form-inline" style="text-align: center;margin-top:20px">
                <el-button>
                    <router-link :to="{path:'/role'}">取消</router-link>
                </el-button>

                    <el-button type="primary" @click.native="Submit">确定</el-button>
            </div>
<!--            <el-form :inline="true" :label-position="'right'" :model="Form" ref="Form" >-->
<!--                <el-form-item>-->
<!--                    -->
<!--                </el-form-item>-->
<!--                -->
<!--            </el-form>-->
        </div>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
export default {

    name: 'user',
    data() {
        return {
            getRowKeys (row) {
                console.log(row.userName)
                return row.id
            },
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            minHeight:0,
            tableData: [

            ],
            input5:null,
            SelectedUerData: [],
            toggleShow: false,
            pageShow1:true,
            pageShow2:false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            userForm: {
            	userName: '',
                loginName: '',
                workingCompany: null,
                characterID: ''
            },
            addFormVisible: false,//新增界面是否显示
            detailVisible: false,//详情界面是否显示
            addRoleVisible: false,//分配界面是否显示
            delVisible: false,//删除界面是否显示，
            resetVisible: false,//重置密码是否显示
            //新增界面数据
            addForm: {
                userName: '',
                loginName: '',
                workingCompany: '',
                department: '',
                characterID: '',
                phone: '',
                info: ''
            },
            resetPasswordForm: {
                password: '',
                password2: ''
            },
            addFormRules: {
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                loginName: [
                    { required: true, message: '请输入登录名', trigger: 'blur' }
                ],
                workingCompany: [
                    { required: true, message: '请输入单位名称', trigger: 'blur' }
                ],
                department: [
                    { required: true, message: '请输入部门', trigger: 'blur' }
                ],
                characterID: [
                    { required: true, message: '请输入身份证号码', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入手机', trigger: 'blur' }
                ]
            },
            resetPasswordRules: {
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ],
                password2: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ]
            },
            treedata: [{
                id: 1,
                label: '一级 超级管理员',
                children: [{
                    id: 2,
                    label: '二级 市局管理员',
                    children: [{
                        id: 4,
                        label: '三级 管理员',
                        children: [{
                            id: 8,
                            label: '四级 管理员'
                        },{
                            id: 9,
                            label: '四级 管理员'
                        }]
                    }, {
                      id: 5,
                      label: '三级 管理员'
                    }]
                },{
                    id: 3,
                    label: '二级 分局管理员',
                    children: [{
                        id: 6,
                        label: '三级 管理员'
                    }, {
                      id: 7,
                      label: '三级 管理员'
                    }]
                }]
            }],
            defaultProps: {
              children: 'children',
              label: 'label'
            },
            department:[],
            multipleSelectionAll: [],   // 所有选中的数据包含跨页数据
            multipleSelection: [],   // 当前页选中的数据
            idKey: 'personId', // 标识列表数据中每一行的唯一键的名称(需要按自己的数据改一下)
            selectId:[],
            ids:null,
            idarr:[]
        }
    },
    created() {
        this.selectdepart()
        this.init()
        this.$nextTick(() => {
            var self = this;


        })
    },
    mounted(){
        //this.minHeight = $('.content').height()-126+'px';
        // $('.myCard').height($('.content').height() - 214 );
    },
    methods: {
        haha(v,k){
            this.idarr=[]
            var hasRow = false
            var newArr = []
            var _this = this
            this.multipleSelection.map((c,i) =>{
                if(c.id==k.id){
                    hasRow = true
                }else{
                    newArr.push(c)
                }
            })
            if(hasRow){
                this.multipleSelection = newArr
            }else{
                this.multipleSelection =  this.multipleSelection.concat(k)
            }
            this.multipleSelection.map((c,i)=>{
                if(!isNaN(c)){
                    this.multipleSelection.splice(i,1)
                }
            })
            this.multipleSelection.map((c,i)=>{
                if(isNaN(c)){
                    this.idarr.push(c.id)
                }

            })
            console.log(this.multipleSelection)
            console.log(this.idarr)
        },

        selectdepart(){
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/admin/managelimit",
                isGet:true
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.department=res.data
                }
            })
        },
        inquiry(){
            if(this.userForm.userName==""&&this.userForm.workingCompany==""){
                this.$message.error("请输入用户名或者部门搜索")
            }else{
                this.init()
            }
        },
        init(){
            this.cur_page=1
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/user/list",
                postData:{
                    userName:this.userForm.userName,
                    policeNo:this.userForm.loginName,
                    idNumber:this.userForm.characterID,
                    deptId:this.userForm.workingCompany,
                    pageNum:this.cur_page,
                    pageSize:"10"
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.tableData=res.data;
                    this.data_length=res.totalRecords
                }else if(res.code != 1){
                    this.$message.error("系统错误")
                }
            })
    },
    	// 表格分页导航
        handleCurrentChange(val) {
            this.cur_page = val;
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/user/list",
                postData:{
                    userName:this.userForm.userName,
                    policeNo:this.userForm.loginName,
                    idNumber:this.userForm.characterID,
                    deptId:this.userForm.workingCompany,
                    pageNum:this.cur_page,
                    pageSize:"10"
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.tableData=res.data;
                    this.data_length=res.totalRecords
                }else if(res.code != 1){
                    this.$message.error("系统错误")
                }
            })
        },
        handleSelectionChange(selectdata,row) {
                console.log("点击复选框")
                var hasRow = false
                var newArr = []
                var _this = this
                this.multipleSelection.map((c,i) =>{
                    if(c.id==row.id){
                        hasRow = true
                    }else{
                        newArr.push(c)
                    }
                })
                if(hasRow){
                    this.multipleSelection = newArr
                }else{
                    this.multipleSelection =  this.multipleSelection.concat(row)
                }


        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        Submit(){
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/buildingUser",
                postData: {
                    roleId:this.$route.query.id,
                    userIds:this.idarr
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.$message.success("添加用户成功")
                    this.idarr=[];
                    this.multipleSelection=[]
                    this.$router.push("/role")
                }else if(res.code != 1){
                    this.$message.error(res.message)
                }
            })
        }
    },
    computed: {
        
    }
}

</script>
<style scoped>
    .userTable{
        margin-top:20px
    }
    .userForm .el-form-item__label{
        width:85px!important;
    }
    .userForm .demo-form-inline input{
        width: 200px!important
    }
    .userForm .demo-form-inline select{
        width: 200px!important
    }
    .addDepartBtn a{
        color:white
    }
    .card2 {
        margin: 0 20px;
    }

    .adduser .search{
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

