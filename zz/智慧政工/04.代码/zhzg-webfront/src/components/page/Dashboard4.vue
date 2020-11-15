<template>
    <div class="cjgl Dashboard4">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）审批</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="sqxx-info4">
                                    <!--申请信息表单  -->
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="80px" ref='searchList' :model="searchList" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="userName">
                                                        <el-input v-model="searchList.userName"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <!-- <el-col :span="8">
                                                    <el-form-item label="警号：" prop="userNo">
                                                        <el-input v-model="searchList.userNo"></el-input>
                                                    </el-form-item>
                                                </el-col> -->
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="工作单位：" prop="companyName">
                                                        <el-input v-model="searchList.companyName"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <!-- <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="申请单号："  prop="formId">
                                                        <el-input v-model="searchList.formId"></el-input>
                                                    </el-form-item>
                                                </el-col> -->
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="离境形式：" prop="departureForm">
                                                        <el-select v-model="searchList.departureForm" readonly="" @change="changeStyle()">
                                                            <el-option key="跟团" label="跟团" value="跟团"></el-option>
                                                            <el-option key="自由行" label="自由行" value="自由行"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="离境原因：" prop="reason">
                                                        <el-select v-model="searchList.reason" readonly="">
                                                            <el-option key="旅游" label="旅游" value="旅游"></el-option>
                                                            <el-option key="探亲" label="探亲" value="探亲"></el-option>
                                                            <el-option key="看病" label="看病" value="看病"></el-option>
                                                            <el-option key="子女入学毕业" label="子女入学毕业" value="子女入学毕业"></el-option>
                                                            <el-option key="其他" label="其他" value="其他"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item>
                                                        <el-button type="primary" @click = "inquiry()">查询</el-button>
                                                        <el-button @click="resetForm('searchList')">重置</el-button>
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
                                                            <i :class="toggleShow?class1:class2"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <!--<el-row>
                                                <el-col>
                                                    <el-form-item>
                                                        <el-button type="primary" icon="el-icon-upload2" @click="export2Excel">导出</el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>-->
                                        </el-form>
                                    </div>
                                    <!--申请信息表格  -->
                                    <div class="sqxx-table4">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" @selection-change="handleSelectionChange">
                                            <el-table-column type="selection" width="55">
                                            </el-table-column>
                                            <el-table-column v-if="tableType" prop="id" label="申请单号" width="160">
                                            </el-table-column>
                                            <el-table-column prop="applicantName" label="姓名" width="70">
                                            </el-table-column>
                                            <el-table-column prop="companyName" label="工作单位">
                                            </el-table-column>
                                            <el-table-column prop="departName" label="部门">
                                            </el-table-column> 
                                            <el-table-column prop="departureReason" label="离境原因">
                                                <template slot-scope="scope">
                                                    <span v-if="scope.row.departureReason=='旅游'">旅游</span>
                                                    <span v-if="scope.row.departureReason=='探亲'">探亲</span>
                                                    <span v-if="scope.row.departureReason=='看病'">看病</span>
                                                    <span v-if="scope.row.departureReason=='子女入学毕业'">子女入学毕业</span>
                                                    <span v-if="scope.row.departureReason=='其他'">其他</span>
                                                    <span v-if="scope.row.departureReason=='参会'">参会</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="departureForm" label="离境形式">
                                                <template slot-scope="scope">
                                                    <span v-if="scope.row.departureForm=='跟团'">跟团</span>
                                                    <span v-if="scope.row.departureForm=='自由行'">自由行</span>
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="applicantTime" label="申请时间">
                                            </el-table-column>
                                            <el-table-column label="操作" align="center" width="150">
                                                <template slot-scope="scope">
                                                    <el-button v-show="scope.row.isTerminate != 0?false:true" type="text"><router-link :to="{path:'/Dashboard3',query:{fid:scope.row.fid,workId:scope.row.workId,alarmNo:scope.row.id,btnTypeNum:1}}">审批</router-link></el-button>
                                                    <el-button type="text"><router-link :to="{path:'/Dashboard3',query:{workId:scope.row.workId,alarmNo:scope.row.id,btnTypeNum:0}}">详情</router-link></el-button>
                                                    <el-button v-show="scope.row.isTerminate == 0?false:true" type="text"><router-link :to="{path:'/Dashboard5',query:{fid:scope.row.fid,workId:scope.row.workId,alarmNo:scope.row.id,btnTypeNum:2}}">销假</router-link></el-button>  
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false">
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
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import { communication } from "../../data/getAxios";
export default {
  name: "dashboard4",
  data() {
    return {
      tableType: false,
      stepActive: 2,
      showLxs: false, //显示旅行社
      toggleShow: true,
      pageShow1: true,
      pageShow2: false,
      cur_page: 1, //表格当前页
      cur_page2: 1,
      pagesize: 10, //表格分页条数
      pagesize2: 10,
      data_length: 0,
      data_length2: 0,
      inittableData: [],
      multipleSelection: [], //多选数组当前选中要删除的项
      class1: "el-icon-arrow-up el-icon--right",
      class2: "el-icon-arrow-down el-icon--right",
      searchList: {
        userName: "",
        userNo: "",
        companyName: "",
        formId: "",
        departureForm: "",
        reason: ""
      },
      idx: -1,
      tableData: []
    };
  },
  created() {
    this.getTabData();
  },
  methods: {
    inquiry() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/getGoAbroadQuery",
        isGet: false,
        postData: {
          userName: self.searchList.userName,
          deptName: self.searchList.companyName,
          departureForm: self.searchList.departureForm,
          departureReason: self.searchList.reason
        }
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.tableData = res.data;
            self.inittableData = res.data;
            self.tableData = self.inittableData.slice(
              (self.cur_page - 1) * self.pagesize,
              self.cur_page * self.pagesize
            );
          } else if (res.code != 1) {
            self.$message.warning(res.message);
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 表格分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.tableData = this.inittableData.slice(
        (this.cur_page - 1) * this.pagesize,
        this.cur_page * this.pagesize
      );
    },
    // 查询分页导航
    handleCurrentChange2(val) {
      this.cur_page2 = val;
    },
    getTabData() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/todolist?type=cgj",
        isGet: true
      };
      communication(_obj)
        .then(res => {
          console.log(res);
          if (res.code == 0) {
            self.tableData = res.data;
            self.inittableData = res.data;
            self.tableData = self.inittableData.slice(
              (self.cur_page - 1) * self.pagesize,
              self.cur_page * self.pagesize
            );
          } else if (res.code != 1) {
            self.$message.warning(res.message);
          }
        })
        .catch(err => {
          console.log("获取个人信息失败！");
        });
    },
    // 导出excel
    export2Excel() {
      //兼容ie10
      var that = this;
      require.ensure([], () => {
        const { export_json_to_excel } = require("../../vendor/Export2Excel"); //引入文件
        const tHeader = [
          "申请单号",
          "姓名",
          "工作单位",
          "部门",
          "离境原因",
          "离境形式",
          "申请时间"
        ]; //对应表格输出的title
        const filterVal = [
          "applicationFormId",
          "applicationUserName",
          "workUnits",
          "departName",
          "departureReason",
          "departureForm",
          "applicationTime"
        ]; // 对应表格输出的数据
        const list = that.tableData;
        const data = that.formatJson(filterVal, list);
        export_json_to_excel(tHeader, data, "申请列表excel"); //对应下载文件的名字
      });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]));
    },
    // 收起展开
    upDown() {
      this.toggleShow = !this.toggleShow;
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.getTabData();
    }
  },
  computed: {
    // role() {
    //     return this.name === 'admin' ? '超级管理员' : '普通用户';
    // }
  }
};
</script>
<style scoped>
.Dashboard2 .el-dialog {
  background: url("../../../static/img/index/scoreBg.png") no-repeat center;
  background-size: 100% 100%;
  height: 460px;
  -webkit-box-shadow: none !important;
  box-shadow: none !important;
}
.Dashboard2 .el-dialog__headerbtn .el-dialog__close {
  color: #1890ff;
  font-size: 22px;
  font-weight: 900;
  position: relative;
  top: 23px;
  right: 23px;
}
.Dashboard2 .el-dialog__body .el-textarea__inner {
  height: 100px !important;
}
.Dashboard2 .el-dialog__body .el-row {
  margin-bottom: 10px;
}
.Dashboard2 .el-dialog__body {
  padding-bottom: 10px !important;
}
.Dashboard2 .el-dialog__footer {
  text-align: center;
}
.Dashboard2 .el-dialog__body .el-row .rateSpan {
  display: inline-block;
  width: 100px;
  text-align: left;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}
