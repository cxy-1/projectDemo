
<template>
    <div class="cjgl organapprove">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>请销假管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>机构变更审批列表</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="splb-info">
                                    <div class="splb-box">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='splbForm' :model="splbForm" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:0px">
                                                <el-col :span="8">
                                                    <el-form-item label="单位：" prop="unit">
                                                        <el-input v-model="splbForm.unit"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="变更类型：" prop="changeType">
                                                        <el-input v-model="splbForm.changeType"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary">查询</el-button>
                                                        <el-button @click="resetForm('splbForm')">重置</el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row style="margin-bottom:0px">
                                                <el-col>
                                                    <el-form-item>
                                                        <el-button type="primary" icon="el-icon-upload2" @click="export2Excel">导出</el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <!--申请信息表格  -->
                                    <div class="splb-table">
                                        <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                                            <el-table-column type="selection" width="55">
                                            </el-table-column>
                                            <el-table-column prop="applicationFormId" label="申请单号" width="160">
                                            </el-table-column>
                                            <el-table-column prop="applicationUserName" label="姓名" width="100">
                                            </el-table-column>
                                            <el-table-column prop="workUnits" label="工作单位">
                                            </el-table-column>
                                            <el-table-column prop="departName" label="部门" >
                                            </el-table-column>
                                            <el-table-column prop="changeType" label="变更类型">
                                            </el-table-column>
                                            <el-table-column prop="applyTime" label="申请时间">
                                            </el-table-column>
                                            <el-table-column label="操作" align="center" width="180">
                                                <template slot-scope="scope">
                                                    <el-button type="text" @click="handleEdit(scope.$index, scope.row)">审批</el-button>
                                                    <el-button type="text" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="pageShow1">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length">
                                            </el-pagination>
                                        </div>
                                        <!--查询分页  -->
                                        <div class="pagination2" v-show="pageShow2" style="margin:10px 0 0 0">
                                            <el-pagination @current-change="handleCurrentChange2" :current-page="cur_page2" :page-size="pagesize2" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length2">
                                            </el-pagination>
                                        </div>
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
export default {
    name: 'dashboard4',
    data() {
        return {
            stepActive: 2,
            showLxs: false,//显示旅行社
            pageShow1:true,
            pageShow2:false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize:10,//表格分页条数
            pagesize2:10,
            data_length:0,
            data_length2:0,
            multipleSelection: [],//多选数组当前选中要删除的项
            class1: 'el-icon-arrow-up el-icon--right',
            class2: 'el-icon-arrow-down el-icon--right',
            splbForm: {
                unit: '',
                changeType: ''
            },
            idx: -1,
            tableData: []
        }
    },
    created() {
        // this.getTabData();
        console.log(this.global.serverPath)
    },
    methods: {
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 表格分页导航
        handleCurrentChange(val) {
            // this.loading = true;
            this.cur_page = val;
            this.getTabData();
        },
        // 查询分页导航
        handleCurrentChange2(val) {
            // this.loading = true;
            this.cur_page2 = val;
            // this.getSearchData();
        },
        getTabData() {
            var self = this;
            self.$http.post(self.global.serverPath+'/zhzg/goAbroad/initQueryGoAbroadApplicationInfo', JSON.stringify({//传递参数
                    "currentNum": self.cur_page,
                    "pageSize": '10'
            })).then(function(res) {
                self.tableData = res.body.rows;
                self.data_length=res.body.totalSize
                self.pageShow1=true;
                self.pageShow2=false;
            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },
        //详情跳转
        handleDetail(index, row) {
            var self = this;
            self.idx = index;
            self.$http.get(self.global.serverPath+'/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
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
        // 审批跳转
        handleEdit(index, row) {
            var self = this;
            self.idx = index;
            const item = self.tableData[index];
            self.$http.get(self.global.serverPath+'/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
                params: {
                    "applicationFormId": row.applicationFormId,//申请单号
                }
            }).then(function(res) {
                console.log(res.body.rows);
                this.$router.push({ path: '/dashboard3', query: { show1: true, show2: false, info: res.body.rows, exist: true } });//跳转到详情页面
            }, function(res) {
                console.log('请求失败！！！！！')
            });
            // this.form = {
            //     devicename: item.devicename,//设备名称
            //     unit: item.unit,//设备编号
            //     belong:item.belong,//所属单位
            //     lead: item.lead,//负责人
            //     describe: item.describe,//描述
            //     phone: item.phone//联系电话
            // }
            // this.editVisible = true;
        },
        // 导出excel
        export2Excel() {//兼容ie10
            var that = this;
            require.ensure([], () => {
                const { export_json_to_excel } = require('../../vendor/Export2Excel');//引入文件
                const tHeader = ['申请单号', '姓名', '工作单位', '部门', '离境原因', '离境形式', '申请时间']; //对应表格输出的title
                const filterVal = ['applicationFormId', 'applicationUserName', 'workUnits', 'departName', 'departureReason', 'departureForm', 'applicationTime'];// 对应表格输出的数据
                const list = that.tableData;
                const data = that.formatJson(filterVal, list);
                export_json_to_excel(tHeader, data, '申请列表excel'); //对应下载文件的名字
            })
        },
        formatJson(filterVal, jsonData) {
            　　　　　　return jsonData.map(v => filterVal.map(j => v[j]))
        　　　　},
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },
    computed: {
        // role() {
        //     return this.name === 'admin' ? '超级管理员' : '普通用户';
        // }
    }
}

</script>
<style scoped>
.pagination2{height: 32px;margin-bottom: 10px;margin-top:10px;}
.pagination2 .el-pagination{
    position: absolute;
    right: 30px;
    bottom:10px;
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

.el-upload__tip {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: #FF0000;
    letter-spacing: 0;
}

.sq-icon {
    background: url('../../../static/img/sq.png') no-repeat center;
    background-size: 100% 100%;
}

.card2 {
    margin: 0 20px;
}

.organapprove .user-info {
    padding: 0 7%;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
}

.organapprove .user-info p {
    margin: 10px 0;
}

.el-row {
    margin-bottom: 20px;
}

.mgb20 {
    margin-bottom: 16px;
}
</style>
<style>
.pagination2 .el-input--small .el-input__inner{
    width: 45px!important;
}
.pagination .el-input--small .el-input__inner{
    width: 45px!important;
}
.splb-table .el-table th>.cell {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.splb-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.splb-info .el-button--text {
    font-size: 14px;
}

.splb-info .el-input--small .el-input__inner {
    width: 220px;
}

.sqxx-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.splb-info .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.splb-info .el-row[data-v-6ad31440] {
    margin-bottom: 14px;
}

.splb-info .el-form-item__label {
    padding: 0;
    margin-top:6px!important;
}

</style>

