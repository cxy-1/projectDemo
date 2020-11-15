<template>
    <div class="cjgl" style="height:100%" ref="bottomRow">
        <div class="jgwh-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row style="margin-bottom:0">
                        <el-card shadow="" class="mgb20">
                            <div class="crumbs">
                                <el-breadcrumb separator="/">
                                    <el-breadcrumb-item>首页</el-breadcrumb-item>
                                    <el-breadcrumb-item>机构管理</el-breadcrumb-item>
                                    <el-breadcrumb-item>添加部门</el-breadcrumb-item>
                                </el-breadcrumb>
                            </div>
                            <!--<div class="titIcon">
                                <span style="visibility:hidden;">添加部门</span>
                                <div class="btn-group">
                                    <el-button>添加</el-button>
                                    <el-button>取消</el-button>
                                    <el-button type="primary">保存</el-button>
                                     <el-button type="primary" @click.native="sqxxSubmit">提交</el-button> 
                                </div>
                            </div>-->
                        </el-card>
                        <el-card class="card1 mgb20">
                            <div slot="header" class="clearfix">
                                <span>申请信息</span>
                            </div>
                            <div class="jbxx-info">
                                <div class="handle-jbxx">
                                    <el-form :inline="false" :rules="jbxxRules" :label-position="'top'" label-width="140px" ref='jbxxForm' :model="jbxxForm" class="demo-form-inline">
                                        <el-row :gutter="6">
                                            <el-col :span="8">
                                                <el-form-item label="单位名称" prop="unitName">
                                                    <el-select v-model="jbxxForm.unitName" readonly="">
                                                        <el-option key="1" label="跟团" value="1"></el-option>
                                                        <el-option key="2" label="自由行" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="单位隶属关系" prop="unitRelate">
                                                    <el-input v-model="jbxxForm.unitRelate"></el-input>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="单位级别" prop="unitLevel">
                                                    <el-select v-model="jbxxForm.unitLevel" readonly="" @change="changeModel()">
                                                        <el-option key="1" label="旅游" value="1"></el-option>
                                                        <el-option key="2" label="探亲" value="2"></el-option>
                                                        <el-option key="3" label="看病" value="3"></el-option>
                                                        <el-option key="4" label="子女入学毕业" value="4"></el-option>
                                                        <el-option key="5" label="参会" value="5"></el-option>
                                                        <el-option key="6" label="其他" value="6"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                        <el-row :gutter="6">
                                            <el-col :span="8">
                                                <el-form-item label="单位性质类别" prop="unitType">
                                                    <el-select v-model="jbxxForm.unitType" readonly="" @change="txTable()">
                                                        <el-option key="1" label="是" value="1"></el-option>
                                                        <el-option key="2" label="否" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="单位所在政区" prop="unitArea">
                                                    <el-select v-model="jbxxForm.unitArea" readonly="">
                                                        <el-option key="1" label="是" value="1"></el-option>
                                                        <el-option key="2" label="否" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="所属部门" prop="belongUnit">
                                                    <el-input v-model="jbxxForm.belongUnit"></el-input>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                        <el-row :gutter="6">
                                            <el-col :span="8">
                                                <el-form-item label="单位名称类别" prop="nameType">
                                                    <el-select v-model="jbxxForm.nameType" readonly="" @change="txTable()">
                                                        <el-option key="1" label="是" value="1"></el-option>
                                                        <el-option key="2" label="否" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="单位职能" prop="unitFunction">
                                                    <el-select v-model="jbxxForm.unitFunction" readonly="">
                                                        <el-option key="1" label="是" value="1"></el-option>
                                                        <el-option key="2" label="否" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="上级单位名称" prop="heighUnit">
                                                    <el-input v-model="jbxxForm.heighUnit"></el-input>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                        <el-row :gutter="6">
                                            <el-col :span="8">
                                                <el-form-item label="隶属单位名称" prop="commandName">
                                                    <el-select v-model="jbxxForm.commandName" readonly="" @change="txTable()">
                                                        <el-option key="1" label="是" value="1"></el-option>
                                                        <el-option key="2" label="否" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="8">
                                                <el-form-item label="单位所在层级" prop="unitRank">
                                                    <el-select v-model="jbxxForm.unitRank" readonly="">
                                                        <el-option key="1" label="是" value="1"></el-option>
                                                        <el-option key="2" label="否" value="2"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </el-form>
                                </div>
                            </div>
                        </el-card>
                        <el-card class="card1 mgb20">
                            <div slot="header" class="clearfix">
                                <span>职数信息</span>
                            </div>
                            <div class="jbxx-info">
                                <div class="zsxx-info">
                                    <el-form :inline="true" label-position="right" label-width="120px" :model="zsxxInfo" class="demo-form-inline">
                                        <el-row :gutter="6">
                                            <el-col :span="12" align="center">
                                                <span class="blueSpan">应配</span>
                                            </el-col>
                                            <el-col :span="12" align="center">
                                                <span class="blueSpan">实际</span>
                                            </el-col>
                                        </el-row>
                                        <el-row :gutter="6">
                                            <el-col :span="12" align="center">
                                                <el-form-item label="正职领导职数：" prop="zlead1">
                                                    <el-input v-model="zsxxInfo.zlead1"></el-input>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="12" align="center">
                                                <el-form-item label="副职领导职数：" prop="flead1">
                                                    <el-input v-model="zsxxInfo.flead1"></el-input>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                        <el-row :gutter="6">
                                            <el-col :span="12" align="center">
                                                <el-form-item label="正职领导职数：" prop="zlead2">
                                                    <el-input v-model="zsxxInfo.zlead2"></el-input>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="12" align="center">
                                                <el-form-item label="副职领导职数：" prop="flead2">
                                                    <el-input v-model="zsxxInfo.flead2"></el-input>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                    </el-form>
                                </div>
                            </div>
                        </el-card>
                        <el-card class="card1 mgb20">
                            <div slot="header" class="clearfix">
                                <span>职数信息</span>
                            </div>
                            <div class="jbxx-info">
                                <el-form :inline="false" label-position="right" label-width="100px" ref='zwxxUp' :model="zwxxUp" class="demo-form-inline">
                                    <el-row :gutter="6">
                                        <el-col :span="12">
                                            <el-form-item label="附件:" prop="upOption">
                                                <el-upload class="upload-demo" :on-success="handleSuccess" v-model="zwxxUp.upOption" drag action="http://192.168.8.105:9096/zhzg/upload/uploadFile" multiple>
                                                    <el-button size="mini">
                                                        <i class="el-icon-upload2"></i> 上传</el-button>
                                                    <div class="el-upload__text downloadt" style="display:inline-block;">
                                                        <span class="icon-world"></span>
                                                        <em>附件信息表</em>
                                                    </div>
                                                </el-upload>
                                                <div ref="redTips" class="el-upload__tip" style="margin-top:0;color:rgba(0,0,0,0.65);">支持word/excel/jpg/png等</div>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form>
                            </div>
                        </el-card>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>
