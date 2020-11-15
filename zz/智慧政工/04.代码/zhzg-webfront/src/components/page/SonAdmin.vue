<template>
    <div class="sonadmin">
        <div class="sqxx-option">
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
                            </el-card>
                            <el-card class="card2">
                                <div class="user-info">
                                    <!-- 表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="120px" ref='userForm' :model="userForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="子管理员名称：">
                                                        <el-input v-model="userForm.userName" placeholder="请输入"></el-input>
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
                                            <el-button class="addDepartBtn" type="primary" icon="el-icon-plus" >    
                                                <router-link :to="{path:'/addSonAdmin'}">新增</router-link>
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                    <!--表格  -->
                                    <div class="userTable">
                                        <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                                            <el-table-column type="selection" width="55"></el-table-column>
                                            <el-table-column prop="userName" label="子管理员名称" width="120"></el-table-column>
                                            <el-table-column prop="loginName" label="人名"></el-table-column>
                                            <el-table-column prop="workingCompany" label="权限"></el-table-column>
                                            <el-table-column label="操作" align="left">
                                                <template slot-scope="scope">
                                                    <el-button type="text" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                                                    <el-button type="text">
                                                        <router-link :to="{path:'/addSonAdmin',query:{alarmNo:scope.row.$index}}">修改</router-link>
                                                    </el-button>
                                                    <el-button type="text" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="pageShow1">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length">
                                            </el-pagination>
                                        </div>
                                        <!--查询分页  -->
                                        <div class="pagination2" v-show="pageShow2" style="margin:10px 0 0 0">
                                            <el-pagination @current-change="handleCurrentChange2" :current-page="cur_page2" :page-size="pagesize2" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length2">
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
        <!-- 删除提示框 -->
        <el-dialog title="删除数据" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">确定删除改数据吗？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
        <!--详情界面-->
        <el-dialog title="子管理员详情" :visible.sync="detailVisible" :close-on-click-modal="false" width="50%">
            <el-form :inline="true" :label-position="'right'"  :model="addForm" label-width="120px" class="demo-form-inline fuzzinfoCon">
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="子管理员名称："><span>{{addForm.userName}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="人名："><span>{{addForm.loginName}}</span></el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="权限：">
                            <span v-for="item in addForm.power" class="span-block">{{item}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="已选人员：">
                            <span v-for="item in addForm.selectuser" class="span-block">{{item}} </span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="6">
                    <el-col :span="24">
                        <el-form-item class="" label="管理范围：" v-if="addForm.range==1"><span>市局</span></el-form-item>
                        <el-form-item class="" label="管理范围：" v-if="addForm.range==2"><span>分局及下级单位 </span></el-form-item>
                        <el-form-item class="" label="管理范围：" v-if="addForm.range==3"><span>支队及下级单位</span></el-form-item>
                        <el-form-item class="" label="管理范围：" v-if="addForm.range==4"><span>特定单位</span></el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    name: 'user',
    data() {
        return {
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
            pageShow1:true,
            pageShow2:false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            userForm: {
            	userName: ''
            },
            detailVisible: false,//详情界面是否显示
            delVisible: false,//删除界面是否显示，
            //新增界面数据
            addForm: {
                userName: '王明源',
                loginName: '0102332',
                power: [
                    '请假审批',
                    '证件申报'
                ],
                selectuser: ['王明源'],
                range: 2
            }
        }
    },
    created() {
       this.init()
    },
    methods: {
        inquiry(){
            var self = this;
			self.$http.get(self.global.serverPath5+'/findLocByAnything', {//传递参数
                params: {
                    "userName":this.userForm.userName
                }   
			}).then(function(res) {
                if(res.body.code == 0){
                    this.tableData = res.body.data
                }else{
                    self.$message.error('暂无数据')
                };
			}, function(res) {
				self.$message.error('请求失败')
			});
        },
        init(){
            var self = this;
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
        //详情
        handleDetail(index, row) {
            var self = this;
            self.idx = index;
            this.detailVisible = true;
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
        handleSelectionChange(val) {
            this.multipleSelection = val;
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
    .span-block{
        display: inline-block;
        width:100%;
    }
</style>


