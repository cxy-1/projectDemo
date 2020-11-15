<template>
    <div class="roleList">
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
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>角色管理列表</span>
                                </div>-->
                            </el-card>
                            <el-card class="card2">
                                <div class="role-info">
                                    <!-- 表单 -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="100px" ref='roleForm' :model="roleForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8" style="max-width:354px">
                                                    <el-form-item label="角色名称：">
                                                        <el-input v-model="roleForm.roleName" placeholder="请输入"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click="inquiry()">查询</el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row :gutter="20">
                                        <el-col :span="24">
                                            <el-button class="addDepartBtn" type="primary" icon="el-icon-plus" @click="addUser">新增角色</el-button>
                                        </el-col>
                                    </el-row>
                                    <!-- 表格 -->
                                    <div class="roleTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%">
                                            <el-table-column prop="name" label="角色名称" ></el-table-column>
                                            <el-table-column prop="description" label="描述"></el-table-column>
                                            <el-table-column label="操作" align="center" width="500">
                                                <template slot-scope="scope">
                                                    <el-button style="color: #409EFF;" type="text" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                                                    <el-button type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/addUser',query:{id:scope.row.id}}">添加用户</router-link>
                                                    </el-button>
                                                    <el-button type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/addPower',query:{id:scope.row.id}}">授权</router-link>
                                                    </el-button>
<!--                                                    <el-button type="text" @click="handlePower(scope.$index, scope.row)"></el-button>-->
                                                    <el-button type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/roleDetail',query:{id:scope.row.id}}">详情</router-link>
                                                    </el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" style="margin-bottom:0" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length">
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
        <el-dialog title="新增角色" :visible.sync="addFormVisible" :close-on-click-modal="false" width="34%">
            <el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
                <el-form-item label="角色名称：" prop="roleName">
                    <el-input v-model="addForm.roleName" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="描述：" prop="description">
                    <el-input v-model="addForm.description" style="width:300px"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit">确定</el-button>
            </div>
        </el-dialog>
        <!--详情界面-->
        <el-dialog title="子管理员详情" :visible.sync="detailVisible" :close-on-click-modal="false" width="50%">
            <el-form :inline="true" :label-position="'right'"  :model="detailForm" label-width="120px" class="demo-form-inline fuzzinfoCon">
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="角色名称："><span>{{detailForm.userName}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="角色状态：">
                            <span v-if="detailForm.state=='true'">启用</span>
                            <span v-if="detailForm.state=='false'">未启用</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="权限：">
                            <span v-for="item in detailForm.power" class="span-block">{{item}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="已选人员：">
                            <span v-for="item in detailForm.selectuser" class="span-block">{{item}} </span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="管理范围：" v-if="detailForm.range==1"><span>市局</span></el-form-item>
                        <el-form-item class="" label="管理范围：" v-if="detailForm.range==2"><span>分局及下级单位 </span></el-form-item>
                        <el-form-item class="" label="管理范围：" v-if="detailForm.range==3"><span>支队及下级单位</span></el-form-item>
                        <el-form-item class="" label="管理范围：" v-if="detailForm.range==4"><span>特定单位</span></el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>
        <!-- 删除提示框 -->
        <el-dialog title="删除数据" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">确定删除改数据吗？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
export default {
    name: 'roleList',
    data() {
        return {
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            tableData: [

            ],
            toggleShow: false,
            pageShow1:true,
            pageShow2:false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            roleForm: {
            	roleName: ''
            },
            addFormVisible: false,//新增界面是否显示
            detailVisible: false,//详情界面是否显示
            addRoleVisible: false,//分配界面是否显示
            delVisible: false,//删除界面是否显示，
            resetVisible: false,//重置密码是否显示
            //新增界面数据
            addForm: {
                roleName: '',
                description: ""
            },
            //新增界面数据
            detailForm: {
                userName: '王明源',
                state: 'true',
                power: [
                    '请假审批',
                    '证件申报'
                ],
                selectuser: ['王明源'],
                range: 2
            },
            addFormRules: {
                roleName: [
                    { required: true, message: '请输入角色名', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
       this.init()
    },
    methods: {
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
        assignRole(){
            this.addRoleVisible = true;
        },
        assignSubmit(){
            console.log(this.$refs.tree.getCheckedNodes())//数组
            this.addRoleVisible = false;
        },
        init(){

            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/role/list",
                postData:{
                    name:this.roleForm.roleName,
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
                roleName: row.roleName
            };
        },
        handleReset(index, row){
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
                url:this.global.serverPath8 + "/user-mgmt/delRole?id="+id,
                isGet:true
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.$message.success("删除成功");
                    this.init()
                }else if(res.code != 1){
                    this.$message.error("系统错误")
                }
            })
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        //显示新增界面
        addUser() {
            this.addFormVisible = true;
            this.addForm = {
                roleName: ''
            };
            // this.resetForm('addForm')
        },
        //提交新增
        addSubmit() {
            console.log(this.addForm.description)
            if(this.addForm.roleName==""){
                this.$message.error("请填写角色名称")
                return
            }else if(this.addForm.roleName.length>20){
                this.$message.error("角色名称过长")
                return
            }else if(this.addForm.description!=undefined&&this.addForm.description.length>20){
                this.$message.error("描述过长")
                return
            }
            const obj = {
                url:this.global.serverPath8 + "/user-mgmt/addRole",
                postData:{
                    name:this.addForm.roleName,
                    description:this.addForm.description
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.$message.success("新增成功")
                    this.init()
                }else if(res.code != 1){
                    this.$message.error("系统错误")
                }
            })
            this.addFormVisible = false;
        },
    },
    computed: {
        
    }
}

</script>
<style scoped>
    .roleTable{
        margin-top:14px
    }
    .roleForm .el-form-item__label{
        width:85px!important;
    }
    .roleForm .demo-form-inline input{
        width: 200px!important
    }
    .roleForm .demo-form-inline select{
        width: 200px!important
    }
    .addDepartBtn a{
        color:white
    }
    .card2 {
        margin: 0 20px;
    }
    .round-green{
        width: 10px;
        height: 10px;
        border-radius: 50%;
        display: inline-block;
        background-color: #52C41A;
        margin-right: 10px;
        vertical-align: baseline;
    }
    .round-gray{
        width: 10px;
        height: 10px;
        border-radius: 50%;
        display: inline-block;
        background-color: #D9D9D9;
        margin-right: 10px;
        vertical-align: baseline;
    }
</style>

