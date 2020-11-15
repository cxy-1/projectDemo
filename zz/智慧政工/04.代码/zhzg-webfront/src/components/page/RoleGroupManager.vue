<template>
    <div>
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>角色组管理</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div slot="header" class="clearfix" style="overflow: hidden">
                                    <span>角色组管理</span>
                                </div>
                                <div class="user-info">
                                    <div class="">
<!--                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />-->
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
                                                label="类型">
                                                <template slot-scope="scope">
                                                    <span v-if="scope.row.type==0">用户</span>
                                                    <span v-if="scope.row.type==1">管理</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column label="操作" align="center" width="250">
                                                <template slot-scope="scope">
                                                    <el-button type="text" @click="detailmenu(scope.row.roleId)">资源详情</el-button>
                                                    <el-button type="text" @click="updatemenu(scope.row.roleId)">修改资源</el-button>
                                                </template>

                                            </el-table-column>

                                        </el-table>
                                        <el-dialog
                                            title="资源详情"
                                            :visible.sync="detailVisible"
                                            width="600px"
                                        >
                                            <el-tree
                                                :data="treeData"
                                                node-key="id"
                                                ref="tree"
                                                highlight-current
                                                :close-on-click-modal="false"
                                                :props="defaultProps">
                                            </el-tree>
                                            <span slot="footer" class="dialog-footer">
                                                    <el-button @click="detailVisible = false">取 消</el-button>
                                                    <el-button type="primary" @click="detailVisible = false">确 定</el-button>
                                                </span>
                                        </el-dialog>
                                        <el-dialog
                                            title="修改资源"
                                            :visible.sync="updateVisible"
                                            width="600px"
                                        >
                                            <el-tree
                                                :data="treeData"
                                                node-key="id"
                                                ref="tree2"
                                                show-checkbox
                                                :default-checked-keys="checkedKeys"
                                                highlight-current
                                                :close-on-click-modal="false"
                                                :check-strictly="checkStrictly"
                                                :props="defaultProps">
                                            </el-tree>
                                            <span slot="footer" class="dialog-footer">
                                                    <el-button @click="updateVisible = false">取 消</el-button>
                                                    <el-button type="primary" @click="updateSubmit">确 定</el-button>
                                                </span>
                                        </el-dialog>
                                        <div class="pagination">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
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
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
    import {communication} from '../../data/getAxios';
    export default {
        name: "RoleGroupManager",
        data(){
            return{
                tableData: [],
                inittableData:[],
                acValue:"1",
                inacValue:"0",
                cur_page: 1,//表格当前页
                pagesize:10,//表格分页条数
                detailVisible:false,
                updateVisible:false,
                treeData:[],
                defaultProps: {
                    children: 'subs',
                    label: 'title'
                },
                permIdList:[],
                roleId:null,
                checkedKeys:[],
                demoData: [],
                leftdemoData: [],
                checkStrictly: true
            }
        },
        created(){
            this.init()
        },
        methods:{
            init(){
                const obj = {
                    url:this.global.serverPath8+"/user-mgmt/role/getBindStatus?dataId=32",
                    isGet:true
                }
                communication(obj).then((res)=>{
                    this.inittableData=res.data
                    this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
                })
            },
            handleCurrentChange(val){
                this.cur_page=val
                this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
            },
            detailmenu(roleId){
                this.treeData=[]
                this.detailVisible=true
                const obj = {
                    url:this.global.serverPath8+"/user-mgmt/menu/listByRole?roleId="+roleId,
                    isGet:true
                }
                communication(obj).then((res)=>{
                    this.treeData=res.data.bsPermissionList
                })
            },
            updatemenu(roleId){
                this.checkStrictly = true;  
                this.roleId=roleId
                this.treeData=[]
                this.permIdList=[]
                this.checkedKeys=[]
                this.updateVisible=true
                const obj = {
                    url:this.global.serverPath8+"/user-mgmt/menu/list?personId="+localStorage.getItem("loginpersonId"),
                    isGet:true
                }
                communication(obj).then((res)=>{
                    this.treeData=res.data
                    const _obj = {
                        url:this.global.serverPath8+"/user-mgmt/menu/listByRole?roleId="+roleId,
                        isGet:true
                    }
                    communication(_obj).then((data)=>{
                        this.permIdList=data.data.permIdList
                        var newArr = [];
                        this.permIdList.forEach(item=>{
                            this.checked(item,this.treeData,newArr)
                        })
                        this.checkedKeys = this.permIdList;
                        this.demoData = newArr;    
                        this.checkStrictly  = false;  
                    })
                })
            },
            checked(id,data,newArr){
                data.forEach(item => {
                    if(item.id == id){
                        if(item.subs!=undefined&&item.subs.length == 0 ){
                            newArr.push(item.id)
                        }
                    }else{
                        if(item.subs!=undefined&&item.subs.length !=0 ){
                            this.checked(id,item.subs,newArr)
                        }
                    }
                });
            },

            updateSubmit(){

                // this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())

                const _obj = {
                    url:this.global.serverPath8+"/user-mgmt/role/updateMenu",
                    postData:{
                        roleId:this.roleId,
                        permIds:this.$refs.tree2.getCheckedKeys().concat(this.$refs.tree2.getHalfCheckedKeys())
                    }
                }
                this.leftdemoData = this.checkedKeys;
                communication(_obj).then((data)=>{
                    if(data.code==0){
                        this.$message.success("操作成功")
                        this.updateVisible=false
                    }else{
                        this.$message.error("系统错误")
                        this.updateVisible=false
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>
