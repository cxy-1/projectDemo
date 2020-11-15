<template>
    <div>
        <el-form>
            <el-form-item label="选择单位" :label-width="formLabelWidth" class="customSelect">
                <el-select v-model="UnitValue" popper-class="customManageBox" placeholder="请选择">
                    <el-option :value="UnitValue">
                            <el-tree
                            style="height: 200px"
                            :data="unitOptionsRYGK"
                            show-checkbox
                            node-key="unitId"
                            ref="RYGKtree"
                            check-on-click-node
                            @check="handleNodeCheckRYGK"
                            :props="dataProps">
                            </el-tree>
                    </el-option>
                </el-select>
            </el-form-item>                     
        </el-form>
        <el-tabs class="customTabs" v-model="activeTab" @tab-click="handleClick">
            <el-tab-pane label="年龄" name="nlAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.nlAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table height="360" :data="tableDataNl.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataNl.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataNl.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="性别" name="xbAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.xbAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataXb.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataXb.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataXb.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="名族" name="mzAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.mzAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataMz.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataMz.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataMz.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="来源" name="lyAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.lyAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataLy.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataLy.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataLy.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="职务" name="zwAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.zwAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataZw.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataZw.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataZw.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="警衔" name="jxAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.jxAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataJx.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataJx.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataJx.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="政治面貌" name="zzmmAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.zzmmAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataZzmm.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataZzmm.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataZzmm.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="学历学位" name="xlxwAttList">
                <div class="left">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.xlxwAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div class="right">
                    <el-table :data="tableDataXlxw.data" style="width: 100%">
                        <el-table-column  v-for="col in tableDataXlxw.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataXlxw.totalRecord"
                        :page-sizes="[5, 10, 20, 30]"
                        :page-size="20"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>           
    </div>
