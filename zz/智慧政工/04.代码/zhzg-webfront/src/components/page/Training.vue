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
                                        <el-breadcrumb-item>教育训练</el-breadcrumb-item>
                                        <el-breadcrumb-item>训历管理</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2 Training">
                                <div class="sqxx-info4">
                                    <div class="handle-box4 TrainingForm">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='Evaluation' :model="Evaluation" class="demo-form-inline">
                                            <el-row :gutter="6" class="mag0">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="userName">
                                                        <el-input v-model="Evaluation.userName"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="工作单位：" prop="workUnit">
                                                        <el-input v-model="Evaluation.workUnit"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="培训班名称：" prop="trainingContent" v-show="toggleShow">
                                                        <el-input v-model="Evaluation.trainingContent"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="身份证号：" prop="idNumber" v-show="toggleShow">
                                                        <el-input v-model="Evaluation.idNumber"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="培训地点：" prop="trainingLoc" v-show="toggleShow">
                                                        <el-input v-model="Evaluation.trainingLoc"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="培训结果：" prop="trainingResult" v-show="toggleShow">
                                                        <el-select v-model="Evaluation.trainingResult" filterable>
                                                            <el-option v-for="item in optionGzdw" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click="inquiry">查询</el-button>
                                                        <el-button @click="resetForm('Evaluation')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class2:class1"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row class="mag0">
                                        <el-upload :on-success="handleSuccess" :headers = "headers" v-model="uploadTraining" class="Trainingdemo" :action="actss" drag  multiple>
                                            <el-button size="small" type="primary">导入训历</el-button>
                                        </el-upload>
                                        <!-- <el-button type="primary" class="TrainingdemoOut" @click="tickling">导出训历</el-button> -->
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div class="sqxx-table4">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" @selection-change="handleSelectionChange">
                                            <el-table-column type="selection" width="55"></el-table-column>
                                            <el-table-column prop="userName" label="姓名" width="70"></el-table-column>
                                            <el-table-column prop="idNumber" label="身份证号"></el-table-column>
                                            <el-table-column prop="workUnit" label="工作单位"></el-table-column>
                                            <el-table-column prop="duty" label="职务"></el-table-column>
                                            <el-table-column prop="trainingTime" label="培训时间"></el-table-column>
                                            <el-table-column prop="trainingName" label="组织单位"></el-table-column>
                                            <el-table-column prop="trainingContent" label="培训班名称"></el-table-column>
                                            <el-table-column prop="trainingResult" label="培训结果"></el-table-column>
                                            <el-table-column label="操作" width="60">
                                                <template slot-scope="scope">
                                                    <el-button type="text"><router-link :to="{path:'/TrainingMinute',query:{personID:scope.row.personID}}">详情</router-link></el-button>  
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
    name: 'dashboard4',
    data() {
        return {
            headers:{token: localStorage.getItem('setToken') },
            actss:this.global.serverPath8 + '/personnel/training/upload',
            uploadTraining:'',  
            bhVisible:false,
            inittableData:[],
            stepActive: 2,
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            showLxs: false,//显示旅行社
            toggleShow: true,
            pageShow1:true,
            cur_page: 1,//表格当前页
            pagesize:10,//表格分页条数
            data_length:0,
            multipleSelection: [],
            Evaluation: {
            	userName:'',
                workUnit:'',
                trainingContent:'',
                idNumber:'',
                trainingLoc:'',
                trainingResult:''
            },
            idx: -1,
            tableData: [],
            personidNum:'',
            evaluateIDNum:'',
            feedbacksArr:[],
            btnMark:'',
            upFileName:[],
            optionGzdw:[]
        }
    },
    created() {
        this.getTabData();
        this.init()
    },
    methods: {
        init(){
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/personnel/training/getTrainingResult',
                isGet:true
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    var danweiArr = [];
                    self.optionGzdw=[];
                    for(var i=0;i<res.data.length;i++){
                        danweiArr.push(res.data[i])
                    };
                    if(res.code==0){
                        for(var i=0;i<res.data.length;i++){
                            var obj = {};
                            obj.label= danweiArr[i].label;
                            obj.value = danweiArr[i].value;
                            self.optionGzdw.push(obj);
                        };
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
    	inquiry(){
    		var self = this;
            const _obj={
                url:self.global.serverPath8 + '/personnel/training/getAllTraining',
                postData:{
                    userName:self.Evaluation.userName,
                    workUnit:self.Evaluation.workUnit,
                    trainingContent:self.Evaluation.trainingContent,
                    idNumber:self.Evaluation.idNumber,
                    trainingLoc:self.Evaluation.trainingLoc,
                    trainingResult:self.Evaluation.trainingResult
                }
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    self.tableData = res.data;
                    self.inittableData=res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        
        tickling(){
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
        getTabData() {
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/personnel/training/getAllTraining',
                isGet:false
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    self.tableData = res.data;
                    self.inittableData=res.data;
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
        handleSuccess(response,file,fileList){
            this.upFileName.push(response.rows);
            console.log(response);
            // console.log(file);
            // console.log(fileList);
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
        traiResultChange(value){
            console.log(value)
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
        
    }
}

</script>
<style scoped>
.TrainingForm .el-form--inline .el-form-item{
    margin-bottom:12px!important;
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
.mag0{
    margin-bottom:0!important;
}
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
.Training .el-form-item__label{
    width: 90px!important
}
.Training .el-upload--text{
    width: inherit!important;
    height: inherit!important;
    border:0!important;
}
.Training .el-upload-dragger{
    width: inherit!important;
    height: inherit!important;
    border:0!important;
}
.Trainingdemo{
    float:left;
    margin-right:20px
}
.TrainingdemoOut{
    border-radius: 6px
}
.Trainingdemo .el-upload-list{
     display: none 
}
</style>

