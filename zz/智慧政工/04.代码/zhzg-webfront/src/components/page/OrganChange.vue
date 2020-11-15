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
                                    <el-breadcrumb-item>机构管理</el-breadcrumb-item>
                                    <el-breadcrumb-item>机构变更申请</el-breadcrumb-item>
                                </el-breadcrumb>
                            </div>
                            <!--<div class="titIcon">
                                <span>机构变更申请</span>
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
                                <span>变更信息一</span>
                            </div>
                            <div class="jbxx-info">
                                <div class="handle-bgxx1">
                                    <el-form :inline="false" :rules="bgxxRules" :label-position="'top'" label-width="140px" ref='bgxxForm' :model="bgxxForm" class="demo-form-inline">
                                        <el-row :gutter="6">
                                            <el-col :span="8">
                                                <el-form-item label="变更类型" prop="changeType">
                                                    <el-select v-model="bgxxForm.changeType" readonly="">
                                                        <el-option key="新增" label="新增" value="新增"></el-option>
                                                        <el-option key="修改" label="修改" value="修改"></el-option>
                                                        <el-option key="撤销" label="撤销" value="撤销"></el-option>
                                                        <el-option key="合并" label="合并" value="合并"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </el-col>
                                            <el-col :span="10">
                                                <el-form-item label="申请报告" prop="applyReport">
                                                    <el-upload class="upload-demo" :on-success="handleSuccess" v-model="bgxxForm.applyReport" drag action="http://192.168.8.105:9096/zhzg/upload/uploadFile" multiple>
                                                        <el-button size="mini">
                                                            <i class="el-icon-upload2"></i> 上传</el-button>
                                                        <div class="el-upload__text downloadt" style="display:inline-block;">
                                                            <span class="icon-world"></span>
                                                            <em>附件信息表.doc</em>
                                                        </div>
                                                    </el-upload>
                                                    <div ref="redTips" class="el-upload__tip" style="margin-top:0;color:rgba(0,0,0,0.65);">支持word/excel/jpg/png等</div>
                                                </el-form-item>
                                            </el-col>
                                        </el-row>
                                        <!--表格  -->
                                        <el-row :gutter="6">
                                            <div class="bgxx-table">
                                                <el-table size="mini" :data="master_user.data" style="width: 100%" highlight-current-row>
                                                    <!-- <el-table-column type="index"></el-table-column> -->
                                                    <el-table-column v-for="(item,j) in master_user.columns" :key="j" :prop="item.field" :label="item.title" :width="item.width">
                                                        <template slot-scope="scope">
                                                            <span v-if="scope.row.isSet">
                                                                <span v-if="item.select">
                                                                    <el-select v-model="master_user.sel[item.field]">
                                                                        <el-option v-for="item in item.options" :key="item.value" :label="item.label" :value="item.value">
                                                                        </el-option>
                                                                    </el-select>
                                                                    <!-- <el-input size="mini" v-model="master_user.sel[v.field]"></el-input> -->
                                                                </span>
                                                                <span v-else>
                                                                    <!-- <span v-if="scope.row.isSet"> -->
                                                                    <el-input size="mini" v-model="master_user.sel[item.field]"></el-input>
                                                                </span>
                                                            </span>
                                                            <span v-else>{{scope.row[item.field]}}</span>

                                                            <!-- <span v-else>{{scope.row[v.field]}}</span> -->
                                                        </template>
                                                    </el-table-column>
                                                    <el-table-column label="操作" width="180">
                                                        <template slot-scope="scope">
                                                            <span class="el-tag el-tag--info el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,true)">
                                                                {{scope.row.isSet?'保存':"编辑"}}
                                                            </span>
                                                            <span v-if="!scope.row.isSet" class="el-tag el-tag--danger el-tag--mini" @click="deleteRow(scope.$index, scope.row)" style="cursor: pointer;">
                                                                删除
                                                            </span>
                                                            <span v-else class="el-tag  el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,false)">
                                                                取消
                                                            </span>
                                                        </template>
                                                    </el-table-column>
                                                </el-table>
                                                <div class="el-table-add-row" style="width: 100%;" @click="addMasterUser()">
                                                    <span>+ 新增单位</span>
                                                </div>
                                            </div>
                                        </el-row>
                                    </el-form>
                                </div>
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
var generateId = {
    _count: 1,
    get() { return ((+new Date()) + "_" + (this._count++)) }
};
export default {
    data() {
        return {
            multipleSelection: [],//多选数组当前选中要删除的项
            bgxxForm: {
                changeType: '',
                applyReport: ''
            },
            master_user: {
                sel: null,//选中行   
                columns: [
                    { field: "unitName", title: "单位名称", width: 120 },
                    {
                        field: "unitLevel", title: "单位级别", width: 120, select: true,
                        options: [{
                            value: '黄金糕1',
                            label: '黄金糕1'
                        }, {
                            value: '双皮奶1',
                            label: '双皮奶1'
                        }]
                    },
                    {
                        field: "nameType", title: "单位名称类型", width: 110, select: true,
                        options: [{
                            value: '黄金糕2',
                            label: '黄金糕2'
                        }, {
                            value: '双皮奶2',
                            label: '双皮奶2'
                        }]
                    },
                    {
                        field: "highUnit", title: "上级单位名称", select: true,
                        options: [{
                            value: '黄金糕3',
                            label: '黄金糕3'
                        }, {
                            value: '双皮奶3',
                            label: '双皮奶3'
                        }]
                    },
                    { field: "unitFunction", title: "单位职能", width: 150 },
                    { field: "zLevel", title: "正职领导级数", width: 120 },
                    { field: "fLevel", title: "副职领导级数", width: 120 }
                    // { field: "info", title: "其他信息" }
                ],
                data: [],
            },
            bgxxRules: {
                changeType: [
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
                console.log('请求失败！！！！！')
            });
        },
        // 删除表格行
        deleteRow(index, row) {
            this.idx = index;
            this.master_user.data.splice(this.idx, 1);
            this.$message.success('删除成功');
        },
        //读取表格数据
        readMasterUser() {
            //根据实际情况，自己改下啊
            this.master_user.data.map(i => {
                i.id = generateId.get();//模拟后台插入成功后有了id
                i.isSet = false;//给后台返回数据添加`isSet`标识
                return i;
            });
        },
        //添加账号
        addMasterUser() {
            for (let i of this.master_user.data) {
                if (i.isSet) return this.$message.warning("请先保存当前编辑项");
            }
            let j = { id: 0, "unitName": "", "unitLevel": "", "nameType": "", "highUnit": "", "unitFunction": "", "zLevel": "", "fLevel": "", "isSet": true, "_temporary": true };
            this.master_user.data.push(j);
            this.master_user.sel = JSON.parse(JSON.stringify(j));
        },
        //修改
        pwdChange(row, index, cg) {
            //点击修改 判断是否已经保存所有操作
            for (let i of this.master_user.data) {
                if (i.isSet && i.id != row.id) {
                    this.$message.warning("请先保存当前编辑项");
                    return false;
                }
            }
            //是否是取消操作
            if (!cg) {
                if (!this.master_user.sel.id) this.master_user.data.splice(index, 1);
                return row.isSet = !row.isSet;
            }
            //提交数据
            if (row.isSet) {
                //项目是模拟请求操作 自己修改下
                // (function() {
                let data = JSON.parse(JSON.stringify(this.master_user.sel));
                for (let k in data) row[k] = data[k];
                this.$message({
                    type: 'success',
                    message: "保存表格!"
                });

                //然后这边重新读取表格数据
                this.readMasterUser();
                row.isSet = false;
                // })();
            } else {
                this.master_user.sel = JSON.parse(JSON.stringify(row));
                row.isSet = true;
            }
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
.bgxx-table .el-table th>.cell {
    color: rgba(0, 0, 0, 0.85);
    font-size: 14px;
}

.bgxx-table .el-tag--info {
    background-color: transparent;
    border: none;
}

.bgxx-table .el-tag--info,
.bgxx-table .el-tag--info .el-tag__close {
    font-size: 14px;
    color: #1890FF;
}

.bgxx-table .el-tag--danger {
    background-color: transparent;
    border: none;
    font-size: 14px;
}

.bgxx-table .el-tag {
    border: none;
    font-size: 14px;
    background-color: transparent;
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

.jbxx-info .el-upload-dragger {
    border: none;
    width: 100%;
}

.jbxx-info .el-upload--text {
    width: 200px;
    border: none;
    height: 33px;
    text-align: left;
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

.el-table-add-row {
    cursor: pointer;
    width: 100%;
    text-align: center;
    height: 32px;
    line-height: 32px;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    border: 1px dashed #D9D9D9;
    color: rgba(0, 0, 0, 0.85);
    margin-top: 10px;
}

.blueSpan {
    color: #1890FF;
}
</style>

