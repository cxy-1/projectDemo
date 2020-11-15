<template>
	<div class="approve">
		<div>
			<el-card shadow="">
				<div class="crumbs">
					<el-breadcrumb separator="/">
						<el-breadcrumb-item>勤务管理</el-breadcrumb-item>
						<el-breadcrumb-item>请销假审批</el-breadcrumb-item>
					</el-breadcrumb>
				</div>
			</el-card> 
		</div>
		<!--<div class="approveHeader is-always-shadow">
			<el-breadcrumb class="crumbs" separator="/">
	            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
	            <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
		  		<el-breadcrumb-item>请销假审批</el-breadcrumb-item>
	        </el-breadcrumb>
	        <p class="approvehead">请销假审批</p>
		</div>-->
		<el-card class="card2" style="margin-top:16px;">
				<el-row>
				  	<!--<el-col :span="24" class="title"><div class="grid-content appli bg-purple-dark">申请信息</div></el-col>-->
				  	<el-form :inline="true" :label-position="'right'" label-width="90px" ref='formInline' :model="formInline" class="demo-form-inline">
			         	<el-row :gutter="6">
			                <el-col :span="8">
				                <el-form-item class="" label="姓名：" prop="userName">
				                	<el-input v-model="formInline.userName"></el-input>
							  	</el-form-item>
							</el-col>
			                <el-col :span="8">
				                <el-form-item class="" label="警号：" prop="userNo">
								    <el-input v-model="formInline.userNo"></el-input>
							  	</el-form-item>
							</el-col>
							<el-col :span="8" v-show="toggleShow">
				                <el-form-item class="" label="工作单位：" prop="companyName">
								    <el-input v-model="formInline.companyName"></el-input>
							  	</el-form-item>
							</el-col>
							<!-- <el-col :span="8" v-show="toggleShow">
								<el-form-item class="formSelect" label="申请单号：" prop="formId">
									<el-input v-model="formInline.formId"></el-input>
								</el-form-item>
							</el-col> -->
							<el-col :span="24" v-show="toggleShow">
								<el-form-item class="formSelect" label="请假类型：" prop="applyType">
									<el-select v-model="formInline.applyType" style="width:200px">
										<el-option label="事假" value="事假"></el-option>
										<el-option label="病假" value="病假"></el-option>
										<el-option label="探亲假" value="探亲假"></el-option>
										<el-option label="婚假" value="婚假"></el-option>
										<el-option label="产假" value="产假"></el-option>
										<el-option label="丧假" value="丧假"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<div class="inquiryBtn">
									<el-button @click="inquiry()" type="primary">查询</el-button>
									<el-button @click="resetForm('formInline')">重置</el-button>
									<el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
										<i :class="toggleShow?class1:class2"></i>
									</el-button>
								</div> 
							</el-col>
			            </el-row>
			        </el-form>
				</el-row>
				<img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
				<div  class="departTable" v-show="tableData.length>0?true:false" >
				  	<el-table class="fuzzinfoTable" ref="multipleTable" :data="tableData" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" >
					    <el-table-column prop="userName" label="姓名"  show-overflow-tooltip></el-table-column>
					    <el-table-column prop="companyName" label="工作单位" show-overflow-tooltip></el-table-column>
					    <el-table-column prop="departName" label="部门" ></el-table-column>
					    <el-table-column prop="applyType" label="请假类型" ></el-table-column>
					    <el-table-column prop="applicantTime" label="申请时间" ></el-table-column>
					    <el-table-column label="操作" >
					    	<template slot-scope="scope">
						    	<el-button v-show="scope.row.isTerminate != 1" type="text"><router-link :to="{path:'/LeaExamApproval',query:{btnTypeNum:1,workId:scope.row.workId,isTerminate:scope.row.isTerminate,fid:scope.row.fid,alarmNo:scope.row.id,flag:'1'}}">审批</router-link></el-button>
						    	<el-button type="text"><router-link :to="{path:'/LeaExamApproval',query:{btnTypeNum:0,alarmNo:scope.row.id,fid:scope.row.fid,flag:'1'}}">详情</router-link></el-button>
						    	<el-button v-show="scope.row.isTerminate == 1" type="text"><router-link :to="{path:'/resumptionLeave',query:{btnTypeNum:2,isTerminate:scope.row.isTerminate,fid:scope.row.fid,alarmNo:scope.row.id}}">销假</router-link></el-button>
					    	</template>
					    </el-table-column>
				  	</el-table>
					<!--表格分页  -->
					<div class="pagination" v-show="tableData.length>0?true:false">
						<el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
						</el-pagination>
					</div>
				</div>
		</el-card>
	</div>
</template>
<script>
import { communication } from "../../data/getAxios";
export default {
  data() {
    return {
      toggleShow: true,
      valueBegin: "",
      pageShow1: true,
      pageShow2: false,
      cur_page: 1, //表格当前页
      cur_page2: 1,
      pagesize: 10, //表格分页条数
      pagesize2: 10,
      data_length: 0,
      data_length2: 0,
      inittableData: [],
      valueEnd: "",
      class1: "el-icon-arrow-up el-icon--right",
      class2: "el-icon-arrow-down el-icon--right",
      formInline: {
        userName: "",
        userNo: "",
        companyName: "",
        formId: "",
        applyType: ""
      },
      tableData: []
    };
  },
  created() {
    this.init();
  },
  methods: {
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
      // this.loading = true;
      this.cur_page2 = val;
      // this.getSearchData();
    },
    inquiry() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/getPleaseOffQuery",
        isGet: false,
        postData: {
          userName: self.formInline.userName,
          policeNo: self.formInline.userNo,
          deptName: self.formInline.companyName,
          applyType: self.formInline.applyType
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
    init() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/todolist?type=qxj",
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
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.init();
    },
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
          "请假类型",
          "申请时间"
        ]; //对应表格输出的title
        const filterVal = [
          "applyFormId",
          "name",
          "workUnit",
          "departName",
          "Reason",
          "applicantTime"
        ]; // 对应表格输出的数据
        const list = that.tableData;
        const data = that.formatJson(filterVal, list);
        export_json_to_excel(tHeader, data, "申请列表excel"); //对应下载文件的名字
      });
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
.appli {
  padding: 15px;
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
  padding: 0 20px 20px;
  padding-top: 0 !important;
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
  border-bottom: 1px solid #ebeef5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.fuzzinfoCon {
  padding: 8px 20px 0;
}
</style>
