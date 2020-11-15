<template>
    <div class="dtj">
        <template>
            <el-row :gutter="20">
                <el-col :span="8" v-for="(item,j) in divBox" :key="j" :ref="data1">
                    <div class="dtjBox" style="margin-bottom:16px;">
                        <div class="dtjTop">
                            <template v-if="item.title=='pleaseSell'">
                                <p><i class="iconfont iconrili"></i>&emsp;请销假</p>
                            </template>
                            <template v-if="item.title=='goAbroad'">
                                <p><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                            </template>
                            <template v-for="subItem in item.subs">
                                <div class="dtjForm" @click="linkTo(item)">
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
        <!-- 提交提示框 -->
        <el-dialog title="提示" :visible.sync="referTo" width="300px" center>
            <div class="del-dialog-cnt" style="text-align:center;"><i class="el-icon-success" style="color:#67c23a;font-size:24px;"></i>&nbsp; 您已成功提交！</div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="referTo=false">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            referTo:false,//提交提示框
            divBox: [],
            data1: ''//用于向父组件传值
        }
    },
    created() {
    },
    methods: {
        linkTo(item){
            console.log(item.title);
            if(item.title=='goAbroad'){//出国境申请详情
                this.$http.get(this.global.serverPath4+'/process_engine/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
                    params: {
                        "applicationFormId": item.subs[0].formInput1,//申请单号
                    }
                }).then(function(res) {
                    console.log(res.body.rows);
                    this.$router.push({ path: '/dashboard2', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
                }, function(res) {
                    console.log('请求失败！！！！！')
                });
            }else{//请销假申请详情
                this.$router.push({ path: '/LeaveManage', query: { alarmNo:item.subs[0].formInput1} });
            }
        },
        //编辑按钮
        handleEdit(index) {
            var self = this;
            if(self.divBox[index].title=='goAbroad'){//出国境
                self.$http.get(self.global.serverPath+'/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {
                    params: {
                        "applicationFormId": self.divBox[index].subs[0].formInput1,//申请单号
                    }
                }).then(function(res) {
                    console.log(res.body.rows);
                    self.$router.push({ path: '/dashboard3', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
                }, function(res) {
                    console.log('请求失败！！！！！')
                });
            }else{//请销假
                // self.$http.get(self.global.serverPath2 +'/zhzg/PleaseSellOff/approvalList', {
                self.$router.push({path:'/LeaExamApproval',query:{alarmNo:self.divBox[index].subs[0].formInput1}})
            }
        },
        // 提交按钮
        referBtn(index){
            this.referTo = true;
        },
        //确定提交
        // handleRefer() {
        //     var self = this;
        //     self.referTo = false;
        //     console.log(self.divBox[index].title)
        //     if(self.divBox[index].title=='goAbroad'){//出国境
        //         self.$http.get(self.global.serverPath+'/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId',{
        //             params: {
        //                 "applicationFormId": self.divBox[index].subs[0].formInput1,//申请单号
        //             }
        //         }).then(function(res) {
        //             console.log(res.body.rows);
        //             self.$router.push({ path: '/dashboard3', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
        //         }, function(res) {
        //             console.log('请求失败！！！！！')
        //         });
        //     }else{//请销假
        //         self.$http.post('http://192.168.8.105:8083/zhzg/PleaseSellOff/Submit',{
		// 				"applyFormId": self.divBox[index].subs[0].formInput1
		// 			}).then(function(res) {
		// 				console.log(res.body);
		// 				// if (res.body.result == 1) {
		// 				// 	self.$message.success('提交成功')
		// 				// 	self.$refs[leaveForm1].resetFields();
		// 				// } else {
		// 				// 	self.$message.warning("提交失败");
		// 				// }
		// 			}, function(res) {
		// 				self.$message.warning("请求失败");
		// 			});
        //     }
        // },
        //删除按钮
        handleDel(index){
            var self = this;
            console.log(self.divBox[index])
            self.divBox.splice(index,1)
        },
        //获取数据
        getCardData() {
            var self = this;
            console.log(localStorage.getItem('loginpersonId'))
            // http://192.168.43.48:8084/process_engine/Personal/queryAllFormsById?personId=0000000030
            self.$http.get(self.global.serverPath4 + '/process_engine/Personal/queryAllFormsById',
                {
                    params: {//传递参数
                        "personId": localStorage.getItem('loginpersonId')
                        // "personId":'019771',
                    }
                }).then(function(res) {
                    // console.log(res.body.pleSeOffFormInfos);//请销假
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
                        localStorage.setItem('dtjNum',self.divBox.length)
                        // self.$emit('dtjNum',self.divBox.length);//子组件传值给父组件
                    } else {
                        self.$message.warning('暂无待提交数据！');
                    }
                }, function(res) {
                    console.log('待提交数据请求失败！！！！！')
                });
        }
    },
    mounted() {//页面加载完执行
        this.data1 = this.divBox.length;
        this.getCardData();
    }
}

</script>
<style scoped>
.dtjForm .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
    margin-bottom: 2px
}
.dtjForm span{
    color:#606266;
}
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

