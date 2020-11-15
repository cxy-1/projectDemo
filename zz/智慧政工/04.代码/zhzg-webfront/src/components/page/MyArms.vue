<template>
    <div class="cjgl">
        <div class="jyxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                                        <el-breadcrumb-item>我的战友</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2 myCard">
                                <div class="jyxx-info">
                                <span class="noData" v-show="noData">暂无数据</span>
                                    <div class="jyxx-table">
                                        <el-timeline>
                                            <el-timeline-item v-for="(activity, index) in activities"
                                                :key="index" color="#1890FF">
                                                {{activity.beginTime + '&emsp;' + activity.endTime + '&emsp;'+ activity.workUnit + '&emsp;'}}
                                                <el-button class="undownBtn" type="text" @click="activity.showTab=!activity.showTab">{{!activity.showTab?'展开':'收起'}}<i :class="!activity.showTab?'el-icon-arrow-down el-icon--right':'el-icon-arrow-up el-icon--right'"></i></el-button>
                                                <transition name="el-zoom-in-top">
                                                    <el-table border :data="activity.tableData" style="width: 100%" v-show="activity.showTab">
                                                        <el-table-column prop="friendName" label="姓名" width="100">
                                                        </el-table-column>
                                                        <el-table-column prop="nowWorkUnit" label="现单位名称">
                                                        </el-table-column>
                                                        <el-table-column prop="relPhone" label="联系方式">
                                                        </el-table-column>
                                                    </el-table>
                                                </transition>
                                            </el-timeline-item>
                                        </el-timeline>
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
export default {
    data() {
        return {
            multipleSelection: [],//多选数组当前选中要删除的项
            idx: -1,
            noData:false,
            activities: [
            //     {
            //     content: '单位名称',//标题
            //     timestamp: '2018.04.12-2018.04.03',//时间
            //     showTab:true,
            //     tableData:[//表格内容
            //         {
            //             amryFriendName:'赵',
            //             workUnit:'南京市公安局',
            //             relPhone:'13748576857'
            //         },
            //         {
            //             amryFriendName:'钱',
            //             workUnit:'南京市公安局',
            //             relPhone:'13748576857'
            //         },
            //         {
            //             amryFriendName:'孙',
            //             workUnit:'南京市公安局',
            //             relPhone:'13748576857'
            //         }
            //     ]
            // },{
            //     content: '单位名称2',//时间标题
            //     timestamp: '2018.04.03-2018.04.03',
            //     showTab:true,
            //     tableData:[
            //         {
            //             amryFriendName:'王',
            //             workUnit:'南京市公安局',
            //             relPhone:'13748576857'
            //         }
            //     ]
            // }
            ]
        }
    },
    created() {
        this.getTabData();
    },
    methods: {
        //获取我的战友数据
        getTabData() {
            var self = this;
            const _obj={
                url:self.global.serverPath8 + '/personnel/armyFriend/getAllMyArmyFriends?personId2=a',
                isGet:true
            };
            communication(_obj).then((res) => {
                console.log(res);
				var trees = res.data;
                if(trees.length>0){
                    for(var i=0;i<trees.length;i++){
                        var obg1={},tab=[];
                        obg1.beginTime=trees[i].beginTime;
                        obg1.showTab = trees[i].showTab;
                        obg1.endTime=trees[i].endTime;
                        obg1.workUnit=trees[i].workUnit;
                        if(trees.length>0){
                            obg1.tableData=trees[i].friendVoList;//table数据不为空
                        }else{
                            obg1.tableData=[];//table数据为空
                        }
                        self.activities.push(obg1);
                    }
                    console.log(self.activities);
                }else{
                    self.noData=true;
                }
            },function(res) {
                self.$message.error('请求失败！')
            });
        },
    },
    mounted(){
        $('.myCard').get(0).style.minHeight=$('.content').height()-126+'px';
    }
}

</script>
<style scoped>
.undownBtn{
    position:relative;
    right:50px;
    float:right;
    bottom:5px;
}
.card2 {
    margin: 0 20px;
}
.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.noData{
    position:absolute;
    top:49%;
    left:49%;
    color:#bbb;
}
.jyxx-table .el-timeline-item__content{
    font-family: PingFangSC-Medium;
    font-size: 16px;
    position:relative;
    padding-top:10px;
    min-height:32px;
    color: #262626;
    background:#eee
}
.jyxx-table .el-table th>.cell {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.jyxx-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
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

