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
                                        <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
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
                            <el-card style="height: 900px;">
                                <div slot="header" style="overflow: hidden;">
                                    <span>菜单结构树</span>
                                </div>

                                <el-tree
                                    :data="treeData"
                                    node-key="id"
                                    ref="tree"
                                    @node-click="handleNodeClick"
                                    :highlight-current="true"
                                    :props="defaultProps">
                                </el-tree>

                            </el-card>

                        </el-aside>
                        <el-main >
                            <el-card style="height: 900px;">
                                <div style="margin-bottom: 25px;">
                                    <div style="border: 1px solid rgb(235, 238, 245);">
                                        <el-row style="margin: 20px 0;" :gutter="20">
                                            <el-form ref="form" :model="form" label-width="80px">
                                                <el-col :span="24">
                                                    <el-form-item label="菜单名称:">
                                                        <el-input readonly v-model="form.title"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-form-item label="菜单路径:">
                                                        <el-input readonly v-model="form.index"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24">

                                                    <el-form-item label="菜单图标:">
                                                        <i :class="form.icon"></i>

                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-form-item label="外链地址:">
                                                        <el-input readonly v-model="form.links"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-form>
                                        </el-row>
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
                treeData:[],
                tableData: [],
                form:{
                    title:"",
                    index:"",
                    icon:"",
                    links:""

                },
                // data: [{
                //     label: '政治部',
                //     children: [{
                //         label: '二级 1-1',
                //         children: [{
                //             label: '三级 1-1-1'
                //         }]
                //     }]
                // }, {
                //     label: '一级 2',
                //     children: [{
                //         label: '二级 2-1',
                //         children: [{
                //             label: '三级 2-1-1'
                //         }]
                //     }, {
                //         label: '二级 2-2',
                //         children: [{
                //             label: '三级 2-2-1'
                //         }]
                //     }]
                // }, {
                //     label: '一级 3',
                //     children: [{
                //         label: '二级 3-1',
                //         children: [{
                //             label: '三级 3-1-1'
                //         }]
                //     }, {
                //         label: '二级 3-2',
                //         children: [{
                //             label: '三级 3-2-1'
                //         }]
                //     }]
                // }],
                // props: {
                //     label: 'name',
                //     children: 'zones',
                //     isLeaf: 'leaf'
                // },
                defaultProps: {
                    children: 'subs',
                    label: 'title'
                },
                cur_page:1,
                pagesize:10,
            }
        },
        created(){
            this.init()
        },
        mounted(){

        },
        methods: {
            init(){
                const __obj = {
                    url:this.global.serverPath8+"/user-mgmt/menu/list?personId="+localStorage.getItem("loginpersonId"),
                    isGet:true
                }
                communication(__obj).then((res)=>{
                    this.treeData=res.data;
                    this.$forceUpdate();
                })
            },
            handleCurrentChange(){

            },
            loadNode(node, resolve) {
                if (node.level === 0) {
                    return resolve([{ name: '南京市公安局',deptId:"000000000000", children: [{
                            label: '二级 1-1',
                            children: [{
                                label: '三级 1-1-1'
                            }]
                        }]}]);
                };
                if(node.level == 1){
                    setTimeout(() => {
                        const data = [{
                            name: 'leaf',
                            leaf: true
                        }, {
                            name: 'zone',
                        }];

                        resolve(data);
                    }, 500);
                }else if(node.level == 2){
                    resolve([])
                }
            },
            handleNodeClick(a,b,c){
                this.form=a;
            },
            handleDel(index, row){
                // this.roleName=row.roleName;//传入角色名称
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRow(row.id)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            deleteRow(id) {
                const obj = {
                    url:this.global.serverPath8 + "/user-mgmt/delUser?userId="+id,
                    isGet:true
                }
                communication(obj).then((res)=>{
                    if(res.code==0){
                        this.$message.success("删除成功");
                    }else if(res.code != 1){
                        this.$message.error("系统错误")
                    }
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
