<template>
    <div class="DepartDetailsErrCon">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）核查</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
                <el-card class="DepartDetailsErr">
                    <div class="errCheckCon is-always-shadow">
                        <el-col :span="24" class="title">
                            <div class="grid-content bg-purple-dark clearfix"><span>核查结果</span></div>
                        </el-col>
                        <div class="errCheck">
                            <el-col :span="24" class="titleCon">
                                <div class="grid-content bg-purple-dark">经对出入境申请记录与实际出入境记录比对，共发现{{errLength}}处异常。{{hcjg}}</div>
                            </el-col>
                        </div>
                        <img v-show="hcxx.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                        <div v-show="hcxx.length>0?true:false">
                            <el-table class="errCheckConTab" ref="errCheck" :data="hcxx" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
                                <el-table-column prop="checkArticle" label="核查项目"></el-table-column>
                                <el-table-column prop="document" label="证件"></el-table-column>
                                <el-table-column prop="startTime" label="开始时间"></el-table-column>
                                <el-table-column prop="endTime"label="结束时间" ></el-table-column>
                                <el-table-column prop="distinction" label="目的地" ></el-table-column>
                            </el-table>
                        </div>
                    </div>
                    <div class="newsCheckCon is-always-shadow">
                        <el-col :span="24" class="title">
                            <div class="grid-content bg-purple-dark clearfix"><span>出入境申请信息</span></div>
                        </el-col>
                        <div class="newsCheck"  v-show="shows">
                            <el-form :inline="true" :label-position="'right'" label-width="130px" ref='sqxx' :model="sqxx" class="demo-form-inline">
                                <el-row :gutter="6">
                                    <el-col :span="8">
                                        <el-form-item class="" label="姓名：">
                                            <el-input v-model="sqxx.username" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="警号：">
                                            <el-input v-model="sqxx.policeno" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="工作单位：">
                                            <el-input v-model="sqxx.companyname" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="开始时间：">
                                            <el-input v-model="sqxx.starttime" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="结束时间：">
                                            <el-input v-model="sqxx.endtime" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="目的地：">
                                            <el-input v-model="sqxx.destinationbroad" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="离境形式：">
                                            <el-input v-model="sqxx.departureform" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="旅行社名称：">
                                            <el-input v-model="sqxx.travelagencyname" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="是否涉密人员：">
                                            <el-input v-model="sqxx.issecret" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="离境原因：">
                                            <el-input v-model="sqxx.departurereason" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item class="" label="是否有同行人员：">
                                            <el-input v-model="sqxx.isfield" readonly placeholder="暂无数据"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <!-- <el-col :span="8">
                                        <el-form-item class="" label="附件模板：">
                                            <el-input v-model="sqxx.companyName"></el-input>
                                        </el-form-item>
                                    </el-col> -->
                                </el-row>
                            </el-form>
                        </div>
                        <img v-show="txr.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                        <div v-show="txr.length>0?true:false">
                            <el-table class="newsCheckTab" ref="txr" :data="txr" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
                                <!-- <el-table-column type="selection" width="55"></el-table-column> -->
                                <el-table-column prop="relationUserName" label="姓名"></el-table-column>
                                <el-table-column prop="appellation" label="称谓"></el-table-column>
                                <el-table-column prop="workUnits" label="工作单位"></el-table-column>
                                <el-table-column prop="jobPosition" label="职务" ></el-table-column>
                            </el-table>
                        </div>
                    </div>
                    <div class="papersCheckCon is-always-shadow">
                        <el-col :span="24" class="title">
                            <div class="grid-content bg-purple-dark clearfix"><span>出入境证件</span></div>
                        </el-col>
                        <div class="papersCheck outCard">
                            <el-form :inline="true" label-position="left" label-width="" ref='crjzj' :model="crjzj" class="demo-form-inline">
                                <el-row :gutter="6">
                                    <el-col :span="8" style="padding-left:16px"><el-form-item class="" label="姓名："><span>{{crjzj.name}}</span></el-form-item></el-col>
                                    <el-col :span="8"><el-form-item class="" label="性别："><span>{{crjzj.sex}}</span></el-form-item></el-col>
                                    <el-col :span="8"><el-form-item class="" label="出生日期："><span>{{crjzj.birthday}}</span></el-form-item></el-col>
                                    <el-col :span="8" style="padding-left:16px"><el-form-item class="" label="身份证号："><span>{{crjzj.characterID}}</span></el-form-item></el-col>
                                </el-row>
                            </el-form>
                        </div>
                        <img v-show="papersChe.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                        <div v-show="papersChe.length>0?true:false">
                            <el-table class="papersTab" ref="papersChe" :data="papersChe" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
                                <el-table-column type="index" label="序号"></el-table-column>
                                <el-table-column prop="docType" label="证件种类"  show-overflow-tooltip></el-table-column>
                                <el-table-column prop="docNumber" label="证件号码" show-overflow-tooltip></el-table-column>
                                <el-table-column prop="issueDate"label="签发日期" ></el-table-column>
                                <el-table-column prop="validDate" label="有效期至" ></el-table-column>
                            </el-table>
                        </div>
                    </div>
                    <div class="recordDepart is-always-shadow">
                        <el-col :span="24" class="title">
                            <div class="grid-content bg-purple-dark clearfix"><span>出入境记录</span></div>
                        </el-col>
                        <img v-show="errCheck.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                        <div v-show="errCheck.length>0?true:false">
                            <el-table class="recordTab" ref="errCheck" :data="errCheck" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
                                <el-table-column type="index" label="序号"></el-table-column>
                                <el-table-column prop="outTime" label="出境时间"  show-overflow-tooltip></el-table-column>
                                <el-table-column prop="inTime"label="入境时间" ></el-table-column>
                                <el-table-column prop="visaType" label="签证类型" ></el-table-column>
                                <el-table-column prop="outShore" label="出口岸" ></el-table-column>
                                <el-table-column prop="inShore" label="入口岸" ></el-table-column>
                                <el-table-column prop="outTranType" label="出境交通方式" ></el-table-column>
                                <el-table-column prop="inTranType" label="入境交通方式" ></el-table-column>
                                <el-table-column prop="destination" label="前往地" ></el-table-column>
                            </el-table>
                        </div>
                    </div>
                </el-card>
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
            papersChe:[],
            txr:[],
            hcxx:[],
            errLength:'',
            hcjg:'',
            errCheck:[],
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            tableData:[],
            toggleShow: false,
            pageShow1:true,
            pageShow2:false,
            shows:false, 
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            sqxx:{
                username:'',//姓名
                policeno:'',//警号
                companyname:'',//工作单位
                starttime:'',//开始时间
                endtime:'',//结束时间
                destinationbroad:'',//出国目的地
                departureform:'',//离境形式
                travelagencyname:'',//旅行社名称
                issecret:'',//是否涉密
                isfield:'',//同行人员
                departurereason:''//离境原因
            },
            crjzj:{
                name:'',
                sex:'',
                birthday:'',
                characterID:''
            },
            departForm: {
            	name:'',
                characterID: '',
                workingCompany: '',
                checkingType: '',
                checkingState: ''
            },
            newsCheck:{
                name:'',
                userNo:'',
                companyName:''
            }
        }
    },
    created() {
        this.inquiry();//申请信息
        this.appliInfor();//出入境证件
        this.moveRecord();//出入境记录
        this.checkResult();//核查结果
    },
    methods: {
        inquiry(){//申请信息
            // var self = this;
            // console.log(self.$route.query.characterID);
            // console.log(self.$route.query.checkingdate);
			// self.$http.get(self.global.serverPath7+'/warning/checkPersonDetil',{
            //     params:{
            //         // "characterid":'460033198212190010',
            //         "characterid":self.$route.query.characterID,
            //         "checkDate":self.$route.query.checkingdate
            //     }  
			// }).then(function(res) {
            //     console.log(res);
            //     if(res.body.status == 10001){
            //         self.hcjg = res.body.result.hcjg;
            //         if(res.body.result.hcxx == '无'){
            //             self.hcxx = ''
            //         }else{
            //             self.hcxx = res.body.result.hcxx;
            //         };
            //         self.errCheck = res.body.result.crjjl;
            //         self.papersChe = res.body.result.crjzjList;
            //         self.sqxx = res.body.result.sqxx;
            //         self.crjzj.username = res.body.result.crjzj.username,
            //         self.crjzj.gender = res.body.result.crjzj.gender,
            //         self.crjzj.birthdate = res.body.result.crjzj.birthdate,
            //         self.crjzj.IDcard = self.$route.query.characterID,
            //         self.sqxx.username = res.body.result.sqxx.username,
            //         self.sqxx.companyname = res.body.result.sqxx.companyname,
            //         self.sqxx.departureform = res.body.result.sqxx.departureform,
            //         self.sqxx.departurereason = res.body.result.sqxx.departurereason,
            //         self.sqxx.deptname = res.body.result.sqxx.deptname,
            //         self.sqxx.destinationbroad = res.body.result.sqxx.destinationbroad,
            //         self.sqxx.endtime = res.body.result.sqxx.endtime,
            //         self.sqxx.isfield = res.body.result.sqxx.isfield,
            //         self.sqxx.policeno = res.body.result.sqxx.policeno,
            //         self.sqxx.starttime = res.body.result.sqxx.starttime,
            //         self.sqxx.travelagencyname = res.body.result.sqxx.travelagencyname,
            //         // self.sqxx.username = res.body.result.sqxx.username,
            //         self.txr = res.body.result.txr
            //         console.log(res.body.result)
            //     }else{
            //         self.$message.error('获取失败！')
            //     }
			// }, function(res) {
			// 	self.$message.error('请求失败！')
			// });
            var self = this; 
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/selectFormAndWithPeo',
                postData:{
                    // "idNumber":self.$route.query.idNumber,//身份证号
                    "personId":self.$route.query.personID
                    // "personId":'0000002007'
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    if (
                    res.data.startTime != null &&
                    res.data.startTime != undefined &&
                     res.data.startTime
                    ) {
                        self.shows = true;
                    } else {
                        self.shows = false;
                    }
                    self.sqxx.username=res.data.applicantName;//姓名
                    self.sqxx.policeno=res.data.policeNo;//警号
                    self.sqxx.companyname=res.data.deptName;//工作单位
                    // self.sqxx.deptname=res.data.,//部门
                    self.sqxx.starttime=res.data.startTime;//开始时间
                    self.sqxx.endtime=res.data.endTime;//结束时间
                    self.sqxx.destinationbroad=res.data.goAbroadForm.destinationBroad;//出国目的地
                    self.sqxx.departureform=res.data.departureForm;//离境形式
                    self.sqxx.travelagencyname=res.data.travelAgencyName;//旅行社名称
                    self.sqxx.issecret=res.data.goAbroadForm.involvedSecretLevel;//是否涉密
                    self.sqxx.isfield=res.data.goAbroadForm.isTogether;//同行人员
                    self.sqxx.departurereason=res.data.departureReason//离境原因
                    self.txr = res.data.goAbroadTogetherForm;
                    // self.data_length2=res.data.length
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('申请信息请求失败！!!!')
            })
        },
        moveRecord(){//出入境记录
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/getRecords',
                postData:{
                    "idNumber":self.$route.query.idNumber
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.errCheck = res.data;
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        appliInfor(){//申请信息
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/getDocuments',
                postData:{
                    "idNumber":self.$route.query.idNumber
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.papersChe = res.data;
                    self.crjzj.name=res.data[0].name;
                    self.sqxx.username=self.crjzj.name,//姓名
                    self.crjzj.sex=res.data[0].sex.slice(1);
                    self.crjzj.birthday=res.data[0].bornhDate;
                    self.crjzj.characterID=self.$route.query.idNumber
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        checkResult(){//核查结果
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/selectCheckResult',
                postData:{
                    "idNumber":self.$route.query.idNumber
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.hcjg=self.$route.query.detail;
                    self.errLength=self.hcjg.split(',').length;
                    self.hcxx=res.data;
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        init(){
            var self = this;
			self.$http.get(self.global.serverPath5+'/selectLoeCheck', {//传递参数
                // params: {
                //     "personID":localStorage.getItem("loginpersonId")
                // }   
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
        }
    },
    computed: {
        
    }
}
</script>
<style scoped>
    .DepartDetailsErr .title{
        line-height: 59px;
        border-bottom: 1px solid #f0f0f0;
        font-size: 18px;
        font-family: "PingFangSC-Medium";
        color: #262626;
    }
</style>
<style>
.outCard .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item{
    margin-bottom:14px!important;
}
    .DepartDetailsErrCon .mgb20{
        margin-bottom: 0
    }
    .DepartDetailsErr{
        background: #f0f0f0
    }
    .DepartDetailsErr .el-card__body{
        padding: 0!important
    }
    .DepartDetailsErrCon .title{
        padding-left:20px!important;
        padding-right:20px!important;
        margin-bottom:12px
    }
    .errCheckCon .titleCon{
        color:red;
        font-size:13px;
        padding-left:24px!important;
       /**padding:0 20px 20px!important;*/
    }
    .errCheckCon, .newsCheckCon, .papersCheckCon, .recordDepart{
        background: white;
        margin: 20px 28px;
    }
    .errCheckConTab, .newsCheckTab, .recordTab, .papersTab{
        padding:10px 20px 20px 20px;
    }
    .recordTab th>.cell,.papersTab th>.cell,.errCheckCon th>.cell{
        color:#333!important;
        font-size:14px!important;
    }
    .recordTab .el-table__body td,.papersTab .el-table__body td,.errCheckCon .el-table__body td{
        font-size:14px!important;
    }
    .papersCheck{
        padding: 0 20px
    }
</style>

