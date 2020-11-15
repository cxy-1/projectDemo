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
                                        <el-breadcrumb-item>请销假管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）核查详情</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <div class="DepartDetaCon">
                                <div class="DepartDetaConInfo is-always-shadow">
                                    <div class="grid-content bg-purple-dark clearfix"><span>出入境证件</span></div>
                                    <div class="DepartDetaTab">
                                        <el-form :inline="true" label-position="left" label-width="" ref='DepartDetails' :model="DepartDetails" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8" style="padding-left:12px"><el-form-item class="" label="姓名："><span>{{DepartDetails.name}}</span></el-form-item></el-col>
                                                <el-col :span="8"><el-form-item class="" label="性别："><span>{{DepartDetails.sex}}</span></el-form-item></el-col>
                                                <el-col :span="8"><el-form-item class="" label="出生日期："><span>{{DepartDetails.birthday}}</span></el-form-item></el-col>
                                                <el-col :span="8" style="padding-left:12px"><el-form-item class="" label="身份证号："><span>{{DepartDetails.characterID}}</span></el-form-item></el-col>
                                            </el-row>
                                        </el-form>
                                        <img v-show="DepartDetaConInfo.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <div v-show="DepartDetaConInfo.length>0?true:false">
                                            <el-table ref="DepartDetaConInfo" :data="DepartDetaConInfo" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
                                                <el-table-column type="index" label="序号"></el-table-column>
                                                <el-table-column prop="docType" label="证件种类"  show-overflow-tooltip></el-table-column>
                                                <el-table-column prop="docNumber" label="证件号码" show-overflow-tooltip></el-table-column>
                                                <el-table-column prop="issueDate"label="签发日期" ></el-table-column>
                                                <el-table-column prop="validDate" label="有效期至" ></el-table-column>
                                            </el-table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="DepartDetaCon">
                                <div class="DepartDetaConlist is-always-shadow">
                                    <div class="grid-content bg-purple-dark clearfix"><span>出入境记录</span></div>
                                    <img v-show="DepartDetaConlist.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                    <div class="DepartDetaTab" v-show="DepartDetaConlist.length>0?true:false">
                                        <template>
                                            <el-table ref="DepartDetaConlist" :data="DepartDetaConlist" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
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
                                        </template>
                                    </div>
                                </div>
                            </div>
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
            DepartDetails:{
                name:'',
                sex:'',
                birthday:'',
                characterID:''
            },
            DepartDetaConInfo:[],
            DepartDetaConlist:[]
        }
    },
    created() {
       this.appliInfor();//申请信息
       this.moveRecord();//出入境记录
    },
    methods: { 
    	appliInfor(){//申请信息
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/getDocuments',
                postData:{
                    "idNumber":self.$route.query.alarmNo
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.DepartDetaConInfo = res.data;
                    self.DepartDetails.name=res.data[0].name;
                    self.DepartDetails.sex=res.data[0].sex.slice(1);
                    self.DepartDetails.birthday=res.data[0].bornhDate;
                    self.DepartDetails.characterID=self.$route.query.alarmNo
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        },
        moveRecord(){//出入境记录
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/statistics/entryandexit/getRecords',
                postData:{
                    "idNumber":self.$route.query.alarmNo
                }
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    self.DepartDetaConlist = res.data;
                }else if(res.code!=1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('请求失败！')
            })
        }
    },
    computed: {
        
    }
}

</script>
<style scoped>
    .departTable{
        margin-top:20px
    }
</style>
<style>
    .departForm .el-form-item__label{
        width:85px!important;
    }
    .DepartDetaCon{
        margin:20px;
        background:white
    }
    .DepartDetaCon .grid-content{
        padding: 15px;
        border-bottom: 1px solid #EBEEF5;
    }
    .DepartDetaTab{
        padding:20px
    }
</style>

