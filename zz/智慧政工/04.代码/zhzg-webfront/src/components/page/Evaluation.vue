<template>
    <div class="cjgl Evaluation">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  --> 
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>评价反馈</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>评价反馈列表</span>
                                </div>-->
                            </el-card>
                            <el-card class="card2">
                                <div class="sqxx-info4">
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='Evaluation' :model="Evaluation" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="userName">
                                                        <el-input v-model="Evaluation.userName"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号：" prop="userNo">
                                                        <el-input v-model="Evaluation.userNo"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click="inquiry">查询</el-button>
                                                        <el-button @click="resetForm('Evaluation')">重置</el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row>
                                        <el-button type="primary" @click="tickling">批量回复</el-button>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div class="sqxx-table4">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" @selection-change="handleSelectionChange">
                                            <el-table-column type="selection" width="55"></el-table-column>
                                            <el-table-column prop="userName" label="姓名" width="70"></el-table-column>
                                            <el-table-column prop="policeNo" label="警号"></el-table-column>
                                            <el-table-column prop="score" label="评分"></el-table-column>
                                            <el-table-column prop="note" label="评价"></el-table-column>
                                            <el-table-column prop="noteDate" label="评价时间"></el-table-column>
                                            <el-table-column prop="feedback" label="回复状态"></el-table-column>
                                            <el-table-column label="操作" >
                                                <template slot-scope="scope">
                                                    <el-button type="text" @click="handleEdit(scope.row.personID,scope.row.evaluateID)">回复</el-button>
                                                    <el-button type="text"><router-link :to="{path:'/EvaluationExplicit',query:{evaluateID:scope.row.evaluateID,personID:scope.row.personID}}">详情</router-link></el-button>  
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
        <el-dialog title="回复" :visible.sync="bhVisible" width="580px" class="bhlog">
            <el-form ref="EvaluationForm" :model="EvaluationForm" label-width="100px">
                <el-form-item label="处理意见:">
                    <el-input class="test_box" type="textarea" v-model="EvaluationForm.rejectCon"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="bhVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveBh">确 定</el-button>
            </span>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import {communication} from '../../data/getAxios';
