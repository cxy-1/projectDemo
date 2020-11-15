<template>
    <div class="depart">
        <div class="sqxx-option">
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
                            <el-card class="departAddFormCon">
                                <div class="departAddForm">
                                    <el-col :span="24" class="title">
                                        <div class="grid-content bg-purple-dark clearfix"><span>新增核查</span></div>
                                    </el-col>
                                    <div class="DepartAdd"> 
                                        <el-row>
                                            <el-col :span="24">
                                                <el-form ref="form" :model="form" label-width="80px">
                                                    <el-row :gutter="6">
                                                        <el-col :span="8">
                                                            <el-form-item label="核查类型">
                                                                <el-select v-model="form.region">
                                                                    <el-option label="随机抽查" value="随机抽查"></el-option>
                                                                    <el-option label="年度普查" value="年度普查"></el-option>
                                                                </el-select>
                                                            </el-form-item>
                                                        </el-col> 
                                                        <el-col :span="8">
                                                            <el-form-item label="单位：" prop="units">
                                                                <!--<el-select v-model="form.units" filterable  @change="getDas">
                                                                    <el-option v-for="item in optionGzdw" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                                                </el-select>-->
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
                                                        <!-- <el-form-item label="单位：">
                                                            <el-select v-model="form.units" value-key="id" @change="changeSelect()">
                                                                <el-option label="江北新区分局" value="江北新区分局"></el-option>
                                                            </el-select>
                                                        </el-form-item>  -->
                                                    </el-row>
                                                </el-form>
                                            </el-col>
                                            <!-- filterable 属性为搜索框 --> 
                                            <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                            <el-col :span="24" class="departAddTable" v-show="tableData.length>0?true:false">
                                                <el-table v-show="tab" :data="tableData" style="width: 100%" ref="selectTable">
                                                    <el-table-column  width="30">
                                                        <template slot="header" slot-scope="scope">
                                                            <el-checkbox v-model="checked" @change.native.stop="allSelect(checked)">&nbsp;</el-checkbox>
                                                        </template>
                                                        <template slot-scope="scope">
                                                            <el-checkbox v-model="idarr" :label="scope.row.personId" @change.native.stop="aloneSelect(scope, scope.row)" :key="scope.personId">&nbsp;</el-checkbox>
                                                        </template>
                                                    </el-table-column>
                                                    <el-table-column prop="userName" label="姓名" width="120"></el-table-column>
                                                    <el-table-column prop="personId" v-if="false"></el-table-column>
                                                    <el-table-column prop="policeNo" label="警号" v-if="false"></el-table-column>
                                                    <el-table-column prop="idNumber" label="身份证号"></el-table-column>
                                                    <el-table-column label="操作" align="center" width="200">
                                                        <template slot-scope="scope">
                                                            <el-button type="text" ><router-link :to="{path:'/DepartDetails',query:{alarmNo:scope.row.idNumber}}" style="color:#409EFF!important">详情</router-link></el-button>
                                                        </template>
                                                    </el-table-column>
                                                </el-table>
                                                <div class="pagination">
                                                    <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="pagetableData.length">
                                                    </el-pagination>
                                                </div>
                                                <!-- <template>  
                                                    <el-transfer :data="unitList" v-model="value3" :render-content="renderFunc" :titles="['选择人员', '已选人员']" :format="{ noChecked: '${total}', hasChecked: '${checked}/${total}' }" @change="handleNodeClick">
                                                    </el-transfer>
                                                </template> -->
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span="24">
                                                <div class="btn-group">
                                                    <el-button @click="preserve()" :disabled="isDisabled2" v-loading.fullscreen.lock="fullscreenLoading1" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">保存</el-button>
                                                    <el-button @click="backBtn">取消</el-button>
                                                    <el-button type="primary" @click="propelli()" :disabled="isDisabled1" v-loading.fullscreen.lock="fullscreenLoading2" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">推送</el-button>
                                                </div>
                                            </el-col>
                                        </el-row>
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
            pagetableData:[],
            optionGzdw:[],
            checked:false,
            idarr:[],
            isDisabled1:true,//推送
            isDisabled2:true,//保存
            multipleSelection: [],
            fullscreenLoading1:false,//保存loading
            fullscreenLoading2:false,//推送loading
            form:{
                region:'',
                // units:''
            },
            pageShow1: false,
            cur_page: 1,//表格当前页
            pagesize: 10,//表格分页条数
            data_length: 0,
            tab:true,
            tableData:[],
            value3: [1],
            unitList:[],
            idCardArr:'',
            nameArr:'',
            renderFunc(h, option) {
                return <span>{ option.label }</span>;
            }
        };
    },
    created() {
        // this.getTreeList();
        // this.company();//获取工作单位
        this.initselectdata();//获取工作单位
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
            this.getDas();
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
            this.getDas();
        },
        // 清除选中
        clearHandle(){
            this.valueTitle = ''
            this.dataId=null
            // this.valueId = null
            // this.defaultExpandedKey = []
            // this.clearSelected()
        },
        allSelect(select){//全选
            if(select){
                this.idarr=[]
                this.multipleSelection=this.pagetableData;
                if(this.multipleSelection.length>0){
                    this.isDisabled1=false//点击推送
                    this.isDisabled2=false//点击保存
                }else{
                    this.isDisabled1=true//点击推送
                    this.isDisabled2=true//点击保存
                }
                this.multipleSelection.map((c,i)=>{
                    if(isNaN(c)){
                        this.idarr.push(c.personId)
                    }
                })
            }else{
                this.idarr=[];
                this.multipleSelection=[]
            }
        },
        aloneSelect(v,k){//可记忆的复选
            console.log(k);
            this.idarr=[];
            var hasRow = false;
            var newArr = [];
            var _this = this;
            this.multipleSelection.map((c,i) =>{
                if(c.id==k.personId){
                    hasRow = true;
                }else{
                    newArr.push(c)
                }
            })
            if(hasRow){
                this.multipleSelection = newArr
            }else{
                this.multipleSelection =  this.multipleSelection.concat(k)
            }
            if(this.multipleSelection.length>0){
                this.isDisabled1=false//点击推送
                this.isDisabled2=false//点击保存
            }else{
                this.isDisabled1=true//点击推送
                this.isDisabled2=true//点击保存
            }
            this.multipleSelection.map((c,i)=>{
                if(!isNaN(c)){
                    this.multipleSelection.splice(i,1)
                }else{
                    this.idarr.push(c.personId)
                }
            })

            if(this.multipleSelection.length!=this.pagetableData.length){
                this.checked=false
            }else{
                this.checked=true
            }
        },
        company() {//获取工作单位下拉框
            var self = this; 
            const _obj={
				url:self.global.serverPath8 + '/statistics/entryandexit/getDeptName',
				isGet:true
			};
			communication(_obj).then((res) => {
                var danweiArr = [];
                self.optionGzdw=[];
                for(var i=0;i<res.data.length;i++){
                    danweiArr.push(res.data[i].split(','))
                };
				if(res.code==0){
                    for(var i=0;i<res.data.length;i++){
                        var obj = {};
                        obj.label= danweiArr[i][0];
                        obj.value = danweiArr[i][1];
                        self.optionGzdw.push(obj);
                    };
				}else if(res.code!=1){
					self.$message.warning(res.message)
				}
			}).catch((err) => {
				console.log('获取工作单位失败！')
			});
        },
        backBtn(){//取消返回列表页
            this.$router.replace('/DepartList')
        },
        // 分页导航
		handleCurrentChange(val) {
			this.cur_page=val;
            this.tableData=this.pagetableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize);
		},
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.selectTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.selectTable.clearSelection();
            }
        },
        preserve(){//保存2
            var self = this;
            var data1=[],timeChange=null;
            for(var i=0;i<self.multipleSelection.length;i++){
                data1.push(self.multipleSelection[i].idNumber)
            };
            if(self.$route.query.clickEdit){//修改跳转过来的
                timeChange=self.$route.query.alarmNo;
            }else{
                timeChange=null;
            }
            if(self.form.region!=''){//核查类型必须写
                self.fullscreenLoading1=true;
                const _obj={
                    url:self.global.serverPath8 + '/statistics/entryandexit/sendPeople',
                    postData:{
                        "idNumber":data1,
                        "timestamp":timeChange,//时间戳
                        "checkType": self.form.region,//核查类型
                        "clickType":2//保存
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.$message.success(res.message);
                        self.fullscreenLoading1=false;
                        self.$router.push({ path: '/DepartList'})
                    }else if(res.code!=1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('保存失败！')
                });
            }else{
                self.$message.warning('请选择核查类型！')
            }
        },
        propelli(){//推送1
            var self = this;
            var data1=[],timeChange=null;
            for(var i=0;i<self.multipleSelection.length;i++){
                data1.push(self.multipleSelection[i].idNumber)
            };
            if(self.$route.query.clickEdit){//修改跳转过来的
                timeChange=self.$route.query.alarmNo;
            }else{
                timeChange=null;
            }
            console.log(data1)
            if(self.form.region!=''){//核查类型必须写
                self.fullscreenLoading2=true;
                const _obj={
                    url:self.global.serverPath8 + '/statistics/entryandexit/sendPeople',
                    postData:{
                        "idNumber":data1,
                        "timestamp":timeChange,//时间戳
                        "checkType": self.form.region,//核查类型
                        "clickType":1//推送
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.$message.success(res.message)
                        self.fullscreenLoading2=false;
                        self.$router.push({ path: '/DepartList'})
                    }else if(res.code!=1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('推送失败！')
                });
            }else{
                self.$message.warning('请选择核查类型！')
            }
        },
        getDas(){//点击单位下拉选项获取数据
            var self = this;
            // this.deptIdCon = val;
            // this.valueTitle = node.unitName
            // this.dataId=node.unitId
            self.cur_page=1;
            const _obj={
                url:self.global.serverPath8 + '/statistics/entryandexit/getPeoName',
                postData:{
                    "dataId":this.dataId
                },
			};
			communication(_obj).then((res) => {
                if(res.code == 0){
                    self.tab=true;
                    self.tableData = res.data;
                    self.pagetableData=res.data;
                    self.tableData=self.pagetableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                }
			}).catch((err) => {
				console.log('获取单位人员失败！')
			});
        },
    },
    mounted(){
        var self=this;
        if(self.$route.query.clickEdit){//列表修改跳转过来
            self.form.units=self.$route.query.dept;//单位
            self.form.region=self.$route.query.checkType;//核查类型
            console.log('修改时间戳：'+self.$route.query.alarmNo)
            const _obj={
                url:self.global.serverPath8 + '/statistics/entryandexit/sendPeopleChange',
                postData:{
                    "timestamp":self.$route.query.alarmNo//时间戳
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    var data1=res.data;
                    self.tableData = data1;
                    self.pagetableData=data1;
                    self.tableData=self.pagetableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize);
                    self.allSelect(true);
                    // if(data1.length>0){
                    //     for(var i=0;i<data1.length;i++){
                    //         selectData.push(data1[i]);
                    //     }
                    //     console.log(selectData)
                    //     setTimeout(function(){
                    //         self.toggleSelection(selectData)
                    //     },1000);
                    // }
                    // for(var i=0;i<res.data.length;i++){
                    //     data1[i].userName=data1[i].name;
                    // }
                    // var deptIdCon2 = data1[0].deptId;
                    // const _obj={
                    //     url:self.global.serverPath8 + '/statistics/entryandexit/getPeoName',
                    //     postData:{
                    //         "deptId":deptIdCon2
                    //     },
                    // };
                    // communication(_obj).then((res) => {
                    //     if(res.code == 0){
                    //         var data2=res.data,selectData=[];//默认选中行数  
                    //         self.tableData = data2;
                    //         if(res.data.length>0 && self.tableData.length>0){
                    //             for(var i=0;i<self.tableData.length;i++){
                    //                 for(var j=0;j<data1.length;j++){
                    //                     if(self.tableData[i].idNumber == data1[j].idNumber){
                    //                         selectData.push(self.tableData[i]);
                    //                     }
                    //                 }
                    //             }
                    //             setTimeout(function(){
                    //                 self.toggleSelection(selectData)
                    //             },600);
                    //         }
                    //     }
                    // }).catch((err) => {
                    //     console.log('获取未核查数据失败！！！！')
                    // });
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('保存失败！')
            });
            
        }else{
            this.initselectdata();//获取工作单位
            // this.company()//获取工作单位
        }
    }
};
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
    .departTable{
        margin-top:20px
    }
</style>
<style>
.departAddTable .el-table th>.cell{
        color:#333;
        font-size:14px;
    }
    .departAddTable .el-table td div{
        font-size:14px;
    }
    .DepartAdd{
        padding: 20px
    }
    .departAddFormCon{
        margin: 20px
    }
    .DepartAdd .el-transfer-panel__header{
        text-align: left
    }
    .DepartAdd .el-transfer-panel__list{
        text-align: left
    }
    .departAddForm .title{
        padding: 18px 20px;
        border-bottom: 1px solid #EBEEF5;
        box-sizing: border-box;
        float:inherit
    }
    .DepartAdd .el-transfer{
        margin: 30px 0;
    }
    .departAddFormCon .el-card__body{
        padding: 0
    }
    .departAddForm .el-transfer-panel{
        width: 30%
    }
    .departAddForm .el-transfer{
        text-align: center
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
</style>

