<template>
    <div class="cjgl policeManage">
        <div class="jyxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row> 
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>人事管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>人员管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>人员信息</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="jyxx-info">
                                    <div class="handle-jyxx">
                                        <el-form :inline="true" :label-position="'right'" label-width="72px" ref='jyxxList' :model="jyxxList" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="name">
                                                        <el-input v-model="jyxxList.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号：" prop="user">
                                                        <el-input v-model="jyxxList.user"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="工作单位：" prop="workUnit"><!--clearable可清除，filterable不可搜索-->
                                                        <!--<el-select v-model="jyxxList.workUnit" filterable clearable @change="getDas">
                                                            <el-option v-for="item in optionGzdw" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                                        </el-select>-->
                                                        <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
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
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click="getSearchData()">查询</el-button>
                                                        <el-button @click="resetForm('jyxxList')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class1:class2"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <div class="jyxx-table ryTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                                            <el-table-column type="selection" width="55"></el-table-column>
                                            <el-table-column prop="userName" label="姓名" width="80"></el-table-column>
                                            <el-table-column prop="unitName" label="工作单位"></el-table-column>
                                            <el-table-column prop="personId" label="personId" v-if="false"></el-table-column>
<!--                                            <el-table-column prop="companyName" label="工作单位">-->
<!--                                            </el-table-column>-->
                                            <el-table-column prop="idNumber" label="身份证号" >
                                            </el-table-column>
                                            <el-table-column prop="phone" label="电话号码">
                                            </el-table-column>
                                            <el-table-column label="操作" align="center" width="70">
                                                <template slot-scope="scope">
                                                    <router-link :to="{path:'/MyFiles',query:{alarmNo:scope.row.personId,clickInfo:true}}">详情</router-link>
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
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import {communication} from '../../data/getAxios';
import TreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
    components: { TreeSelect },
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
            deptIdCon:'',
            optionGzdw:[],
            optionZzmm:[],//政治面貌
            optionZwjb:[],//职务级别
            stepActive: 2,
            inittableData:[],
            showLxs: false,//显示旅行社
            toggleShow: true,
            pageShow1: true,
            cur_page: 1,//表格当前页
            pagesize: 10,//表格分页条数
            data_length: 0,
            multipleSelection: [],//多选数组当前选中要删除的项
            class1: 'el-icon-arrow-up el-icon--right',
            class2: 'el-icon-arrow-down el-icon--right',
            jyxxList: {
                name: '',//姓名
                user: '',//警号
                workUnit: null,//工作单位
                onWork: '',//是否在职
                // workName: null,//职务名称
                // workJob: '',//工作部门
                // workLevel: null,//职务级别
                // politicCount: null//政治面貌
            },
            idx: -1,
            tableData: []
        }
    },
     watch: {
        aa(val) {
            this.$refs.selectTree.filter(val);
        }
    },
    created() {
        this.Company();
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
                this.valueTitle = res.data[0].unitName+"(包含子部门)";
                this.dataId = res.data[0].dataId;
                this.getSearchData();
            })
        },
        append(data) {
            // this.flag=false
            this.valueTitle = data.unitName+"(包含子部门)";
            this.dataId=data.dataId;
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
            this.valueTitle = node.unitName;
            this.dataId=node.unitId;
        },
        // 清除选中
        clearHandle(){
            this.valueTitle = '';
            this.dataId=null;
        },
        Company() {//工作单位
            var self = this;
            const _obj={
				url:self.global.serverPath8 + '/user-mgmt/admin/managelimit',
				isGet:true
			};
			communication(_obj).then((res) => {
				if(res.code==0){
                    for(var i=0;i<res.data.length;i++){
                        var obj = {};
                        obj.value = res.data[i].companyId;
                        obj.label = res.data[i].fullName;
                        self.optionGzdw.push(obj);
                    };
				}else if(res.code != 1){
					self.$message.warning(res.message)
				}
			}).catch((err) => {
				console.log('获取工作单位失败！')
			});
        },
        getDas(val){
            this.deptIdCon = val;
            console.log(this.jyxxList.workUnit);
        },
        // 批量删除
        delAll() {
            const length = this.multipleSelection.length;
            let str = '';
            this.del_list = this.del_list.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
            }
            this.data_length = this.data_length - length;
            // console.log(this.data_length)
            this.$message.error('删除了' + str);
            this.multipleSelection = [];
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 表格分页导航
        handleCurrentChange(val){
            this.cur_page=val
            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
        },
        // 查询表格
        getSearchData() {
            var self = this;
            self.cur_page=1;
            if(self.dataId == null ||self.dataId == ''){
                self.$message.warning('请选择工作单位');
            }else{
                const _obj={
                    url:self.global.serverPath8 + '/user-mgmt/user/list',
                    postData:{
                        "unitId":self.jyxxList.workUnit==''?null:self.jyxxList.workUnit,
                        // "pageNum":1,
                        // "pageSize":"20000",
                        "dataId":self.dataId,
                        "userName":self.jyxxList.name==''?null:self.jyxxList.name,
                        "policeNo":self.jyxxList.user==''?null:self.jyxxList.user,
                    },
                    isGet:false
                };
                communication(_obj).then((res) => {
                    console.log('bbbbb'+res);
                    if(res.code == 0){
                        self.tableData = res.data;
                        self.inittableData= res.data;
                        self.tableData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                });
            }
            
        },
        //详情跳转
        handleDetail(index, row) {
            var self = this;
            self.idx = index;
            self.$http.get(self.global.serverPath + '/zhzg/archive/queryMyArchiveBasicInfoByPersonId', {//传递参数
                params: {
                    "personId": row.personId,//表格返回
                }
            }).then(function(res) {
                this.$router.push({ path: '/myFiles', query: { clickInfo: res.body.rows, clickTurn: true } });//跳转到详情页面
            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },
        // 审批跳转
        handleEdit(index, row) {
            var self = this;
            self.idx = index;
            const item = self.tableData[index];
            self.$http.get(self.global.serverPath + '/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
                params: {
                    "applicationFormId": row.applicationFormId,//申请单号
                }
            }).then(function(res) {
                this.$router.push({ path: '/dashboard3', query: { show1: true, show2: false, info: res.body.rows, exist: true } });//跳转到详情页面
            }, function(res) {
                console.log('请求失败！！！！！')
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
        changeUnit() {
            var self = this;
            if (self.jyxxList.onWork == '是') {
                // self.showLxs = true;
            } else {
                // self.showLxs = false;
            }
        },
        // 重置表单
        resetForm(formName) {
            this.dataId=null;
            this.valueTitle=null;
            this.$refs[formName].resetFields();
            console.log(this.jyxxList.workUnit)
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
  .vue-treeselect{
        width:220px!important;
    }
.pagination2 {
    height: 32px;
    margin-bottom: 10px;
    margin-top: 10px;
}

.pagination2 .el-pagination {
    position: absolute;
    right: 30px;
    bottom: 10px;
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

.policeManage .user-info {
    padding: 0 7%;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
}

.policeManage .user-info p {
    margin: 10px 0;
}

.el-row {
    margin-bottom: 6px;
}

.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.ryTable .el-table .cell{
    line-height:34px!important;
}
  
.zwmcTree .el-form-item__content {
    width: 53%!important;
}

.pagination2 .el-input--small .el-input__inner {
    width: 45px!important;
}

.pagination .el-input--small .el-input__inner {
    width: 45px!important;
}

.jyxx-info .el-button--text {
    font-size: 14px;
}

.jyxx-info .el-input--small .el-input__inner {
    width: 220px;
}

.jyxx-info .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.jyxx-info .el-row[data-v-6ad31440] {
    margin-bottom: 14px;
}

.jyxx-info .el-form-item__label {
    padding: 0;
}
</style>