export default {
    name: 'dashboard4',
    data() {
        return {
            bhVisible:false,
            inittableData:[],
            stepActive: 2,
            showLxs: false,//显示旅行社
            toggleShow: false,
            pageShow1:true,
            pageShow2:false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            multipleSelection: [],
            Evaluation: {
            	userName:'',
                userNo: ''
            },
            EvaluationForm:{
                rejectCon:''
            },
            idx: -1,
            tableData: [],
            personidNum:'',
            evaluateIDNum:'',
            feedbacksArr:[],
            btnMark:''
        }
    },
    created() {
        this.getTabData();
    },
    methods: {
    	inquiry(){
    		var self = this;
            const _obj={
                url:self.global.serverPath8 + '/message/evaluate/getAllEvaluate',
                postData:{
                    'userName':self.Evaluation.userName,
                    'policeNo':self.Evaluation.userNo
                }
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    self.tableData = res.data;
                    self.inittableData= res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        
        tickling(){//点击反馈
            this.bhVisible = true
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 表格分页导航
        handleCurrentChange(val){
            this.cur_page=val
            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
        },
        // 查询分页导航
        handleCurrentChange2(val) {
            // this.loading = true;
            this.cur_page2 = val;
            // this.getSearchData();
        },
        getTabData() {
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/message/evaluate/getAllEvaluate',
                isGet:false
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    self.tableData = res.data;
                    self.inittableData= res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        //详情跳转
        handleDetail(index, row) {
            var self = this;
            self.idx = index;
            self.$http.get(self.global.serverPath4+'/process_engine/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
                params: {
                    "applicationFormId": row.applicationFormId,//申请单号
                }
            }).then(function(res) {
                console.log(res.body.rows);
                this.$router.push({ path: '/dashboard3', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },
        // 点击反馈
        handleEdit(personId,evaluateId) {
            var self = this;
            self.bhVisible = true;
            self.personidNum = personId;
            self.evaluateIDNum = evaluateId;
            self.btnMark = 2;
        },
        saveBh(){
            var self = this;
            self.feedbacksArr = [];
            if(self.btnMark == 2){
                var feedbacksObj = {};
                feedbacksObj.personId = self.personidNum;
                feedbacksObj.evaluateId = self.evaluateIDNum;
                feedbacksObj.feedbackNote = self.EvaluationForm.rejectCon;
                self.feedbacksArr.push(feedbacksObj);
            }else{
                console.log(self.multipleSelection);
                for(var i=0;i<self.multipleSelection.length;i++){
                    var feedbacksObj = {};
                    feedbacksObj.personId = self.multipleSelection[i].personID;
                    feedbacksObj.evaluateId = self.multipleSelection[i].evaluateID;
                    feedbacksObj.feedbackNote = self.EvaluationForm.rejectCon;
                    self.feedbacksArr.push(feedbacksObj);
                };
            }
            const _obj={
                url:self.global.serverPath8 + '/message/evaluate/sendFeedback',
                postData:{
                    'feedbacks':self.feedbacksArr
                }
            };
            communication(_obj).then((res) => {
                console.log(res);
                if(res.code == 0){
                    self.bhVisible = false;
                    self.EvaluationForm.rejectCon = '';
                }else{
                    self.bhVisible = false;
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        // 导出excel
        export2Excel() {//兼容ie10
            var that = this;
            require.ensure([], () => {
                const { export_json_to_excel } = require('../../vendor/Export2Excel');//引入文件
                const tHeader = ['申请单号', '姓名', '工作单位', '部门', '离境原因', '离境形式', '申请时间']; //对应表格输出的title
                const filterVal = ['applicationFormId', 'applicationUserName', 'workUnits', 'departName', 'departureReason', 'departureForm', 'applicationTime'];// 对应表格输出的数据
                const list = that.tableData;
                const data = that.formatJson(filterVal, list);
                export_json_to_excel(tHeader, data, '申请列表excel'); //对应下载文件的名字
            })
        },
        formatJson(filterVal, jsonData) {
            　　　　　　return jsonData.map(v => filterVal.map(j => v[j]))
        　　　　},
        // 收起展开
        upDown() {
            this.toggleShow = !this.toggleShow;
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.getTabData();
        }
    },
    computed: {
        // role() {
        //     return this.name === 'admin' ? '超级管理员' : '普通用户';
        // }
    }
}

</script>
<style scoped>
.Dashboard2 .el-dialog{
    background: url('../../../static/img/index/scoreBg.png') no-repeat center;
    background-size: 100% 100%;
    height:460px;
    -webkit-box-shadow:none!important;
    box-shadow:none!important;
}
.Dashboard2 .el-dialog__headerbtn .el-dialog__close{
    color:#1890FF;
    font-size:22px;
    font-weight:900;
    position:relative;
    top:23px;
    right:23px;
}
.Dashboard2 .el-dialog__body .el-textarea__inner{
    height:100px!important;
}
.Dashboard2 .el-dialog__body .el-row{
    margin-bottom:10px;
}
.Dashboard2 .el-dialog__body{
    padding-bottom:10px!important;
}
.Dashboard2 .el-dialog__footer{
    text-align:center;
}
.Dashboard2 .el-dialog__body .el-row .rateSpan{
    display:inline-block;
    width:100px;
    text-align:left;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0,0,0,0.85);
}
.inputPadding .el-form--label-top .el-form-item__label{
    padding:0 0 4px;
}
.pagination2{height: 32px;margin-bottom: 10px;margin-top:10px;}
.pagination2 .el-pagination{
    position: absolute;
    right: 30px;
    bottom:10px;
}

.icon-world {
    display: inline-block;
    width: 14px;
    height: 16px;
    position: relative;
    top: 3px;
    background: url('../../../static/img/file-word.png') no-repeat center;
    background-size: 100% 100%;
}

.el-upload__tip {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: #FF0000;
    letter-spacing: 0;
}

.sq-icon {
    background: url('../../../static/img/sq.png') no-repeat center;
    background-size: 100% 100%;
}

.card2 {
    margin: 0 20px;
}

.Dashboard4 .user-info {
    padding: 0 7%;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
}

.Dashboard4 .user-info p {
    margin: 10px 0;
}

.el-row {
    margin-bottom: 20px;
}

.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.pagination2 .el-input--small .el-input__inner{
    width: 45px!important;
}
.pagination .el-input--small .el-input__inner{
    width: 45px!important;
}
.sqxx-table4 .el-table th>.cell {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.sqxx-table4 .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.sqxx-info4 .el-button--text {
    font-size: 14px;
}

.sqxx-info4 .el-input--small .el-input__inner {
    width: 220px;
}

.sqxx-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.sqxx-info4 .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.sqxx-info4 .el-row[data-v-6ad31440] {
    margin-bottom: 0px;
}

.sqxx-info4 .el-form-item__label {
    padding: 0;
}
.Evaluation a{
    color:#409EFF!important
}
</style>

