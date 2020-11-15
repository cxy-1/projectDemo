<template>
    <div class="myProcess yyfkPage" style="height:100%;">
        <el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                    <el-breadcrumb-item>我的反馈</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </el-card>
        <el-card class="card2" style="margin-top:15px;">
            <div slot="header" class="clearfix">
                <span>我的意见</span>
            </div>
            <div class="wdda-info">
                <div class="wdda-table longInput">
                    <el-row style="margin:0 20px 0 0">
                        <span class="tipWord">提醒语句：</span>
                        <el-input type="textarea" v-model="viewArea" style="width:calc(100% - 90px);"></el-input>
                    </el-row>
                    <el-row style="text-align:center;margin:14px 0 0 0;">
                        <el-button @click="resetWarn()">取消</el-button>
                        <el-button type="primary" @click="sendQuest()">提交</el-button>
                    </el-row>
                </div>
            </div>
        </el-card>
        <el-card class="card2 historyCard" style="margin-top:15px;">
            <div slot="header" class="clearfix">
                <span>反馈历史</span>
            </div>
            <div class="wdda-info">
                <div class="wdda-table">
                    <div class="questList">
                        <ul class="allList">
                            <li v-for="(item,index) in listData">
                                <p>
                                    <span class="answerTitle">意见：</span><span class="longAnswer" :title="item.wtSpan">{{item.wtSpan}}</span><span>{{item.wtTime}}</span>
                                </p>
                                <p>
                                    <span class="answerTitle">回复：</span><span class="longAnswer" :title="item.wtSpan">{{item.hfSpan}}</span><span>{{item.hfTime}}</span>
                                </p>
                            </li>
                        </ul>
                        <div class="pagination">
                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                            </el-pagination>
                        </div>
                    </div>
                </div>
            </div>
        </el-card>
        <!--<div class="yyfk-info"  style="height:calc(100% - 42px);">
            <div class="handle-box1">
                <el-row style="margin:0 20px 0 0">
                    <el-input type="textarea" v-model="viewArea"></el-input>
                </el-row>
                <el-row style="text-align:center;margin:14px 0;">
                     <el-button type="primary">提&emsp;交</el-button> 
                    <el-button type="primary" @click="sendQuest()">提交</el-button>
                </el-row>
            </div>
            <div class="handle-box2" style="height:calc(100% - 224px);overflow:auto">
                <el-row style="margin-bottom:10px;">
                    <label  style="color:#409EFF">反馈历史</label>
                </el-row>
                <div class="questList">
                    <ul class="allList">
                        <li v-for="(item,index) in listData">
                            <p>
                                <span>{{item.wtSpan}}</span><span>{{item.wtTime}}</span>
                            </p>
                            <p>
                                <span>平台回复：{{item.hfSpan}}</span><span>{{item.hfTime}}</span>
                            </p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>-->

    </div>
</template>
<script>
import {communication} from '../../../data/getAxios';
export default {
    data() {
        return {
            viewArea: '',
            listData:[],
            inittableData:[],
            cur_page: 1,//当前页
            pagesize:5//分页条数
        }
    },
    
    created() {
        this.getList();
    },
    methods: {
        // 取消
        resetWarn(){
           this.viewArea=''; 
        },
        // 分页
        handleCurrentChange(val){
            this.cur_page=val
            this.listData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
        },
        getList(){
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/message/evaluate/getAllEvaluate',
                isGet:false
            };
            communication(_obj).then((res) => {
                if(res.code == 0){
                    self.listData=[];
                    self.inittableData=[];
                    for(var i=0;i<res.data.length;i++){
                        var obj={};
                        obj.wtSpan=res.data[i].note;
                        obj.wtTime=res.data[i].noteDate;
                        obj.hfSpan=res.data[i].feedbackNote;
                        obj.hfTime=res.data[i].feedbackDate;
                        self.listData.push(obj);
                        self.inittableData.push(obj);
                    }
                    self.listData=self.inittableData.slice((self.cur_page-1)*self.pagesize,(self.cur_page)*self.pagesize)
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
        // 点击提交按钮
        sendQuest(){
            var self = this;
            if(self.viewArea!=''){
                var self = this;
                const _obj={
                    url:self.global.serverPath8 + '/message/evaluate/sendEvaluate',
                    isGet:false,
                    postData:{
                        'overview':'5',
                        'goAboard':'5',
                        'leave':'5',
                        'note':self.viewArea
                    }
                };
                communication(_obj).then((res) => {
                    if(res.code == 0){
                        this.getList();
                        self.viewArea = '';
                        self.$message.success('提交成功');
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                },function(res) {
                    self.$message.error('请求失败！')
                });
            }else{
                self.$message.warning('请先填写问题！')
            }

        }
    },
    mounted() {
        // $('.right_card').height(this.$refs.wdlcOption.offsetHeight - 80);
    }
}

</script>
<style scoped>
.tipWord{
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0,0,0,0.85);
    text-align: right;
    width:90px;
    vertical-align:top;
}
.answerTitle{
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0,0,0,0.85);
    font-weight:900;
    display:inline-block;
    width:44px;
    float:left;
}
.allList>li>p{
    height: 38px;
    line-height:38px;
    padding:0 12px;
    font-size: 14px;
}
.allList>li{
    margin-bottom:6px;
    border-bottom:1px solid #E8E8E8;
}
.allList>li>p>span:last-child{
    float: right;
    display:inline-block;
}
.longAnswer{
    overflow: hidden;    
 	text-overflow: ellipsis; 
 	white-space: nowrap;
     color:red;
     display:inline-block;
     width:calc(100% - 200px);
}
.allList>li>p>span:not(:first-child){
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0,0,0,0.65);
    display:inline-block;
}

.allList{
    list-style-type: none;
}
.yyfk-info {
    margin-top: 20px;
    height: 224px;
}

.clearfix {
    height: 22px;
    line-height: 26px;
}
</style>
<style>
    .yyfkPage .el-card__header{
        padding:10px 16px;
    }
.longInput .el-textarea__inner{
    height:70px;
}
.historyCard .el-card__body{
    padding-top:6px;
}
.handle-box1 .el-textarea__inner {
    height: 100px!important;
}
</style>

