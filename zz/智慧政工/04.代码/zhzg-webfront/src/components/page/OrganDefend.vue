<template>
    <div class="cjgl organdefend" style="height:100%" ref="bottomRow">
        <div class="jgwh-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row style="margin-bottom:0">
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>机构管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>组织机构维护</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <!--vue-split-panel分割面板  -->
                            <Split ref="splitPanel">
                                <SplitArea :size="25">
                                    <div class="treeBox" ref="treeCard">
                                        <el-card class="card1">
                                            <!-- <el-tree :data="unitList" :props="defaultProps" @node-click="handleNodeClick" :render-content="renderContent"></el-tree> -->
                                            <el-tree :data="unitList" :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true" :default-expanded-keys="['1',2,3,4,5,6]" :render-content="renderContent"></el-tree>
                                        </el-card>
                                    </div>
                                </SplitArea>
                                <SplitArea :size="75">
                                    <el-card class="card2">
                                        <div class="jgwh-info">
                                            <!--申请信息表单  -->
                                            <div class="handle-jgwh">
                                                <el-form :inline="true" :label-position="'right'" label-width="80px" ref='searchList' :model="searchList" class="demo-form-inline">
                                                    <el-row :gutter="6" style="margin-bottom:8px">
                                                        <el-col :span="8">
                                                            <el-form-item label="单位名称：" prop="unitName">
                                                                <el-input v-model="searchList.unitName"></el-input>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="8">
                                                            <el-form-item label="单位级别：" prop="unitLevel">
                                                                <el-select v-model="searchList.unitLevel" readonly="">
                                                                    <el-option key="局级" label="局级" value="局级"></el-option>
                                                                    <el-option key="副厅局级" label="副厅局级" value="副厅局级"></el-option>
                                                                    <el-option key="县处级" label="县处级" value="县处级"></el-option>
                                                                    <el-option key="副县处级" label="副县处级" value="副县处级"></el-option>
                                                                    <el-option key="科级" label="科级" value="科级"></el-option>
                                                                    <el-option key="副科级" label="副科级" value="副科级"></el-option>
                                                                    <el-option key="股级" label="股级" value="股级"></el-option>
                                                                    <el-option key="其他" label="其他" value="其他"></el-option>
                                                                </el-select>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="8">
                                                            <el-form-item>
                                                                <el-button type="primary" @click="getSearchData()">查询</el-button>
                                                                <el-button @click="resetForm('searchList')">重置</el-button>
                                                            </el-form-item>
                                                        </el-col>
                                                    </el-row>
                                                    <el-row style="margin-bottom:8px">
                                                        <el-col>
                                                            <el-form-item>
                                                                <!-- <el-button type="primary" icon="el-icon-plus" @click="addUnit()">添加部门</el-button> -->
                                                                <el-button icon="el-icon-back">撤销部门</el-button>
                                                                <el-button icon="el-icon-sort">合并机构</el-button>
                                                            </el-form-item>
                                                        </el-col>
                                                    </el-row>
                                                </el-form>
                                            </div>
                                            <!--申请信息表格  -->
                                            <div class="jgwh-table">
                                                <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                                                    <el-table-column type="selection" width="55">
                                                    </el-table-column>
                                                    <el-table-column prop="orgShortedName" label="单位名称">
                                                    </el-table-column>
                                                    <el-table-column prop="orgId" label="单位编码" >
                                                    </el-table-column>
                                                    <el-table-column prop="orgLevel" label="单位级别">
                                                    </el-table-column>
                                                    <el-table-column prop="positiveLeaderNum" label="正职领导职数">
                                                    </el-table-column>
                                                    <el-table-column prop="negativeLeaderNum" label="副职领导职数">
                                                    </el-table-column>
                                                    <!--<el-table-column label="操作" align="center" width="140">
                                                        <template slot-scope="scope">
                                                            <el-button type="text" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                                                            <el-button type="text" @click="handleDetail(scope.$index, scope.row)">顺序</el-button>
                                                            <el-button type="text" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
                                                        </template>
                                                    </el-table-column>-->
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
                                </SplitArea>
                            </Split>
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
    data() {
        return {
            stepActive: 2,
            defaultProps: {//树图元素
                children: 'children',
                label: 'label'
            },
            unitList: [],
            showLxs: false,//显示旅行社
            pageShow1: true,
            pageShow2: false,
            cur_page: 1,//表格当前页
            cur_page2: 1,
            pagesize: 10,//表格分页条数
            pagesize2: 10,
            data_length: 0,
            data_length2: 0,
            orgId:'',//机构编号
            multipleSelection: [],//多选数组当前选中要删除的项
            searchList: {
                unitName: '',
                unitLevel: ''
            },
            idx: -1,
            tableData: []
        }
    },
    created() {//在模板渲染成html前调用
        this.getTreeList();
        this.getTabData();//表格默认展示第一级数据

    },
    methods: {
        // 添加部门
        addUnit() {
            this.$router.push('/organdefend2')
        },
        // 获取折叠树形列表数据
        getTreeList() {
            var that = this;
            that.$http.get(that.global.serverPath + '/zhzg/org/queryOrgTreeInfoByOrgId', {
                params: { "orgId":localStorage.getItem('loginCompanyId') },
            }).then(function(res) {
                this.unitList = res.body.rows;
            }, function(res) {
                console.log('获取树形列表失败！')
            });
        },
        // 点击菜单栏，获取表格数据
        handleNodeClick(data) {
            var that = this;
            that.orgId=data.id;
            that.tableData = [];
            that.$http.post(that.global.serverPath + '/zhzg/org/initQueryBsOrgInfoByOrgId', {
                "orgId": data.id,
                "currentNum": that.cur_page,
                "pageSize": that.pagesize
            }).then(function(res) {
                that.tableData = res.body.rows;
                that.data_length = res.body.totalSize
                that.pageShow1 = true;
                that.pageShow2 = false;
            }, function(res) {
                console.log('获取表格数据失败！')
            });
        },
        renderContent(h, { node, data, store }) {
            return (
                <span>
                    <i class={data.className} ></i>
                    <span style="font-size:14px;">
                        {node.label}
                    </span>
                </span>
            );
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 表格分页导航
        handleCurrentChange(val) {
            /** <em style="font-size:14px;color:red">{data.number}</em>*/
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
            var that = this;
            that.tableData = [];
            that.$http.post(that.global.serverPath + '/zhzg/org/initQueryBsOrgInfoByOrgId', {
                "orgId": localStorage.getItem('loginCompanyId'),
                "currentNum": that.cur_page,
                "pageSize": that.pagesize
            }).then(function(res) {
                that.tableData = res.body.rows;
                that.data_length = res.body.totalSize
                that.pageShow1 = true;
                that.pageShow2 = false;
            }, function(res) {
                console.log('获取表格数据失败！')
            });
        },
        // 查询表格
        getSearchData() {
            var that = this;
            that.tableData = [];
            that.$http.post(that.global.serverPath + '/zhzg/org/queryBsOrgPagesInfoByOrgId', {
                "currentNum": that.cur_page,
                "pageSize": that.pagesize2,
                "orgId": that.orgId,
                "orgFullName": that.searchList.unitName,
                "orgLevel": that.searchList.unitLevel
            }).then(function(res) {
                that.tableData = res.body.rows;
                that.data_length2 = res.body.totalSize
                that.pageShow1 = false;
                that.pageShow2 = true;
            }, function(res) {
                console.log('查询表格数据失败！')
            });
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
                this.$router.push('/organdefend2')
                console.log(res.body.rows);
                // this.$router.push({ path: '/dashboard3', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },
        // 审批跳转
        handleEdit(index, row) {
            this.idx = index;
            const item = this.tableData[index];
            this.$router.push('/dashboard3')
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
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    },
    mounted() {//在模板渲染成html后调用
        this.$refs.treeCard.style.height = this.$refs.bottomRow.offsetHeight - 120 + 'px';//滚动条高度
        $('.split').height = "500px"
        // this.$refs.splitPanel.style.height = this.$refs.bottomRow.offsetHeight - 110 + 'px';//分割面板高度
        $('.split').height((this.$refs.bottomRow.offsetHeight - 120))
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
.treeBox {
    overflow-y: auto;
    margin-right: 5px;
}

.el-tree-node__content>span>span {
    font-size: 14px!important;
}

.pagination2 {
    height: 32px;
    margin-bottom: 10px;
    margin-top: 10px;
}

.pagination2 .el-pagination {
    position: absolute;
    right: 30px;
    bottom: 10px;
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

.card1 {
    margin: 0 0 20px 20px;
}

.card2 {
    margin: 0 20px 20px 5px;
}

.organdefend .user-info {
    padding: 0 7%;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
}

.organdefend .user-info p {
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
/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
.treeBox::-webkit-scrollbar {
    width: 6px;
    /*滚动条宽度*/
    height: 6px;
    /*滚动条高度*/
}
/*定义滚动条轨道 内阴影+圆角*/
.treeBox::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px #F5F5F5;
    border-radius: 4px;
    /*滚动条的背景区域的圆角*/
    background-color: #F5F5F5;
    /*滚动条的背景颜色*/
}
/*定义滑块 内阴影+圆角*/
.treeBox::-webkit-scrollbar-thumb {
    border-radius: 4px;
    /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px #BBC3D2;
    background-color: #BBC3D2;
    /*滚动条的背景颜色*/
}
.jgwh-option .el-row[data-v-00040dd2] {
    margin-bottom: 0;
}

.pagination2 .el-input--small .el-input__inner {
    width: 45px!important;
}

.pagination .el-input--small .el-input__inner {
    width: 45px!important;
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

.sqxx-table .el-table td div {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
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
</style>

