<template>
    <!--    <div>-->
    <!--        <el-table-->
    <!--            ref="multipleTable"-->
    <!--            :data="tableData"-->
    <!--            tooltip-effect="dark"-->
    <!--            style="width: 100%">-->
    <!--            <el-table-column  width="55">-->
    <!--                <template slot="header" slot-scope="scope">-->
    <!--                    <el-checkbox v-model="checked" @change.native.stop="hahaa()">&nbsp;</el-checkbox>-->
    <!--&lt;!&ndash;                    <el-checkbox v-model="idarr"  @change.native.stop="hahaa()">&nbsp;</el-checkbox>&ndash;&gt;-->
    <!--                </template>-->
    <!--                <template slot-scope="scope">-->
    <!--                    <el-checkbox v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>-->
    <!--                </template>-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                label="id"-->
    <!--                width="120">-->
    <!--                <template slot-scope="scope">{{ scope.row.id }}</template>-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                label="日期"-->
    <!--                width="120">-->
    <!--                <template slot-scope="scope">{{ scope.row.date }}</template>-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                prop="name"-->
    <!--                label="姓名"-->
    <!--                width="120">-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                prop="address"-->
    <!--                label="地址"-->
    <!--                show-overflow-tooltip>-->
    <!--            </el-table-column>-->
    <!--        </el-table>-->
    <!--        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[4]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">-->
    <!--        </el-pagination>-->
    <!--        <table  id="tipMessageLabel" v-show="false">-->
    <!--           <tr>-->
    <!--               <th>id</th>-->
    <!--               <th>日期</th>-->
    <!--               <th>姓名</th>-->
    <!--               <th>地址</th>-->
    <!--           </tr>-->
    <!--            <tr v-for="item in multipleSelection">-->
    <!--                <td>{{item.id}}</td>-->
    <!--                <td>{{item.date}}</td>-->
    <!--                <td>{{item.name}}</td>-->
    <!--                <td>{{item.address}}</td>-->
    <!--            </tr>-->
    <!--        </table>-->
    <!--        <el-button @click="caonima">导出</el-button>-->
    <!--    </div>-->
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
                                        <el-breadcrumb-item>休假计划汇总</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="departForm">
                                    <!--申请信息表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="75px" ref='passportForm' :model="passportForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="name">
                                                        <el-input v-model="passportForm.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号：" prop="policeNo">
                                                        <el-input v-model="passportForm.policeNo"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="部门：" prop="dataId">
                                                        <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
                                                            <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                                            <el-option :value="valueTitle" :label="valueTitle">
                                                                <div class="tree-list">
                                                                    <el-tree id="tree-option"
                                                                         ref="selectTree"
                                                                         :expand-on-click-node="false"
                                                                         :data="selectdata"
                                                                         :props="dataProps"
                                                                         node-key="id"
                                                                         :highlight-current="true"
                                                                         @node-click="handleNodeClick2"
                                                                         check-on-click-node

                                                                         :filter-node-method="filterNode"
                                                                    >
                                                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                                                        <span>{{ node.label }}</span>
                                                                        <span v-if="data.childCompany.length!=0">
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
                                                <el-col :span="24" v-show="toggleShow">
                                                    <el-form-item label="年份：" prop="years">
                                                        <el-select v-model="passportForm.years">
                                                            <el-option label="2019年" value="2019"></el-option>
                                                            <el-option label="2020年" value="2020"></el-option>
                                                            <el-option label="2021年" value="2021"></el-option>
                                                            <el-option label="2022年" value="2022"></el-option>
                                                            <el-option label="2023年" value="2023"></el-option>
                                                            <el-option label="2024年" value="2024"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="22" style="text-align: right;">
                                                    <el-form-item>
                                                        <el-button type="primary" @click = "inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('passportForm')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class1:class2"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row :gutter="20">
                                        <el-col :span="24" class="departUpload">
                                            <el-button type="primary"  @click="leadingout">导出</el-button>
                                        </el-col>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div  class="departTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table :data="tableData" v-show="tableData.length>0?true:false">
                                            <el-table-column  width="55">
                                                <template slot="header" slot-scope="scope">
                                                    <el-checkbox v-model="checked" @change.native.stop="hahaa()">&nbsp;</el-checkbox>
                                                    <!--                    <el-checkbox v-model="idarr"  @change.native.stop="hahaa()">&nbsp;</el-checkbox>-->
                                                </template>
                                                <template slot-scope="scope">
                                                    <el-checkbox  v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="name" label="姓名" ></el-table-column>
                                            <el-table-column  label="计划休假天数">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.totalHolidays}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column width="80" prop="one" label="1月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.january?scope.row.january:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="2月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.february?scope.row.february:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="3月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.march?scope.row.march:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="4月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.april?scope.row.april:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="5月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.may?scope.row.may:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="6月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.june?scope.row.june:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="7月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.july?scope.row.july:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="8月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.august?scope.row.august:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="9月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.septemper?scope.row.septemper:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="10月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.october?scope.row.october:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="11月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.november?scope.row.november:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  width="80" prop="two" label="12月">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.december?scope.row.december:"0"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label="操作" align="center" width="150">
                                                <template slot-scope="scope">
                                                    <el-button type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/approveDetail',query:{personId:scope.row.personId,key:2,year:scope.row.lastTime}}">详情</router-link>
                                                    </el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                            </el-pagination>
                                        </div>
                                        <el-dialog title="批量审批" :visible.sync="approve" :close-on-click-modal="false" width="50%">
                                            <el-form :model="approveDate" label-width="120px">
                                                <el-form-item label="审批结果：" prop="approveResult">
                                                    <el-radio v-model="approveDate.approveResult" label="1">同意</el-radio>
                                                    <el-radio v-model="approveDate.approveResult" label="2">驳回</el-radio>
