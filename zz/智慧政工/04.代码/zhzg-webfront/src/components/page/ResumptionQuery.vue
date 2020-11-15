<template>
	<div class="approve">
		<div class="approveHeader is-always-shadow">
			<el-breadcrumb class="crumbs" separator="/">
				<el-breadcrumb-item>勤务管理</el-breadcrumb-item>
				<el-breadcrumb-item>请销假查询</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="is-always-shadow leave leaveList">
			<div class="" style="padding:8px 10px">
				<el-row>
					<el-form :inline="true" :label-position="'right'" label-width="90px" ref='form' :model="form" class="demo-form-inline fuzzinfoCon">
						<el-row :gutter="6">
							<el-col :span="8">
								<el-form-item class="" label="姓名：" prop="userName">
									<el-input v-model="form.userName"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item class="" label="警号：" prop="userNo">
									<el-input v-model="form.userNo"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item class="" label="工作单位：" prop="companyName" v-show="toggleShow">
                                    <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
                                        <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                        <el-option :value="valueTitle" :label="valueTitle">
                                            <div class="tree-list">
                                                <el-tree id="tree-option"
                                                         ref="selectTree"
                                                         :expand-on-click-node="false"
                                                         :data="selectdata"
                                                         :props="dataProps"
                                                         node-key="id"
                                                         :highlight-current="true"
                                                         @node-click="handleNodeClick2"
                                                         check-on-click-node
                                                         :filter-node-method="filterNode"
                                                >
                                                        <span class="custom-tree-node" slot-scope="{ node, data }">
                                                            <span>{{ node.label }}</span>
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
                                        </el-option>
                                    </el-select>
								</el-form-item>
							</el-col>
							<el-col :span="8" v-show="toggleShow">
								<el-form-item class="formSelect" label="请假事由：" prop="applyType">
									<el-select v-model="form.applyType">
										<el-option label="事假" value="事假"></el-option>
										<el-option label="病假" value="病假"></el-option>
										<el-option label="探亲假" value="探亲假"></el-option>
										<el-option label="婚假" value="婚假"></el-option>
										<el-option label="产假" value="产假"></el-option>
										<el-option label="丧假" value="丧假"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="8" v-show="toggleShow">
								<el-form-item class="formSelect" label="请假主体：" prop="mainstay">
									<el-select v-model="form.mainstay">
										<el-option label="个人" value="个人"></el-option>
										<el-option label="本人" value="本人"></el-option>
										<el-option label="团体" value="团体"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="8" v-show="toggleShow">
								<el-form-item class="valueBegin" label="开始时间：" prop="valueBegin">
									<el-date-picker v-model="form.valueBegin" :picker-options="pickerOptions0" format='yyyy-MM-dd' value-format="yyyy-MM-dd"
									 type="date"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="16" v-show="toggleShow">
								<el-form-item class="valueEnd" label="结束时间：" prop="valueEnd">
									<el-date-picker :picker-options="pickerOptions1" v-model="form.valueEnd" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="8" style="margin-bottom:6px;text-align: center;">
								<el-button @click="init()" type="primary">查询</el-button>
								<el-button @click="resetForm('form')">重置</el-button>
								<el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
									<i :class="toggleShow?class1:class2"></i>
								</el-button>
							</el-col>
						</el-row>
					</el-form>
				</el-row>
				<div style="padding:0 20px 14px 20px">
					<el-button type="primary" icon="el-icon-upload2" @click="export2Excel">导出</el-button>
				</div>
				<template>
					<img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg"
					/>
					<el-table v-show="tableData.length>0?true:false" class="fuzzinfoTable" ref="multipleTable" :data="tableData" stripe tooltip-effect="dark"
					 style="width: 100%;border-bottom:0">
						<el-table-column type="selection" width="55"> </el-table-column>
						<el-table-column prop="name" label="姓名" show-overflow-tooltip width="90"></el-table-column>
						<el-table-column prop="companyName" label="工作单位"></el-table-column>
						<el-table-column prop="duty" label="职务"></el-table-column>
						<el-table-column prop="reason" label="请假事由"></el-table-column>
						<el-table-column prop="startTime" label="开始日期"></el-table-column>
						<el-table-column prop="endTime" label="结束日期"></el-table-column>
						<el-table-column prop="applicationTime" label="申请时间"></el-table-column>
						<el-table-column label="操作" width="80">
							<template slot-scope="scope">
								<el-button type="text">
									<router-link :to="{path:'/LeaExamApproval',query:{alarmNo:scope.row.applicationId,show1: false, show2: true,flag:'0'}}">详情</router-link>
								</el-button>
							</template>
						</el-table-column>
					</el-table>
					<!--表格分页  -->
					<div class="pagination" v-show="tableData.length>0?true:false">
						<el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper"
						 :total="inittableData.length">
						</el-pagination>
					</div>
				</template>
			</div>
		</div>
	</div>
