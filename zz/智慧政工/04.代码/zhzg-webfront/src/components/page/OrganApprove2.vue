<template>
    <div class="cjgl">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>机构管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>机构变更审批</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <div class="titIcon">
                                    <i class="faSelf sq-icon"></i>
                                    <div class="btn-group" v-show="now1">
                                        <el-button @click="bhVisible = true">驳回</el-button>
                                        <el-button @click="backBtn">取消</el-button>
                                        <el-button type="primary" @click="agreeVisible = true">同意</el-button>
                                        <!-- <el-button type="primary" @click.native="sqxxSubmit">提交</el-button> -->
                                    </div>
                                    <div class="btn-group" v-show="now2">
                                        <el-button type="primary" @click="backBtn">关闭</el-button>
                                    </div>
                                </div>
                                <div class="steps">
                                    <el-steps :active="stepActive" align-center>
                                        <el-step title="机构变更申请" icon="el-icon-location" :description="step1"></el-step>
                                        <el-step title="人事处审核" icon="el-icon-location" :description="step2"></el-step>
                                        <el-step title="政治部审批" icon="el-icon-location" :description="step3"></el-step>
                                        <el-step title="完成" icon="el-icon-location" :description="step4"></el-step>
                                    </el-steps>
                                </div>
                            </el-card>
                            <el-card class="card2" style="margin-bottom:15px">
                                <div slot="header" class="clearfix">
                                    <span>变更信息一</span>
                                </div>
                                <div class="bgxx-info">
                                    <div class="bgxx-box">
                                        <el-form :inline="false" label-position="right" label-width="140px" ref='bgxxForm' :model="bgxxForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="10">
                                                    <el-form-item label="变更类型：" prop="changeType">
                                                        <el-input v-model="bgxxForm.changeType" readonly=""></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="10">
                                                    <el-form-item label="申请报告：" prop="attOption">
                                                        <div class="el-upload__text downLoad" @click="downWorld()">
                                                            <span class="icon-world"></span>
                                                            <span ref="tipModel">关于科技信息化处内设机构调整的请示.doc</span>
                                                        </div>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                </div>
                                <!--申请信息表格  -->
                                <div class="bgxx-table1">
                                    <el-table :data="tableData" style="width: 100%">
                                        <el-table-column prop="oldName" label="原单位名称">
                                        </el-table-column>
                                        <el-table-column prop="unitLevel" label="单位级别" width="140">
                                        </el-table-column>
                                        <el-table-column prop="nameType" label="单位名称类型" width="160">
                                        </el-table-column>
                                        <el-table-column prop="highName" label="上级单位名称">
                                        </el-table-column>
                                        <el-table-column prop="unitFunction" label="单位职能" width="120">
                                        </el-table-column>
                                        <el-table-column prop="zLevel" label="正职领导职数" width="110">
                                        </el-table-column>
                                        <el-table-column prop="fLevel" label="副职领导职数" width="110">
                                        </el-table-column>
                                    </el-table>
                                </div>
                                <!--单位表格信息  -->
                                <div class="bgxx-table">
                                    <el-table size="mini" :data="master_user.data" style="width: 100%" highlight-current-row>
                                        <el-table-column v-for="(v,j) in master_user.columns" :key="j" :prop="v.field" :label="v.title" :width="v.width">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.isSet">
                                                    <span v-if="v.select">
                                                        <el-select v-model="master_user.sel[v.field]">
                                                            <el-option v-for="item in v.options" :key="item.value" :label="item.label" :value="item.value">
                                                            </el-option>
                                                        </el-select>
                                                    </span>
                                                    <span v-else>
                                                        <el-input size="mini" v-model="master_user.sel[v.field]"></el-input>
                                                    </span>
                                                    <span v-else>{{scope.row[v.field]}}</span>
                                                </span>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!-- 驳回提示框 -->
        <el-dialog title="驳回" :visible.sync="bhVisible" width="580px" class="bhlog">
            <el-form ref="bhForm" :model="bhForm" label-width="100px">
                <el-form-item label="审批意见:">
                    <div class="test_box" ref="view" contenteditable="true" style="-webkit-user-select:text"><br /></div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="bhVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveBh">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 同意提示框 -->
        <el-dialog title="操作提示" :visible.sync="agreeVisible" width="580px" class="agreelog">
            <div class="del-dialog-cnt">
                <p>
                    <i class="el-icon-success"></i>
                </p>
                <br/>
                <p>您已成功审批</p>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="agreeTip">确 定</el-button>
            </span>
        </el-dialog>
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    data() {
        return {
            stepActive: 2,
            bhVisible: false,//驳回弹窗
            agreeVisible: false,//同意弹窗
            now1: true,//按钮切换
            now2: false,
            step1: localStorage.getItem('step1'),//步骤一描述
            step2: '',
            step3: '',
            step4: '',
            nowTime: '',//当前时间
            routeForm: [],//列表页传来的数据
            bhForm: {
                view: ''
            },
            master_user: {
                sel: JSON.parse(JSON.stringify({
                    id: 0,
                    "changeName": "",
                    "unitLevel": "",
                    "nameType": "",
                    "highName": "",
                    "unitFunction": "",
                    "zLevel": "",
                    "fLevel": "", "isSet": true, "_temporary": true
                })),//选中行   
                columns: [
                    { field: "changeName", title: "修改单位名称", select: false },
                    {
                        field: "unitLevel", title: "单位级别", width: 140, select: true, options: [{
                            value: '局级',
                            label: '局级'
                        }, {
                            value: '省厅局级',
                            label: '省厅局级'
                        },
                        {
                            value: '县处级',
                            label: '县处级'
                        },
                        {
                            value: '副县处级',
                            label: '副县处级'
                        },
                        {
                            value: '科级',
                            label: '科级'
                        },
                        {
                            value: '副科级',
                            label: '副科级'
                        },
                        {
                            value: '股级',
                            label: '股级'
                        },
                        {
                            value: '其他',
                            label: '其他'
                        }]
                    },
                    {
                        field: "nameType", title: "单位名称类型", width: 160, select: true,
                        options: [{
                            value: '科',
                            label: '科'
                        }, {
                            value: '支队',
                            label: '支队'
                        }]
                    },
                    {
                        field: "highName", title: "上级单位名称", select: true,
                        options: [{
                            value: '南京市公安局机构树',
                            label: '南京市公安局机构树'
                        }, {
                            value: '科级信息化处',
                            label: '科级信息化处'
                        }]
                    },
                    {field: "unitFunction", title: "单位职能", width: 120, select: false},
                    {field: "zLevel", title: "正职领导级数", width: 110, select: false},
                    {field: "fLevel", title: "副职领导级数", width: 110, select: false}
                ],
                data: [{
                    id: 0,
                    "changeName": "",
                    "unitLevel": "",
                    "nameType": "",
                    "highName": "",
                    "unitFunction": "",
                    "zLevel": "",
                    "fLevel": "",
                    "isSet": true, "_temporary": true
                }],
            },
            bgxxForm: {
                changeType: ''
            },
            idx: -1,
            tableData: []
        }
    },
    created() {
    },
    methods: {
        // 下载文档
        downWorld() {
            var $eleForm = $("<form method='get'></form>");
            $eleForm.attr("action", "http://192.168.8.105:8088/FileUpload/出国境.zip");
            $(document.body).append($eleForm);
            //提交表单，实现下载
            $eleForm.submit();
        },
        // 获取当前时间函数
        timeFormate(timeStamp) {
            let year = new Date(timeStamp).getFullYear();
            let month = new Date(timeStamp).getMonth() + 1 < 10 ? "0" + (new Date(timeStamp).getMonth() + 1) : new Date(timeStamp).getMonth() + 1;
            let date = new Date(timeStamp).getDate() < 10 ? "0" + new Date(timeStamp).getDate() : new Date(timeStamp).getDate();
            let hh = new Date(timeStamp).getHours() < 10 ? "0" + new Date(timeStamp).getHours() : new Date(timeStamp).getHours();
            let mm = new Date(timeStamp).getMinutes() < 10 ? "0" + new Date(timeStamp).getMinutes() : new Date(timeStamp).getMinutes();
            this.nowTime = year + "-" + month + "-" + date + " " + hh + ":" + mm;
        },
        //取消
        backBtn() {
            this.$router.push('/organapprove');
        },
        // 审批同意
        agreeTip() {
            var self = this;
            self.$http.get(self.global.serverPath + '/zhzg/goAbroad/checkGoAbroadApplicationInfo', {
                params: {
                    "opinions": '',//申请单号
                }
            }).then(function(res) {
                self.agreeVisible = false;
                self.$message.success('审批成功');
                self.timeFormate(new Date());
                self.step2 = localStorage.getItem('jc_name') + ' ' + self.nowTime;
                localStorage.setItem('step2', self.step2)
                if (localStorage.getItem('jc_name') == '政工') {
                    self.step3 = localStorage.getItem('jc_name') + ' ' + self.nowTime;
                    localStorage.setItem('step3', self.step3)
                } else if (localStorage.getItem('jc_name') == '党委') {
                    self.step4 = localStorage.getItem('jc_name') + ' ' + self.nowTime;
                    localStorage.setItem('step4', self.step4)
                }
                console.log(self.step2)
                self.$router.push('/dashboard4');
            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },
        // 确定驳回
        saveBh() {
            var self = this;
            self.$http.get(self.global.serverPath + '/zhzg/goAbroad/checkGoAbroadApplicationInfo', {
                params: {
                    "opinions": self.$refs.view.innerText,//申请单号
                }
            }).then(function(res) {
                console.log(res.body.rows);
                self.bhVisible = false;//关闭弹窗
                this.$router.push('/dashboard4');

            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },
    mounted() {

    },
    computed: {

    }
}

</script>
<style scoped>
.downLoad {
    color: #20a0ff;
    cursor: pointer;
}

.icon-world {
    display: inline-block;
    width: 14px;
    height: 16px;
    position: relative;
    top: 3px;
    background: url('../../../static/img/file-word.png') no-repeat center;
    background-size: 100% 100%;
}

.del-dialog-cnt p {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    text-align: center;
}

.del-dialog-cnt i {
    font-family: PingFangSC-Regular;
    font-size: 32px;
    color: green;
    opacity: 0.7;
    text-align: center;
}

.test_box {
    width: 400px;
    min-height: 120px;
    max-height: 300px;
    border: 1px solid #DCDFE6;
}

.icon-world {
    display: inline-block;
    width: 14px;
    height: 16px;
    position: relative;
    top: 3px;
    background: url('../../../static/img/file-word.png') no-repeat center;
    background-size: 100% 100%;
}

.sq-icon {
    background: url('../../../static/img/sq.png') no-repeat center;
    background-size: 100% 100%;
}

.card2 {
    margin: 0 20px;
}

.steps {
    padding: 3% 3% 0 3%;
}

.el-row {
    margin-bottom: 20px;
}

.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.bgxx-table1 .el-table th>.cell {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.bgxx-table1 .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.redtip span {
    color: #FF0000!important;
    font-size: 14px;
}

.agreelog .el-dialog__header,
.bhlog .el-dialog__header {
    border-bottom: 1px solid #E8E8E8;
}

.agreelog .el-dialog__footer,
.bhlog .el-dialog__footer {
    border-top: 1px solid #E8E8E8;
}

.agreelog .el-dialog__body {
    padding: 50px 20px;
}

.bgxx-table .el-table th>.cell {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.bgxx-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.bgxx-info .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.bgxx-info .el-form-item__label {
    padding: 0;
}

.bgxx-info .el-input__inner {
    border: none;
    padding: 0;
}

.bgxx-box .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
    line-height: 32px;
}

.bgxx-box .el-form-item__label {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    letter-spacing: 0;
}

.bgxx-box .el-select {
    width: 76%;
}

.bgxx-box .el-input__inner {
    width: 98%;
}

.timeInput .el-input__inner {
    width: 100%;
}

.bgxx-box .el-select .el-input__inner {
    width: 100%;
}
.bgxx-box .el-upload--text {
    width: 76%;
    height: 28px;
    border: none;
    text-align: left;
}

.bgxx-box .el-upload-dragger {
    border: none;
    height: 100%;
    text-align: left;
    padding-left: 3px;
}

.bgxx-box .el-upload-dragger {
    width: 100%;
}

.bgxx-box .el-upload-dragger .el-upload__text {
    text-align: left;
}

.steps .el-step__title {
    font-size: 14px!important;
}

.steps .el-step__icon-inner[class*=el-icon]:not(.is-status) {
    font-size: 17px!important;
}

.steps .el-step__title.is-process {
    font-weight: normal;
    color: rgba(0, 0, 0, 0.65);
}

.steps .el-step__head.is-process {
    color: #C0C4CC;
}

.steps .el-step__title.is-wait {
    color: rgba(0, 0, 0, 0.65);
}

.steps .el-step__title.is-finish {
    color: rgba(0, 0, 0, 0.85);
    font-family: PingFangSC-Medium;
    font-size: 14px;
    font-weight: 600;
}
</style>

