<template>
    <div class="myProcess xxtzszPage">
        <el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>系通管理</el-breadcrumb-item>
                    <el-breadcrumb-item>通知管理</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </el-card>
        <el-card class="card2">
            <div slot="header" class="clearfix">
                <span>消息通知设置</span>
            </div>
            <div class="yyfk-info">
                <div class="handle-box2">
                    <el-row style="margin-bottom:10px;">
                        <label style="color:#409EFF">短信提醒消息</label>
                    </el-row>
                    <el-row>
                        <ul class="allList">
                            <li>
                                <span>短信消息提醒将通过短信平台发送给个人联系电话通知</span>
                                <el-switch v-model="switchVlave1" active-text="开" inactive-text="关">
                                </el-switch>
                            </li>
                        </ul>
                    </el-row>
                    <el-row style="margin-bottom:10px;">
                        <label style="color:#409EFF">系统提醒消息</label>
                    </el-row>
                    <el-row>
                        <ul class="allList">
                            <li>
                                <span>系统消息将以站内信的形式通知</span>
                                <el-switch v-model="switchVlave2" active-text="开" inactive-text="关">
                                </el-switch>
                            </li>
                        </ul>
                    </el-row>
                    <el-row style="margin-bottom:10px;">
                        <label for="" style="color:#409EFF">待办任务提醒</label>
                    </el-row>
                    <el-row>
                        <ul class="allList">
                            <li>
                                <span>待办任务将以站内信的形式通知</span>
                                <el-switch v-model="switchVlave3" active-text="开" inactive-text="关">
                                </el-switch>
                            </li>
                        </ul>
                    </el-row>
                </div>
            </div>
        </el-card>

    </div>
</template>
<script>
export default {
    data() {
        return {
            switchVlave1: true,
            switchVlave2: false,
            switchVlave3: true,
        }
    },
    created() {

    },
    methods: {

    },
    computed: {

    }
}

</script>
<style scoped>
.allList {
    list-style-type: none;
    border-bottom:1px solid #a0a0a0;
    margin-bottom:30px;
}
.allList>li{
    font-size: 15px;
    height: 50px;
    line-height:50px;
    padding:0 12px;
}
.allList>li>.el-switch{
    float: right;
    font-size: 15px;
    top: 14px;
}
.yyfk-info {
    margin-top: 20px;
}

.clearfix {
    height: 30px;
    line-height: 30px;
}
</style>
<style>
.xxtzszPage .el-card__header{
    padding:10px 16px;
}
</style>

