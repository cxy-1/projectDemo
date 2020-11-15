<template>
    <div class="depart">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>角色管理</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row style="margin:0 25px;">
                <el-col  :span="24">
                    <el-container  class="special">
                        <el-aside width="300px" style="margin-right: 35px;">
                            <el-card style="height: 900px;overflow: scroll;height: 900px;">
                                <div slot="header" style="overflow: hidden;">
                                    <span>组织结构树</span>
                                </div>
<!--                                <el-tree ></el-tree>-->
                                <el-tree :highlight-current="true"  :props="props" :load="loadNode" lazy @node-click="handleNodeClick">
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <span>
                                            <i v-if="node.data.isUnit==1&&!node.expanded"><img src="static/img/wenjianclosed.png" alt=""></i>
                                            <i v-if="node.data.isUnit==1&&node.expanded"><img src="static/img/wenjianopened.png" alt=""></i>
                                            <i v-if="node.data.isUnit==0"  style="color: #178efe" class="el-icon-user-solid"></i>
                                            <span v-if="node.data.isUnit==1" :title="node.data.unitName">{{ node.data.unitName }}</span>
                                            <span v-if="node.data.isUnit==0" :title="node.data.roleName">{{ node.data.roleName }}</span>
                                        </span>
                                    </span>
                                </el-tree>
                            </el-card>

                        </el-aside>
                        <el-main v-if="!isData">
                            <el-card style="height: 900px;">
                                <div slot="header" class="clearfix">
                                    <span>角色管理功能介绍</span>
                                </div>
                                <p>
                                    欢迎使用角色管理
                                </p>
                            </el-card>
                        </el-main>
                        <el-main v-if="isData">

                            <el-card style="height: 900px;">
                                <div style="margin-bottom: 25px;">
                                    <div style="margin-bottom: 10px;">
                                        <el-button type="primary" @click="binduser" round>关联用户</el-button>
                                    </div>
                                    <div style="border: 1px solid rgb(235, 238, 245);">
                                        <el-row style="margin: 20px 0;" :gutter="20">
                                            <el-form ref="form" :model="form" label-width="80px">
                                                <el-col :span="8">
                                                    <el-form-item label="角色名称:">
                                                        <el-input v-model="form.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="描述:">
                                                        <el-input v-model="form.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="类型:">
                                                        <el-input v-model="form.type==0?'用户':'管理'"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-form>
                                        </el-row>
                                    </div>
                                </div>

                                <div class="special">
                                    <el-tabs type="card">
                                        <el-tab-pane label="已授权用户">
                                            <el-table
                                                ref="singleTable"
                                                :data="tableData"
                                                highlight-current-row
                                                style="width: 100%">
                                                <el-table-column
                                                    property="userName"
                                                    label="姓名"
                                                    width="100">
                                                </el-table-column>
                                                <el-table-column
                                                    property="sex"
                                                    label="性别"
                                                    width="100">
                                                </el-table-column>
                                                <el-table-column
                                                    property="policeNo"
                                                    label="警号"
                                                    width="120">
                                                </el-table-column>
                                                <el-table-column
                                                    property="unitName"
                                                    label="单位"
                                                   >
                                                   <template slot-scope="scope">
                                                    {{unitNameData(scope)}}
                                                   </template>
                                                </el-table-column>

                                                <el-table-column
                                                    property="idNumber"
                                                    label="身份证号">
                                                </el-table-column>
                                                <el-table-column label="操作" align="center" width="200">
                                                    <template slot-scope="scope">
                                                        <el-button  type="text" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                                                    </template>
                                                </el-table-column>
                                            </el-table>
                                            <div class="pagination">
                                                <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                                </el-pagination>
                                            </div>
                                        </el-tab-pane>
                                        <el-tab-pane label="包含资源">
                                            <el-tree
                                                :data="treeData"
                                                node-key="id"
                                                ref="tree"
                                                highlight-current
                                                :props="defaultProps">
                                            </el-tree>
                                        </el-tab-pane>
                                    </el-tabs>
                                </div>
                            </el-card>
                        </el-main>
                    </el-container>
                </el-col>
            </el-row>

        </div>
        <el-dialog
            title="分配用户"
            :visible.sync="binduserVisible"
            width="90%">
            <el-row style="margin-bottom: 20px;" :gutter="6">
                <Split ref="splitPanel" style="height: 500px"  @onDragEnd="onDragEnd" :gutterSize="9">
                        <SplitArea :size="30" class="splitarea">
                            <div class="box">
                                <!-- <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
                                    <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                    <el-option :value="valueTitle" :label="valueTitle">
                                        
                                    </el-option>
                                </el-select> -->
                                <el-input
                                    placeholder="输入关键字进行过滤"
                                    v-model="filterText">
                                </el-input>
                                <div class="tree-list">
                                    <el-tree id="tree-option" 
                                            v-loading="treeLoading"
                                            ref="reftree"
                                            :expand-on-click-node="false"
                                            :data="selectdata"
                                            :props="dataProps"
                                            node-key="unitId"
                                            :default-expanded-keys="['320000000000']"
                                            :highlight-current="true"
                                            @node-click="handleNodeClick2"
                                            check-on-click-node
                                            :filter-node-method="filterNode"

                                    >
                                <span class="custom-tree-node" slot-scope="{ node, data }">
                                    <span >{{ node.label }}</span>
                                    <span v-if="data.childCompany.length!=0">
                                        <el-button
                                            type="text"
                                            size="mini"
                                            @click.stop="() => append(data)">
                                            包含
                                        </el-button>
                                    </span>
                                </span>
                                    </el-tree>
                                </div>
                            </div>
                        </SplitArea>
                        <SplitArea :size="70" class="splitarea">                            
                            <div class="box">
                                <el-table
                                    v-loading="tableLoading"
                                    ref="singleTable"
                                    :data="userData"
                                    highlight-current-row
                                    style="width: 100%">
                                    <el-table-column  width="30">
                                        <template slot="header" slot-scope="scope">
                                            <el-checkbox v-model="checked" @change.native.stop="hahaa()">&nbsp;</el-checkbox>
                                            <!--                    <el-checkbox v-model="idarr"  @change.native.stop="hahaa()">&nbsp;</el-checkbox>-->
                                        </template>
                                        <template slot-scope="scope">
                                            <el-checkbox :disabled="disableddata.indexOf(scope.row.personId)!=-1" v-model="idarr" :label="scope.row.personId" @change.native.stop="haha(scope, scope.row)" :key="scope.personId">&nbsp;</el-checkbox>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        property="userName"
                                        label="姓名"
                                        width="100">
                                    </el-table-column>
                                    <el-table-column
                                        property="sex"
                                        label="性别"
                                        width="100">
                                    </el-table-column>
                                    <el-table-column
                                        property="policeNo"
                                        label="警号"
                                        width="120">
                                    </el-table-column>
                                    <el-table-column
                                        property="companyName"
                                        label="单位"
                                    >
                                    </el-table-column>

                                    <el-table-column
                                        property="idNumber"
                                        label="身份证号">
                                    </el-table-column>
                                </el-table>
                                <div class="pagination">
                                    <el-pagination @current-change="handleCurrentChange2" :current-page="cur_page2" :page-size="pagesize2" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="initbindData.length">
                                    </el-pagination>
                                </div>
                            </div>
                        </SplitArea>
                </Split>
                <el-col :span="8">
                    
