<template>
    <div class="mainadmin">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>管理员设置</el-breadcrumb-item>
                                        <el-breadcrumb-item>主管理员更换</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <!--<div class="titIcon">
                                    <span>主管理员更换</span>
                                </div>-->
                            </el-card>
                            <el-row :gutter="20">
                                <el-col :span="12" style="padding-right: 0">
                                    <el-card class="card2 myCard" v-bind:style="{ minHeight: minHeight+'px'}">
                                        <div class="currentAdmin">
                                            <el-row :gutter="20" class="search">
                                                <el-col :span="24" class="clearfix"><span>当前主管理员</span></el-col>
                                            </el-row>
                                            <el-row :gutter="20" style="margin-top:20px;">
                                                <el-col :span="20">
                                                    <el-form label-position="right" ref="Mainform" :model="Mainform" label-width="100px" class="demo-form-inline">
                                                        <el-form-item label="当前主管理员" prop="userName">
                                                            <el-input v-model="Mainform.userName" readonly="" required></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="登录名" prop="loginName">
                                                            <el-input v-model="Mainform.loginName" readonly="" required></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="身份证号" prop="characterID">
                                                            <el-input v-model="Mainform.characterID" readonly="" required></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="手机号" prop="phone">
                                                            <el-input v-model="Mainform.phone" readonly="" required></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="工作单位" prop="workingCompany">
                                                            <el-input v-model="Mainform.workingCompany" readonly="" required></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="部门" prop="department">
                                                            <el-input v-model="Mainform.department" readonly="" required></el-input>
                                                        </el-form-item>
                                                    </el-form>
                                                </el-col>
                                            </el-row>
                                        </div> 
                                    </el-card>
                                </el-col>
                                <el-col :span="12" v-bind:style="{ minHeight: minHeight+'px'}">
                                    <el-card class="card2 myCard">
                                        <div class="setAdmin">
                                            <el-row :gutter="20" class="search">
                                                <el-col :span="24" class="clearfix"><span>更换主管理员</span></el-col>
                                            </el-row>
                                            <el-row :gutter="20" style="margin-top:20px;">
                                                <el-col :span="20">
                                                    <el-form label-position="right" class="demo-form-inline" :model="Setform" label-width="120px" :rules="SetformRules" ref="Setform">
                                                        <el-form-item label="当前主管理员" prop="userName">
                                                            <el-input v-model="Setform.userName"></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="登录名" prop="loginName">
                                                            <el-input v-model="Setform.loginName"></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="身份证号" prop="characterID">
                                                            <el-input v-model="Setform.characterID"></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="手机号" prop="phone">
                                                            <el-input v-model="Setform.phone"></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="工作单位" prop="workingCompany">
                                                            <el-input v-model="Setform.workingCompany"></el-input>
                                                        </el-form-item>
                                                        <el-form-item label="部门" prop="department">
                                                            <el-input v-model="Setform.department"></el-input>
                                                        </el-form-item>
                                                    </el-form>
                                                </el-col>
                                            </el-row>
                                        </div>
                                    </el-card>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-form :inline="true" :label-position="'right'" :model="Form" ref="Form" class="demo-form-inline" style="text-align: center;margin-top:20px">
                <el-form-item>
                    <el-button @click="resetForm('Setform')">取消</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click.native="Submit">确定</el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
export default {
    name: 'user',
    data() {
        return {
            class1: 'el-icon-arrow-down el-icon--right',
            class2: 'el-icon-arrow-up el-icon--right',
            Mainform: {
            	userName: '李成名',
                loginName: '20182909',
                workingCompany: '南京市公安局',
                characterID: '320917288200020190',
                phone: '18778333902',
                department: '政治部'
            },
            Setform: {
                userName: '',
                loginName: '',
                workingCompany: '',
                characterID: '',
                phone: '',
                department: ''
            },
            SetformRules: {
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                loginName: [
                    { required: true, message: '请输入登录名', trigger: 'blur' }
                ],
                workingCompany: [
                    { required: true, message: '请输入单位名称', trigger: 'blur' }
                ],
                department: [
                    { required: true, message: '请输入部门', trigger: 'blur' }
                ],
                characterID: [
                    { required: true, message: '请输入身份证号码', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入手机', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
       this.init()
    },
    methods: {
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
            var self = this;
        },
        // 重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        //提交新增
        Submit() {
        },
    },
    computed: {
    }
}

</script>
<style scoped>
    .card2 {
        margin: 0 20px;
    }

    .mainadmin .search{
        height: 36px;
        border-bottom: 1px solid #d7d7d7;
        margin-left: 0;
        margin-right: 0;
    }
</style>