<!--                                                    <el-input v-model="approveDate.approveResult" style="width:300px"></el-input>-->
                                                </el-form-item>
                                                <el-form-item label="审批意见：" prop="description">
                                                    <el-input  type="textarea" :rows="2" placeholder="请输入内容" v-model="approveDate.description" style="width:300px"></el-input>
                                                </el-form-item>
                                            </el-form>
                                            <div slot="footer" class="dialog-footer">
                                                <el-button @click="approve = false">取消</el-button>
                                                <el-button type="primary" @click.native="approveSubmit">确定</el-button>
                                            </div>
                                        </el-dialog>
                                        <table  id="tipMessageLabel" v-show="false">
                                            <tr>
                                                <th>姓名</th>
                                                <th>计划休假天数</th>
                                                <th>1月</th>
                                                <th>2月</th>
                                                <th>3月</th>
                                                <th>4月</th>
                                                <th>5月</th>
                                                <th>6月</th>
                                                <th>7月</th>
                                                <th>8月</th>
                                                <th>9月</th>
                                                <th>10月</th>
                                                <th>11月</th>
                                                <th>12月</th>
                                            </tr>
                                            <tr v-for="item in multipleSelection">
                                                <td>{{item.name}}</td>
                                                <td>{{item.totalHolidays}}</td>
                                                <td>{{item.january==null?"0":item.january}}</td>
                                                <td>{{item.february==null?"0":item.february}}</td>
                                                <td>{{item.march==null?"0":item.march}}</td>
                                                <td>{{item.april==null?"0":item.april}}</td>
                                                <td>{{item.may==null?"0":item.may}}</td>
                                                <td>{{item.june==null?"0":item.june}}</td>
                                                <td>{{item.july==null?"0":item.july}}</td>
                                                <td>{{item.august==null?"0":item.august}}</td>
                                                <td>{{item.septemper==null?"0":item.septemper}}</td>
                                                <td>{{item.october==null?"0":item.october}}</td>
                                                <td>{{item.november==null?"0":item.november}}</td>
                                                <td>{{item.december==null?"0":item.december}}</td>
                                            </tr>
                                        </table>
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
        name: "passportList",
        data(){
            return{
                approveDate:{
                    approveResult:"1",
                    description:"",
                },

                approve:false,
                acValue:1,
                inacValue:0,
                checked:false,
                getRokes(row){
                    return row.id
                },
                cur_page:1,
                pagesize:4,
                inittableData: [],
                multipleSelection: [],
                tableData:[],
                idarr:[],
                sqxxForm:{
                    upOption:''
                },
                headers:{token: localStorage.getItem('setToken') },
                actss:this.global.serverPath8+"/personnel/passport/import",
                class1: 'el-icon-arrow-down el-icon--right',
                class2: 'el-icon-arrow-up el-icon--right',
                tableData:[],
                toggleShow: true,
                pageShow1:true,
                pageShow2:false,
                cur_page: 1,//表格当前页
                cur_page2: 1,
                pagesize:10,//表格分页条数
                pagesize2:10,
                data_length:0,
                data_length2:0,
                multipleSelection:[],
                recordId:'',
                passportForm: {
                    name:'',
                    policeNo:"",
                    years:"",
                    dataId:null
                },
                idCardArr:['152104198602040010','320113197512179512'],
                initdata:[],
                loading:null,
                personIds:[],
                selectdata:[],
                dataProps:{
                    children: 'childCompany',
                    label: 'unitName'
                },
                valueTitle:"",
                aa:null,
                dataId:null
            }
        },
        created(){
            this.initselectdata()

        },
        mounted(){
            this.$nextTick(()=>{
                // this.init()
            })

        },
        methods:{
            initselectdata(){
                const _obj = {
                    url:this.global.serverPath8 + "/user-mgmt/orgmanage/unittreeAuto",
                    isGet:true
                }
                communication(_obj).then((res)=>{
                    this.selectdata=res.data;
                    this.valueTitle = res.data[0].unitName + "(包含子部门)";
                    this.dataId = res.data[0].dataId;
                    this.inquiry();
                })
            },
            append(data) {
                this.valueTitle = data.unitName+"(包含子部门)"
                this.dataId=data.dataId

            },
            filterNode(value, data) {
                if (!value) return true;
                return data.label.indexOf(value) !== -1;
            },
            handleNodeClick2(node){
                this.valueTitle = node.unitName
                this.dataId=node.unitId
            },
            // 清除选中
            clearHandle(){
                this.valueTitle = '';
                this.dataId=null;
            },
            hahaa(v){
                if(this.checked){
                    this.idarr=[];
                    this.personIds=[]
                    this.multipleSelection=[]
                    this.multipleSelection=this.inittableData;
                    this.multipleSelection.map((c,i)=>{
                        if(isNaN(c)){
                            this.idarr.push(c.id)
                            this.personIds.push(c.personId)
                        }

                    })
                }else{
                    this.idarr=[];
                    this.personIds=[]
                    this.multipleSelection=[]
                }
            },
            haha(v,k){
                this.idarr=[]
                this.personIds=[]
                var hasRow = false
                var newArr = []
                var _this = this
                this.multipleSelection.map((c,i) =>{
                    if(c.id==k.id){
                        hasRow = true
                    }else{
                        newArr.push(c)
                    }
                })
                if(hasRow){
                    this.multipleSelection = newArr
                }else{
                    this.multipleSelection =  this.multipleSelection.concat(k)
                }
                this.multipleSelection.map((c,i)=>{
                    if(!isNaN(c)){
                        this.multipleSelection.splice(i,1)
                    }else{
                        this.idarr.push(c.id)
                        this.personIds.push(c.personId)
                    }
                })

                if(this.multipleSelection.length!=this.inittableData.length){
                    this.checked=false
                }else{
                    this.checked=true
                }
            },
            handleCurrentChange(val){
                this.cur_page=val
                this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
            },
            selectall(){
                this.multipleSelection=this.inittableData
            },
            init(){
                const _obj={
                    url:this.global.serverPath8+'/statistics/holidayPlan/List',
                    postData:{
                        "name":this.passportForm.name,
                        "policeNo":this.passportForm.policeNo,
                        "year":this.passportForm.years,
                        "dataId":this.dataId
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        this.inittableData = res.data
                        this.inittableData.forEach((item,index)=>{
                            item.id=index
                        })
                        this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                    }else{
                        if(res.code!=1){
                            this.$message.warning(res.message);
                        }
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })

            },
            leadingout(){
                console.log(this.multipleSelection)
                $("#tipMessageLabel").table2excel({
                    // 不被导出的表格行的CSS class类
                    exclude: ".noExl",
                    name: "Excel Document Name",
                    filename: "年休假计划.xls",
                });
                this.multipleSelection=[]
                this.idarr=[]
                this.personIds=[]
                this.checked=false
            },
            piapprove(){
                if(this.personIds.length==0){
                    this.$message.error("请勾选数据")
                }else{
                    this.approve=true;
                }
            },
            inquiry(){
                if(this.dataId==null||this.dataId==""){
                    this.$message.error("请选择部门搜索")
                    return
                }
                this.cur_page=1;
                this.init()
                this.multipleSelection=[];
                this.idarr=[

                ]
                this.personIds=[]
            },
            // 收起展开
            upDown() {
                this.toggleShow = !this.toggleShow;
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                console.log(this.multipleSelection);
                // for(var i=0;i<this.multipleSelection.length;i++){
                //     this.idCardArr += this.multipleSelection[i].characterID+','
                // };
            },
            // 重置表单
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.cur_page=1
                // this.init()
                this.multipleSelection=[]
                this.idarr=[]
                this.personIds=[]
                this.dataId=null
                this.valueTitle=""
            },
            handleApprove(row){

            },
            approveSubmit(){
                const obj = {
                    url:this.global.serverPath8+'/statistics/annualLeave/batchUpdateState',
                    postData:{
                        "personIds":this.personIds,
                        "state":this.approveDate.approveResult,
                        "opinion":this.approveDate.description
                    }
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.approve = false;
                        if(this.approveDate.approveResult==1){
                            this.$message.success("审批成功")
                        }else{
                            this.$message.success("驳回成功")
                        }
                        this.init();
                    }else{
                        this.$message.error("系统错误")
                    }
                })
            }
        }

    }
</script>

<style scoped>
    .departTable{
        margin-top:20px
    }
    .sb{
        mso-number-format:"/@";
    }
    .el-scrollbar .el-scrollbar__view .el-select-dropdown__item{
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
        font-weight: 700;
    }
    .el-tree >>>.is-current .el-tree-node__children .el-tree-node__label{
        color:#606266;
        font-weight: normal;
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
    /**.departForm .demo-form-inline input{
        width: 200px!important
    }
    .departForm .demo-form-inline select{
        width: 200px!important
    }*/
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