</template>
<script>
    import api from './api';
    export default {
        data: function() {
            return {             
                // UnitData
                UnitValue: [],
                unitOptionsRYGK: [],
                dataProps:{
                    children: 'childCompany',
                    label: 'unitName'
                },
                value: '',
                formLabelWidth: '120px',
                // tab
                activeTab: 'nlAttList',
                // ul
                tabList: {
                    nlAttList: [
                        {name:'51岁及以上'},
                        {name:'41-50(岁)'},
                        {name:'31-40(岁)'},
                        {name:'30岁及以下'}
                    ],
                    xbAttList: [
                        {name:'男'},
                        {name:'女'}
                    ],
                    mzAttList: [
                        {name:'汉'},
                        {name:'少数民族'}
                    ],
                    lyAttList: [
                        {name:'警察院校毕业生'},
                        {name:'社会招考'},
                        {name:'实施公务员法机关调入'},
                        {name:'复原军人'},
                        {name:'转业军人'},
                        {name:'其他'}
                    ],
                    zwAttList: [
                        {name:'正厅领导职'},
                        {name:'副厅领导职'},
                        {name:'副局领导职'},
                        {name:'副局非领导职'},
                        {name:'正处职'},
                        {name:'副处职'},
                        {name:'正科职'},
                        {name:'副科职'}
                    ],
                    jxAttList: [
                        {name:'一级警监'},
                        {name:'二级警监'},
                        {name:'三级警监'},
                        {name:'一级警督'},
                        {name:'二级警督'},
                        {name:'三级警督'},
                        {name:'一级警司'},
                        {name:'二级警司'},
                        {name:'三级警司'},
                        {name:'一级警员'}
                    ],
                    zzmmAttList: [
                        {name:'中共党员'},
                        {name:'中共预备党员'},
                        {name:'共青团员'},
                        {name:'其他党派'},
                        {name:'群众'}
                    ],
                    xlxwAttList: [
                        {name:'博士'},
                        {name:'硕士'},
                        {name:'本科'},
                        {name:'大专'},
                        {name:'高中及以下'}
                    ],
                    
                },
                activeClass: '0',
                listQuery: {
                    departIds: [],
                    type: '51岁及以上',
                    // pagination
                    pageNo: 1,
                    pageSize: 10,
                },
                // table             
                tableDataNl: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataXb: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataMz: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataLy: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataZw: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataJx: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataXlxw: {
                    data: [],
                    column:[],
                    totalRecord:0
                },
                tableDataZzmm: {
                    data: [],
                    column:[],
                    totalRecord:0
                }
            }
        },
        mounted() {
            // 调用获取单位方法，初始化单位及表格数据
            this.getUnitTree('').then(()=>{
                this.setAllChecKeys();
                // 获取当前勾选的departIds数组
                this.getChecKeys();
                // 传参 单位、type、pageNo、pageSize
                this.getTableData(this.listQuery.departIds,this.listQuery.type,this.listQuery.pageNo,this.listQuery.pageSize);
            });                             
        },
        methods: {
            //json对象转化成json数组对象
            stringToArray(result) {
                let arrColumn=[];
                // 格式转换{"prop":"name","label":"姓名"},
                for(let i in result.column){
                    arrColumn.push({"prop":i,"label":result.column[i]});            
                }
                return arrColumn;
            },            
            // 获取单位
            getUnitTree(departIds){
                return api.get_rygk_unitdata(departIds).then(result => {
                    return result.data;
                }).then(departIds => {
                    this.unitOptionsRYGK = departIds;                                                                             
                });
            },
            // 设置默认勾选根目录  
            setAllChecKeys(){
                this.$refs.RYGKtree.setCheckedKeys([this.unitOptionsRYGK[0].unitId]); 
            },
            // 获取勾选的目录
            getChecKeys(){
                this.listQuery.departIds = this.$refs.RYGKtree.getCheckedKeys();
            },
            // 单位勾选 获取departIds
            handleNodeCheckRYGK(item,node){
                console.log(node)
                var checkArray = node.checkedNodes.map((a, i) => {
                    return {
                        name: a.unitId
                    }
                })
                this.listQuery.departIds = node.checkedKeys;
                // 查询生成表
                this.getTableData(this.listQuery.departIds,this.listQuery.type,this.listQuery.pageNo,this.listQuery.pageSize);
            },            
            // tabs板块切换
            handleClick(tab, event) {
                this.activeTab == tab.paneName;//每次切换都预设activeTab到model里
                this.resetItem();//重置左侧item
                this.getTableData(this.listQuery.departIds,this.listQuery.type,this.listQuery.pageNo,this.listQuery.pageSize);
            },
            // 类别点击切换 获取type
            btnGetItem(index){
                this.activeClass = index;
                this.listQuery.type = this.tabList[this.activeTab][index].name;   
                console.log( this.listQuery.type)                
                // tabList.nlAttList
                this.getTableData(this.listQuery.departIds,this.listQuery.type,this.listQuery.pageNo,this.listQuery.pageSize);
            },
            // 重置左侧item，默认选择第一行
            resetItem(){
                this.btnGetItem(0);
            },
            // 6:获取表格数据
            getTableData(departIds,type,pageNo,pageSize) {                
                let data = {
                    "departIds": departIds,
                    "type": type,
                    "pageNo": pageNo,
                    "pageSize": pageSize
                };
                // 各tabs（年龄、性别、名族...）接口请求
                if(this.activeTab == 'nlAttList'){
                    api.get_rygk_nl(data).then(result => {
                        //json对象转化成json数组对象
                        let arrColumn = this.stringToArray(result);
                        // 生成表格
                        this.tableDataNl.data = result.data;
                        this.tableDataNl.column = arrColumn;
                        this.tableDataNl.totalRecord = result.totalRecord;
                    });                    
                }else if(this.activeTab == 'xbAttList'){
                    api.get_rygk_xb(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataXb.data = result.data;
                        this.tableDataXb.column = arrColumn;
                        this.tableDataXb.totalRecord = result.totalRecord
                    });
                }else if(this.activeTab == 'mzAttList'){
                    api.get_rygk_mz(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataMz.data = result.data;
                        this.tableDataMz.column = arrColumn;
                        this.tableDataMz.totalRecord = result.totalRecord
                    });
                }else if(this.activeTab == 'lyAttList'){
                    api.get_rygk_zw(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataZw.data = result.data;
                        this.tableDataZw.column = arrColumn;
                        this.tableDataZw.totalRecord = result.totalRecord
                    });
                }else if(this.activeTab == 'zwAttList'){
                    api.get_rygk_ly(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataLy.data = result.data;
                        this.tableDataLy.column = arrColumn;
                        this.tableDataLy.totalRecord = result.totalRecord
                    });
                }else if(this.activeTab == 'jxAttList'){
                    api.get_rygk_jx(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataJx.data = result.data;
                        this.tableDataJx.column = arrColumn;
                        this.tableDataJx.totalRecord = result.totalRecord
                    });
                }else if(this.activeTab == 'zzmmAttList'){
                    api.get_rygk_zzmm(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataZzmm.data = result.data;
                        this.tableDataZzmm.column = arrColumn;
                        this.tableDataZzmm.totalRecord = result.totalRecord
                    });
                }else if(this.activeTab == 'xlxwAttList'){
                    api.get_rygk_xlxw(data).then(result => {                     
                        let arrColumn = this.stringToArray(result);
                        this.tableDataXlxw.data = result.data;
                        this.tableDataXlxw.column = arrColumn;
                        this.tableDataXlxw.totalRecord = result.totalRecord
                    });
                }            
            }
        }
    }
</script>

<style scoped>
    .customSelect {
        position: absolute; 
        top: 17px; 
        left: 100px;
    }
    .left {
        float: left;
        width: 199px;
        height: 400px;
        overflow: auto;
        border-right: 1px solid #E8E8E8;
    }
    .listStyle li {
        width: 100%;
        height: 43px;
        line-height: 43px;
        text-indent: 50px;
        cursor: pointer;
    }
    .listStyle li.active {
        background-color: #1890FF;
        color: #ffffff;
    }
    .right {
        float: left;
        width: calc(100% - 200px);
        height: 400px;
        overflow: auto;
    }
    .el-pagination {
        margin-top: 5px;
    }
</style>