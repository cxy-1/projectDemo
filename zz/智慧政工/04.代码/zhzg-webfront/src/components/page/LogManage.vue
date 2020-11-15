<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-setting"></i>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>日志管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :label-position="'right'" label-width="90px" ref='formInline' :model="formInline" class="demo-form-inline">
                    <el-row :gutter="6"><!--gutter 属性来指定每一栏之间的间隔，默认间隔为 0--> 
                        <el-col :span="7">
                            <el-form-item label="操作用户：" prop="operat">
                                <el-input v-model="formInline.operat" placeholder=""></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="客户端IP：" prop="clientIP">
                                <el-input v-model="formInline.clientIP" placeholder=""></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="操作类型：" prop="operatType">
                                <el-input v-model="formInline.operatType" placeholder=""></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="3"></el-col>
                    </el-row>
                    <el-row :gutter="6">
                        <el-col :span="7">
                            <el-form-item label="操作时间：" prop="operatTime">
                                <el-input v-model="formInline.operatTime" placeholder=""></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="菜单名称：" prop="menuName">
                                <el-input v-model="formInline.menuName" placeholder=""></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7"><el-form-item></el-form-item></el-col>
                        <el-col :span="3">
                            <el-form-item class="right-btn">
                                <el-button type="primary" icon="search" @click="search">查询</el-button>
                                <el-button @click="resetForm('formInline')">重置</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <el-form-item>
                                <el-button size="small" type="success" @click="export2Excel">导出Excel</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <el-table :data="data.slice((cur_page-1)*pagesize,cur_page*pagesize)" border :style="'width:100%'" ref="multipleTable" @selection-change="handleSelectionChange">
                <!-- <el-table-column type="selection" width="48" align="center"></el-table-column> -->
                <el-table-column label="序号" type="index" align="center" :index="indexMethod"></el-table-column>
                <el-table-column prop="operat" label="操作用户" width="160"></el-table-column>
                <el-table-column prop="clientIP" label="客户端IP"></el-table-column>
                <el-table-column prop="operatType" label="操作类型"></el-table-column>
                <el-table-column prop="operatResult" label="操作结果"></el-table-column>
                <el-table-column prop="operatTime" label="操作时间"></el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @size-change="handleSizeChange" 
                @current-change="handleCurrentChange" 
                :current-page="cur_page" :page-size="pagesize" 
                :page-sizes="[10, 20, 30, 40]" 
                layout="total, sizes, prev, pager, next, jumper" 
                :total="data_length">
                </el-pagination>
            </div>
        </div>
        <!--copyright  -->
        <el-footer :style="'text-align:center'">&copy;2018-2021&emsp;徐州公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    // name: 'map',
    data() {
        return {
            getUrl: './static/logTable.json',
            tableData: [],//表格当前页数据
            cur_page: 1,
            pagesize: 10,
            multipleSelection: [],//多选数组
            select_cate: '',
            select_word: '',
            data_length: 0,
            del_list: [],
            is_search: false,
            addFormVisible: false,//新增界面是否显示
            formInline: {//查询form
                operat: '',
                clientIP: '',
                operatType: '',
                operatTime: '',
                menuName: ''
            },
            idx: -1,
        }
    },
    created() {
        this.getData();
    },
    computed: {
        data() {
            return this.tableData.filter((d) => {//过滤器
                let is_del = false;
                for (let i = 0; i < this.del_list.length; i++) {
                    if (d.clientIP === this.del_list[i].clientIP) {
                        is_del = true;
                        break;
                    }
                }
                if (!is_del) {
                    if (d.clientIP.indexOf(this.formInline.clientIP) > -1 &&
                        (d.operat.indexOf(this.formInline.operat) > -1 ||
                            d.clientIP.indexOf(this.formInline.clientIP) > -1)
                    ) {
                        return d;
                    }
                }
            })
        }
    },
    methods: {
        // 导出excel
        export2Excel() {//兼容ie10
            var that=this;
　　　　　　 require.ensure([], () => {
　　　　　　　　const { export_json_to_excel } = require('../../vendor/Export2Excel');//引入文件
　　　　　　　　const tHeader = [ '操作用户', '客户端IP', '操作类型', '操作结果','操作时间']; //对应表格输出的title
　　　　　　　　const filterVal = ['operat', 'clientIP', 'operatType', 'operatResult', 'operatTime'];// 对应表格输出的数据
　　　　　　　　const list = that.tableData;
　　　　　　　　const data = that.formatJson(filterVal, list);
　　　　　　　　export_json_to_excel(tHeader, data, '列表excel'); //对应下载文件的名字
　　　　　　 })
　　　　},
　　　　formatJson(filterVal, jsonData) {
　　　　　　return jsonData.map(v => filterVal.map(j => v[j]))
　　　　},
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        // 分页导航
        handleCurrentChange(val) {
            this.cur_page = val;
            // this.getData();
        },
        //显示新增界面
        handleAdd() {
            this.addFormVisible = true;
            this.addForm = {
                name: '',
                type: '',
                datainfo: '',
                state: ''
            };
            // this.resetForm('addForm')
        },
        handleSizeChange(val) {
            this.pagesize = val;
            // this.getData()
            console.log(`每页 ${val} 条`);
        },
        getData() {
            // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
            // if (process.env.NODE_ENV === 'development') {
            //     this.url = '/ms/table/list';
            // };
            // this.$axios.post(this.url, {
            //     page: this.cur_page
            // }).then((res) => {
            //     this.tableData = res.data.list;
            // })
            var self = this;
            $.ajax({
                type: "get",
                dataType: "json",
                url: this.getUrl,
                success: function(json) {
                    self.tableData = json.list
                    self.data_length = json.list.length;
                },
                error: function() {
                    alert('加载失败')
                }
            })
        },
        search() {
            this.is_search = true;
        },
        formatter(row, column) {
            return row.address;
        },
        filterTag(value, row) {
            return row.tag === value;
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 序号
        indexMethod(index) {
            return index * 1 + 1;
        }
    }
}

</script>

<style scoped>
.el-form-item {
    margin-bottom: 12px
}

.pointer-img {
    position: absolute;
    top: 80px;
    right: 150px;
    width: 33%;
}

.el-tabs__nav-wrap,
.el-tabs {
    width: 100%!important;
    margin-left: 0px!important;
}

.right-btn {
    float: right;
}

.handle-select {
    width: 200px;
}

.pagination {
    margin-bottom: 10px
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.el-footer {
    font-size: 12px;
    color: #333;
    line-height: 20px
}

.del-dialog-cnt {
    font-size: 16px;
    text-align: center
}
</style>
<style>
.handle-box .el-form--inline .el-form-item {
    /* margin-right:0; */
}

.pointer-img .el-textarea__inner {
    height: 150px;
    background: url(../../../static/img/pointer.png) no-repeat center;
    background-size: 100% 100%;
}
</style>

