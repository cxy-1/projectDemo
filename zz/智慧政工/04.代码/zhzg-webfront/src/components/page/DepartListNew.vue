<template>
    <div class="depart">
        <div class="sqxx-option" style="position:relative;">
            <img v-show="false" :src="require('static/img/manage/loadGif.gif')" width="80px" class="checking" />
            <span v-show="false" class="checking" style="margin-left:-70px;top:160px"> 任务正在核查中。。。</span>
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）核查</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="departForm">
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='departForm' :model="departForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="核查人员：" prop="name">
                                                        <el-input v-model="departForm.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="核查单位：" prop="checkUnit">
                                                        <!--<el-input v-model="departForm.checkUnit"></el-input>-->
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
                                                <el-col :span="8" v-show="false">
                                                    <el-form-item label="核查结果："  prop="checkResult">
                                                        <el-select v-model="departForm.checkResult" readonly="">
                                                            <el-option key="异常" label="异常" value="异常"></el-option>
                                                            <el-option key="正常" label="正常" value="正常"></el-option>
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
                                                <el-col :span="24"  v-show="toggleShow">
                                                    <el-form-item label="核查状态：" prop="checkingState">
                                                        <el-select v-model="departForm.checkingState" readonly="">
                                                            <el-option key="未核查" label="未核查" value="未核查"></el-option>
                                                            <el-option key="核查中" label="核查中" value="核查中"></el-option>
                                                            <el-option key="已核查" label="已核查" value="已核查"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item >
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
                                    <el-row :gutter="20" v-show="false">
                                        <el-col :span="24">
                                            <!--<el-button class="addDepartBtn" type="primary" @click="addBtn()" :disabled="isDisabled1" icon="el-icon-plus">添加核查</el-button>-->
                                            <el-button class="addDepartBtn" type="primary" @click="addBtn()" icon="el-icon-plus">添加核查</el-button>
                                        </el-col>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div>
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%" >
                                            <el-table-column prop="checkType" label="核查类型"></el-table-column>
                                            <el-table-column prop="name" label="核查人员"></el-table-column>
                                            <el-table-column prop="dept" label="dept" v-if="false"></el-table-column>
                                            <el-table-column prop="checkCompany" label="核查单位" ></el-table-column>
                                            <el-table-column prop="sendTime" label="核查日期"></el-table-column>
                                            <el-table-column prop="checkStatus" label="任务状态">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.checkStatus=='核查中'?'执行中':'已执行'}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="timestamp" label="时间戳" v-if="false"></el-table-column>
                                            <el-table-column prop="idNumber" label="idCard" v-if="false"></el-table-column>
                                            <el-table-column prop="checkResult" label="核查结果 " v-if="false"></el-table-column>
                                            <el-table-column label="操作" align="center">
                                                <template slot-scope="scope">
                                                    <el-button type="text" style="font-size:14px"><router-link :to="{path:'/depart2',query:{alarmNo:scope.row.timestamp,detailClick:true}}">详情</router-link></el-button>
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
            aa:null,
            flag:true,
            valueTitle:null,
            selectdata: [],
            defaultProps: {
                children: 'childCompany',
                label: 'unitName'
            },
            dataId:null,
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            tableData:[],
            toggleShow: true,
            pageShow1:true,
            checking:false,
            pageShow2:false,
            inittableData:[],
            isDisabled1:true,//添加核查
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            departForm: {
            	name:'',//核查人员
                // characterID: '',
                checkResult: '',//核查结果
                checkUnit:'',//核查单位
                checkingType: '',//核查类型
                checkingState: ''//核查状态
            },
        }
    }, 
    created() {
       this.init();
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
        // 添加核查按钮
        addBtn(){
            this.$router.push('/DepartAdd');
        },
        inquiry(){//查询按钮
            var self = this;
            self.cur_page=1;
            console.log(self.dataId)
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/swithCheckJob',
                postData:{
                    "checkPeo":self.departForm.name,//姓名模糊查询
                    "checkType":self.departForm.checkingType,//核查类型 模糊查询
                    "checkStatus":self.departForm.checkingState,//核查状态
                    "checkResult":self.departForm.checkResult,//核查结果
                    "checkCompany":self.dataId,// 核查单位(包含的是dataID)
                    "queryType":'未核查'
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.tableData = res.data;
                    self.inittableData= res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize)
                }else if(res.code != 1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        init(){//初始化列表
            var self = this;
            self.cur_page=1;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/swithCheckJob',
                postData:{
                    "checkPeo":'',//姓名模糊查询
                    "checkType":'',//核查类型 模糊查询
                    "checkStatus":'',//核查状态
                    "checkResult":'',//核查结果
                    "checkCompany":'',// 核查单位
                    "queryType":'未核查'
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.tableData=[];
                    self.tableData = res.data;
                    self.inittableData= res.data;
                    self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize)
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
            this.dataId=null;
            this.valueTitle=null;
            this.$refs[formName].resetFields();
            this.init();
        }
    },
    computed: {
        
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
    .departListTable{
        margin-top:20px
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
</style>
<style>
.departListTable .el-table th>.cell{
        color:#333;
        font-size:14px;
    }
    .departListTable .el-table td div{
        font-size:14px;
    }
    .departForm .el-form-item__label{
        width:85px!important;
    }
    .departForm .demo-form-inline input{
        width: 200px!important
    }
    .departForm .demo-form-inline select{
        width: 200px!important
    }
    .addDepartBtn a{
        color:white
    }
</style>

