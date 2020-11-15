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
                                        <el-breadcrumb-item>护照管理</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="departForm">
                                    <!--申请信息表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="85px" ref='passportForm' :model="passportForm" class="demo-form-inline">
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
                                                <el-col :span="8" v-show="toggleShow" >
                                                    <el-form-item label="部门：" prop="dataId">
                                                        <el-select id="wanghao1"  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
                                                            <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                                            <el-option :value="valueTitle" :label="valueTitle">
                                                                <div id="wanghao" class="tree-list">
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
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="证件种类：" prop="papersType">
                                                        <el-select v-model="passportForm.papersType" readonly="">
                                                            <el-option label="全部" value=""></el-option>
                                                            <el-option label="普通护照" value="14"></el-option>
                                                            <el-option label="往来港澳通行证" value="21"></el-option>
                                                            <el-option label="大陆居民往来台湾通行证" value="25"></el-option>
                                                            <el-option label="公务护照" value="12"></el-option>
                                                            <el-option label="中华人民共和国出入境通行证" value="20"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="16" v-show="toggleShow">
                                                    <el-form-item label="状态：" prop="state">
                                                        <el-select v-model="passportForm.state" readonly="">
                                                            <el-option  label="全部" value=""></el-option>
                                                            <el-option  label="未上交" value="0"></el-option>
                                                            <el-option  label="已上交" value="1"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="22" style="text-align: right;">
                                                    <el-form-item>
                                                        <el-button type="primary" @click = "inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('passportForm')">重置</el-button>
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
                                            <el-button type="primary"  @click="leadingout">导出护照</el-button>
                                            <el-upload ref="my-upload"  class="upload-demo" :headers = "headers" accept=".html" :on-error="handleError" :on-success="handleSuccess" v-model="sqxxForm.upOption" drag :action="actss" multiple :before-upload="beforeUpload">
                                                <el-button size="small" type="primary">导入护照</el-button>
                                            </el-upload>
                                        </el-col>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div class="departTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%">
                                            <el-table-column  width="30">
                                                <template slot="header" slot-scope="scope">
                                                    <el-checkbox v-model="checked" @change.native.stop="hahaa()">&nbsp;</el-checkbox>
                                                    <!--                    <el-checkbox v-model="idarr"  @change.native.stop="hahaa()">&nbsp;</el-checkbox>-->
                                                </template>
                                                <template slot-scope="scope">
                                                    <el-checkbox v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="userName" label="姓名" width="80"></el-table-column>
                                            <el-table-column prop="sex" :formatter="zhsex" label="性别" width="50"></el-table-column>
                                            <el-table-column prop="policeNo" label="警号" width="90"></el-table-column>
                                            <el-table-column prop="company" label="工作单位" ></el-table-column>
                                            <el-table-column prop="idNumber" label="身份证号" v-if="false"></el-table-column>
                                            <el-table-column prop="papersType" :formatter="zhpapersType" label="证件类型"></el-table-column>
                                            <el-table-column prop="papersNo" label="证件号码" v-if="false"></el-table-column>
                                            <el-table-column prop="issuingDate" label="签发日期" width="100"></el-table-column>
                                            <el-table-column prop="validityDate" label="有效期至" width="100"></el-table-column>
                                            <el-table-column prop="state" :formatter="zhstate" label="状态" width="80"></el-table-column>
                                            <el-table-column prop="state" label="上交/发放" width="100">
                                                <template slot-scope="scope">
                                                     <!-- <el-button type="primary" @click="upHand(scope.row.id,scope.row.state)">上交</el-button>  -->
                                                      <el-switch @change="switchChange(scope.row.id,scope.row.state)" v-model="scope.row.state"  :active-value="acValue" :inactive-value="inacValue"></el-switch>&emsp;  
                                                </template> 
                                            </el-table-column>
                                            <el-table-column label=""  width="80">
                                                <template slot-scope="scope">
                                                    <el-button type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/passportDetail',query:{id:scope.row.id}}">查看详情</router-link>
                                                    </el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                            </el-pagination>
                                        </div>
                                        <table  id="tipMessageLabel" v-show="false">
                                            <tr>
                                                <th>姓名</th>
                                                <th>性别</th>
                                                <th>警号</th>
                                                <th>工作单位</th>
                                                <th>身份证号</th>
                                                <th>证件类型</th>
                                                <th>证件号码</th>
                                                <th>签发日期</th>
                                                <th>有效期至</th>
                                                <th>上交状态</th>
                                            </tr>
                                            <tr v-for="item in multipleSelection">
                                                <td>{{item.userName}}</td>
                                                <td>{{item.sex==1?"男":"女"}}</td>

                                                <td >{{item.policeNo+"&nbsp;"}}</td>
                                                <td>{{item.company}}</td>
                                                <td>{{item.idNumber+"&nbsp;"}}</td>
                                                <td v-if="item.papersType==14">普通护照</td>
                                                <td v-else-if="item.papersType==21">往来港澳通行证</td>
                                                <td v-else-if="item.papersType==25">大陆居民来往台湾通行证</td>
                                                <td v-else-if="item.papersType==12">公务护照</td>
                                                <td v-else-if="item.papersType==20">中华人民共和国出入境通行证</td>
                                                <td v-else></td>
                                                <td>{{item.papersNo}}</td>
                                                <td>{{item.issuingDate}}</td>
                                                <td>{{item.validityDate}}</td>
                                                <td>{{item.state=="0"?"未上交":"已上交"}}</td>
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
        <!-- 上交提示框 -->
         <!-- <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">是否确定上交/发放？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="switchChange">确 定</el-button>
            </span>
        </el-dialog>  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
    export default {
        name: "passportList",
        data(){
            return{
                acValue:1,
                inacValue:0,
                checked:false,
                delVisible:false,
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
                recordId:'',
                tabId:'',
                tabState:'',
                passportForm: {
                    name:'',
                    policeNo:"",
                    idNumber: '',
                    company: '',
                    checkingType: '',
                    checkingState: '',
                    papersType:"",
                    state:""
                },
                idCardArr:['152104198602040010','320113197512179512'],
                initdata:[],
                loading:null,
                historyTable:[],
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
                const obj = {
                    url:this.global.serverPath8 + "/user-mgmt/admin/managelimit",
                    isGet:true
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.initdata=res.data;
                    }
                })
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
                this.valueTitle = data.unitName+"(包含子部门)";
                this.dataId=data.dataId;

            },
            filterNode(value, data) {
                if (!value) return true;
                return data.label.indexOf(value) !== -1;
            },
            handleNodeClick2(node){
                this.valueTitle = node.unitName;
                this.dataId=node.unitId;
            },
            // 清除选中
            clearHandle(){
                this.valueTitle = '';
                this.dataId=null;
            },
            upHand(id,state){
                this.tabId=id;
                this.tabState=state;
                this.delVisible=true;
            },
            beforeUpload(){
                    this.loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
            },
            switchChange(id,state){
                const _obj={
                    url:this.global.serverPath8+'/personnel/passport/updateState',
                    postData:{
                        passportId:id,
                        // passportId:this.tabId,
                        state:state,
                        // state:state,
                        operator:localStorage.getItem("loginUser")
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        this.$message.success("操作成功");
                        this.delVisible=false;
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })
            },
            zhpapersType(row,column){
                if(row.papersType==14){
                    return "普通护照"
                }else if(row.papersType==21){
                    return "往来港澳通行证"
                }else if(row.papersType==12){
                    return "公务护照"
                }
                else if(row.papersType==20){
                    return "中华人民共和国出入境通行证"
                }
                else if(row.papersType==12){
                    return "公务护照"
                }else{
                    return "大陆居民来往台湾通行证"
                }
            },
            zhstate(row,column){
               return row.state==0?"未上交":"已上交"
            },
            zhsex(row,column){
                return row.sex==1?"男":"女"
            },
            hahaa(v){
                if(this.checked){
                    this.idarr=[]
                    this.multipleSelection=this.inittableData;
                    this.multipleSelection.map((c,i)=>{
                        if(isNaN(c)){
                            this.idarr.push(c.id)
                        }

                    })
                }else{
                    this.idarr=[];
                    this.multipleSelection=[]
                }
            },
            haha(v,k){
                this.idarr=[]
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
                    url:this.global.serverPath8+'/personnel/passport/list',
                    postData:{
                        "userName":this.passportForm.name,
                        "policeNo":this.passportForm.policeNo,
                        "idNumber":this.passportForm.idNumber,
                        "dataId":this.dataId,
                        "papersType":this.passportForm.papersType,
                        "state":this.passportForm.state,
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        this.inittableData = res.data;
                        this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                    }else if(res.code!=1){
                        this.$message.warning(res.message)
                    }
                })
            },
            handleSuccess(response,file,fileList){
                if(response.code == 0){
                    this.$message.success("导入成功")
                    this.cur_page=1
                    this.init()
                    this.multipleSelection=[]
                    this.idarr=[]
                    this.checked=false
                    this.loading.close();
                }else{
                    this.$message.error("系统错误，请检查上传文件")
                    this.loading.close();
                }
            },
            handleError(response,file,fileList){
                this.$message.error("系统错误，请检查上传文件")
                this.loading.close();
            },
            leadingout(){
                $("#tipMessageLabel").table2excel({
                    // 不被导出的表格行的CSS class类
                    exclude: ".noExl",
                    name: "Excel Document Name",
                    filename: "护照管理.xls",
                });
                this.multipleSelection=[]
                this.idarr=[]
                this.checked=false
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
            },
            // 收起展开
            upDown() {
                this.toggleShow = !this.toggleShow;
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
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
                this.dataId=null
                this.valueTitle=""
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
    /*.el-tabs__header,.is-top {*/
    /*    margin-bottom: 0px!important;*/
    /*}*/
    /*.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {*/
    /*    !*height: auto;*!*/
    /*    min-height: 200px;*/
    /*    max-height: 274px;*/
    /*    padding: 0;*/
    /*    !*overflow: hidden;*!*/
    /*    overflow-y: scroll;*/
    /*}*/


    /*.el-select-dropdown__item.selected {*/
    /*    font-weight: normal;*/
    /*}*/

    /*.el-select-dropdown__item.hover, .el-select-dropdown__item:hover {*/
    /*    background: #ffffff;*/
    /*}*/
    /*.el-select-dropdown__list {*/
    /*    list-style: none;*/
    /*    !* padding: 6px 0; *!*/
    /*    margin: 0;*/
    /*    -webkit-box-sizing: content-box;*/
    /*    box-sizing: content-box;*/
    /*}*/
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
