<template>
    <div class="table LeaveManage" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                    <el-breadcrumb-item>休假计划</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="LeaveManCon"></div>
            <div class="LeaveUser-info">
                <p></p> 
                <p></p>
            </div>
        </el-card>
        <div class="containe">
            <div class="leave">
                <el-row>
                    <el-col :span="24" class="title">
                        <div class="grid-content bg-purple-dark">申请信息</div>
                    </el-col>
                    <el-row>
                        <el-col :span="22">
                            <el-form :inline="true" :rules="rules2" label-position="left" label-width="" ref='leaveForm' :model="leaveForm" class="demo-form-inline fuzzinfoCon">
                                <el-row :gutter="6">
                                    <el-col :span="8" style="padding-left:24px">
                                        <el-form-item prop="years" class="formSelect" label="年份：">
                                            <el-select disabled v-model="leaveForm.year">
                                                <el-option label="2019年" value="2019"></el-option>
                                                <el-option label="2020年" value="2020"></el-option>
                                                <el-option label="2021年" value="2021"></el-option>
                                                <el-option label="2022年" value="2022"></el-option>
                                                <el-option label="2023年" value="2023"></el-option>
                                                <el-option label="2024年" value="2024"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item prop="workingYears" class="formSelect" label="工作年限：">
                                            <el-input readonly v-model="leaveForm.workingYears"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item prop="holidayDays" class="formSelect" label="可休年假：">
                                            <el-input readonly v-model="leaveForm.annualLeaveDays"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8" style="padding-left:24px">
                                        <el-form-item prop="holidayDays" class="formSelect" label="年龄：">
                                            <el-input readonly v-model="leaveForm.age"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item prop="holidayDays" class="formSelect" label="是否晚婚：">
                                            <el-input readonly v-model="leaveForm.lateMarriage==true?'是':'否'"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item prop="holidayDays" class="formSelect" label="可休婚假：">
                                            <el-input readonly v-model="leaveForm.marriageDays"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row style="margin-top: 10px;">
                                    <el-col :span="24">
                                        <div class="sqxx-table txryTable">
                                            <el-table size="mini" align="center" :data="leaveSchemeList" border style="width: 100%" highlight-current-row>
                                                <!-- <el-table-column type="index"></el-table-column> -->
                                                <el-table-column label="月份" prop="plannedVacationDays">
                                                    <template slot-scope="scope">
                                                        <el-select v-model="scope.row.month">
                                                            <el-option label="1月" value="1"></el-option>
                                                            <el-option label="2月" value="2"></el-option>
                                                            <el-option label="3月" value="3"></el-option>
                                                            <el-option label="4月" value="4"></el-option>
                                                            <el-option label="5月" value="5"></el-option>
                                                            <el-option label="6月" value="6"></el-option>
                                                            <el-option label="7月" value="7"></el-option>
                                                            <el-option label="8月" value="8"></el-option>
                                                            <el-option label="9月" value="9"></el-option>
                                                            <el-option label="10月" value="10"></el-option>
                                                            <el-option label="11月" value="11"></el-option>
                                                            <el-option label="12月" value="12"></el-option>
                                                        </el-select>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column label="计划休假天数" prop="startTime">
                                                    <template slot-scope="scope">
                                                        <el-input v-model="scope.row.holidayPlanDays" onkeyup="value=value.replace(/[^\d]/g,'')"></el-input>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column  label="休假类型" prop="endTime">
                                                    <template slot-scope="scope">
                                                        <el-select v-model="scope.row.type">
                                                            <el-option label="年休假" value="0"></el-option>
                                                            <el-option label="探亲假" value="2"></el-option>
                                                            <el-option label="婚假" value="1"></el-option>
                                                        </el-select>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column v-if="$route.query.key!=2"  label="操作">
                                                    <template slot-scope="scope">
                                                        <el-button @click="deleteRow(scope.$index)" type="text">删除</el-button>
                                                    </template>
                                                </el-table-column>
                                            </el-table>
                                            <div v-if="$route.query.key!=2" class="el-table-add-row" style="width: 100%;" @click="addMasterUser">
                                                <span>+ 添加</span>
                                            </div>
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="24">
                                        <div v-if="$route.query.key!=2" class="btn-group">
                                            <el-button @click="saveForm()">保存</el-button>
                                            <el-button @click="resetForm()">取消</el-button>
                                            <el-button type="primary" @click="submitLeave">提交</el-button>
                                        </div>
                                        <div v-if="$route.query.key==2" class="btn-group">
                                            <el-button @click="resetForm()">返回</el-button>
                                        </div>
                                    </el-col>
                                </el-row>
                            </el-form>
                        </el-col>
                    </el-row>
                </el-row>

            </div>
        </div>
        <el-footer :style="'text-align:center'">&copy;2019-2022&emsp;公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
    import crypto from 'crypto';
    export default {
        data() {
            return {
                isApprove:false,
                loading:false,
                leaveForm: {
                    holidayDays:"",
                    workingYears:"",
                    years:"2019"
                },
                leaveSchemeList:[],
                rules1: {
                    appellation: [
                        { required: true, message: '请输入同行人称谓', trigger: 'change' },
                    ],
                    name: [
                        { required: true, message: '请输入同行人姓名', trigger: 'change' },
                    ],
                    unit: [
                        { required: true, message: '请输入同行人工作单位', trigger: 'change' },
                    ],
                    duties: [
                        { required: true, message: '请输入同行人职务', trigger: 'change' },
                    ]
                },
                rules2: {
                    // address:[
                    // 	{ required: true, message: '请输入外出地点', trigger: 'blur' },
                    // ],
                    valueBegin: [
                        { required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    valueEnd: [
                        { required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    applyType: [
                        { required: true, message: '请选择请假事由', trigger: 'change' }
                    ],
                },
            }
        },
        created() {
            this.init()
        },
        methods:{
            init(){
                const obj = {
                    url:this.global.serverPath8+"/statistics/holidayPlan/details?year="+(this.$route.query.year==undefined?null:this.$route.query.year)+"&personId="+localStorage.getItem("loginpersonId"),
                    isGet:true
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.leaveForm=res.data.applicationInfo
                        this.leaveSchemeList=res.data.holidayPlan==null?[]:res.data.holidayPlan
                        this.leaveSchemeList.forEach((item,index)=>{
                            item.month=item.month+''
                            item.type=item.type+''
                        })
                    }
                })

            },
            addMasterUser(){
                this.leaveSchemeList.push({
                    month:"",
                    holidayPlanDays:"",
                    type:""
                })
            },
            saveForm(){
                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                var loginpersonId = localStorage.getItem('loginpersonId')
                var flag = false
                var totalnianjia = 0;
                var totalhunjia  = 0;
                this.leaveSchemeList.forEach((item,index)=>{
                    item.personId=loginpersonId;
                    item.year=this.leaveForm.year
                    item.applicatType=1
                    if(item.type==0){
                        totalnianjia+=parseInt(item.holidayPlanDays)
                    }
                    if(item.type==1){
                        totalhunjia+=parseInt(item.holidayPlanDays)
                    }
                    if(item.month==""||item.holidayPlanDays==""||item.type==""){
                        flag=true
                        return
                    }
                })
                if(flag||this.leaveSchemeList.length==0){
                    loading.close();
                    this.$message.error("请填写完整数据")
                }else if(totalnianjia>this.leaveForm.annualLeaveDays){
                    loading.close();
                    this.$message.error("计划年假天数超出可休年假天数")
                }else if(totalhunjia>this.leaveForm.marriageDays){
                    loading.close();
                    this.$message.error("计划婚嫁天数超出可休婚假天数")
                }else{
                    const obj = {
                        url:this.global.serverPath8+"/statistics/holidayPlan/application",
                        postData:{holidayPlanList:this.leaveSchemeList}
                    }
                    communication(obj).then((res)=>{
                        if(res.code==0){
                            loading.close();
                            this.$message.success("操作成功")
                            this.$router.push("/beonleave")
                        }else{
                            loading.close();
                        }
                    })
                }

            },
            resetForm(){

                this.$router.push("/beonleave")
            },
            submitLeave(){
                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                var loginpersonId = localStorage.getItem('loginpersonId')
                var flag = false
                var totalnianjia = 0;
                var totalhunjia  = 0;
                this.leaveSchemeList.forEach((item,index)=>{
                    item.personId=loginpersonId;
                    item.year=this.leaveForm.year
                    item.applicatType=1
                    if(item.type==0){
                        totalnianjia+=parseInt(item.holidayPlanDays)
                    }
                    if(item.type==1){
                        totalhunjia+=parseInt(item.holidayPlanDays)
                    }
                    if(item.month==""||item.holidayPlanDays==""||item.type==""){
                        flag=true
                        return
                    }
                })
                if(flag||this.leaveSchemeList.length==0){
                    loading.close();
                    this.$message.error("请填写完整数据")
                }else if(totalnianjia>this.leaveForm.annualLeaveDays){
                    loading.close();
                    this.$message.error("计划年假天数超出可休年假天数")
                }else if(totalhunjia>this.leaveForm.marriageDays){
                    loading.close();
                    this.$message.error("计划婚嫁天数超出可休婚假天数")
                }else{
                    const obj = {
                        url:this.global.serverPath8+"/statistics/holidayPlan/application",
                        postData:{holidayPlanList:this.leaveSchemeList}
                    }
                    communication(obj).then((res)=>{
                        if(res.code==0){
                            loading.close();
                            this.$message.success("操作成功")
                            this.$router.push("/beonleave")
                        }else{
                            loading.close();
                        }
                    })
                }

            },
            deleteRow(index){
                this.leaveSchemeList.splice(index,1)
            }
        }
    }
</script>

<style scoped>

    .wrapper .el-table th{
        text-align: center!important;
    }
    .el-form-item {
        margin-bottom: 12px
    }
    .el-tabs__nav-wrap,
    .el-tabs {
        width: 100%!important;
        margin-left: 0px!important;
    }
    .right-btn {
        float: right;
    }
    .handle-select {
        width: 200px;
    }
    .pagination {
        margin-bottom: 10px
    }
    .pagination2 {
        height: 32px;
        margin-bottom: 10px
    }
    .pagination2 .el-pagination {
        position: absolute;
        right: 30px;
        bottom: 10px;
    }
     .txryTable .el-table--border th,.txryTable .el-table__fixed-right-patch{
        text-align:center;
    }
    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .el-footer {
        height: 20px!important;
        font-size: 12px;
        color: #333;
        line-height: 20px
    }
    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }
    /*表格内容居中*/
    .el-table td, .el-table th{
        text-align: center;
    }
    .LeaveManage .el-table__row td{
        text-align: center
    }
</style>
<style>
    .el-input.is-disabled .el-input__inner {
         background-color: white;
         border-color: #DCDFE6;
         color: #606266;
         cursor: not-allowed;
    }
    .upClass .el-upload-dragger{
        width:auto;
        height:auto;
        border:none;
    }
    .leaveManage .el-form-item__content{
        width:calc(100% - 145px)!important;
    }
    .el-table-add-row {
        cursor: pointer;
        width: 100%;
        text-align: center;
        height: 32px;
        line-height: 32px;
        font-family: PingFangSC-Regular;
        font-size: 14px;
        border: 1px dashed #D9D9D9;
        color: #409eff;
        margin-top: 10px;
        border-color: #b3d8ff
    }
    .table .crumbs {
        background: white;
        margin-bottom: 0;
    }
    .table .titIcon{
        margin-top:10px
    }
    .leave {
        margin:16px 20px;
        background: white;
    }
    .leaveList .leave .el-row {
        margin-bottom: 0px!important;
    }
    .leave .el-rowL:first-child {
        margin-bottom: 0;
    }
    .infoTab .el-table--border {
        border: 0
    }
    .applyNum .grid-content {
        padding:20px;
        border-bottom: 1px solid #f0f0f0;
        margin-bottom: 15px;
        font-weight: bold;
    }
    .fuzzinfoCon {
        padding:20px 20px 10px;
    }
    .fuzzinfoCon input {
        width: 208px!important;
    }
    .fuzzinfoCon select {
        width: 208px!important;
    }
    .el-upload {
        width: initial;
        height: initial;
        border: 0
    }
    .el-upload-list {
        float: left;
    }
    .el-upload {
        float: left
    }
    .el-upload-list__item:first-child {
        margin-top: 5px
    }
    .infoTab .el-table__empty-block {
        width: 100%!important
    }
    .el-upload-list__item-name {
        color: rgb(32, 160, 255)
    }
    .content .apply {
        background-position: 10px!important;
        height: 30px;
        line-height: 30px;
        padding-left: 45px;
    }
    .applyNum {
        padding: 10px 15px;
        background: white;
    }
    .applyNum .grid-content {
        margin-bottom: 0;
        border: 0;
    }
    .applyNum .grid-content:nth-of-type(2) {
        padding: 0;
    }
    .apply, .gridright {
        padding: 0;
    }
    .gridright {
        text-align: right;
    }
    .submit {
        color: white;
    }
    .stepCon {
        width: 100%;
        overflow: hidden;
        background: white;
        margin: 0 auto;
        padding:20px;
    }
    .steps {
        padding: 3% 3% 0 3%;
    }
    .infoTab .el-table__empty-block {
        min-height: 53px;
    }
    .sqxx-table{
        padding: 0 20px 20px;
    }
    .sqxx-table .el-tag--info {
        background-color: transparent;
        border: none;
    }
    .containe .title{
        float:inherit;
        padding: 18px 20px;
        border-bottom: 1px solid #EBEEF5;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }
    .sqxx-table .el-tag--info,
    .sqxx-table .el-tag--info .el-tag__close {
        font-size: 14px;
        color: #1890FF;
    }
    .sqxx-table .el-tag--danger {
        background-color: transparent;
        border: none;
        font-size: 14px;
    }
    .steps .el-step__icon-inner[class*=el-icon]:not(.is-status) {
        font-size: 17px!important;
    }
    .steps .el-step__title {
        font-size: 14px!important;
    }
    .sqxx-table .el-tag {
        border: none;
        font-size: 14px;
        background-color: transparent;
    }
    .LeaveUser-info {
        padding: 0 11.7%;
        font-family: PingFangSC-Regular;
        font-size: 14px;
        color: rgba(0, 0, 0, 0.65);
    }
    .LeaveUser-info p {
        margin: 10px 0;
    }
    .sq-icon {
        background: url('../../../static/img/sq.png') no-repeat center;
        background-size: 100% 100%;
    }
    .faSelf {
        display: inline-block;
        width: 30px;
        height: 30px;
        position: relative;
    }
    .leave .title{
        float:inherit
    }
    .figurePortrait{
        position: relative;
        width: 125px;
        height: 175px;
    }
    .figurePortrait img{
        position: absolute;
        left: 26%;
        top: 50%;
        transform: translate(-50%,-50%);
        width: 100%;
    }
    .prompt{
        font-size: 12px;
        color: red;
        line-height: 22px;
        text-indent: 2.5rem;
    }
    .reasonLeaveBox .formSelect{
        width: 100%;
        position:relative
    }
    .reasonLeaveBox .reasonLeaveCon .el-textarea__inner{
        height: 60px!important;
    }
    .reasonLeaveBox .numberV{
        position:absolute;
        left:80%;
        bottom:55px
    }
    .promptCon{
        border-radius: 5px;
        padding: 8px;
        margin-bottom: 15px;
    }
    .fuzzinfoCon .borderShow{
        border:1px solid  #DCDFE6;
        display:block;
        width:calc(100% - 50px);
    }
    .fuzzinfoCon .borderHide{
        border:0;
        display: none;
    }
    .LeaveManCon{display: flex;flex-direction:row;width: 90%;margin:0 auto}
    .LeaveManCon>div{flex: 1;position: relative;height: 130px;overflow: hidden;}
    .LeaveManCon .multiple{display: flex;flex-direction: column;}
    .LeaveManCon .daughter{
        flex: 1;
        position: relative;
    }
    .LeaveManCon .circleDot ,.roundDot{
        position: absolute;
        background: white;
        z-index: 10;
    }
    .LeaveManCon .circle{
        position: relative;
        width: 100%;
        height: 100%;
        padding-left:20px;
        padding-right:20px;
        font-size: 12px;
        white-space: nowrap;
    }
    .LeaveManCon .circle>div{
        position: absolute;
        width: 10px;
        height: 10px;
        left: 5px;
        top:50%;
        transform: translateY(-50%);
        background: #eee;
        border-radius: 5px;
    }
    .LeaveManCon .circleDot{
        left:50%;
        transform: translate(-50%,-50%);
        top:50%;
    }
    .LeaveManCon .roundDot{
        top:50%;
        left:5%;
        transform: translate(-50%,-50%);
    }
    .LeaveManCon .data{
        position: absolute;
        top:50%;
        left: 10%;
        transform: translateY(-50%);
        font-size: 12px;
        min-width:180px ;
        background: white;
    }
    .LeaveManCon .name{
        position: absolute;
        top:50%;
        right: 10%;
        transform: translateY(-50%);
        font-size: 12px;
    }
    .LeaveManCon .finishColor{
        background: #3385ff;
    }
    .LeaveManCon .procedure{
        background: #eee;;
    }
    .LeaveManCon .contentCon{
        width: 100%;
        position: absolute;
        left:50%;
        top:105px;
        transform: translate(-50%,-50%);
        text-align: center;
    }
    .LeaveManCon .alone p{
        font-size: 12px;
        line-height: 20px;
    }
    .LeaveManCon>div .figure{
        width: 100%;
        height: 2px;
        position: absolute;
        top:50%;
        transform: translateY(-50%);
    }
    .LeaveManCon>div:first-child .figure{
        width: calc(50% - 20px);
        height: 2px;
        position: absolute;
        top:50%;
        transform: translateY(-50%);
        right: 0;
    }
    .LeaveManCon>div:last-child .figure{
        width: calc(50% - 20px);
        height: 2px;
        position: absolute;
        top:50%;
        transform: translateY(-50%);
        left: 0;
    }
    .LeaveManCon .multiple .figure{
        position: absolute;
        width: calc(100% - 20px);
        height: 2px;
        top:50%;
        transform: translateY(-50%);
        left: 50%;
    }
    .gray .circle div{
        background:gray;
    }
    .gray .figure{
        background: gray;
    }
    .gray .contentCon p{
        color:gray ;
    }
    .green .circle div{
        background: #409EFF;
    }
    .green .figure{
        background: #409EFF;
    }
    .green .contentCon{
        color:#409EFF ;
    }
    .red .circle div{
        background: #F56C6C;
    }
    .red .figure{
        background: #F56C6C;
    }
    .red .contentCon{
        color:#F56C6C ;
    }
    .green{
        color: #409EFF;
    }
    .red .data{
        color: #F56C6C;
    }
    .gray .data{
        color: gray;
    }

    .reasonLeaveBox .reasonLeaveCon{
        width:100%
    }
    .reasonLeaveBox .reasonLeaveCon input{
        width: 80%!important;
    }
    
   
    .sqxx-table .el-tag--info {
        background-color: transparent;
        border: none;
    }

    .sqxx-table .el-tag--info,
    .sqxx-table .el-tag--info .el-tag__close {
        font-size: 14px;
        color: #1890FF;
    }

    .sqxx-table .el-tag--danger {
        background-color: transparent;
        border: none;
        font-size: 14px;
    }

    .sqxx-table .el-tag {
        border: none;
        font-size: 14px;
        background-color: transparent;
    }
</style>

