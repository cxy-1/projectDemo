<template>
    <div class="ysp">
        <template>
            <el-row :gutter="20">
                <el-col :span="8" v-for="(item,j) in divBox" :key="j" :ref="data1">
                    <div class="dtjBox" style="margin-bottom:16px;">
                        <div class="dtjTop">
                            <template v-if="item.title=='pleaseSell'">
                                <p>
                                    <i class="iconfont iconrili"></i>&emsp;请销假</p>
                            </template>
                            <template v-if="item.title=='goAbroad'">
                                <p>
                                    <i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                            </template>
                            <template v-for="subItem in item.subs">
                                <div class="dtjForm" @click="linkTo2(item)">
                                    <el-form :model="subItem" label-width="74px" ref="subForm">
                                        <el-form-item label="申请单号：">
                                            <span>{{subItem.formInput1}}</span>
                                        </el-form-item>
                                        <el-form-item :label="item.title=='pleaseSell'?'请假事由：':'离境原因：'">
                                            <span>{{subItem.formInput2}}</span>
                                        </el-form-item>
                                        <el-form-item :label="item.title=='pleaseSell'?'请假日期：':'离境日期：'">
                                            <span>{{subItem.formInput3}}</span>
                                        </el-form-item>
                                        <el-form-item label="提交时间：">
                                            <span>{{subItem.formInput4}}</span>
                                        </el-form-item>
                                        <!--<el-form-item class="lastBtn">
                                            <div class="dtjBottom">
                                                <el-button-group>
                                                    <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
                                                        <el-button icon="iconfont iconbianji" @click="handleEdit(j)"></el-button>
                                                    </el-tooltip>
                                                    <el-tooltip class="item" effect="dark" content="提交" placement="bottom">
                                                        <el-button icon="iconfont icontijiao" @click="referBtn(j)"></el-button>
                                                    </el-tooltip>
                                                    <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                                                        <el-button icon="iconfont iconshanchu" @click="handleDel(j)"></el-button>
                                                    </el-tooltip>
                                                </el-button-group>
                                            </div>
                                        </el-form-item>-->
                                    </el-form>
                                </div>
                            </template>
                        </div>
                    </div>
                </el-col>
                <el-col :span="8"></el-col>
                <el-col :span="8"></el-col>
            </el-row>
        </template>
    </div>
</template>

