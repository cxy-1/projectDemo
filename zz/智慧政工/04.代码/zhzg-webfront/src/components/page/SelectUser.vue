<template>
    <div   class="SelectUser">
        <el-row  :gutter="20">
            <el-col  :span="18" style="padding-right: 0;">
                <el-card  class="card2 myCard" v-bind:style="{ minHeight: minHeight+'px'}">
                    <div class="user-info">
                        <!-- 表单  -->
                        <el-form :inline="true" :label-position="'right'" label-width="100px" ref='userForm' :model="userForm" class="demo-form-inline">
                            <el-row :gutter="6">
                                <el-col :span="8">
                                    <el-form-item prop="userName" label="用户名：">
                                        <el-input v-model="userForm.userName" placeholder="请输入"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
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
                                <el-col :span="8">
                                    <el-form-item>
                                        <el-button type="primary" @click="inquiry()">查询</el-button>

                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </el-form>
                        <!--表格  -->
                        <div class="userTable">
                            <el-table ref="LegalCustomerOrderTable"  :data="tableData" style="width: 100%">
                                <el-table-column  width="55">
                                    <template slot-scope="scope">
                                        <el-checkbox v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="userName" label="用户名" width="100"></el-table-column>
                                <el-table-column prop="policeNo" width="100" label="警号"></el-table-column>
                                <el-table-column prop="company" label="工作单位"></el-table-column>
                                <el-table-column prop="dept" label="部门"></el-table-column>
                                <el-table-column prop="idNumber" label="身份证号"></el-table-column>
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
            <div class="separateline myCard" v-bind:style="{ minHeight: minHeight+'px'}"></div>
            <el-col  :span="5"  v-bind:style="{ minHeight: minHeight+'px'}">
                <el-card class="myCard">
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
        <el-form :inline="true" :label-position="'right'"  ref="Form" class="demo-form-inline" style="text-align: center;margin-top:20px">
            <el-form-item>
                <el-button type="primary" @click.native="Submit">下一步</el-button>
            </el-form-item>
        </el-form>
        <!-- <el-form :inline="true" :label-position="'right'" :model="Form" ref="Form" class="demo-form-inline" style="text-align: center;margin-top:20px">
            <el-form-item>
                <el-button type="primary" @click.native="Submit">下一步</el-button>
            </el-form-item>
        </el-form> -->
                        
        </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
export default {
    name: 'user',
    data() {
        return {
            idarr:[],
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            stepType:0,
            minHeight:0,
            input5:'',
            tableData: [
            ],
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
            multipleSelection:[]
        }
    },
    created() {
        this.selectdepart()
        this.init()
        this.$nextTick(() => {
            var self = this;
            this.minHeight = 200;
        })
    },
    mounted(){
        //this.minHeight = $('.content').height()-126+'px';
        $('.myCard').height($('.content').height() - 214 );
    },
    methods: {
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
        Submit() {
            localStorage.setItem('SelectedUerData',JSON.stringify(this.idarr));
            this.$router.push({ path: '/addSonAdmin2'})
        },
        inquiry(){
            if(this.userForm.userName==""&&this.userForm.workingCompany==""){
                this.$message.error("请输入用户名或者部门搜索")
            }else{
                this.init()
            }
        },
        leadingout(){
            var self = this;
            self.$http.get(self.global.serverPath7+'/warning/downLoadTxt', {//传递参数
                params: {
                    id:self.idCardArr
                }   
            }).then(function(res) {
                window.open(self.global.serverPath7+'/warning/downLoadTxt?id='+self.idCardArr)
            }, function(res) {
                self.$message.error('请求失败')
            });
        },
        assignRole(){
            this.addRoleVisible = true;
        },
        assignSubmit(){
            console.log(this.$refs.tree.getCheckedNodes())//数组
            this.addRoleVisible = false;
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
        //详情
        handleDetail(index, row) {
            var self = this;
            self.idx = index;
            this.detailVisible = true;
            this.addForm = {
                userName: row.userName,
                loginName: row.loginName,
                workingCompany: row.workingCompany,
                department: row.department,
                characterID: row.characterID,
                phone: row.phone,
                info: row.info
            };
        },
        handleReset(index, row){
            this.resetVisible = true;
        },
        handleDel(index, row){
            this.idx = index;
            // this.roleName=row.roleName;//传入角色名称
            this.delVisible = true;
        },
        // 确定删除
        deleteRow() {
            this.tableData.splice(this.idx, 1);
            this.$message.success('删除成功');
            this.data_length=this.tableData.length
            this.delVisible = false;
        },
        // 收起展开
        upDown() {
            this.toggleShow = !this.toggleShow;
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.SelectedUerData = val;

        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        //显示新增界面
        addUser() {
            this.addFormVisible = true;
            this.addForm = {
                userName: '',
                loginName: '',
                workingCompany: '',
                department: '',
                characterID: '',
                phone: '',
                info: ''
            };
            // this.resetForm('addForm')
        },
        //提交新增
        addSubmit() {
            var line = {
                userName: this.addForm.userName,
                loginName: this.addForm.loginName,
                workingCompany: this.addForm.workingCompany,
                department: this.addForm.department,
                characterID: this.addForm.characterID,
                phone: this.addForm.phone,
                info: this.addForm.info
            }
            // this.tableData.unshift(line)
            this.tableData.push(line)
            this.data_length = this.tableData.length
            this.addFormVisible = false;
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

    .SelectUser .search{
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

