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
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）查询列表</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div class="queryForm">
                                    <div class="handle-box4">
                                        <el-form :inline="true" :label-position="'right'" label-width="110px" ref='queryForm' :model="queryForm" class="demo-form-inline">
                                            <el-row :gutter="6">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="name">
                                                        <el-input v-model="queryForm.name"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号：" prop="policeNum">
                                                        <el-input v-model="queryForm.policeNum"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="工作单位：" prop="workUnit">
                                                        <!--<el-input v-model="queryForm.workUnit"></el-input>-->
                                                        <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"  @clear="clearHandle">
                                                            <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                                            <el-option :value="valueTitle" :label="valueTitle">
                                                                <div class="tree-list">
                                                                    <el-tree id="tree-option"
                                                                            ref="selectTree"
                                                                            :expand-on-click-node="false"
                                                                            :data="selectdata"
                                                                            :props="defaultProps"
                                                                            :check-strictly="flag"
                                                                            node-key="id"
                                                                            :highlight-current="true"
                                                                            @node-click="handleNodeClick"
                                                                            check-on-click-node
                                                                            :filter-node-method="filterNode">
                                                                            <span class="custom-tree-node" slot-scope="{ node, data }">
                                                                            <span>{{ node.label }}</span>
                                                                            <span v-if="data.childCompany">
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
                                                            </el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <!--<el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="申请单号：" prop="requestNum">
                                                        <el-input v-model="queryForm.requestNum"></el-input>
                                                    </el-form-item>
                                                </el-col>-->
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="离境形式：" prop="leaveType">
                                                        <el-select v-model="queryForm.leaveType" readonly="">
                                                            <el-option key="跟团" label="跟团" value="跟团"></el-option>
                                                            <el-option key="自由行" label="自由行" value="自由行"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="离境原因：" prop="leaveResult">
                                                        <el-select v-model="queryForm.leaveResult" readonly="">
                                                            <el-option key="旅游" label="旅游" value="旅游"></el-option>
                                                            <el-option key="探亲" label="探亲" value="探亲"></el-option>
                                                            <el-option key="看病" label="看病" value="看病"></el-option>
                                                            <el-option key="子女入学毕业" label="子女入学毕业" value="子女入学毕业"></el-option>
                                                            <el-option key="其他" label="其他" value="其他"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col> 
                                                <el-col :span="8" v-show="toggleShow">
                                                    <el-form-item label="查询开始时间" prop="startTime" class="timeInput">
                                                        <el-date-picker v-model="queryForm.startTime" :picker-options="pickerOptions0" format='yyyy-MM-dd' style="width: 76%;" value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24" v-show="toggleShow">
                                                    <el-form-item label="查询结束时间" prop="endTime" class="timeInput">
                                                        <el-date-picker :picker-options="pickerOptions1" v-model="queryForm.endTime" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="22" style="text-align: right;">
                                                    <el-form-item>
                                                        <el-button type="primary" @click = "init()">查询</el-button> 
                                                        <el-button @click="resetForm('queryForm')">重置</el-button> 
                                                        <el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}} 
                                                            <i :class="toggleShow?class2:class1"></i>
                                                        </el-button>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row> 
                                        </el-form>
                                    </div>
                                    <!--申请信息表格  -->
                                    <div class="">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                                             <el-table-column type="selection" width="55"></el-table-column> 
                                            <el-table-column prop="applicationId" label="申请单号" width="220" v-if="false"></el-table-column>
                                            <el-table-column prop="name" label="姓名" width="90"></el-table-column>
                                            <el-table-column prop="companyName" label="工作单位" ></el-table-column>
                                            <el-table-column prop="deptName" label="部门" v-if="false"></el-table-column>
                                            <el-table-column prop="leaveReason" label="离境原因"></el-table-column>
                                            <el-table-column prop="leaveType" label="离境形式 "></el-table-column>
                                            <el-table-column prop="applicationTime" label="申请时间 "></el-table-column>
                                            <el-table-column label="操作" align="center" width="80">
                                                <template slot-scope="scope">
                                                    <el-button type="text"><router-link :to="{path:'/Dashboard3',query:{workId:scope.row.companyName,alarmNo:scope.row.applicationId,show1: false, show2: true,btnTypeNum:0}}">详情</router-link></el-button>
                                                    <!--<el-button type="text" @click="handleDetail(scope.$index, scope.row)">详情</el-button>-->
                                                    <!-- <el-button type="text" style="font-size:14px"><router-link :to="{path:'/Dashboard3',query:{alarmNo:scope.row.timestamp}}">详情</router-link></el-button> -->
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
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import { communication } from "../../data/getAxios";
export default {
  name: "depart",
  data() {
    return {
      aa: null,
      flag: true,
      valueTitle: null,
      selectdata: [],
      defaultProps: {
        children: "childCompany",
        label: "unitName"
      },
      dataId: null,
      class1: "el-icon-arrow-down el-icon--right",
      class2: "el-icon-arrow-up el-icon--right",
      tableData: [],
      toggleShow: true,
      pageShow1: true,
      pageShow2: false,
      inittableData: [],
      cur_page: 1, //表格当前页
      cur_page2: 1,
      pagesize: 10, //表格分页条数
      pagesize2: 10,
      data_length: 0,
      data_length2: 0,
      pickerOptions0: {
        //开始时间
        // disabledDate: time => {
        //   let endDateVal = this.queryForm.endTime;
        //   if (endDateVal) {
        //     //不能早于当前日期
        //     return (
        //       time.getTime() > new Date(endDateVal).getTime() ||
        //       time.getTime() < Date.now() - 8.64e7
        //     );
        //   } else {
        //     return time.getTime() < Date.now() - 8.64e7;
        //   }
        // }
      },
      pickerOptions1: {
        //结束时间
        disabledDate: time => {
          let beginDateVal = this.queryForm.startTime;
          if (beginDateVal) {
            return (
              time.getTime() <
              new Date(beginDateVal).getTime() - 1 * 24 * 60 * 60 * 1000
            );
          } else {
            return time.getTime() < Date.now() - 8.64e7;
          }
        }
      },
      queryForm: {
        name: "", //姓名
        policeNum: "", //警号
        workUnit: "", //工作单位
        requestNum: "", //申请单号
        leaveType: "", //离境形式
        leaveResult: "", //离境原因
        startTime: "", //查询开始时间
        endTime: "" //查询结束时间
      }
    };
  },
  created() {
    //    this.init()
    this.initselectdata();
  },
  methods: {
    init() {
      //初始化列表
      var self = this;
      self.cur_page = 1;
      if (self.dataId == null || self.dataId == "") {
        self.$message.warning("请选择工作单位");
      } else {
        const _obj = {
          url: self.global.serverPath8 + "/flow/query/getGoAbroad",
          postData: {
            userName: self.queryForm.name,
            policeNo: self.queryForm.policeNum,
            deptID: self.dataId,
            // "applicationId":self.queryForm.requestNum,
            departureForm: self.queryForm.leaveType,
            departureReason: self.queryForm.leaveResult,
            beginTime: self.queryForm.startTime,
            endTime: self.queryForm.endTime
          }
        };
        communication(_obj)
          .then(res => {
            if (res.code == 0) {
              self.tableData = [];
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
            console.log("请求失败！");
          });
      }
    },
    initselectdata() {
      const _obj = {
        url: this.global.serverPath8 + "/user-mgmt/orgmanage/unittreeAuto",
        isGet: true
      };
      communication(_obj).then(res => {
        this.selectdata = res.data;
        this.valueTitle = res.data[0].unitName + "(包含子部门)";
        this.dataId = res.data[0].dataId;
        this.init();
      });
    },
    append(data) {
      this.valueTitle = data.unitName + "(包含子部门)";
      this.dataId = data.dataId;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getReturnNode(node, _array, value) {
      let isPass =
        node.selectdata &&
        node.selectdata.label &&
        node.selectdata.label.indexOf(value) !== -1;
      isPass ? _array.push(isPass) : "";
      this.index++;
      if (!isPass && node.level != 1 && node.parent) {
        this.getReturnNode(node.parent, _array, value);
      }
    },
    handleNodeClick(node) {
      this.valueTitle = node.unitName;
      this.dataId = node.unitId;
    },
    // 清除选中
    clearHandle() {
      this.valueTitle = "";
      this.dataId = null;
    },

    //详情跳转
    // handleDetail(index, row) {
    //     var self = this;
    //     self.idx = index;
    //     self.$http.get(self.global.serverPath4+'/process_engine/goAbroad/queryGoAbroadDetailInfoByApplicationFormId', {//传递参数
    //         params: {
    //             "applicationFormId": row.applicationId,//申请单号applicationId
    //         }
    //     }).then(function(res) {
    //         console.log(res.body.rows);
    //         this.$router.push({ path: '/dashboard3', query: { show1: false, show2: true, info: res.body.rows, exist: true } });//跳转到详情页面
    //     }, function(res) {
    //         console.log('请求失败！！！！！')
    //     });
    // },
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
    // 收起展开
    upDown() {
      this.toggleShow = !this.toggleShow;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 重置表单
    resetForm(formName) {
      this.dataId = null;
      this.valueTitle = null;
      this.$refs[formName].resetFields();
    }
  },
  computed: {}
};
</script>
<style scoped>
.departListTable {
  margin-top: 20px;
}
.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
  height: auto;
  /*max-height: 274px;*/
  overflow: auto;
  padding: 0;
}
.el-select-dropdown__item.selected {
  font-weight: normal;
}
ul li >>> .el-tree .el-tree-node__content {
  height: auto;
  padding: 0 20px;
}
.el-tree-node__label {
  font-weight: normal;
}
.el-tree >>> .is-current .el-tree-node__label {
  color: #409eff;
}
.el-tree >>> .is-current .el-tree-node__children .el-tree-node__label {
  color: #606266;
  font-weight: normal;
}
</style>
<style>
.departListTable .el-table th > .cell {
  color: #333;
  font-size: 14px;
}
.departListTable .el-table td div {
  font-size: 14px;
}
.queryForm .demo-form-inline input {
  width: 200px !important;
}
.queryForm .demo-form-inline select {
  width: 200px !important;
}
</style>

