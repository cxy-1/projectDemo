<template>
    <div class="depart">
        <div>
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>集体离宁审批列表</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="teamForm">
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='teamForm' :model="teamForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="带队领导：" prop="lead">
                                                        <el-input v-model="teamForm.lead"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="单位：" prop="checkUnit">
                                                        <el-input v-model="teamForm.checkUnit"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="职务：" prop="duties">
                                                        <el-input v-model="teamForm.duties"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="申请单号：" prop="applyNum">
                                                        <el-input v-model="teamForm.applyNum"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="16" v-show="toggleShow">
                                                    <el-form-item label="外出事由：" prop="leaveReason">
                                                        <el-select v-model="teamForm.leaveReason" readonly="">
                                                            <el-option key="全部" label="全部" value="全部"></el-option>
                                                            <el-option key="核查中" label="核查中" value="核查中"></el-option>
                                                            <el-option key="已核查" label="已核查" value="已核查"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item >
                                                        <el-button type="primary" @click = "inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('teamForm')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class2:class1"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row :gutter="20">
                                        <el-col :span="24">
                                            <el-button class="outDepartBtn" type="primary" @click="outBtn()" icon="el-icon-upload2">导出</el-button>
                                        </el-col>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div class="departListTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                                             <el-table-column type="selection" width="55"></el-table-column> 
                                            <el-table-column prop="checkType" label="申请单号"></el-table-column>
                                            <el-table-column prop="name" label="带队领导" width="80"></el-table-column>
                                            <el-table-column prop="checkCompany" label="工作单位" ></el-table-column>
                                            <el-table-column prop="sendTime" label="职务" ></el-table-column>
                                            <el-table-column prop="checkStatus" label="外出事由" ></el-table-column>
                                            <el-table-column prop="timestamp" label="人数" v-if="false"></el-table-column>
                                            <el-table-column prop="idNumber" label="idCard" v-if="false"></el-table-column>
                                            <el-table-column prop="checkResult" label="开始时间-结束时间 " width="160"></el-table-column>
                                            <el-table-column prop="applyTime" label="申请时间 "></el-table-column>
                                            <el-table-column label="操作" align="center" width="120">
                                                <template slot-scope="scope">
                                                    <el-button type="text" style="font-size:14px"><router-link :to="{path:'',query:{}}">审批</router-link></el-button>
                                                    <el-button type="text" style="font-size:14px"><router-link :to="{path:'',query:{}}">详情</router-link></el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table> 
                                        <!--表格分页  -->
                                         <div class="pagination" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
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
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import {communication} from '../../data/getAxios';
export default {
    name: 'depart',
    data() {
        return {
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            tableData:[],
            toggleShow: true,
            pageShow1:true,
            inittableData:[],
            cur_page: 1,//表格当前页
            pagesize:10,//表格分页条数
            teamForm: {
            	lead:'',//带队领导
                checkUnit: '',//单位
                duties:'',//职务
                applyNum: '',//申请单号
                leaveReason: ''//外出事由
            },
        }
    }, 
    created() {
       this.init()
    },
    methods: {
        // 添加核查按钮
        outBtn(){
            // this.$router.push('/DepartAdd');
        },
        inquiry(){//查询按钮
            var self = this;
            const _obj={
                // url:self.global.serverPath8+'/statistics/entryandexit/swithCheckJob',
                // postData:{
                //     "checkPeo":self.teamForm.name,//姓名模糊查询
                //     "checkType":self.teamForm.checkingType,//核查类型 模糊查询
                //     "checkStatus":self.teamForm.checkingState,//核查状态
                //     "checkResult":self.teamForm.checkResult,//核查结果
                //     "checkCompany":self.teamForm.checkUnit// 核查单位
                // }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    // self.tableData = res.data;
                    // self.inittableData= res.data;
                    // self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize)
                }else if(res.code != 1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        init(){//初始化列表
            var self = this;
            const _obj={
                // url:self.global.serverPath8+'/statistics/entryandexit/swithCheckJob',
                // postData:{
                //     "checkPeo":'',//姓名模糊查询
                //     "checkType":'',//核查类型 模糊查询
                //     "checkStatus":'',//核查状态
                //     "checkResult":'',//核查结果
                //     "checkCompany":''// 核查单位
                // }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    // self.tableData=[];
                    // self.tableData = res.data;
                    // self.inittableData= res.data;
                    // self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize)
                }else if(res.code != 1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
    	// 表格分页导航
        handleCurrentChange(val){
            this.cur_page=val
            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
        },
        // 收起展开
        upDown() {
            this.toggleShow = !this.toggleShow;
        },
        handleSelectionChange(val) {//全选
            this.multipleSelection = val;
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.init();
        }
    },
    computed: {
        
    }
}

</script>
<style scoped>
    .departListTable{
        margin-top:20px
    }
    
</style>
<style>
.departListTable .el-table th>.cell{
        color:#333;
        font-size:14px;
    }
    .departListTable .el-table td div{
        font-size:14px;
    }
    .teamForm .el-form-item__label{
        width:85px!important;
    }
    .teamForm .demo-form-inline input{
        width: 200px!important
    }
    .teamForm .demo-form-inline select{
        width: 200px!important
    }
    .outDepartBtn a{
        color:white
    }
</style>

