<template>
    <div class="SelectPower">        
        <el-row :gutter="20" class="selectDiv">
            <el-col :span="3">分配权限</el-col>
<!--            <el-col :span="8">-->
<!--                <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全部</el-checkbox>-->
<!--            </el-col>-->
        </el-row>
        <el-row :gutter="20">
            <el-col :span="8">
                <div class="selectLevel1" style="margin-top: 20px;margin-left: 20px;">
<!--                    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">-->
<!--                        <el-table-column label="一级菜单" prop="title">-->
<!--                            <template slot-scope="scope">-->
<!--                                <el-checkbox-group v-model="checkList" @change="handleCheckedPowerChange">-->
<!--                                    <el-checkbox :label="scope.row">{{scope.row.title}}</el-checkbox>-->
<!--                                </el-checkbox-group>-->
<!--                                &lt;!&ndash;                                                            <el-checkbox v-model="checkList">{{scope.row.title}}</el-checkbox>&ndash;&gt;-->
<!--                            </template>-->
<!--                        </el-table-column>-->
<!--                        <el-table-column label="二级菜单">-->
<!--                            <template  slot-scope="scope">-->
<!--                                &lt;!&ndash;                                                            <el-checkbox v-if="scope.row.subs" v-model="checkList">{{scope.row.subs.title}}</el-checkbox>&ndash;&gt;-->
<!--                                <el-checkbox-group  v-model="checkList" @change="handleCheckedPowerChange" >-->
<!--                                    <el-checkbox style="display: block;margin-right: 0px;margin-bottom: 10px;" :key="item.id" v-for="item in scope.row.subs" :label="item">{{item.title}}</el-checkbox>-->
<!--                                </el-checkbox-group>-->
<!--                            </template>-->
<!--                        </el-table-column>-->
<!--                        <el-table-column label="三级菜单">-->
<!--                            <template slot-scope="scope">-->
<!--                                <div v-for="item in scope.row.subs">-->
<!--                                    <el-checkbox-group  v-model="checkList" @change="handleCheckedPowerChange" >-->
<!--                                        <el-checkbox style="display: block;margin-right: 0px;margin-bottom: 10px;" :key="item2.id" v-for="item2 in item.subs"  :label="item2">{{item2.title}}</el-checkbox>-->
<!--                                    </el-checkbox-group>-->
<!--                                </div>-->

<!--                            </template>-->
<!--                        </el-table-column>-->
<!--                    </el-table>-->
                    <el-tree
                        :data="tableData"
                        show-checkbox
                        node-key="id"
                        ref="tree"
                        highlight-current
                        :props="defaultProps">
                    </el-tree>
                </div>
            </el-col>
        </el-row>
        <el-form :inline="true" :label-position="'right'" class="demo-form-inline" style="text-align: center;margin-top:20px">
            <el-form-item>
                <el-button type="primary" @click.native="Submit">完成</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import {communication} from "../../data/getAxios";

