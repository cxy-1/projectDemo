<template>
    <div class="depart">
        <div class="sqxx-option" style="position:relative;">
            <img v-show="checking" :src="require('static/img/manage/loadGif.gif')" width="80px" class="checking" />
            <span v-show="checking" class="checking" style="margin-left:-70px;top:160px"> 任务正在核查中。。。</span>
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row> 
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出入境核查</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card> 
                            <el-card class="card2">
                                <div class="departForm">
                                    <!--申请信息表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="85px" ref='departForm' :model="departForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="name">
                                                        <el-input v-model="departForm.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="身份证号：" prop="characterID">
                                                        <el-input v-model="departForm.characterID"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="false">
                                                    <el-form-item label="工作单位：" prop="workingCompany">
                                                        <!--<el-input v-model="departForm.workingCompany"></el-input>-->
                                                        <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"  @clear="clearHandle">
                                                            <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                                            <el-option :value="valueTitle" :label="valueTitle">
                                                                <div class="tree-list">
                                                                    <el-tree id="tree-option"
                                                                            ref="selectTree"
                                                                            :expand-on-click-node="false"
                                                                            :data="selectdata"
                                                                            :props="defaultProps"
                                                                            :check-strictly="flag"
                                                                            node-key="id"
                                                                            :highlight-current="true"
                                                                            @node-click="handleNodeClick"
                                                                            check-on-click-node
                                                                            :filter-node-method="filterNode">
                                                                            <span class="custom-tree-node" slot-scope="{ node, data }">
                                                                            <span>{{ node.label }}</span>
                                                                            <span v-if="data.childCompany">
                                                                            <el-button
                                                                                type="text"
                                                                                size="mini"
                                                                                @click.stop="() => append(data)">
                                                                                包含 
                                                                            </el-button>
                                                                            </span>
                                                                        </span>
                                                                    </el-tree>
                                                                </div>
                                                            </el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="核查类型：" prop="checkingType">
                                                        <el-select v-model="departForm.checkingType" readonly="">
                                                            <el-option key="全部" label="全部" value="全部"></el-option>
                                                            <el-option key="实时核查" label="实时核查" value="实时核查"></el-option>
                                                            <el-option key="年度普查" label="年度普查" value="年度普查"></el-option>
                                                            <el-option key="随机抽查" label="随机抽查" value="随机抽查"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24" v-show="toggleShow">
                                                    <el-form-item label="核查状态：" prop="checkingState">
                                                        <el-select v-model="departForm.checkingState" readonly="">
                                                            <el-option key="0" label="未核查" value="0"></el-option>
                                                            <el-option key="1" label="核查中" value="1"></el-option>
                                                            <el-option key="2" label="已核查" value="2"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click = "inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('departForm')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class2:class1"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row :gutter="20"> 
                                        <el-col :span="24" class="departUpload">
                                            <el-button type="primary" icon="el-icon-upload2" @click="leadingout()" v-loading.fullscreen.lock="fullscreenLoading1" :disabled="btndis1" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">导出核查人员</el-button>
                                            <el-upload ref="my-upload" icon="el-icon-download" :disabled="btndis2" class="upload-demo" :headers = "headers" accept=".html" :on-success="handleSuccess" v-model="sqxxForm.upOption" drag :action="actss" multiple>
                                                <el-button size="small" type="primary" :disabled="btndis2" v-loading.fullscreen.lock="fullscreenLoading2" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">导入出入境记录</el-button>
											</el-upload>
                                        </el-col>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div class="departTable"> 
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <!-- <el-table ref="tabPage" :data="tableData"  style="width: 100%" @selection-change="handleSelectionChange"> -->
                                        <el-table v-show="tableData.length>0?true:false" ref="tabPage" :data="tableData" row-key="id" style="width: 100%" @select="one1" @selection-change="handleSelectionChange">
                                            <!-- <el-table-column type="selection" width="55"></el-table-column> -->
                                            <el-table-column type="selection" width="55" :reserve-selection="one2"></el-table-column>
                                            <el-table-column prop="name" label="姓名" width="80"></el-table-column>
                                            <el-table-column prop="idNumber" label="身份证号"></el-table-column>
                                            <el-table-column prop="company" label="工作单位">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.company+scope.row.dept}}</span>
                                                </template>
                                            </el-table-column>
                                            <!--<el-table-column prop="dept" label="部门"></el-table-column>-->
                                            <el-table-column prop="timestamp" label="timestamp" v-if="false"></el-table-column>
                                            <el-table-column prop="checkType" label="核查类型" width="100"></el-table-column>
                                            <el-table-column label="核查状态" width="90">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.checkSort==0?'未核查':scope.row.checkSort==1?'核查中':'已核查'}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="sendTime" label="推送时间" width="100"></el-table-column>
                                            <el-table-column label="操作" align="center" width="80">
                                                <template slot-scope="scope">
                                                    <el-button type="text" ><router-link :to="{path:'/DepartDetails',query:{alarmNo:scope.row.idNumber}}" style="color:#409EFF!important">详情</router-link></el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false" style="margin-bottom:0;position:relative">
                                            <router-link :to="{path:'/depart'}"><el-button type="primary" style="position:absolute;left:45%;">返回</el-button></router-link>
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
            aa:null,
            flag:true,
            valueTitle:null,
            selectdata: [],
            defaultProps: {
                children: 'childCompany',
                label: 'unitName'
            },
            dataId:null,
            one2:true,
            sqxxForm:{
                upOption:''
            },
            checking:false,
            btndis1:true,//导出
            btndis2:true,//导入
            inittableData:[],
            fullscreenLoading1:false,//导出loading
            fullscreenLoading2:false,//导出loading
            headers:{token: localStorage.getItem('setToken') },
            actss:this.global.serverPath8+"/statistics/entryandexit/upload",
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            tableData:[],
            toggleShow: true,
            pageShow1:true,
            pageShow2:false,
            multipleSelection:[],
            cur_page: 1,//表格当前页
            pagesize:10,//表格分页条数
            data_length:0,
            recordId:'',
            departForm: {
            	name:'',
                characterID: '',
                workingCompany: '',
                checkingType: '',
                checkingState: ''
            },
            idCardArr:[],
            idtest:[]
        }
    },
    created() {
        this.initselectdata();
    },
    methods: {
        initselectdata(){
            const _obj = {
                url:this.global.serverPath8 + "/user-mgmt/orgmanage/unittreeAuto",
                isGet:true
            }
            communication(_obj).then((res)=>{
                this.selectdata=res.data;
            })
        },
        append(data) {
            // this.flag=false
            this.valueTitle = data.unitName+"(包含子部门)";
            this.dataId=data.dataId;
            // this.getDas();
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        getReturnNode(node,_array,value){
            let isPass = node.selectdata &&  node.selectdata.label && node.selectdata.label.indexOf(value) !== -1;
            isPass?_array.push(isPass):'';
            this.index++;
            if(!isPass && node.level!=1 && node.parent){
                this.getReturnNode(node.parent,_array,value);
            }
        },
        handleNodeClick(node){
            console.log(node)
            this.valueTitle = node.unitName
            this.dataId=node.unitId;
            // this.getDas();
        },
        // 清除选中
        clearHandle(){
            this.valueTitle = ''
            this.dataId=null
            // this.valueId = null
            // this.defaultExpandedKey = []
            // this.clearSelected()
        },
        one1(val){
            this.one2=false;
        },
        init2(){//检查数据是否有核查中
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/swithCheckJob',
                postData:{
                    "checkPeo":'',//姓名模糊查询
                    "checkType":'',//核查类型 模糊查询
                    "checkStatus":'',//核查状态
                    "checkResult":'',//核查结果
                    "checkCompany":''// 核查单位
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    if(res.data.length>0){
                        for(var i=0;i<res.data.length;i++){
                            if(res.data[i].checkStatus == '核查中'){
                               self.checking=true; 
                            }
                        }
                    }
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        init(test){
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/iocheckmulti',
                postData:{
                    "name":'',//姓名
                    "idNumber":'',// 身份证号
                    "companyName":'',// 工作单位
                    "checkType":'',//核查类型
                    "checkStatus":'',//核查状态
                    "timestamp":self.$route.query.alarmNo//详情跳转的时间戳
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.idtest=res.data;
                    self.tableData = res.data;
                    self.inittableData=res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                    if(self.tableData.length>0){//表格中有数据
                        for(var i=0;i<self.tableData.length;i++){
                            if(self.tableData[0].checkSort == 0){//只要有未核查只能导出
                                self.btndis1=false;
                                self.btndis2=true;//checkSort==1
                            }else if(self.tableData[0].checkSort != 0&&self.tableData[0].checkSort == 1){//核查中只能导入
                                self.btndis1=true;//未核查只能导出
                                self.btndis2=false;
                            }
                        }
                    }
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        handleSuccess(response,file,fileList){//导出出入境记录成功
            this.fullscreenLoading2=true;
            if(response.code == 0){
                var self = this,arr1=[],arr2=[],arr3=[];
                if(self.tableData.length>0){
                    for(var i=0;i<self.tableData.length;i++){
                        if(self.tableData[i].checkSort==1){//状态核查中
                            arr1.push(self.tableData[i].idNumber)//获取所有表格数据身份证号
                            arr2.push(self.tableData[i].timestamp)//获取所有表格数据身份证号
                            arr3.push(self.tableData[0].checkType)//获取所有表格数据身份证号
                        }
                    }
                }
                const _obj={
                    url:self.global.serverPath8+'/statistics/entryandexit/save',
                    postData:{
                        "idNumber":arr1,
                        "checkType":arr3[0],
                        "timestamp":arr2[0],//时间戳
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==999){
                        self.init();
                        self.btndis1=true;
                        self.btndis2=true;
                        self.fullscreenLoading2=false;
                        self.$router.go(0);//刷新页面
                        self.$message.success(res.message)
                        // self.tableData = res.data
                    }else if(res.code!=1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })
            }
            // else{
            //     this.$message.success(response.message);
            // }
        },
        leadingout(){//导出
            var self = this;
            if(self.multipleSelection.length>0){//选中才能导出
                self.fullscreenLoading1=true;//loading
                var arr=[];
                for(var i=0;i<self.multipleSelection.length;i++){
                    arr.push(self.multipleSelection[i].idNumber)
                }
                const _obj={
                    url:self.global.serverPath8+'/statistics/entryandexit/output',
                    postData:{
                        'idNumber':arr
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.fullscreenLoading1=false;//loading
                        window.location.href=self.global.serverPath11+'/entryandexit/download';
                        setTimeout(() => {
                            self.$router.go(0);//刷新页面
                        }, 3000);
                    }else if(res.code!=1){
                        self.$message.warning(res.message);
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })
            }else{
                self.$message.warning('请先选择未核查人员')
            }
            
        },
        inquiry(){//查询按钮detailClick
            var self = this;
            self.cur_page=1;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/iocheckmulti',
                postData:{
                    "name":self.departForm.name,//姓名
                    "idNumber":self.departForm.characterID,// 身份证号
                    "companyName":self.valueTitle,// 工作单位
                    "checkType":self.departForm.checkingType,//核查类型
                    "checkStatus":self.departForm.checkingState,//核查状态
                    "timestamp":self.$route.query.alarmNo//详情跳转的时间戳
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.idtest=res.data;
                    self.tableData = res.data;
                    self.inittableData= res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                    if(self.tableData.length>0){//表格中有数据
                        for(var i=0;i<self.tableData.length;i++){
                            if(self.tableData[0].checkSort == 0){//只要有未核查只能导出
                                self.btndis1=false;
                                self.btndis2=true;
                            }else if(self.tableData[0].checkSort != 0&&self.tableData[0].checkSort == 1){//核查中只能导入
                                self.btndis1=true;//未核查只能导出
                                self.btndis2=false;
                            }
                        }
                    }
                }else if(res.code!=1){
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
        handleSelectionChange(val) {
            // this.multipleSelection = val;
            console.log(val)
            if(val.length>9){
                this.multipleSelection=this.idtest;
            }else{
                this.multipleSelection = val;
            }
            for(var i=0;i<this.multipleSelection.length;i++){
                this.idCardArr += this.multipleSelection[i].characterID+','
            };
        },
        // 重置表单
        resetForm(formName) {
            this.dataId=null;
            this.valueTitle=null;
            this.$refs[formName].resetFields();
            this.init();
        }
    },
    mounted() {
        if(this.$route.query.sendData){//推送跳转
            this.tableData=this.$route.query.sendData;
            // this.init2();
        }else{
            this.init();
            // this.init2();
        }
    }
}

</script>
<style scoped>
.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
        height: auto;
         /*max-height: 274px;*/
        overflow:auto;
        padding: 0;
    }
.el-select-dropdown__item.selected{
    font-weight: normal;
}
ul li >>>.el-tree .el-tree-node__content{
    height:auto;
    padding: 0 20px;
}
.el-tree-node__label{
    font-weight: normal;
}
.el-tree >>>.is-current .el-tree-node__label{
    color: #409EFF;
}
.el-tree >>>.is-current .el-tree-node__children .el-tree-node__label{
    color:#606266;
    font-weight: normal;
}
.checking{
    position:absolute;
    color:#303030;
    font-size:18px;
    top:130px;
    font-weight:100;
    z-index:99;
    left:50%;
    margin-left:-150px;
}
.pagination2{height: 32px;margin-bottom: 10px;margin-top:10px;}
.pagination2 .el-pagination{
    position: absolute;
    right: 30px;
    bottom:10px;
}
.pagination2 .el-input--small .el-input__inner{
    width: 45px!important;
}
    .departTable{
        margin-top:20px
    }
</style>
<style>
.depart .el-table th>.cell{
    font-family: 'PingFangSC-Medium'!important;
    font-size: 14px;
    color: rgba(0,0,0,0.85);
}
.depart .el-table .cell{
    font-family: 'PingFangSC-Regular';
    font-size: 14px;
    color: rgba(0,0,0,0.65);
}
    .departForm .el-form-item__label{
        font-family: 'PingFangSC-Regular';
        font-size: 14px;
        color: rgba(0,0,0,0.85);
        text-align: right;
    }
    .departForm .demo-form-inline input{
        width: 200px!important
    }
    .departForm .demo-form-inline select{
        width: 200px!important
    }
    .departUpload .el-upload-list{
        display: none
    }
    .departUpload .el-upload--text{
        width: inherit;
        height:inherit;
        border:0;
        border-radius: 3px
    }
    .departUpload .el-upload-dragger{
        width: inherit;
        height:inherit;
        border:0;
        border-radius: 3px
    }
    .departUpload>button{
        float:left
    }
    .departUpload>div{
        float:left;
        margin-left: 20px
    }
</style>

