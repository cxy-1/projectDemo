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
                                        <el-breadcrumb-item>休假计划申请</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>  
                            </el-card>
                            <el-card class="card2">
                                <div class="departForm">
                                    <!--申请信息表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="60px" ref='passportForm' :model="passportForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="7" style="max-width:333px">
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
                                                <el-col :span="4">
                                                    <el-form-item>
                                                        <el-button type="primary" @click = "inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('passportForm')">重置</el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <el-row :gutter="20">
                                        <el-col :span="24" class="departUpload">
                                            <el-button :disabled="isApprove" type="primary"  @click="addbeonleave">添加</el-button>
                                        </el-col>
                                    </el-row>
                                    <!--申请信息表格  -->
                                    <div  class="departTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%">
                                            <el-table-column label="年份">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.year+"年"}}</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="two" label="计划休假天数">
                                                <template slot-scope="scope">
                                                    <span>{{scope.row.totalDays+"天"}}</span>
                                                </template>
                                            </el-table-column>
<!--                                            <el-table-column prop="state" label="状态">-->
<!--                                                <template slot-scope="scope">-->
<!--                                                    <span v-if="scope.row.state==0">待申请</span>-->
<!--                                                    <span v-if="scope.row.state==1">已提交</span>-->
<!--                                                    <span v-if="scope.row.state==2">已通过</span>-->
<!--                                                    <span v-if="scope.row.state==3">已驳回</span>-->
<!--                                                </template>-->
<!--                                            </el-table-column>-->
                                            <el-table-column label="操作" align="center" width="150">
                                                <template slot-scope="scope">
                                                    <el-button v-if="scope.row.state!=1&&scope.row.state!=2" type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/addbeonleave',query:{key:1,year:scope.row.lastTime}}">修改</router-link>
                                                    </el-button>
                                                    <el-button @click="tijiao(scope.row)" type="text">
                                                        提交
<!--                                                        <router-link style="color: #409EFF;" :to="{path:'/approveDetail',query:{personId:scope.row.personId,key:1,year:scope.row.year}}">提交</router-link>-->
                                                    </el-button>
                                                    <el-button type="text">
                                                        <router-link style="color: #409EFF;" :to="{path:'/addbeonleave',query:{key:2,year:scope.row.lastTime}}">详情</router-link>
                                                    </el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <el-dialog
                                            title="提示"
                                            :visible.sync="dialogVisible"
                                            width="30%"
                                            >
                                            <span>您确定提交这条休假计划吗？</span>
                                            <span slot="footer" class="dialog-footer">
                                                <el-button @click="dialogVisible = false">取 消</el-button>
                                                <el-button type="primary" @click="queding">确 定</el-button>
                                            </span>
                                        </el-dialog>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                            </el-pagination>
                                        </div>
<!--                                        <el-dialog title="批量审批" :visible.sync="approve" :close-on-click-modal="false" width="50%">-->
<!--                                            <el-form :model="approveDate" label-width="120px">-->
<!--                                                <el-form-item label="审批结果：" prop="approveResult">-->
<!--                                                    <el-radio v-model="approveDate.approveResult" label="1">同意</el-radio>-->
<!--                                                    <el-radio v-model="approveDate.approveResult" label="2">驳回</el-radio>-->
<!--                                                    &lt;!&ndash;                                                    <el-input v-model="approveDate.approveResult" style="width:300px"></el-input>&ndash;&gt;-->
<!--                                                </el-form-item>-->
<!--                                                <el-form-item label="审批意见：" prop="description">-->
<!--                                                    <el-input  type="textarea" :rows="2" placeholder="请输入内容" v-model="approveDate.description" style="width:300px"></el-input>-->
<!--                                                </el-form-item>-->
<!--                                            </el-form>-->
<!--                                            <div slot="footer" class="dialog-footer">-->
<!--                                                <el-button @click="approve = false">取消</el-button>-->
<!--                                                <el-button type="primary" @click.native="approveSubmit">确定</el-button>-->
<!--                                            </div>-->
<!--                                        </el-dialog>-->
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
                dialogVisible:false,
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
                toggleShow: false,
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
                    years:"2019"
                },
                idCardArr:['152104198602040010','320113197512179512'],
                initdata:[],
                loading:null,
                personIds:[],
                isApprove:false,
                scopeyears:""
            }
        },
        created(){


        },
        mounted(){
            this.$nextTick(()=>{
                this.init()
            })

        },
        methods:{
            handleCurrentChange(val){
                this.cur_page=val
                this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
            },
            init(){
                const _obj={
                    url:this.global.serverPath8+"/statistics/holidayPlan/personalList",
                    postData:{
                        year:this.passportForm.years
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        this.inittableData = res.data
                        this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                    }else{
                        if (res.code != 1){
                            this.$message.warning(res.message);
                        }
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })

            },
            inquiry(){
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
                this.init()
                this.multipleSelection=[]
                this.idarr=[]
                this.personIds=[]
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
                        this.init()

                    }else{
                        if (res.code != 1){
                            this.$message.error("系统错误");
                        }
                    }
                })
            },
            addbeonleave(){
                this.$router.push("/addbeonleave")
            },
            tijiao(row){
                this.scopeyears=row.year
                this.dialogVisible=true
            },
            queding(){
                const _obj = {
                    url:this.global.serverPath8+"/statistics/holidayPlan/details?year="+this.scopeyears,
                    isGet:true
                }
                communication(_obj).then((res)=>{
                    if(res.code==0){
                        var leaveSchemeList=res.data.holidayPlan
                        leaveSchemeList.forEach((item,index)=>{
                            item.type=1
                        })
                        const obj = {
                            url:this.global.serverPath8+"/statistics/holidayPlan/application",
                            postData:{holidayPlanList:leaveSchemeList}
                        }
                        communication(obj).then((res)=>{
                            if(res.code==0){
                                this.$message.success("操作成功")
                                this.dialogVisible=false
                                this.init()
                            }
                        })
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
