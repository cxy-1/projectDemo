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
                                        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                                        <el-breadcrumb-item>我的训历</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2 myTrain">
                                <div slot="header" class="clearfix">
                                    <span>我的训历</span>
                                </div>
                                <div class="sqxx-info4">
                                    <div class="handle-box4 trainMinute">
                                        <el-form :inline="true" label-position="left" label-width="" ref='Evaluation' :model="Evaluation" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" style="padding-left:12px;">
                                                        <span>{{Evaluation.userName}}</span>
                                                        <!-- <el-input v-model="Evaluation.userName"></el-input> -->
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号：">
                                                        <span>{{Evaluation.policeNo}}</span>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="职务：">
                                                        <span>{{Evaluation.duty}}</span>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" style="padding-left:12px;">
                                                    <el-form-item label="工作单位：">
                                                        <span>{{Evaluation.workUnit}}</span>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="身份证号：">
                                                        <span>{{Evaluation.idNumber}}</span>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <!--申请信息表格  -->
                                    <div class="sqxx-table4">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table :data="tableData" v-show="tableData.length>0?true:false">
                                            <el-table-column prop="trainingTime" label="培训时间"></el-table-column>
                                            <el-table-column prop="trainingLoc" label="培训地点"></el-table-column>
                                            <el-table-column prop="trainingName" label="组织单位"></el-table-column>
                                            <el-table-column prop="trainingContent" label="培训班名称"></el-table-column>
                                            <el-table-column prop="trainingResult" label="培训结果"></el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                            </el-pagination>
                                        </div>
                                    </div>
                                    <!-- <div class="btn-group" style="margin:0 20px;margin-top:0px">
                                        <el-button type="primary" @click="backBtn">关闭</el-button>
                                    </div> -->
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
            uploadTraining:'',  
            bhVisible:false,
            stepActive: 2,
            showLxs: false,//显示旅行社
            toggleShow: false,
            pageShow1:true,
            inittableData:[],
            cur_page: 1,//表格当前页
            pagesize:10,//表格分页条数
            data_length:0,
            multipleSelection: [],
            Evaluation: {
            	userName:'',
                policeNo:'',
                duty:'',
                workUnit:'',
                idNumber:''
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
        this.init()
    },
    methods: {
        //关闭
        backBtn() {
            this.$router.go(-1);
        },
        init(){
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/personnel/training/getAllTrainingToken',
                isGet:true,
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    self.Evaluation.userName = res.data.userName;
                    self.Evaluation.duty = res.data.duty;
                    self.Evaluation.idNumber = res.data.idNumber;
                    self.Evaluation.policeNo = res.data.policeNo;
                    self.Evaluation.workUnit = res.data.workUnit;
                    self.tableData = res.data.traningInfoListVoList;
                    self.inittableData= res.data.traningInfoListVoList;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        // 表格分页导航
        handleCurrentChange(val){
            this.cur_page=val;
            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
        }
    },
    computed: {
        
    }
}

</script>
<style scoped>
.trainMinute .el-form--inline .el-form-item{
    margin-bottom:10px;
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
    .myTrain .el-card__header{
        padding:10px 16px;
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