</template>
<script>
import { communication } from "../../data/getAxios";
export default {
  data() {
    return {
      personIdType: false,
      toggleShow: true,
      valueBegin: "",
      pageShow1: true,
      pageShow2: false,
      inittableData: [],
      cur_page: 1, //表格当前页
      cur_page2: 1,
      pagesize: 10, //表格分页条数
      pagesize2: 10,
      data_length: 0,
      data_length2: 0,
      // valueEnd: "",
      class1: "el-icon-arrow-up el-icon--right",
      class2: "el-icon-arrow-down el-icon--right",
      endDatePicker: this.processDate(),
      pickerOptions0: {
        //开始时间
        // disabledDate: time => {
        //   let endDateVal = this.form.valueEnd;
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
          let beginDateVal = this.form.valueBegin;
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
      form: {
        userName: "",
        userNo: "",
        companyName: "",
        formId: "",
        applyType: "",
        mainstay: "",
        valueBegin: "",
        valueEnd: ""
      },
      tableData: [],
      selectdata: [],
      dataProps: {
        children: "childCompany",
        label: "unitName"
      },
      valueTitle: "",
      aa: null,
      dataId: null
    };
  },
  created() {
    // this.init();
    this.initselectdata();
  },
  methods: {
    // 重置表单
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
      console.log(data);
      // this.flag=false
      this.valueTitle = data.unitName + "(包含子部门)";
      this.dataId = data.dataId;
    },
    filterNode(value, data) {
      console.log(data);
      if (!value) return true;
      console.log(data);
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick2(node) {
      console.log(node);
      this.valueTitle = node.unitName;
      this.dataId = node.unitId;
    },
    // 清除选中
    clearHandle() {
      this.valueTitle = "";
      this.dataId = null;
      // this.valueId = null
      // this.defaultExpandedKey = []
      // this.clearSelected()
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dataId = null;
      this.valueTitle = "";
      // this.init();
    },
    // 收起展开
    upDown() {
      this.toggleShow = !this.toggleShow;
    },
    // 结束时间限制
    processDate() {
      const self = this;
      return {
        disabledDate(time) {
          if (self.form.valueEnd) {
            //如果开始时间不为空，则结束时间大于开始时间
            return new Date(self.form.valueEnd).getTime() > time.getTime();
          } else {
            return time.getTime() > Date.now(); //开始时间不选时，结束时间最大值小于等于当天
          }
        }
      };
    },
    export2Excel() {
      var that = this;
      require.ensure([], () => {
        const { export_json_to_excel } = require("../../vendor/Export2Excel"); //引入文件
        const tHeader = [
          "申请单号",
          "姓名",
          "工作单位",
          "职务",
          "请假事由",
          "开始日期",
          "结束日期",
          "申请时间"
        ]; //对应表格输出的title
        const filterVal = [
          "applicationId",
          "name",
          "companyName",
          "duty",
          "reason",
          "startTime",
          "endTime",
          "applicationTime"
        ]; // 对应表格输出的数据
        const list = that.tableData;
        const data = that.formatJson(filterVal, list);
        export_json_to_excel(tHeader, data, "申请列表excel"); //对应下载文件的名字
      });
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
    init() {
      if (this.dataId == null || this.dataId == "") {
        this.$message.error("请选择部门搜索");
        return;
      }
      var self = this;
      self.cur_page = 1;
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/getPleaseOff",
        isGet: false,
        postData: {
          userName: self.form.userName,
          policeNo: self.form.userNo,
          deptID: self.dataId,
          leaveReason: self.form.applyType,
          beginTime: self.form.valueBegin,
          endTime: self.form.valueEnd,
          leaveSubject: self.form.mainstay
        }
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.inittableData = res.data;
            self.tableData = self.inittableData.slice(
              (self.cur_page - 1) * self.pagesize,
              self.cur_page * self.pagesize
            );
          } else {
            self.tableData = [];
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    handleSizeChange(val) {
      console.log("每页 ${val} 条");
    },
    handleCurrentChange(val) {
      this.cur_page = val;
      this.tableData = this.inittableData.slice(
        (this.cur_page - 1) * this.pagesize,
        this.cur_page * this.pagesize
      );
    },
    // 收起展开
    upDown() {
      this.toggleShow = !this.toggleShow;
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]));
    }
  }
};
</script>
<style scoped>
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
  font-weight: 700;
}
.el-tree >>> .is-current .el-tree-node__children .el-tree-node__label {
  color: #606266;
  font-weight: normal;
}
</style>
<style>
.fuzzinfoTable td,
.fuzzinfoTable th {
  font-size: 14px !important;
}

.pagination2 .el-input--small .el-input__inner {
  width: 45px !important;
}

.pagination .el-input--small .el-input__inner {
  width: 45px !important;
}

.leaveList {
  overflow: hidden;
  background: #f0f0f0;
}

.wrapper .leave {
  margin: 16px 20px;
  background: white;
}

.inquiryBtn {
  padding: 20px;
}

.inquiryBtn:nth-of-type(3) {
  padding-bottom: 0;
}

.paging {
  overflow: hidden;
  background: white;
  padding: 5px 0;
}

.block {
  float: right;
  margin-right: 15px;
}

.fuzzinfoCon input {
  width: 208px !important;
}

.fuzzinfoCon select {
  width: 208px !important;
}

.leaveList .el-row {
  margin-bottom: 0;
}

.crumbs {
  background: white;
}

.approve .content {
  padding: 20px;
  background: white;
}

.approveHeader {
  padding: 20px;
  background: white;
}

.approvehead {
  font-family: PingFangSC-Medium;
  font-size: 16px;
  color: #272727;
  font-weight: bold;
}

.wrapper .title {
  float: inherit;
  padding: 18px 20px;
  border-bottom: 1px solid #ebeef5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

.fuzzinfoCon {
  padding: 20px 20px 0;
}
</style>
