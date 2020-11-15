<style>
    .row-mid > div > em {
        color: #EE6723;
        font-style: normal;
    }
</style>
<template>
    <div class="rb-container">
        <div class="rb-header">
            <div class="rb-header-search search-button-custom-style">
                <el-row>
                    <el-col :span="2">
                        <div class="titleStyle">规范宝典</div>
                    </el-col>
                    <el-col :span="21" :offset="1">
                        <el-input size="big" style="width: 76%" placeholder="请输入您想查找的内容" v-model="keyword">
                            <el-button class="" type="primary" slot="append" @click="btnSearch">搜索</el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8" :offset="3" style="margin-top: 10px;">
                        <el-radio v-model="radio" label="1">按标题检索</el-radio>
                        <el-radio v-model="radio" label="2">按全文检索</el-radio>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div class="rb-body">
            <div class="eb-body-content">
                <div>
                    <el-tabs v-model="category" @tab-click="handleClick">
                        <el-tab-pane label="全部" name="all">全部</el-tab-pane>
                        <el-tab-pane label="人事" name="rs">人事</el-tab-pane>
                        <el-tab-pane label="组教" name="zj">组教</el-tab-pane>
                        <el-tab-pane label="训练学校" name="xlxx">训练学校</el-tab-pane>
                        <el-tab-pane label="离退休" name="ltx">离退休</el-tab-pane>
                        <el-tab-pane label="警辅" name="jf">警辅</el-tab-pane>
                        <el-tab-pane label="政工综合" name="zgzh">政工综合</el-tab-pane>             
                        <el-tab-pane label="工会" name="gh">工会</el-tab-pane>
                        <el-tab-pane label="见义勇为基金会" name="jyywjjh">见义勇为基金会</el-tab-pane>
                    </el-tabs>
                </div>
                <div class="eb-body-data">
                    <div v-show="data.length == 0" class="el-table__empty-block" style="width: 100%;">
                        <span class="el-table__empty-text">{{nodata}}</span>
                    </div>
                    <div class="rb-body-row" v-for="(item,index) in data" :key="index">
                        <!-- <div class="row-left">
                            <el-checkbox class="middleCheck"></el-checkbox>
                        </div> -->
                        <div class="row-mid">
                            <div v-html="item.title" @click="btnPreview(item.title,item.path)">
                                {{item.title}}
                            </div>
                            <div v-html="item.content">
                                {{item.content}}
                            </div>
                        </div>
                        <div class="row-right">
                            <el-button size="medium" @click="btnPreview(item.title,item.path)">预览</el-button>
                        </div>
                    </div>
                </div>
                <div class="rb-page-nav" v-show="data.length > 0">
                    <el-pagination @size-change="handleSizeChange"
                                   background
                                   @current-change="handleCurrentChange"
                                   :current-page="page.currentPage"
                                   :page-sizes="[10]"
                                   :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper"
                                   :total="page.totalPage">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import regBoxData from '../../data/regBoxData'; // 数据接口

    export default {
        name: "RegBox",
        data() {
            return {
                keyword: '',  // 查询的关键词
                category: 'all',
                radio: '1',
                checkList: [], // checkbox数组
                data: [], // 列表数据
                nodata: '没有输入搜索内容',
                page: { // 分页
                    currentPage: 1,
                    pageSize: 10,
                    totalPage: 100
                }
            }
        },
        methods: {
            handleClick(tab, event) {
                let _this = this;
                _this.category = tab.name;
                _this.page.currentPage=1;
                _this.initPage();
            },
            btnSearch() {
                let _this = this;
                _this.page.currentPage = 1;
                _this.initPage();
            },
            initPage() { // 查询
                let _this = this;
                _this.nodata = '暂无数据';
                let data = {
                    "keyword": _this.keyword,
                    "pageSize": _this.page.pageSize,
                    "currentPage": _this.page.currentPage,
                    "kind": _this.radio,
                    "category": _this.category
                };
                regBoxData.initPage(data).then(result => {
                    _this.data = result.data;
                    _this.page.totalPage = result.totalPage;
                });
            },
            btnPreview(titles,path) { // 预览
                regBoxData.regPreview({"title":titles}).then(result => {
                    window.open(path);
                });
                // window.open(path);
            },
            handleSizeChange(pageSize) { // 每页显示条数变化
                let _this = this;
                _this.page.pageSize = pageSize;
                _this.initPage();
            },
            handleCurrentChange(currentPage) { // 页码变化
                let _this = this;
                _this.page.currentPage = currentPage;
                _this.initPage();
            }
        },
        mounted() {
            let _this = this;
            _this.initPage();
        }
    }
</script>

<style scoped>
    .rb-container {
        width: 100%;
        height: 100%;
    }

    .rb-header {
        background: #fff;
        width: 100%;
        height: 150px;
        padding: 20px 15px;
    }

    .rb-header-nav {
        width: 100%;
        height: 20px;
    }

    .rb-header-title {
        margin-top: 10px;
        height: 20px;
        line-height: 20px;
        color: #272727;
        font-size: 16px;
        font-family: PingFangSC-Medium;
    }

    .rb-header-search {
        width: calc(100% - 200px);
        height: 45px;
        margin: 30px auto;
    }

    .rb-body {
        background-color: #f0f0f0;
    }

    .rb-body .el-tab-pane {
        display: none;
    }

    .rb-body .eb-body-content {
        width: calc(100% - 40px);
        min-height: 100px;
        margin: 20px auto;
        padding: 15px;
        box-sizing: border-box;
        background-color: #ffffff;
    }

    .eb-body-data {
        /* background-color: #fff; */
        /* width: 100%; */
        /* height: calc(100% - 50px); */
        /* min-height: 300px; */
        clear: both;
    }

    .rb-body-row {
        width: calc(100% - 50px);
        margin: 0 auto;
        height: 120px;
        border-bottom: 1px dashed #E8E8E8;
    }

    .rb-body-row > div {
        float: left;
        /* margin: 15px 0; */
    }

    .row-left {
        width: 30px;
        height: 100%;
    }

    .row-mid {
        width: calc(100% - 80px);
        height: 100%;
        overflow: hidden;
    }

    .row-right {
        width: 80px;
        height: 100%;
    }

    .row-mid > div:first-child {
        width: 100%;
        height: 30px;
        line-height: 30px;
        color: #1890FF;
        cursor: pointer;
    }

    .row-mid > div:nth-child(2) {
        width: 100%;
        height: 60px;
        line-height: 30px;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
        text-align: justify;
    }

    .row-right > button {
        float: right;
        margin-top: 36px;
    }

    .row-left > label {
        margin-top: 5px;
        width: 16px;
        height: 16px;
    }

    .rb-page-nav {
        width: 100%;
        height: 50px;
        top: 15px;
        position: relative;
    }

    .rb-page-nav > div {
        float: right;
        margin-right: 20px;
    }

    /* data style */

    .nodata {
        text-align: center;
    }

    .titleStyle {
        font-family: PingFangSC-Semibold;
        font-size: 30px;
        color: #1890FF;
        white-space:nowrap;
        line-height: 40px;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.30);
    }
</style>
