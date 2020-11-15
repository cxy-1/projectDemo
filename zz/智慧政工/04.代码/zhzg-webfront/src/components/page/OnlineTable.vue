<template>
    <div class="rb-container">
        <div class="rb-header">
            <div class="rb-header-search search-button-custom-style">
                <el-row>
                    <el-col :span="2">
                        <div class="titleStyle">在线表格</div>
                    </el-col>
                    <el-col :span="21" :offset="1">
                        <el-input size="big" style="width: 76%" placeholder="请输入您想查找的内容" v-model="keyword">
                            <el-button class="" type="primary" slot="append" @click="btnSearch">搜索</el-button>
                        </el-input>
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
                    </el-tabs>
                </div>
                <div class="eb-body-data">
                    <div class="rb-body-row"> 
                        <el-row>   
                             <el-col :span="24" class="chageLine" v-for="(item,index) in data" :key="index">
                                <el-checkbox-group v-model="checkList">                                                                   
                                    <div class="grid-box" @click="btnPreview(item.path)">  
                                        <!-- <el-checkbox :label="item.path" class="table-checkbox" v-if="item.type!='folder'"></el-checkbox>-->
                                        <div class="grid-box-icon">
                                            <img :src="require('static/img/otable/excel.png')" v-if="item.type=='excel'" alt="">
                                            <img :src="require('static/img/otable/word.png')" v-else-if="item.type=='word'" alt="">
                                            <img :src="require('static/img/otable/ppt.png')" v-else-if="item.type=='ppt'" alt="">
                                        </div>
                                        <div class="grid-box-name">{{item.name}}</div>
                                    </div>
                                     <div class="row-right"> 
                                         <el-button size="medium" @click="btnDownload(item.path)">下载</el-button> 
                                     </div> 
                                </el-checkbox-group>
                            </el-col> 
                        </el-row>
                    </div>
                    <div class="rb-page-nav" v-show="data.length > 0">
                        <el-pagination background
                                    @current-change="handleCurrentChange"
                                    :current-page="page.currentPage"
                                    :page-sizes="[10]"
                                    :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper"
                                    :total="page.totalPage.length">
                        </el-pagination>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import onlineData from '../../data/onlineData'; // 数据接口

    export default {
        name: "RegBox",
        data() {
            return {
                keyword: '',  // 查询的关键词
                category: 'all',
                checkList: [], // checkbox数组
                data: [], // 列表数据
                page: { // 分页
                    currentPage: 1,
                    pageSize: 10,
                    totalPage: []
                },
                nodata: '没有输入搜索内容',
                fileData: { 
                    path: "",
                    type: "",
                    name: ""
                },
                downData: {
                    PersonId: localStorage.getItem('loginpersonId'),
                    pathList: [],
                }
            }
        },
        mounted() {
            var _this = this;
            _this.initPage();
        },
        methods: {
            handleCurrentChange(val){//前端分页
                let _this=this;
                _this.page.currentPage=val;
                _this.data=_this.page.totalPage.slice((_this.page.currentPage-1)*_this.page.pageSize,(_this.page.currentPage)*_this.page.pageSize);
            },
            btnSearch() {
                let _this = this;
                _this.initPage();
                console.log(_this.checkList)
            },
            handleClick(tab, event) {
                var _this = this;
                console.log(this.category)
                _this.initPage();
            },
            initPage() { // 查询
                let _this = this;
                _this.nodata = '暂无数据';
                _this.checkList = [];
                let data = {
                    "keyword": _this.keyword,
                    "category": _this.category
                };
                onlineData.initPage(data).then(result => {
                    _this.data = result.data;
                    _this.page.totalPage=result.data;
                    _this.data=_this.page.totalPage.slice((_this.page.currentPage-1)*_this.page.pageSize,(_this.page.currentPage)*_this.page.pageSize);
                    _this.fileData.path = result.data.path;
                    _this.fileData.type = result.data.type;
                    _this.fileData.name = result.data.name;
                });
            },          
            btnDownload(path) {
                let _this = this,paths=[]; //下载文件
                paths.push(path);    
                console.log(paths)
                let data = {
                    "personId": localStorage.getItem('loginpersonId'),
                    "pathList": paths,
                }
                onlineData.download(data).then(result => {
                    result.data.forEach(function(value,index){
                        window.open(value);
                    })                                
                })
            }, 
            btnPreview(path) {
                let _this = this; 
                // _this.data = [];  
                
                onlineData.getPdf({"path":path, "personId":localStorage.getItem('loginpersonId')}).then(result => {
                    window.open(result.data.path);
                });
            }       
        }
    }
</script>

<style scoped>
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
    .row-right {
        width: 80px;
        float: right;
        position: relative;
        top: -50px;
    }
    .chageLine{
        height:62px;
    }
    .rb-container {
        width: 100%;
        height: 100%;
        min-width: 900px;
    }

    .rb-header {
        background: #fff;
        width: 100%;
        height: 140px;
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
        height: 40px;
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
        clear: both;
    }

    .rb-body-row {
        position: relative;
    }    

    .btnDownload {
        position: fixed;
        right: 50px;
        top: 330px;
        width: 70px;
        text-align: right;
        font-family: PingFangSC-Regular;
        font-size: 14px;
        color: rgba(0,0,0,0.85);
        letter-spacing: 0;
        text-align: center;
        cursor: pointer;
        background: url('../../../static/img/otable/download.png') no-repeat left;
        z-index: 1000;
    }

    .grid-content {        
        position: relative;    
        margin-top: 30px;                         
    }    

    .table-checkbox {
        /* position: absolute; */
        width: 16px;
        height: 16px;
        /* left: 4%; */
        /* top: 4%; */
    }
    .grid-box .el-checkbox{
        margin-right:10px;
    }
    .grid-box {
        padding: 10px; 
        text-align: center; 
        cursor: pointer;
        position: relative;
        border: 1px solid transparent; 
        width: 80%;
        height: 40px;
        line-height:40px;
        display: flex;
    }
    @media screen and (min-width: 1600px) {
        .grid-box {
            padding: 10px; 
            text-align: center; 
            cursor: pointer;
            position: relative;
            border: 1px solid transparent; 
            width: 70%;
            height: 40px;
            display: flex;
        }
    }
    .chageLine .el-checkbox-group.active,.chageLine .el-checkbox-group:hover {
        background-color: #F3F7FD;
        border: 1px solid #DCE6F2;
        border-radius: 3px;
    }
    .grid-box-icon {
        position: relative;
        height: 30px;
         top:5px; 
        /* margin-top: 15px; */
        cursor: pointer;
    }
    .grid-box-icon>img{
        height:30px;
    }
    .grid-box-name {
        font-family: PingFangSC-Regular;
        font-size: 16px;
        color: rgba(0,0,0,0.65);
        letter-spacing: 0;
        text-align: center;
        margin-left:10px;
    }    

    .nodata {
        text-align: center;
    }

    .titleStyle {
        font-family: PingFangSC-Semibold;
        font-size: 30px;
        color: #1890FF;
        line-height: 40px;
        white-space:nowrap;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.30);
    }
</style>
