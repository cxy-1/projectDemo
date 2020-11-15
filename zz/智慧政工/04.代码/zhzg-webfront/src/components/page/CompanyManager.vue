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
                                        <el-breadcrumb-item>单位管理</el-breadcrumb-item>
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
                        <el-aside width="250px" style="margin-right: 35px;">
                            <el-card style="height: 600px;overflow: scroll">
                                <div slot="header" style="overflow: hidden;">
                                    <span>组织结构树</span>
                                </div>
                                <!--                                <el-tree ></el-tree>-->
                                <el-tree :highlight-current="true" :data="data" :props="defaultProps" @node-click="handleNodeClick">
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <span>
                                            <i><img src="static/img/fenzhi.png" alt=""></i>
                                            {{node.label}}
                                        </span>
                                    </span>
                                </el-tree>
                            </el-card>

                        </el-aside>
                        <el-main v-if="!isData">
                            <el-card style="height: 600px;">
                                <div slot="header" class="clearfix">
                                    <span>单位管理功能介绍</span>
                                </div>
                                <p>
                                    欢迎使用单位管理
                                </p>
                            </el-card>
                        </el-main>
                        <el-main v-if="isData">
                            <el-card style="height: 600px;">
                                <div slot="header" style="overflow: hidden">
                                    <span>单位包含角色</span>
<!--                                    <el-button @click="wanghao" style="float: right; padding: 3px 0" type="text">王浩</el-button>-->
                                </div>
                                <div class="special">
                                    <el-table
                                        ref="singleTable"
                                        :data="tableData"
                                        highlight-current-row
                                        style="width: 100%">
                                        <el-table-column
                                            property="name"
                                            label="名称"
                                        >
                                        </el-table-column>
                                        <el-table-column
                                            property="remark"
                                            label="描述"
                                        >
                                        </el-table-column>
                                        <el-table-column
                                            property="address"
                                            width="150"
                                            label="类型">
                                            <template slot-scope="scope">
                                                <span v-if="scope.row.type==0">用户</span>
                                                <span v-if="scope.row.type==1">管理</span>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="操作" align="center" width="150">
                                            <template slot-scope="scope">
                                                <el-switch @change="switchChange(scope.row,scope.row.isBind)" v-model="scope.row.isBind"  :active-value="acValue" inactive-text="解绑" :inactive-value="inacValue" active-text="绑定" ></el-switch>
                                            </template>

                                        </el-table-column>
                                    </el-table>
                                    <div class="pagination">
                                        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                        </el-pagination>
                                    </div>
                                </div>
                            </el-card>
                        </el-main>
                    </el-container>
                </el-col>
            </el-row>

        </div>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
    export default {
        name: "passportList",
        data(){
            return {
                isData:false,
                tableData: [],
                form:{name:""},
                data: [],
                props: {
                    label: 'name',
                    children: 'zones',
                    isLeaf: 'leaf'
                },
                defaultProps: {
                    children: 'childCompany',
                    label: 'unitName'
                },
                cur_page:1,
                pagesize:10,
                inittableData:[],
                acValue:"1",
                inacValue:"0",
                unitId:null,
                dataId:null
            }
        },
        created(){
            this.init()
        },
        mounted(){

        },
        methods: {
            handleCurrentChange(val){
                this.cur_page=val
                this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
            },
            loadNode(node, resolve) {
                if (node.level === 0) {
                    return resolve([{ name: 'region' }]);
                }
                if (node.level > 1) return resolve([]);

                setTimeout(() => {
                    const data = [{
                        name: 'leaf',
                        leaf: true
                    }, {
                        name: 'zone'
                    }];

                    resolve(data);
                }, 500);
            },
            handleNodeClick(a,b,c){
                this.isData=true
                this.unitId=a.unitId
                this.dataId=a.dataId
                const obj = {
                    url:this.global.serverPath8+"/user-mgmt/role/getBindStatus?dataId="+this.dataId,
                    isGet:true
                }
                communication(obj).then((res)=>{
                    this.inittableData=res.data;
                    this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize);
                })

            },

            init(){
                const obj = {

                    url:this.global.serverPath8+"/user-mgmt/orgmanage/admintree",
                    postData:{}
                }
                communication(obj).then((res)=>{
                    this.data=res.data;
                })
            },
            wanghao(){
                var arr = []
                this.inittableData.forEach((item,index)=>{
                    var obj = {}
                    if(item.remark!="市局角色"){
                        obj.roleId=item.roleId
                        obj.unitId=this.unitId
                        obj.dataId=this.dataId
                        arr.push(obj)
                    }
                })

                var _obj={
                    url:this.global.serverPath8+"/user-mgmt/role/bindRole",
                    postData:{
                        roleOrgKeys:arr
                    }
                }
                communication(_obj).then((res) => {
                    if(res.code==0){
                        this.$message.success("操作成功")
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })

            },
            switchChange(id,state){
                if(state==0){
                    var _obj={
                        url:this.global.serverPath8+"/user-mgmt/role/unBindRole",
                        postData:{
                            roIds:[id.roId]
                        }
                    };
                }else{
                    var _obj={
                        url:this.global.serverPath8+"/user-mgmt/role/bindRole",
                        postData:{
                            roleOrgKeys:[{roleId:id.roleId, unitId:this.unitId, dataId:this.dataId}]
                        }
                    }
                    // postdate=[{roleId:id, unitId:this.unitId, dataId:this.dataId}]
                }

                communication(_obj).then((res) => {
                    if(res.code==0){
                        this.$message.success("操作成功")
                        const obj = {
                            url:this.global.serverPath8+"/user-mgmt/role/getBindStatus?dataId="+this.dataId,
                            isGet:true
                        }
                        communication(obj).then((res)=>{
                            this.inittableData=res.data
                            this.cur_page=1
                            this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                        })
                    }
                }).catch((err) => {
                    console.log('请求失败！')
                })
            },
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
</style>
<style>

</style>