.inputPadding .el-form--label-top .el-form-item__label {
  padding: 0 0 4px;
}
.pagination2 {
  height: 32px;
  margin-bottom: 10px;
  margin-top: 10px;
}
.pagination2 .el-pagination {
  position: absolute;
  right: 30px;
  bottom: 10px;
}

.icon-world {
  display: inline-block;
  width: 14px;
  height: 16px;
  position: relative;
  top: 3px;
  background: url("../../../static/img/file-word.png") no-repeat center;
  background-size: 100% 100%;
}

.el-upload__tip {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #ff0000;
  letter-spacing: 0;
}

.sq-icon {
  background: url("../../../static/img/sq.png") no-repeat center;
  background-size: 100% 100%;
}

.card2 {
  margin: 0 20px;
}

.Dashboard4 .user-info {
  padding: 0 7%;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.65);
}

.Dashboard4 .user-info p {
  margin: 10px 0;
}

.el-row {
  margin-bottom: 20px;
}

.mgb20 {
  margin-bottom: 16px;
}
</style>
<style>
.pagination2 .el-input--small .el-input__inner {
  width: 45px !important;
}
.pagination .el-input--small .el-input__inner {
  width: 45px !important;
}
.sqxx-table4 .el-table th > .cell {
  font-family: PingFangSC-Medium;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.sqxx-table4 .el-table td div {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.sqxx-info4 .el-button--text {
  font-size: 14px;
}

.sqxx-info4 .el-input--small .el-input__inner {
  width: 220px;
}

.sqxx-table .el-table td div {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.sqxx-info4 .el-row[data-v-6aef4342] {
  margin-bottom: 0;
}

.sqxx-info4 .el-row[data-v-6ad31440] {
  margin-bottom: 0px;
}

.sqxx-info4 .el-form-item__label {
  padding: 0;
}
</style>
<style>
.handle-box3 .el-select {
  width: 98%;
}
</style>