export default {
    name: 'SelectPower',
    data() {
        return {
            defaultProps: {
                children: 'subs',
                label: 'title'
            },
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            minHeight:0,
            checkList: [],//选中复选框
            checkList1:[],
            checkList2:[],
            checkList3:[],
            checkOptions: [],//所有复选框
            tableData: [
                {
                    level1: [{ 
                        id: 1,  
                        name: '警员信息'
                    }],
                    level2: [{ 
                        id: 2,
                        name: '我的档案'
                    },{ 
                        id: 3,
                        name: '信息管理'
                    }],
                    level3: []
                }, {
                    level1: [{ 
                        id: 4,  
                        name: '请假申请'
                    }],
                    level2: [{ 
                        id: 5,
                        name: '请假审批'
                    },{ 
                        id: 6,
                        name: '出国（境）申请'
                    }],
                    level3: []
                }, {
                    level1: [{ 
                        id: 7,  
                        name: '证件申报'
                    }],
                    level2: [{ 
                        id: 8,
                        name: '持枪证申报'
                    }],
                    level3: [{ 
                        id: 9,
                        name: '我的持枪证'
                    },{ 
                        id: 10,
                        name: '持枪证审批'
                    },{ 
                        id: 11,
                        name: '持枪证审批3'
                    },{ 
                        id: 12,
                        name: '持枪证审批4'
                    }]
                }
            ],
            checkAll: false,
            isIndeterminate: true,
            SelectedUerData: [],
        }
    },
    created() {
        this.init();
        this.tableData.map((table) => {
            const level1Ids = table.level1.map(level => level.id)
            const level2Ids = table.level2.map(level => level.id)
            const level3Ids = table.level3.map(level => level.id)
            this.checkOptions = [
                ...this.checkOptions,
                ...level1Ids,
                ...level2Ids,
                ...level3Ids,
            ];
        });
        console.log(this.checkOptions);
    },
    mounted(){
        $('.myCard').height($('.content').height() - 214 );
    },
    methods: {
        //提交
        Submit() {
            const obj = {
                url:this.global.serverPath8 + '/user-mgmt/adminSetting',
                postData:{
                    roleId:localStorage.getItem('roleId'),
                    userIds:JSON.parse(localStorage.getItem('SelectedUerData')),
                    deptIds:JSON.parse(localStorage.getItem('deptIds')),

                    permIds:this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())
                }
            }
            communication(obj).then((res)=>{
                if(res.code==0){
                    this.$router.push({ path: '/role'})
                }else if(res.code != 1){
                    this.$message.error(res.message)
                }

            })

        },
        inquiry(){
            var self = this;
            console.log(this.userForm.userName);
            self.$http.get(self.global.serverPath5+'/findLocByAnything', {//传递参数
                params: {
                    "userName":this.userForm.userName,
                    "characterID":this.userForm.characterID,
                    "workingCompany":this.userForm.workingCompany,
                    "loginName":this.userForm.loginName,
                }   
            }).then(function(res) {
                if(res.body.code == 0){
                    this.tableData = res.body.data
                }else{
                    self.$message.error('暂无数据')
                };
            }, function(res) {
                self.$message.error('请求失败')
            });
        },
        init(){
            const obj={
                url:this.global.serverPath8 + '/user-mgmt/menu/list',
                isGet:true

            }
            communication(obj).then((res)=>{
                this.tableData=res.data
            })
        },
        handleCheckAllChange(val) {
            this.checkList = val ? this.checkOptions : [];
            this.isIndeterminate = false;
        },
        getParent(data2, nodeId2) {
            var arrRes = [];
            if (data2.length == 0) {
                if (!!nodeId2) {
                    arrRes.unshift(data2)
                }
                console.log(arrRes)
                return arrRes;
            }
            let rev = (data, nodeId) => {
                for (var i = 0, length = data.length; i < length; i++) {
                    let node = data[i];
                    if (node.id == nodeId) {
                        arrRes.unshift(node)
                        rev(data2, node.parentId);
                        break;
                    } else {
                        if (!!node.subs) {
                            rev(node.subs, nodeId);
                        }
                    }
                }

                return arrRes;
            };
            arrRes = rev(data2, nodeId2);
            return arrRes;
        },
            handleCheckedPowerChange(value) {
            // this.getParent(this.tableData,value[value.length-1])
            // console.log(this.getParent(this.tableData,value[value.length-1]))
            // let checkedCount = value.length;
            // this.checkAll = checkedCount === this.checkOptions.length;
            // this.isIndeterminate = checkedCount > 0 && checkedCount < this.checkOptions.length;
        },
        handleCheckedPowerChange(value) {
            // this.getParent(this.tableData,value[value.length-1])
            // console.log(value[value.length-1].title)
            // this.getParent(this.tableData,value[value.length-1].id).forEach((item,index)=>{
            //     if(item.id!=value[value.length-1].id){
            //         this.checkList.push(value[value.length-1])
            //         this.flag=true
            //     }else{
            //
            //     }
            // })
            this.checkList=this.getParent(this.tableData,value[value.length-1].id)

            // console.log(this.checkList)
            // let checkedCount = value.length;
            // this.checkAll = checkedCount === this.checkOptions.length;
            // this.isIndeterminate = checkedCount > 0 && checkedCount < this.checkOptions.length;
        },
        //提交新增
        addSubmit() {
            var line = {
                userName: this.addForm.userName,
                loginName: this.addForm.loginName,
                workingCompany: this.addForm.workingCompany,
                department: this.addForm.department,
                characterID: this.addForm.characterID,
                phone: this.addForm.phone,
                info: this.addForm.info
            }
            // this.tableData.unshift(line)
            this.tableData.push(line)
            this.data_length = this.tableData.length
            this.addFormVisible = false;
        },
    },
    computed: {
        
    }
}

</script>
<style scoped>
    .addDepartBtn a{
        color:white
    }
    .card2 {
        margin: 0 20px;
    }

    .SelectUser .search{
        height: 40px;
        border-bottom: 1px solid #ddd;
        margin-left: 0;
        margin-right: 0;
    }

    .separateline{
        float: left;
        padding:0;
        width: 36px;
         /*background: url('../../../static/img/sq.png') no-repeat center;*/
        width: 12px;
        height: 60px;
    }
    .SelecteduserList{
        height: 30px;
        margin: 10px;
        margin-left: 0;
        text-align: left;
        font-size: 14px;
    }
</style>