<script>
export default {
    data() {
        return {
            multipleSelection: [],//多选数组当前选中要删除的项
            zwxxUp:{
                upOption:''
            },
            zsxxInfo: {
                zlead1: '',
                flead1: '',
                zlead2: '',
                flead2: ''
            },
            jbxxForm: {
                unitName: '',//单位名称
                unitRelate: '',//单位隶属关系
                unitLevel: '',//单位级别
                unitType: '',//单位性质类别
                unitArea: '',//单位所在政区
                belongUnit: '',//所属部门
                nameType: '',//单位名称类别
                unitFunction: '',//单位职能
                heighUnit: '',//上级单位名称
                commandName: '',//隶属单位名称
                unitRank: ''//单位所在层级
            },
            jbxxRules: {
                unitName: [
                    { required: true, message: '请输入', trigger: 'blur' }
                ],
                unitLevel: [
                    { required: true, message: '请选择', trigger: 'blur' }
                ],
                belongUnit: [
                    { required: true, message: '请选择', trigger: 'blur' }
                ],
                commandName: [
                    { required: true, message: '请输入', trigger: 'blur' }
                ],
                unitRank: [
                    { required: true, message: '请选择', trigger: 'blur' }
                ]
            },
            idx: -1
        }
    },
    created() {//在模板渲染成html前调用

    },
    methods: {
        // 文件上传成功钩子
        handleSuccess(response, file, fileList) {
            // this.upFileName.push(response.rows);
            console.log(this.upFileName)
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },

        //详情跳转
        handleDetail(index, row) {
            var self = this;
            self.idx = index;
            self.$http.get(self.global.serverPath + '/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
                params: {
                    "applicationFormId": row.applicationFormId,//申请单号
                }
            }).then(function(res) {
                console.log(res.body.rows);
                this.$router.push({ path: '/dashboard3', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
            }, function(res) {
                alert('请求失败！！！！！')
            });
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },
    mounted() {//在模板渲染成html后调用
        // this.$refs.treeCard.style.height = this.$refs.bottomRow.offsetHeight - 120 + 'px';//滚动条高度
        // console.log(this.$refs.bottomRow.offsetHeight - 110 + 'px')
    },
    computed: {
        // role() {
        //     return this.name === 'admin' ? '超级管理员' : '普通用户';
        // }
    }
}

</script>
<style scoped>
.el-tree-node__content>span>span {
    font-size: 14px!important;
}

.el-upload__tip {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: #FF0000;
    letter-spacing: 0;
}

.card1 {
    margin: 0 20px;
}

.el-row {
    margin-bottom: 20px;
}

.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.icon-world {
    display: inline-block;
    width: 14px;
    height: 16px;
    position: relative;
    top: 3px;
    background: url('../../../static/img/file-word.png') no-repeat center;
    background-size: 100% 100%;
}
.jbxx-info .el-upload-dragger{
    border:none;
    width:100%;
}
.jbxx-info .el-upload--text{
    width:200px;
    border:none;
    height:33px;
    text-align:left;
}
.zsxx-info .el-form-item {
    width: 100%;
}

.zsxx-info .el-form-item__content {
    width: 40%;
}

.handle-jbxx .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
    line-height: 20px;
}

.handle-jbxx .el-form-item__label {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    letter-spacing: 0;
}

.handle-jbxx .el-select {
    width: 76%;
}

.handle-jbxx .el-input__inner {
    width: 76%;
}

.handle-jbxx .el-select .el-input__inner {
    width: 100%;
}

.handle-jbxx .el-upload--text {
    width: 76%;
    height: 32px;
    border: none;
    text-align: left;
}

.handle-jbxx .el-upload-dragger {
    border: none;
    height: 100%;
    text-align: left!important;
    padding-left: 3px;
}

.handle-jbxx .el-upload__tip {
    margin-top: 0;
}

.handle-jbxx .el-upload-dragger {
    width: 100%;
}

.handle-jbxx .el-upload-dragger .el-upload__tex downloadt {
    text-align: left;
}

.jgwh-option .el-row[data-v-00040dd2] {
    margin-bottom: 0;
}

.jgwh-table .el-table th>.cell {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.jgwh-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.jgwh-info .el-button--text {
    font-size: 14px;
}

.jgwh-info .el-input--small .el-input__inner {
    width: 220px;
}

.jgwh-info .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.jgwh-info .el-row[data-v-6ad31440] {
    margin-bottom: 14px;
}

.jgwh-info .el-form-item__label {
    padding: 0;
}

.blueSpan {
    color: #1890FF;
}
</style>