<!--                    <SelectTree @listenToChild='getChildData' :selectdata="selectdata"></SelectTree>-->
                </el-col>

                <!-- <el-col :span="8">
                    <el-button type="primary" @click="inquiry()">查询</el-button>
                    <el-button @click="resetForm()">重置</el-button>
                </el-col> -->
            </el-row>
            
            <span slot="footer" class="dialog-footer">
                <el-button @click="binduserVisible = false">取 消</el-button>
                <el-button type="primary" @click="binduserSubmit">确 定</el-button>
            </span>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>
<style>
.splitarea {
    padding: 5px 5px;    
    height: 100%;
    overflow: auto;
    border: 1px solid rgb(235, 238, 245);
}
.splitarea .box {
    width: 100%;
}
.splitarea::-webkit-scrollbar-track {  
	-webkit-box-shadow: inset 0 0 6px transparent;  
	border-radius: 0;  
	background-color: transparent;  
}  
.gutter {
    /* background-color: transparent; */
    background-color: #ffffff;
    background-repeat: no-repeat;
}
</style>

<script>
    import {communication} from '../../data/getAxios';
    import SelectTree from './SelectTree.vue'
    export default {
        name: "passportList",
        components:{
            SelectTree
        },            
        data(){
            return {
                // heightEle: '300px',
                treeLoading: true,
                tableLoading: false,
                filterText: '',
                isData:false,
                tableData: [],
                form:{name:""},
                data: [{
                    label: '政治部',
                }, {
                    label: '一级 2',
                }, {
                    label: '一级 3',
                }],
                props: {
                    label: 'name',
                    children: 'zones',
                    isLeaf: 'leaf' 
                },
                defaultProps: {
                    children: 'subs',
                    label: 'title'
                },
                treeData:[],                
                cur_page:1,
                pagesize:10,
                inittableData:[],
                binduserVisible:false,
                unitId:null,
                userData:[],
                selectdata:[{
                id: 1,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{
                    id: 9,
                    label: '三级 1-1-1'
                    }, {
                    id: 10,
                    label: '三级 1-1-2'
                    }]
                }]
                }, {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
                }, {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id: 8,
                    label: '二级 3-2'
                }]
                }],
                arrexp:[1,2,3,4],
                dataProps:{
                    children: 'childCompany',
                    label: 'unitName'
                },
                valueTitle:"",
                aa:null,
                multipleSelection:[],
                initbindData:[],
                cur_page2:1,
                pagesize2:10,
                unitId2:null,
                checked:false,
                idarr:[],
                roId:null,
                roleId:null,
                dataId:null,
                remark:"",
                disableddata:[]
            }
        },
        created(){

        },
        mounted(){
            var that = this;
            setTimeout(function(){
                that.arrexp = [1,2,3]
            },8000)
        },
        watch: {
            filterText(val) {
                this.$refs.reftree.filter(val);
            }
        },
        methods: {
            unitNameData(scope){
                var value = scope.row.unitName;
                var newValue;
                if(value.indexOf("南京市公安局") == 0 && value != "南京市公安局"){
                    newValue = value.replace("南京市公安局","");
                }else {
                    newValue = value;
                }
                return newValue;
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.unitName.indexOf(value) !== -1;
            },
            getChildData (val) {
                console.log(`子组件传递过来的数据: ${val}`); // hello
            },
            handleCurrentChange(val){
                this.cur_page=val
                this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
            },
            loadNode(node, resolve) {
                // debugger
                if (node.level === 0) {
                    const obj = {
                        url:this.global.serverPath8+"/user-mgmt/orgmanage/headOrgPeoTree?personId="+localStorage.getItem("loginpersonId"),
                        // url:"http://192.168.8.141:8088/orgmanage/headOrgPeoTree?personId="+localStorage.getItem("loginpersonId"),
                        isGet:true
                    }
                    communication(obj).then((res)=>{
                        resolve(res.data)
                        // debugger
                    })

                }else{
                    const _obj = {
                        url:this.global.serverPath8 + "/user-mgmt/orgmanage/nodeOrgRoleTree?unitId="+node.data.unitId,
                        isGet:true
                    }
                    communication(_obj).then((res)=>{
                        const data = res.data
                        resolve(data);
                    })
                }
            },
            handleNodeClick(a,b,c){
                this.unitId=a.unitId
                this.roId=a.roId
                this.roleId=a.roleId
                this.dataId=a.dataId
                if(a.isUnit==0){
                    this.isData=true
                    const obj = {
                        url:this.global.serverPath8+"/user-mgmt/user/getBindUser?roId="+a.roId+"&roleId="+a.roleId,
                        isGet:true
                    }
                    communication(obj).then((res)=>{
                        this.inittableData=res.data;
                        this.cur_page=1
                        this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                    })
                    const __obj = {
                        url:this.global.serverPath8+"/user-mgmt/menu/listByRole?roleId="+a.roleId,
                        isGet:true
                    }
                    communication(__obj).then((res)=>{
                        this.treeData=res.data.bsPermissionList
                    })
                    const _obj = {
                        url:this.global.serverPath8+"/user-mgmt/role/getRoleDetail?roleId="+a.roleId,
                        isGet:true
                    }
                    communication(_obj).then((res)=> {
                        this.form = res.data
                        this.remark=res.data.remark
                    })
                }

            },         
            binduser(){
                this.treeLoading = true;
                this.binduserVisible=true
                this.multipleSelection=[]
                this.idarr=[]
                this.checked=false
                this.valueTitle = ""
                this.unitId2=null
                this.selectdata=[];
                this.initbindData=[]
                this.userData=[]

                const _obj = {
                    url:this.global.serverPath8 + "/user-mgmt/orgmanage/unittreeAuto",
                    isGet:true
                }
                communication(_obj).then((res)=>{
                    this.selectdata=res.data;
                    this.treeLoading = false;
                })

            },
            binduserSubmit(){
                console.log(this.multipleSelection)
                this.binduserVisible=false
                var arr =[]
                this.disableddata.forEach((item,index)=>{
                    this.multipleSelection.forEach((value,index2)=>{
                        if(item==value.personId){
                            this.multipleSelection.splice(index2,1)

                        }
                    })
                })

                this.multipleSelection.forEach((item,index)=>{
                    var dataobject = {}
                    dataobject.personId=item.personId
                    dataobject.roId=this.roId
                    dataobject.roleId=this.roleId
                    dataobject.dataId=this.dataId
                    dataobject.remark=this.remark
                    arr.push(dataobject)
                })
                const obj = {
                    // url:"http://192.168.8.147:8088/user/buildingRole",
                    url:this.global.serverPath8+"/user-mgmt/user/bindRole",
                    postData:{
                        userRoleKeys:arr
                    }
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.$message.success("操作成功")
                        const obj = {
                            url:this.global.serverPath8+"/user-mgmt/user/getBindUser?roId="+this.roId+"&roleId="+this.roleId,
                            isGet:true
                        }
                        communication(obj).then((res)=>{
                            this.inittableData=res.data;
                            this.cur_page=1;
                            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                        })
                    }
                })

            },
            append(data) {
                console.log(data)
                // this.flag=false
                this.valueTitle = data.unitName+"(包含子部门)"
                this.unitId2=data.dataId
                this.inquiry()

            },
            // filterNode(value, data) {
            //     console.log(data)
            //     if (!value) return true;
            //     console.log(data)
            //     return data.label.indexOf(value) !== -1;
            // },
            handleNodeClick2(node){
                console.log(node)
                this.valueTitle = node.unitName
                this.unitId2=node.unitId
                this.inquiry();
            },
            // 清除选中
            clearHandle(){
                this.valueTitle = ''
                this.unitId2=null
                // this.valueId = null
                // this.defaultExpandedKey = []
                // this.clearSelected()
            },
            handleCurrentChange2(val){
                this.cur_page2=val
                this.userData=this.initbindData.slice((this.cur_page2-1)*this.pagesize2,(this.cur_page2)*this.pagesize2)
            },
            inquiry(){
                // this.clearHandle();
                this.tableLoading = true;
                this.multipleSelection=[]
                this.idarr=[]
                this.checked=false
                this.disableddata=[]

                const obj = {
                    url:this.global.serverPath8 + "/user-mgmt/user/getUnbindUser?unitId="+this.unitId2,
                    isGet:true
                }
                communication(obj).then((res)=>{
                    this.initbindData=res.data;
                    this.inittableData.forEach((item)=>{
                        this.initbindData.forEach((value)=>{
                            if(item.personId==value.personId){
                                this.idarr.push(value.personId)
                                this.multipleSelection.push(value)
                                this.disableddata.push(value.personId)
                            }
                        })
                    })
                    this.tableLoading = false;
                    this.cur_page2=1;
                    this.userData=this.initbindData.slice((this.cur_page2-1)*this.pagesize2,(this.cur_page2)*this.pagesize2);
                })
            },
            resetForm(){
                this.multipleSelection=[]
                this.idarr=[]
                this.checked=false
                this.valueTitle = ""
                this.unitId2=null
            },
            hahaa(v){
                if(this.checked){
                    this.idarr=[]
                    this.multipleSelection=this.initbindData;
                    this.multipleSelection.map((c,i)=>{
                        if(isNaN(c)){
                            this.idarr.push(c.personId)
                        }

                    })
                }else{
                    this.idarr=[];
                    this.multipleSelection=[]
                }
            },
            haha(v,k){
                this.idarr=[]
                var hasRow = false
                var newArr = []
                var _this = this
                this.multipleSelection.map((c,i) =>{
                    if(c.personId==k.personId){
                        hasRow = true
                    }else{
                        newArr.push(c)
                    }
                })
                if(hasRow){
                    this.multipleSelection = newArr
                }else{
                    this.multipleSelection =  this.multipleSelection.concat(k)
                }
                this.multipleSelection.map((c,i)=>{
                    if(!isNaN(c)){
                        this.multipleSelection.splice(i,1)
                    }else{
                        this.idarr.push(c.personId)
                    }
                })

                if(this.multipleSelection.length!=this.initbindData.length){
                    this.checked=false
                }else{
                    this.checked=true
                }
            },
            handleDel(index, row){
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRow(row.ruId)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            deleteRow(id) {
                const obj = {
                    url:this.global.serverPath8+"/user-mgmt/user/unBindRole?ruId="+id,
                    // url:"http://192.168.8.147:8088/user/delRole?ruId="+id,
                    isGet:true
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.$message.success("删除成功");
                        const obj = {
                            url:this.global.serverPath8+"/user-mgmt/user/getBindUser?roId="+this.roId+"&roleId="+this.roleId,
                            isGet:true
                        }
                        communication(obj).then((res)=>{
                            this.inittableData=res.data;
                            this.cur_page=1
                            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                        })

                    }else if(res.code != 1){
                        this.$message.error("系统错误")
                    }
                })
            },
            onDragEnd(size){
                console.log(size)
            }
        }
    }
</script>

<style scoped>
    .special .el-main{
        padding: 0;
    }
    .el-tabs__header,.is-top {
        margin-bottom: 0px!important;
    }
    .el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
        /*height: auto;*/
        min-height: 200px;
        max-height: 274px;
        padding: 0;
        /*overflow: hidden;*/
        overflow-y: scroll;
    }


    .el-select-dropdown__item.selected {
        font-weight: normal;
    }

    .el-select-dropdown__item.hover, .el-select-dropdown__item:hover {
        background: #ffffff;
    }
    .el-select-dropdown__list {
        list-style: none;
        /* padding: 6px 0; */
        margin: 0;
        -webkit-box-sizing: content-box;
        box-sizing: content-box;
    }
</style>
<style>

</style>
