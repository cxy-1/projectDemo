<template>
    <div class="hcxq">
        <div class="hcxq-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）核查详情</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2 tabCard">
                                <el-tabs v-model="message">
                                    <el-tab-pane label="核查异常" name="first" class="table1">
                                        <img v-show="tableData1.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData1.length>0?true:false" :data="tableData1" style="width: 100%" @selection-change="handleSelectionChange">
                                             <el-table-column type="selection">
                                            </el-table-column> 
                                            <el-table-column prop="checkType" label="核查类型" width="90px">
                                            </el-table-column>
                                            <el-table-column prop="userName" label="核查人员" width="90px">
                                            </el-table-column>
                                            <el-table-column prop="idNumber" label="身份证号">
                                            </el-table-column>
                                            <el-table-column prop="timestamp" label="timestamp" v-if="false">
                                            </el-table-column>
                                            <el-table-column prop="checkResult" label="核查结果" width="90px">
                                            </el-table-column>
                                            <el-table-column prop="warnType" label="预警类型" >
                                            </el-table-column>
                                            <el-table-column prop="detail" label="detail" v-if="false">
                                            </el-table-column>
                                            <el-table-column prop="warnStatus" label="warnStatus" v-if="false">
                                            </el-table-column>
                                            <el-table-column prop="personID" label="personID" v-if="false">
                                            </el-table-column>
                                            <el-table-column label="操作" align="center" width="200px">
                                                <template slot-scope="scope">
                                                    <el-switch @change="switchChange(scope.row.idNumber,scope.row.timestamp,scope.row.warnStatus)" v-model="scope.row.type" active-text="非异常" inactive-text="异常"></el-switch>
                                                    <el-button type="text"><router-link :to="{path:'/DepartDetailsErr',query:{idNumber:scope.row.idNumber,personID:scope.row.personID,detail:scope.row.detail}}">详情</router-link></el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                         <!--<div class="pagination">
                                            <el-pagination @current-change="handleCurrentChange1" :current-page="cur_page1" :page-size="pagesize1" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length1">
                                            </el-pagination>
                                        </div>--> 
                                </el-tab-pane>
                                <el-tab-pane label="核查正常" name="second" class="table2">
                                    <img v-show="tableData2.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                    <template v-if="message === 'second'">
                                        <el-table v-show="tableData2.length>0?true:false" :data="tableData2" style="width: 100%" @selection-change="handleSelectionChange">
                                             <el-table-column type="selection">
                                            </el-table-column> 
                                            <el-table-column prop="checkType" label="核查类型" width="90px">
                                            </el-table-column>
                                            <el-table-column prop="userName" label="核查人员">
                                            </el-table-column>
                                            <el-table-column prop="idNumber" label="身份证号">
                                            </el-table-column>
                                            <el-table-column prop="personID" label="personID" v-if="false">
                                            </el-table-column>
                                            <el-table-column prop="detail" label="detail" v-if="false">
                                            </el-table-column>
                                            <el-table-column prop="checkResult" label="核查结果">
                                            </el-table-column>
                                            <el-table-column label="操作" align="center">
                                                <template slot-scope="scope">
                                                    <!--<el-button type="text"><router-link :to="{path:'/DepartDetailsSuccess',query:{characterID:scope.row.characterID,checkingdate:scope.row.checkingdate}}">详情</router-link></el-button>-->
                                                    <el-button type="text"><router-link :to="{path:'/DepartDetailsSuccess',query:{idNumber:scope.row.idNumber,personID:scope.row.personID,detail:scope.row.detail}}">详情</router-link></el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                         <!--<div class="pagination">
                                            <el-pagination @current-change="handleCurrentChange2" :current-page="cur_page2" :page-size="pagesize2" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length2">
                                            </el-pagination>
                                        </div> -->
                                        </template>
                                    </el-tab-pane>
                                </el-tabs>
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
    name: 'checknormal',
    data() {
        return {
            message: 'first',
            cur_page1: 1,//表格当前页
            pagesize1:10,//表格分页条数
            data_length1:0,
            cur_page2: 1,
            pagesize2:10,
            data_length2:0,
            value1:true,
            multipleSelection: [],//多选数组当前选中要删除的项
            idx: -1,
            tableData1: [],
            tableData2: []
        }
    },
    created() {
        this.getTabData1();
        this.getTabData2();
    },
    methods: {
        switchChange(number,timestamp,warnStatus){
            var self = this; 
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/changeCheck',
                postData:{
                    "idNumber":number,
                    "timestamp":timestamp,//时间戳
                    "warnStatus":warnStatus//核查类型
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.getTabData1();
                    self.getTabData2();
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 表格分页导航
        handleCurrentChange1(val) {
            this.cur_page1 = val;
            this.getTabData();
        },
        handleCurrentChange2(val) {
            this.cur_page2 = val;
            this.getTabData();
        },
        getTabData1() {//核查异常
            var self = this,times='';
            if(self.$route.query.alarmNo){
                times=self.$route.query.alarmNo
            }
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/getNormal',
                postData:{
                    "isNormal":1,
                    "timestamp":times
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.tableData1 = res.data
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        getTabData2() {//核查正常
            var self = this,times='';
            if(self.$route.query.alarmNo){
                times=self.$route.query.alarmNo
            }
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/getNormal',
                postData:{
                    "isNormal":0,
                    "timestamp":times
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.tableData2 = res.data
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
    },
    computed: {
        // role() {
        //     return this.name === 'admin' ? '超级管理员' : '普通用户';
        // }
    }
}

</script>
<style scoped>

.card2 {
    margin: 0 20px;
}
.mgb20 {
    margin-bottom: 16px;
}

</style>
<style>
.pagination .el-input--small .el-input__inner{
    width: 45px!important;
}
.hcxq-option .el-tabs__header{
    margin:0 0 20px;
}
.hcxq-option .el-tabs__item{
    height:50px;
    line-height:50px;
    font-family: 'PingFangSC-Medium';
    font-size: 16px;
}
.tabCard .el-card__body{
    padding:8px 25px!important;
}
.hcxq-option .el-table th>.cell{
    font-family: 'PingFangSC-Medium';
    font-size: 14px;
    color: rgba(0,0,0,0.85);
}
.table1 .el-table .cell{
    font-family:'PingFangSC-Regular';
    font-size: 14px;
    color: #FF0000;
}
.table2 .el-table .cell{
    font-family: 'PingFangSC-Regular';
    font-size: 14px;
    color: rgba(0,0,0,0.65);
}
</style>

