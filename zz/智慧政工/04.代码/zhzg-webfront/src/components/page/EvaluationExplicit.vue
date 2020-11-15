<template>
    <div class="cjgl Evaluation">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20 padBot">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>评价反馈</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="EvaluationExplicit">
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='EvaluationExplicit' :model="EvaluationExplicit" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="6">
                                                    <el-form-item label="姓名：">
                                                        <el-input v-model="EvaluationExplicit.userName"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="12">
                                                    <el-form-item label="警号：">
                                                        <el-input v-model="EvaluationExplicit.userNo"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="12">
                                                    <el-form-item label="评分：">
                                                        <el-input v-model="EvaluationExplicit.mark"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <div class="EvaluationCon">
                                                <el-col :span="24">
                                                    <el-row :gutter="20">
                                                        <el-col :span="3"><span class="rateSpan">首页</span></el-col>
                                                        <el-col :span="8">
                                                            <el-rate v-model="EvaluationExplicit.scoreValue1" show-text></el-rate>
                                                        </el-col>
                                                    </el-row>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-row :gutter="20">
                                                        <el-col :span="3"><span class="rateSpan">请销假管理</span></el-col>
                                                        <el-col :span="8">
                                                            <el-rate v-model="EvaluationExplicit.scoreValue2" show-text></el-rate>
                                                        </el-col>
                                                    </el-row>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-row :gutter="20">
                                                        <el-col :span="3"><span class="rateSpan">出国（境）管理</span></el-col>
                                                        <el-col :span="8">
                                                            <el-rate v-model="EvaluationExplicit.scoreValue3" show-text></el-rate>
                                                        </el-col>
                                                    </el-row>
                                                </el-col>
                                            </div>
                                            <div class="EvaluationText">
                                                <el-row :gutter="20" label-width="90px">
                                                    <el-col :span="24">
                                                        <el-form-item label="意见内容：">
                                                            <el-input type="textarea" v-model="EvaluationExplicit.ideaCon"></el-input>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="24">
                                                        <el-form-item label="处理意见：">
                                                            <el-input type="textarea" v-model="EvaluationExplicit.note"></el-input>
                                                        </el-form-item>
                                                    </el-col>
                                                </el-row>
                                            </div>
                                        </el-form>
                                        <el-row class="EvaluationExplicitBtn">
                                            <el-col :span="12">
                                                <el-button type="primary" @click="cancel">关闭</el-button>
                                                <!-- <el-button type="primary">反馈</el-button> -->
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
    name: 'dashboard4',
    data() {
        return {
            EvaluationExplicit:{
                userName:'',
                userNo:'',
                mark:'',
                ideaCon:'',
                note:'',
                scoreValue1:null,
                scoreValue2:null,
                scoreValue3:null
            }
        }
    },
    created() {
        this.init()
    },
    methods: {
        cancel(){
            this.$router.push({ path: '/Evaluation'})
        },
    	init(){
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/message/evaluate/getEvaluateInfo',
                postData:{
                    'personID':self.$route.query.personID,
                    'evaluateID':self.$route.query.evaluateID
                }
            };
            communication(_obj).then((res) => {
                console.log(res);
                if(res.code == 0){
                    self.EvaluationExplicit.userName = res.data.userName;
                    self.EvaluationExplicit.userNo = res.data.policeNo,
                    self.EvaluationExplicit.mark = res.data.score,
                    self.EvaluationExplicit.ideaCon = res.data.note,
                    self.EvaluationExplicit.note = res.data.feedbackNote,
                    self.EvaluationExplicit.scoreValue1 = res.data.scoreInfoDo.leaveScore,
                    self.EvaluationExplicit.scoreValue2 = res.data.scoreInfoDo.overviewScore,
                    self.EvaluationExplicit.scoreValue3 = res.data.scoreInfoDo.goAboardScore
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        }
    },
    computed: {
        
    }
}

</script>
<style scoped>

.Dashboard3 .pingfen .el-dialog__body .el-row .rateSpan{
    display:inline-block;
    width:100px;
    text-align:right;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0,0,0,0.85);
} 
.EvaluationCon>.el-col{
    height: 51px;
}
.EvaluationCon .el-col-3{
    text-align: right;
    font-size: 14px;
    color: #606266;
}
.EvaluationCon .el-form-item__label{
    width:90px!important
}
</style>
<style>
.padBot .el-card__body{
    padding:18px 20px 10px 20px!important;
}
.EvaluationCon .el-form-item{
    width: 100%
}
.EvaluationText .el-form-item{
    width: 45%
}
.EvaluationText .el-form-item__label{
    width:90px!important;
    height:50px;
    line-height: 50px
}
.EvaluationText .el-form-item__content{
    width:calc(100% - 90px)
}
.EvaluationExplicitBtn .el-col{
    text-align: center
}
.Evaluation .el-card__body{
    padding: 30px 20px
}
</style>