<script>
export default {
    data() {
        return {
            divBox: [],
            data1:''//用于向父组件传值
        }
    },
    created() {

    },
    methods: {
        linkTo2(item){
            if(item.title=='goAbroad'){//出国境申请详情
                this.$http.get(this.global.serverPath4+'/process_engine/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
                    params: {
                        "applicationFormId": item.subs[0].formInput1,//申请单号
                    }
                }).then(function(res) {//出国境审批
                    this.$router.push({ path: '/dashboard3', query: {show1: false, show2: true, info: res.body.rows, exist: true} });//跳转到详情页面
                }, function(res) {
                    alert('请求失败！！！！！')
                });
            }else{//请销假申请详情
                this.$router.push({ path: '/LeaExamApproval', query: {show1: false, show2: true, exist: true, alarmNo:item.subs[0].formInput1 , num:1} });
            }
        },
       inquiry2(){
            var self = this;
			self.$http.get(self.global.serverPath4+'/process_engine/Personal/queryAllFormsByPending', {//传递参数
                params: {
                    "personId":localStorage.getItem("loginpersonId"),
                    "flag":1,
                }   
			}).then(function(res) {
                var obj1 = {}, obj2 = {}, addArr1 = [], addArr2 = [], addSubs2 = {}, addSubs1 = {}, titArr1 = [], titArr2 = [];
                    if (res.body.msg) {
                        if (res.body.bsGoAbroadList.length > 0) {//出国境
                            var cgj = res.body.bsGoAbroadList;
                            for (var i = 0; i < cgj.length; i++) {
                                addSubs1 = {};
                                titArr1.push(res.body.bsGoAbroadList[i].flag);
                                addSubs1.formInput1 = cgj[i].applicationFormId;
                                addSubs1.formInput2 = cgj[i].departureReason;
                                addSubs1.formInput3 = cgj[i].startTime + '至' + cgj[i].endTime;
                                addSubs1.formInput4 = cgj[i].applicationTime;
                                addArr1.push(addSubs1);
                            };
                            for (var k = 0; k < titArr1.length; k++) {
                                var arr = [];
                                obj1[k] = {};
                                obj1[k].title = titArr1[k];
                                obj1[k].subs = arr;
                                obj1[k].subs.push(addArr1[k]);
                                self.divBox.push(obj1[k])
                            };
                        }
                        if (res.body.pleSeOffFormInfos.length > 0) {//请销假
                            var qxj = res.body.pleSeOffFormInfos;
                            for (var i = 0; i < qxj.length; i++) {
                                addSubs2 = {};
                                titArr2.push(res.body.pleSeOffFormInfos[i].flag);
                                addSubs2.formInput1 = qxj[i].applyFormId;
                                addSubs2.formInput2 = qxj[i].applyType;
                                addSubs2.formInput3 = qxj[i].beginTime + '至' + qxj[i].endTime;
                                addSubs2.formInput4 = qxj[i].applicantTime;
                                addArr2.push(addSubs2);
                            };
                            for (var k = 0; k < titArr2.length; k++) {
                                var arr = [];
                                obj2[k] = {};
                                obj2[k].title = titArr2[k];
                                obj2[k].subs = arr;
                                obj2[k].subs.push(addArr2[k]);
                                self.divBox.push(obj2[k])
                            };
                        }
                        localStorage.setItem('yspNum',self.divBox.length);
                        // self.$emit('yspNum',self.divBox.length);//子组件传值给父组件
                    } else {
                        self.$message.warning('暂无已审批数据！');
                        localStorage.setItem('yspNum',0)
                    }
			}, function(res) {
				self.$message.error('请求失败')
			});
        },
    },
    mounted(){//页面加载完执行
        this.data1=this.divBox.length;
        this.inquiry2()
    }
}

</script>
<style scoped>
.dtjBox {
    border: 1px solid #E8E8E8;
    cursor:pointer;
}

.dtjTop {
    padding: 6px 0 0 0;
}
.dtjTop .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item{
    padding-left: 15px;
}
.dtjTop .lastBtn{
    padding:0;
    margin-bottom:0!important;
}

.dtjBox p {
    opacity: 0.85;
    font-family: PingFangSC-Regular;
    font-size: 16px;
    color: #000000;
    margin-bottom: 10px;
    padding-left:15px;
}

.dtjBox p i {
    font-size: 26px;
    color: #D42425;
    position: relative;
    top: 2px;
}

.dtjForm .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
    margin-bottom: 2px
}

.bmxy-info {
    font-family: PingFangSC-Regular;
    font-size: 16px;
    color: rgba(0, 0, 0, 1);
    text-align: justify;
    margin: 25px;
}

.last-info {
    text-indent: 46px;
}

.bmxy-tit {
    text-align: center;
    font-family: PingFangSC-Medium;
    font-size: 18px;
    font-weight: 900;
    color: rgba(0, 0, 0, 0.85);
    text-align: center;
}
</style>
<style>
.lastBtn .el-form-item__content{
    margin-left: 0!important;
}
.dtjBottom .iconfont {
    font-size: 22px!important;
}

.dtjTop .el-form-item__label {
    padding: 0!important;
}

.dtjBottom .el-button-group {
    width: 100%;
}

.dtjBottom .el-button-group>.el-button {
    border-bottom: none;
    display: inline-block;
    width: 33.3%;
}

.dtjBottom .el-button-group>.el-button:first-child {
    border-left: none;
}

.dtjBottom .el-button-group>.el-button:last-child {
    border-right: none;
}
</style>

