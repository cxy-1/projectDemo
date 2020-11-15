<template>
    <div class="userList">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>用户管理列表</span>
                                </div>-->
                            </el-card>
                            <el-card class="card2">
                                <div class="user-info">
                                    <!-- 表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="100px" ref='userForm' :model="userForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item prop="userName" label="用户名：">
                                                        <el-input v-model="userForm.userName" placeholder="请输入"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item prop="loginName" label="警号：">
                                                        <el-input v-model="userForm.loginName" placeholder="请输入"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item prop="workingCompany" label="部门：">
                                                        <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
                                                            <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                                            <el-option :value="valueTitle" :label="valueTitle">
                                                                <div class="tree-list">
                                                                    <el-tree id="tree-option"
                                                                             ref="selectTree"
                                                                             :expand-on-click-node="false"
                                                                             :data="data"
                                                                             :props="defaultProps"
                                                                             :check-strictly="flag"
                                                                             node-key="id"
                                                                             :highlight-current="true"
                                                                             @node-click="handleNodeClick"
                                                                             check-on-click-node
                                                                             default-expand-all
                                                                             :filter-node-method="filterNode"

                                                                    >
                     <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span v-if="data.children">
          <el-button
              type="text"
              size="mini"
              @click.stop="() => append(data)">
            包含
          </el-button>
        </span>
      </span>
                                                                    </el-tree>
                                                                </div>
                                                            </el-option>
                                                        </el-select>
<!--                                                        <el-select filterable  clearable v-model="userForm.workingCompany" placeholder="请选择">-->
<!--                                                            <el-option-->
<!--                                                                v-for="item in department"-->
<!--                                                                :key="item.id"-->
<!--                                                                :label="item.name"-->
<!--                                                                :value="item.companyId">-->
<!--                                                            </el-option>-->
<!--                                                        </el-select>-->
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24" v-show="toggleShow">
                                                    <el-form-item prop="characterID" label="身份证号：">
                                                        <el-input v-model="userForm.characterID" placeholder="请输入"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click="inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('userForm')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class2:class1"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
