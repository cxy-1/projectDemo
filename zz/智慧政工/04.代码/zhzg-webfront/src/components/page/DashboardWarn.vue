<template>
    <div class="cjgl DashboardWarn">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出境提醒设置</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div slot="header" class="clearfix">
                                    <span>提醒管理</span>
                                </div>
                                <div class="sqxx-info4">
                                    <!--申请信息表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='DashboardWarn' :model="DashboardWarn" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:10px">
                                                <el-col :span="8">
                                                    <el-form-item label="开始时间：" prop="startTime" class="timeInput">
                                                        <el-date-picker v-model="DashboardWarn.startTime" :picker-options="pickerOptions0" format='yyyy-MM-dd' style="width: 76%;" value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                    </el-form-item>
                                                </el-col> 
                                                <el-col :span="8">
                                                    <el-form-item label="结束时间：" prop="endTime" class="timeInput">
                                                        <el-date-picker :picker-options="pickerOptions1" v-model="DashboardWarn.endTime" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-form-item label="提醒语句：" prop="remarks">
                                                            <el-input v-model="DashboardWarn.remarks"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24" style="margin-top:8px">
                                                    <div class="btn-group">
                                                        <el-button type="primary" @click="saveForm()">保存</el-button>
                                                        <el-button @click="resetForm()">取消</el-button>
                                                    </div>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                </div>
                            </el-card>
                            <el-card class="card2" style="margin-top:15px;">
                                <div slot="header" class="clearfix">
                                    <span>提醒记录</span>
                                </div>
                                <div class="wdda-info" style="margin-top:-14px">
                                    <img v-show="txjlTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                    <div class="wdda-table" v-show="txjlTable.length>0?true:false">
                                        <el-table :data="txjlTable" style="width: 100%">
                                            <el-table-column type="index" label="序号" :index="txjlindexMethod" width="55" align="center">
                                            </el-table-column>
                                            <el-table-column prop="startTime" label="开始时间" width="160">
                                            </el-table-column>
                                            <el-table-column prop="endTime" label="结束时间">
                                            </el-table-column>
                                            <el-table-column prop="message" label="内容">
                                            </el-table-column>
                                            <el-table-column prop="addTime" label="添加时间">
                                            </el-table-column>
                                        </el-table>
                                        <div class="pagination" style="margin:10px 0 0 0" >
                                            <el-pagination @current-change="txjlhandleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="txjltableData.length">
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
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import {communication} from '../../data/getAxios';
export default {
    data() {
        return {
            txjlTable:[],
            txjltableData:[],
            cur_page:1,
            pagesize:10,
            DashboardWarn:{
                startTime:'',
                endTime:'',
                remarks:''
            },
            pickerOptions0: {//开始时间
                disabledDate: time => {
                    let endDateVal = this.DashboardWarn.endTime;
                    if (endDateVal) {//不能早于当前日期
                        return (time.getTime() > new Date(endDateVal).getTime()) || (time.getTime()  < Date.now() - 8.64e7);
                    }else{
                        return time.getTime()  < Date.now() - 8.64e7;
                    }
                }
            },
            pickerOptions1: {//结束时间
                disabledDate: time => {
                    let beginDateVal = this.DashboardWarn.startTime;
                    if (beginDateVal) {
                        return (
                            time.getTime() < new Date(beginDateVal).getTime() - 1 * 24 * 60 * 60 * 1000
                        );
                    }else{
                        return time.getTime()  < Date.now() - 8.64e7;
                    }
                }
            },
        }
    },
    created() {
        this.warn();
        this.tableData();
    },
    methods: {
        //获取表格信息
        tableData(){
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/message/reminder/allList',
                isGet:true
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.txjlTable = res.data;
                    self.txjltableData=res.data;
                    self.txjlTable=self.txjltableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('获取失败！')
            })
        },
        // 表格信息序号
        txjlindexMethod(index){
            return (this.cur_page-1)*this.pagesize+index+1;
        },
        // 分页
        txjlhandleCurrentChange(val){
            this.cur_page=val;
            this.txjlTable=this.txjltableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize);
        },
        warn(){
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/message/reminder/get',
                isGet:true
            };
            communication(_obj).then((res) => {
                console.log(res);
                if(res.code == 0){
                    self.DashboardWarn.startTime = res.data.startTime;
                    self.DashboardWarn.endTime = res.data.endTime;
                    self.DashboardWarn.remarks = res.data.message;
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
    	saveForm(){ 
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/message/reminder/setting',
                postData:{
                    message:self.DashboardWarn.remarks,
                    startTime:self.DashboardWarn.startTime,
                    endTime:self.DashboardWarn.endTime
                }
            };
            communication(_obj).then((res) => {
                console.log(res);
                if(res.code == 0){
                    self.$message.success('保存成功');
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        resetForm(){

        }
    },
    computed: {
    }
}

</script>
<style scoped>
.DashboardWarn .sqxx-info4{
    /* height: 200px */
}
.DashboardWarn .el-card__body>.el-row{
    height: 30px
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

</style>