<!--                                    <el-row :gutter="20">-->
<!--                                        <el-col :span="24">-->
<!--                                            <el-button class="addDepartBtn" type="primary" icon="el-icon-plus" @click="addUser">新增用户</el-button>-->
<!--                                        </el-col>-->
<!--                                    </el-row>-->
                                    <!--表格  -->
                                    <div class="">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%">
                                            <el-table-column prop="userName" label="用户名" width="90"></el-table-column>
                                            <el-table-column prop="policeNo" label="警号" width="80"></el-table-column>
                                            <el-table-column prop="company" label="工作单位"></el-table-column>
                                            <el-table-column prop="dept" label="部门"></el-table-column>
                                            <el-table-column prop="idNumber" label="身份证号" width="174"></el-table-column>
                                            <el-table-column prop="phone" label="手机"></el-table-column>
                                            <el-table-column label="操作" align="center" width="200">
                                                <template slot-scope="scope">
                                                    <el-button type="text" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                                                    <el-button type="text" @click="handleReset(scope.$index, scope.row)">重置密码</el-button>
                                                    <el-button type="text" @click="assignRole(scope.row.id)">分配角色</el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" style="margin-bottom:0" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :total="totalRecords" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper">
                                            </el-pagination>
                                        </div>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!--新增界面-->
        <el-dialog title="新增用户" :visible.sync="addFormVisible" :close-on-click-modal="false" width="34%">
            <el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
                <el-form-item label="用户名：" prop="userName">
                    <el-input v-model="addForm.userName" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="登录名：" prop="loginName">
                    <el-input v-model="addForm.loginName" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="身份证号：" prop="characterID">
                    <el-input v-model="addForm.characterID" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="手机号：" prop="phone">
                    <el-input v-model="addForm.phone" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="工作单位：" prop="workingCompany">
                    <el-input v-model="addForm.workingCompany" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="部门：" prop="department">
                    <el-input v-model="addForm.department" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="备注：" prop="info" >
                    <el-input v-model="addForm.info" style="width:300px" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit">确定</el-button>
            </div>
        </el-dialog>
        <!--详情界面-->
        <el-dialog title="用户详情" :visible.sync="detailVisible" :close-on-click-modal="false" width="34%">
            <el-form :inline="true" :label-position="'right'"  :model="addForm" label-width="120px" class="demo-form-inline fuzzinfoCon">
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="用户名："><span>{{addForm.userName}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="登录名："><span>{{addForm.loginName}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="身份证号："><span>{{addForm.characterID}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="手机号："><span>{{addForm.phone}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="工作单位："><span>{{addForm.workingCompany}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="部门："><span>{{addForm.department}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="备注："><span>{{addForm.info}}</span></el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>
        <!--分配角色-->
        <el-dialog title="分配角色" style="overflow: hidden" :visible.sync="addRoleVisible" :close-on-click-modal="false" width="50%">
            <el-checkbox-group v-model="checkList">
                <el-checkbox :key="item.id" style="display: block;margin-left: 30px;margin-right: 0px;margin-bottom: 20px;"  v-for="item in initdata" :label="item.id">{{item.name}}</el-checkbox>

            </el-checkbox-group>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addRoleVisible = false">取消</el-button>
                <el-button type="primary" @click.native="assignSubmit">确定</el-button>
            </div>
        </el-dialog>
        <!-- 删除提示框 -->
        <el-dialog title="删除数据" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">确定删除改数据吗？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
        <!--重置密码-->
        <el-dialog title="重置密码" :visible.sync="resetVisible" :close-on-click-modal="false" width="34%">
            <el-form :model="resetPasswordForm" label-width="120px" :rules="resetPasswordRules" ref="resetPasswordForm">
                <el-form-item label="新密码：" prop="password">
                    <el-input v-model="resetPasswordForm.password" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="确认密码：" prop="password2" >
                    <el-input v-model="resetPasswordForm.password2" style="width:300px"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetVisible = false">取消</el-button>
                <el-button type="primary" @click.native="resetSubmit">确定</el-button>
            </div>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
export default {
    name: 'userList',
    data() {
        return {
            aa:null,
            flag:true,
            valueTitle:null,
            data: [{
                id: 1,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{
                        id: 9,
                        label: '三级 1-1-1'
                    }, {
                        id: 10,
                        label: '三级 1-1-2'
                    }]
                }]
            }, {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
            }, {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id: 8,
                    label: '二级 3-2'
                }]
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            tableData: [

            ],
            userId:null,
            toggleShow: true,
            pageShow1:true,
            pageShow2:false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            userForm: {
            	userName: null,
                loginName: null,
                workingCompany: null,
                characterID: null
            },
            totalRecords:0,
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
            checkList:[],
            initdata:[],
            userId:null,
            department:[]
        }
    },
    created() {
        this.init()
        this.selectdepart()
    },
    watch: {
        aa(val) {
            this.$refs.selectTree.filter(val);
        }
    },
    methods: {
        append(data) {
            console.log(data)
            // this.flag=false
            this.valueTitle = data[this.defaultProps.label]+"(包含子部门)"
            // const newChild = { id: id++, label: 'testtest', children: [] };
            // if (!data.children) {
            //     this.$set(data, 'children', []);
            // }
            // data.children.push(newChild);
        },
        filterNode(value, data) {
            console.log(data)
            if (!value) return true;
            console.log(data)
            return data.label.indexOf(value) !== -1;
        },
        handleNodeClick(node){
            console.log(node)
            this.flag=true
            this.valueTitle = node[this.defaultProps.label]
        },
        // 清除选中
        clearHandle(){
            this.valueTitle = ''
            // this.valueId = null
            // this.defaultExpandedKey = []
            // this.clearSelected()
        },
        /* 清空选中样式 */
        // clearSelected(){
        //     let allNode = document.querySelectorAll('#tree-option .el-tree-node')
        //     allNode.forEach((element) => element.classList.remove('is-current'))
        // }
        selectdepart(){
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/admin/managelimit",
                isGet:true
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.department=res.data;
                }
            })
        },
        inquiry(){
            this.cur_page=1;
            this.init()
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
        assignRole(id){
            this.userId=id
            const _obj = {
                url:this.global.serverPath8 + "/user-mgmt/getUser?userId="+id,
                isGet:true
            }
            communication(_obj).then((res)=>{
                if(res.code==0){
                    this.checkList=res.data.role;
                }
            })
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/role/list",
                postData:{
                    name:"",
                    pageNum:1,
                    pageSize:"10000"
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.initdata=res.data;
                    this.addRoleVisible = true;
                }
            })

        },
        assignSubmit(){
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/bindingRole",
                postData:{
                    userId:this.userId,
                    roleIds:this.checkList
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.$message.success("操作成功");
                    this.checkList=[];
                    this.addRoleVisible = false;
                }else if(res.code != 1){
                    this.$message.error("系统错误")
                }
            })

        },
        init(){
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
            console.log(val)
            this.init();
        },
        // 查询分页导航
        handleCurrentChange2(val) {
            // this.loading = true;
            this.cur_page2 = val;
            // this.getSearchData();
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
        setSha(vals) {
            let sha256 = require("js-sha256").sha256//这里用的是require方法，所以没用import   
            return sha256(vals)
        },
        handleReset(index, row){
            this.userId = row.id
            this.resetVisible = true;
        },
        handleDel(index, row){
            this.idx = index;
            // this.roleName=row.roleName;//传入角色名称
            this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteRow(row.id)
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        // 确定删除
        deleteRow(id) {
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/delUser?userId="+id,
                isGet:true
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
        // 收起展开
        upDown() {
            this.toggleShow = !this.toggleShow;
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.init()
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
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/addUser",
                postData: {
                    userName:this.addForm.userName,
                    policeNo:this.addForm.loginName,
                    idNumber:this.addForm.characterID,
                    companyId:this.addForm.workingCompany,
                    deptId:this.addForm.department,
                    phone:this.addForm.phone,
                    remark:this.addForm.info
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.addFormVisible = false;
                    this.addForm = {
                        userName: '',
                        loginName: '',
                        workingCompany: '',
                        department: '',
                        characterID: '',
                        phone: '',
                        info: ''
                    };
                    this.$message.success("用户新增成功")
                }else if(res.code != 1){
                    this.$message.error("系统错误")
                }
            })
            this.addFormVisible = false;
        },
        //重置密码
        resetSubmit(){
            if(this.resetPasswordForm.password==""||this.resetPasswordForm.password2==""){
                this.$message.error("请填写密码")
                return
            }
            if(this.resetPasswordForm.password!=this.resetPasswordForm.password2){
                this.$message.error("两次密码不符")
                return
            }
            var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
            if(!reg.test(this.resetPasswordForm.password)){
                this.$message.error("密码长度要大于6位，由数字和字母组成!");
                return
            }
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/updatePwd",
                postData:{
                    id:this.userId,
                    password:this.setSha(this.resetPasswordForm.password)
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.$message.success("密码重置成功");
                    this.resetPasswordForm.password="";
                    this.resetPasswordForm.password2="";
                    this.resetVisible=false;
                }else if(res.code != 1){
                    this.$message.error("系统错误")
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
        margin-top:14px
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
    .assignButton i.el-icon-sort{
        transform:rotate(90deg);
    }
    .el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
       height: auto;
         /*max-height: 274px;*/
        overflow:auto;
        padding: 0;
    }


    .el-select-dropdown__item.selected {
        font-weight: normal;
    }

    .el-select-dropdown__item.hover, .el-select-dropdown__item:hover {
        background: #ffffff;
    }
    .el-select-dropdown__list {
        list-style: none;
        /* padding: 6px 0; */
        margin: 0;
        -webkit-box-sizing: content-box;
        box-sizing: content-box;
    }
</style>

