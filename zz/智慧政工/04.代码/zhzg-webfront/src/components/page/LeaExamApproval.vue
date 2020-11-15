<template>
	<div  class="LeaExamApproval" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-card shadow="">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                    <el-breadcrumb-item>请销假审批</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="titIcon" v-show="titIconTypenum">
                <i class="faSelf sq-icon"></i>
                <p v-show="danhao?true:false">申请单号：{{danhao}}</p>
            </div>
            <div class="LeaveManCon" v-show="noLead"></div>
            <div class="processBarLead" v-show="lead"></div>
            <div class="user-info">
                <p></p>
                <p></p>
            </div>
        </el-card>
        <div class="info is-always-shadow applicantInfo">
        	<div class="">
				<div slot="header" class="clearfix" style="padding:10px 16px;border-bottom:1px solid #ebeef5;">
					<span>申请信息</span>
				</div>
        		<el-row style="margin-top:10px;">
				  	<el-form :inline="true" label-position="left" label-width="" ref='formInline' :model="formInline" class="demo-form-inline fuzzinfoCon">
			         	<el-row :gutter="6" style="padding-left:40px">
			              <el-col :span="8"><el-form-item class="" label="姓名："><span>{{formInline.name}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="警号："><span>{{formInline.jinghao}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="工作单位："><span>{{formInline.danwei}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="部门："><span>{{formInline.bumen}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="休假类型："><span>{{formInline.applyType}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="请假事由："><span>{{formInline.shiyou}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show=" formInline.applyType == '婚假'?true:false"><el-form-item class="" label="是否晚婚："><span>{{formInline.isLateMarriage}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show="gongzuonianxian"><el-form-item class="" label="工作年限："><span>{{formInline.workingYears}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show="gongzuonianxian"><el-form-item class="" label="请假累计："><span>{{formInline.leavedayss}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show="gongzuonianxian"><el-form-item class="" label="病假累计："><span>{{formInline.sickLeaveDays}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show="chanjialeixing"><el-form-item class="" label="产假类型："><span>{{formInline.maternityLeaveType}}</span></el-form-item></el-col>
			              <el-col :span="8" v-show="wanyu"><el-form-item class="" label="是否晚育："><span>{{formInline.isLateChildbirth}}</span></el-form-item></el-col>
			              <el-col :span="8" v-show="taishu"><el-form-item class="" label="胎数："><span>{{formInline.births}}</span></el-form-item></el-col>
			              <el-col :span="8" v-show="qinshuguanxi"><el-form-item class="" label="亲属关系："><span>{{formInline.kinship}}</span></el-form-item></el-col>
                    <el-col :span="8"><el-form-item class="" label="开始时间："><span>{{formInline.beginTime}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="结束时间："><span>{{formInline.endTime}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="休假天数："><span>{{formInline.tianshu}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show="formInline.applyType == '事假'?true:false"><el-form-item class="" label="已休天数："><span>{{formInline.casualLeaveDays}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show=" formInline.applyType == '病假'?true:false"><el-form-item class="" label="已休天数："><span>{{formInline.sickLeaveDays}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="是否离宁："><span>{{formInline.lining}}</span></el-form-item></el-col>
			              <el-col :span="8" v-show="jiaotong"><el-form-item class="" label="交通工具："><span>{{formInline.vehicle}}</span></el-form-item></el-col>
			              <el-col :span="8" v-show="waichudidian">
                      <el-form-item class="" label="外出地点：">
                        <el-popover trigger="hover" placement="right">
                          <p>{{ formInline.address }}</p>
                          <div slot="reference" class="name-wrapper">
                            <el-tag size="medium">{{ formInline.address }}</el-tag>
                          </div>
                        </el-popover>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8"><el-form-item class="" label="同行人员："><span>{{formInline.isTogether}}</span></el-form-item></el-col>
                    <el-col :span="8"><el-form-item class="" label="备注："><span>{{formInline.remarks}}</span></el-form-item></el-col>
                    <el-col :span="8" v-show="showAttachment"><el-form-item class="" label="证明材料："><a :href="fileUrl" >{{formInline.upFileName}}</a></u></el-form-item></el-col>
                    <el-col :span="8" class="fujian" v-show="fujian">
                          <el-form-item label="请假审批表：" prop="attOption">
                              <div v-for="(item,index) in wordPathUrl " class="el-upload__text downLoad" @click="downWorld(item)">
                                  <span class="icon-world"></span>
                                  <span ref="tipModel">离宁外出请假审批表{{index+1}}</span>
                              </div>
                              <div class="exampleIm"></div>
                              <div ref="redTips" class="el-upload__tip"></div>
                          </el-form-item>
                    </el-col>
			              <el-col :span="8" class="fujian" v-show="fujian2">
                                <el-form-item label="证明材料：" prop="attOption">
                                    <div class="el-upload__text downLoad" @click="downWorld2()">
                                        <span class="icon-world"></span>
                                        <span ref="tipModel">证明材料</span>
                                    </div>
                                    <div class="exampleIm"></div>
                                    <div ref="redTips" class="el-upload__tip"></div>
                                </el-form-item>
                            </el-col>
			          </el-row>
                <el-row :gutter="6" style="padding-left:40px" v-if="fujian3">
                    <el-col :span="8"><el-form-item class="" label="实际开始时间："><span>{{formInline.updatePleOffForm.startTime}}</span></el-form-item></el-col>
			              <el-col :span="8"><el-form-item class="" label="实际结束时间："><span>{{formInline.updatePleOffForm.endTime}}</span></el-form-item></el-col>
			            	<el-col :span="8"><el-form-item class="" label="实际请假天数："><span>{{formInline.updatePleOffForm.days}}</span></el-form-item></el-col>
                </el-row>
					</el-form>
					<div class="approvalTab">
						<el-table :data="xlTable" height="100%" v-show="approvalTab" :show-header="true" style="width: 100%">
							<el-table-column prop="userName" label="姓名"></el-table-column>
							<el-table-column prop="userNo" label="称谓"></el-table-column>
							<el-table-column prop="workUnit" label="单位"></el-table-column>
							<el-table-column prop="department" label="职务"></el-table-column>
						</el-table>
					</div>
					<template>
						<el-table class="fuzzinfoTable" ref="multipleTable" :data="tableData" stripe tooltip-effect="dark" style="width: 100%;border-bottom:0" v-show="historyTable" >
							<el-table-column prop="applicationId" label="申请单号"></el-table-column>
							<el-table-column prop="name" label="姓名"  show-overflow-tooltip></el-table-column>
							<el-table-column prop="companyName" label="工作单位" show-overflow-tooltip></el-table-column>
							<el-table-column prop="duty"label="职务" ></el-table-column>
							<el-table-column prop="reason" label="请假事由" ></el-table-column>
							<el-table-column prop="distinction" label="外出地点" ></el-table-column>
							<el-table-column label="同行人员" width="80">
								<template slot-scope="scope">
									<span>{{scope.row.withPeo.split('[')[1].split(']')[0].length == 0?'无':'有'}}</span>
								</template>
							</el-table-column>
							<!-- <el-table-column prop="withPeo" label="同行人员"></el-table-column>  -->
							<el-table-column prop="startTime"label="开始日期" ></el-table-column>
							<el-table-column prop="endTime"label="结束日期" ></el-table-column>
							<el-table-column prop="applicationTime"label="申请时间" ></el-table-column>
						</el-table>
						<!--表格分页  -->
						<div class="pagination" v-show="pageShow1">
							<el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length">
							</el-pagination>
						</div>
					</template>
				</el-row>
				<div class="btn-group">
                    <!--申请信息表格  -->
                    <div class="sqxx-table" v-show="showPleSeOffTogether" style="padding:1px 20px 20px 20px">
                        <el-table :data="tableData" style="width: 100%">
                            <el-table-column prop="relationUserName" label="姓名" width="200">
                            </el-table-column>
                            <el-table-column prop="appellation" label="称谓" width="220">
                            </el-table-column>
                            <el-table-column prop="workUnits" label="工作单位">
                            </el-table-column>
                            <el-table-column prop="jobPosition" label="职务" width="240">
                            </el-table-column>
                        </el-table>
					</div>
					<div v-show="now1" class="btn-group leaveApprovalBtn">
						<el-button class="rejectBtn" @click="bhVisible = true">驳回</el-button>
						<el-button @click="cancelBtn">返回</el-button>
						<el-button type="primary" class="submit" @click="agree">同意</el-button>
					</div>
					<div v-show="now2" class="btn-group leaveApprovalBtn">
						<el-button type="primary" @click="cancelBtn">返回</el-button>
					</div>
                </div>
        	</div>
        </div>
        <input v-show="false" type="button" pos="pos0" class="posTest" value="电子签章">
        <el-dialog title="请选择公章" :visible.sync="xzlogBox" width="400px" class="xzlogBox">
            <div class="verseList">
                <ul>
                    <li v-for="(item,index) in iamgesArr" v-model="item.title" :key="index">
                        <div>
                            <img :src="item.src" />
                        </div>
                    </li>
                </ul>
                <div class="xzlogLeftBtn xzlogBtn" @click='timeLeft()'><i class="el-icon-caret-left"></i></div>
                <div class="xzlogRightBtn xzlogBtn" @click='timeRight()'><i class="el-icon-caret-right"></i></div>
            </div>
            <div slot="footer" class="xzlogFooter">
                <el-button @click="xzlogBox = false">取 消</el-button>
                <el-button type="primary" @click="agreeForm" :loading="agreeFlag">确 定</el-button>
            </div>
        </el-dialog>
        <!-- 驳回提示框 -->
        <el-dialog title="驳回" :visible.sync="bhVisible" width="580px" class="bhlog">
            <el-form ref="rejectForm" :model="rejectForm" label-width="100px">
                <el-form-item label="审批意见:">
                    <el-input class="test_box" type="textarea" v-model="rejectForm.rejectCon"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="bhVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveBh">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog :visible.sync="pingfen" class="visible" width="620px">
            <div class="del-dialog-cnt" style="margin-top:60px;">
                <div style="text-align:center;margin-bottom:20px;margin-left:120px;">
                    <el-row>
                        <el-col :span="8"><span class="rateSpan">首页</span></el-col>
                        <el-col :span="8">
                            <el-rate
                                v-model="scoreValue1Con"
                                show-text>
                            </el-rate>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><span class="rateSpan">请销假管理</span></el-col>
                        <el-col :span="8">
                            <el-rate
                                v-model="scoreValue2Con"
                                show-text>
                            </el-rate>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><span class="rateSpan">出国（境）管理</span></el-col>
                        <el-col :span="8">
                            <el-rate
                                v-model="scoreValue3Con"
                                show-text>
                            </el-rate>
                        </el-col>
                    </el-row>
                </div>
                <div style="padding:0 10px;width:320px;margin:0 auto;">
                    <el-input
                        type="textarea" placeholder="分享使用体验，多一点评价，帮助我们不断完善（写满15个汉字，才是好同志）"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        v-model="scoreView"
                        maxlength="100"
                        show-word-limit>
                    </el-input>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dashboardRate">提 交</el-button>
                <!--<el-button type="primary" @click="deleteRow">确 定</el-button>-->
            </span>
        </el-dialog>
	</div>
</template>
<script>
import { communication } from "../../data/getAxios";
export default {
  data() {
    return {
      noLead: false,
      lead: false,
      iamgesArr: [],
      fujian2Url: "",
      pingfen: false,
      scoreValue1Con: null,
      scoreValue2Con: null,
      scoreValue3Con: null,
      scoreView: "",
      scoreVisible: true, //willa记得改false
      pageShow1: false,
      pageShow2: false,
      cur_page: 1, //表格当前页
      cur_page2: 1,
      pagesize: 10, //表格分页条数
      pagesize2: 10,
      data_length: 0,
      data_length2: 0,
      historyTable: false,
      loading: false,
      now1: null, //按钮切换
      now2: null,
      titIconTypenum: false,
      approvalTab: false,
      showPleSeOffTogether: false,
      fujian: false,
      fujian2: false,
      fujian3: false,
      jiaotong: false,
      chanjialeixing: false,
      wanyu: false,
      taishu: false,
      qinshuguanxi: false,
      waichudidian: false,
      gongzuonianxian: false,
      valueBegin: "",
      valueEnd: "",
      danhao: "",
      stepType: 2,
      btnType: 1, //1为同意   2为驳回
      xlTable: [],
      tableData: [],
      formInline: {
        name: "",
        jinghao: "",
        danwei: "",
        bumen: "",
        beginTime: "",
        endTime: "",
        tianshu: "",
        lining: "",
        shiyou: "",
        applyType: "",
        remarks: "",
        upFileName: "",
        isTogether: "",
        sickLeaveDays: "",
        leavedayss: "",
        casualLeaveDays: "",
        isLateMarriage: ""
      },
      showAttachment: false,
      fileUrl: "",
      reject: false,
      rejectForm: {
        rejectCon: ""
      },
      xzlogBox: false,
      agreeFlag: false,
      applicantPeople: null,
      bhVisible: false,
      xzlogBoxNum: 0,
      wordPathUrl: [],
      isOfficalSea: true //需要盖章
    };
  },
  created() {
    this.init();
  },
  methods: {
    dashboardRate() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/message/evaluate/sendEvaluate",
        isGet: false,
        postData: {
          overview: self.scoreValue1Con,
          goAboard: self.scoreValue2Con,
          leave: self.scoreValue3Con,
          note: self.scoreView
        }
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.pingfen = false;
            this.$router.push("/wdlc");
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    titIconType() {
      if (this.$route.query.num) {
        this.titIconTypenum = false;
      } else {
        this.titIconTypenum = true;
      }
    },
    history() {
      var self = this;
      self.pageShow1 = true;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/statistics/serviceManagement/swithCheckBsPleseoffForm",
        isGet: false,
        postData: {
          personId: self.$route.query.alarmNo,
          pageSize: "10",
          currentNum: self.cur_page,
          name: "",
          policeNo: "",
          companyName: "",
          applicationId: "",
          reason: "",
          startTime: "",
          endTime: "",
          entity: ""
        }
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.tableData = res.data;
            self.data_length = res.data.length;
            self.historyTable = true;
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    // 表格分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getTabData();
    },
    // 查询分页导航
    handleCurrentChange2(val) {
      this.cur_page2 = val;
    },
    ready() {
      var self = this;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/flow/process/getProcessBar?workId=" +
          self.workId,
        isGet: true
      };
      communication(_obj).then(
        res => {
          console.log(res);
          if (res.code == 0) {
            var index = -1;
            var stepArr = [];
            if (res.data.processNo == "011") {
              self.lead = true;
              var msg = res.data.hashMap;
              for (var val in msg) {
                stepArr.push(msg[val]);
              }
              for (var i = 0; i < stepArr.length; i++) {
                $(".processBarLead").append(
                  '<div class="aloneF ' +
                    (stepArr[i].applyStatus == 0
                      ? "gray"
                      : stepArr[i].applyStatus == 1
                        ? "green"
                        : stepArr[i].applyStatus == 2 ? "red" : "gray") +
                    '"><div class="alone"><div class="xian"></div><div class="dept"><span class="yuan"></span><span class="deptName">' +
                    stepArr[i].applyName +
                    '</span></div><div class="leadName">' +
                    (stepArr[i].applyUser || "") +
                    '</div><div class="leadDate">' +
                    (stepArr[i].endTime || "") +
                    "</div></div></div>"
                );
              }
              var processBarLeadLiWidth =
                $(".processBarLead").width() / (stepArr.length - 1);
              $(".processBarLead>div").width(processBarLeadLiWidth);
              for (var k = 0; k < stepArr.length; k++) {
                if (k == 5) {
                  $(".processBarLead>div")
                    .eq(k)
                    .css({
                      left: processBarLeadLiWidth * 2,
                      top: "65px",
                      width: processBarLeadLiWidth * 3
                    });
                } else if (k > 5) {
                  $(".processBarLead>div")
                    .eq(k)
                    .css({ left: processBarLeadLiWidth * (k - 1) });
                } else {
                  $(".processBarLead>div")
                    .eq(k)
                    .css({ left: processBarLeadLiWidth * k });
                }
              }
            } else {
              self.noLead = true;
              var msg = res.data.hashMap;
              var optionCon = "";
              for (var val in msg) {
                stepArr.push(msg[val]);
              }
              for (var i = 0; i < stepArr.length; i++) {
                //applyStatus=0 不亮/灰色    applyStatus=1 亮/蓝色   applyStatus=2 亮/红色
                $(".LeaveManCon").append(
                  '<div class="alone ' +
                    (stepArr[i].applyStatus == 0
                      ? "gray"
                      : stepArr[i].applyStatus == 1
                        ? "green"
                        : stepArr[i].applyStatus == 2 ? "red" : "gray") +
                    '"><div class="circleDot finishType"><div class="circle"><div></div>' +
                    (stepArr[i].applyName || "") +
                    '</div></div><div class="figure"></div><div class="contentCon"><p>' +
                    (stepArr[i].applyUser || "") +
                    "</p><p>" +
                    (stepArr[i].endTime || "") +
                    "</p> </div></div>"
                );
              }
              for (var r = 0; r < $(".data").length; r++) {
                $(".data")
                  .eq(r)
                  .css({
                    height:
                      $(".daughter")
                        .eq(r)
                        .height() +
                      1 +
                      "px",
                    "line-height":
                      $(".daughter")
                        .eq(r)
                        .height() + "px",
                    width:
                      $(".data")
                        .eq(r)
                        .width() + 15
                  });
                $(".roundDot")
                  .eq(r)
                  .css({
                    height:
                      $(".daughter")
                        .eq(r)
                        .height() +
                      1 +
                      "px"
                  });
              }
              if (stepArr[stepArr.length - 1].status == 1) {
                self.pingfen = true;
              }
            }
          } else if (res.code != 1) {
            self.$message.error(res.message);
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );

      const obj = {
        url:
          self.global.serverPath8 +
          "/flow/query/isOfficalSea?workId=" +
          self.workId +
          "&fid=" +
          (self.$route.query.fid ? self.$route.query.fid : self.workId),
        isGet: true
      };
      communication(obj).then(
        res => {
          //不需要盖章
          if (res.data == "0") {
            self.isOfficalSea = false;
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    //下载‘提交’后的申请表
    downWorld(item) {
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr("action", item); //待修改0
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    downWorld2() {
      var $eleForm = $("<form method='get'></form>");
      // $eleForm.attr("action", "http://192.168.8.135:8084/serviceManagement/"+ this.fujian2Url );
      $eleForm.attr(
        "action",
        this.global.serverPath10 +
          "/serviceManagement/" +
          res.body.row[0].enclosure
      );
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    cancel() {
      this.reject = false;
    },
    cancelBtn() {
      if (this.$route.query.flag == "0") {
        this.$router.push("/resumptionQuery");
      } else {
        this.$router.push("/approveManage");
      }
    },
    //详细
    init() {
      if (
        this.$route.query.btnTypeNum === 0 ||
        this.$route.query.btnTypeNum === "0"
      ) {
        this.now1 = false;
        this.now2 = true;
      } else if (
        this.$route.query.btnTypeNum === 1 ||
        this.$route.query.btnTypeNum === "1"
      ) {
        this.now1 = true;
        this.now2 = false;
      } else {
        this.now1 = false;
        this.now2 = true;
      }
      var self = this;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/flow/query/formInformation?type=qxjInfo&id=" +
          self.$route.query.alarmNo,
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.workId = res.data.pleSeOffForm.workId;
            self.ready();
            self.applicantPeople = res.data.pleSeOffForm.applicantPeople;
            if (res.data.pleSeOffForm.vehicle != "") {
              self.jiaotong = true;
            }
            if (res.data.pleSeOffForm.maternityLeaveType != null) {
              self.chanjialeixing = true;
            }
            if (res.data.pleSeOffForm.applyType == "产假") {
              self.wanyu = true;
            }
            if (res.data.pleSeOffForm.kinship != null) {
              self.qinshuguanxi = true;
            }
            if (
              res.data.pleSeOffForm.workingYears != null &&
              res.data.pleSeOffForm.applyType == "年休假"
            ) {
              self.gongzuonianxian = true;
            }
            if (
              res.data.pleSeOffForm.enclosure != "" &&
              res.data.pleSeOffForm.enclosure != "undefined" &&
              res.data.pleSeOffForm.enclosure != null
            ) {
              self.fujian2 = true;
            }
            if (res.data.pleSeOffForm.isLeaveNj == "是") {
              self.waichudidian = true;
            }
            if (res.data.pleSeOffForm.applyType == "产假") {
              self.taishu = true;
            }
            if (res.data.pleSeOffForm.togetherList != null) {
              self.approvalTab = true;
            }
            if (
              res.data.pleSeOffTogetherInfo != null &&
              res.data.pleSeOffTogetherInfo.length > 0
            ) {
              self.showPleSeOffTogether = true;
              self.tableData = res.data.pleSeOffTogetherInfo;
            }
            if (
              res.data.pleSeOffForm.upFilePath != null &&
              res.data.pleSeOffForm.upFilePath != ""
            ) {
              self.showAttachment = true;
              self.fileUrl =
                self.global.serverPath10 +
                "/process/download?fileName=" +
                res.data.pleSeOffForm.upFileName +
                "&filePath=" +
                encodeURI(res.data.pleSeOffForm.upFilePath);
            }
            if (res.data.wordPath != null) {
              self.fujian = true;
              var downPathArr = res.data.wordPath.split(",");
              downPathArr.forEach((item, index) => {
                var downPath = item.replace(
                  "/home/apache-tomcat-8.5.40/webapps",
                  ""
                );
                self.wordPathUrl.push(this.global.serverPath6 + downPath);
              });
            }
            if (res.data.updatePleOffForm != null) {
              self.fujian3 = true;
            } else {
              self.fujian3 = false;
            }
            let leavedayss = "";
            if (res.data.basicPleaseOffForm != null) {
              leavedayss =
                Number(res.data.basicPleaseOffForm.casualLeaveDays) +
                Number(res.data.basicPleaseOffForm.sickLeaveDays) +
                Number(res.data.basicPleaseOffForm.homeLeaveDays) +
                Number(res.data.basicPleaseOffForm.marriageLeaveDays) +
                Number(res.data.basicPleaseOffForm.maternityLeaveDays) +
                Number(res.data.basicPleaseOffForm.funeralLeaveDays) +
                Number(res.data.basicPleaseOffForm.paternityLeaveDays) +
                Number(res.data.basicPleaseOffForm.annualLeaveDays) +
                Number(res.data.basicPleaseOffForm.paidLeaveDays) +
                Number(res.data.basicPleaseOffForm.statutoryLeaveDays) +
                Number(res.data.basicPleaseOffForm.otherLeaveDays);
            }
            self.formInline = {
              chanjialeixing: res.data.pleSeOffForm.maternityLeaveType,
              jiaotong: res.data.pleSeOffForm.vehicle,
              name: res.data.pleSeOffForm.applicantName,
              jinghao: res.data.pleSeOffForm.policeNo,
              danwei: res.data.pleSeOffForm.companyName,
              bumen: res.data.pleSeOffForm.deptName,
              beginTime: res.data.pleSeOffForm.beginTime,
              endTime: res.data.pleSeOffForm.endTime,
              tianshu: res.data.pleSeOffForm.leaveDay,
              lining: res.data.pleSeOffForm.isLeaveNj,
              isLateMarriage: res.data.pleSeOffForm.isLateMarriage,
              zhuti: res.data.pleSeOffForm.leaveSubject,
              applyType: res.data.pleSeOffForm.applyType,
              shiyou: res.data.pleSeOffForm.leaveCause,
              vehicle: res.data.pleSeOffForm.vehicle,
              remarks: res.data.pleSeOffForm.remarks,
              births: res.data.pleSeOffForm.births,
              address: res.data.pleSeOffForm.address,
              maternityLeaveType: res.data.pleSeOffForm.maternityLeaveType,
              isLateChildbirth: res.data.pleSeOffForm.isLateChildbirth,
              kinship: res.data.pleSeOffForm.kinship,
              workingYears: res.data.pleSeOffForm.workingYears,
              upFileName: res.data.pleSeOffForm.upFileName,
              upFilePath: res.data.pleSeOffForm.upFilePath,
              updatePleOffForm: res.data.updatePleOffForm,
              isTogether: res.data.pleSeOffForm.isTogether,
              sickLeaveDays: res.data.basicPleaseOffForm.statutoryLeaveDays,
              casualLeaveDays: res.data.basicPleaseOffForm.casualLeaveDays,
              leavedayss: leavedayss
            };
          } else if (res.code != 1) {
            self.$message.warning(res.message);
          }
        })
        .catch(err => {
          console.log("获取信息失败！");
        });
    },
    agree() {
      if (this.$route.query.isTerminate == 2 || !this.isOfficalSea) {
        this.agreeForm();
      } else {
        var self = this;
        var verseArrList = [];
        var posid = $(".posTest").attr("pos");
        var signatureCreator = Signature.create();
        signatureCreator.run({
          position: posid, //设置盖章定位dom的ID，必须设置
          okCall: function(fn, image) {
            for (var i = 0; i < $(".kg-wrapper>div").length; i++) {
              var obj = {};
              obj.title = $(".kg-wrapper>div")
                .eq(i)
                .attr("title");
              obj.src = $(".kg-wrapper img")
                .eq(2 * i)
                .attr("src");
              verseArrList.push(obj);
            }
            console.log(verseArrList);
            const _obj = {
              url: self.global.serverPath8 + "/flow/process/getImage",
              postData: {
                images: verseArrList
              },
              isGet: false
            };
            communication(_obj).then(
              res => {
                console.log(res);
                if (res.code == 0) {
                  self.iamgesArr = res.data;
                  self.xzlogBox = true;
                } else if (res.code != 1) {
                  self.$message.warning(res.message);
                }
              },
              function(res) {
                self.$message.error("请求失败");
              }
            );
          },
          cancelCall: function() {
            //点击取消后的回调方法
            console.log("取消！");
          }
        });
      }
    },
    saveBh() {
      var self = this;
      self.btnType = 2;
      if (
        self.rejectForm.rejectCon == "" ||
        self.rejectForm.rejectCon == "undefined" ||
        self.rejectForm.rejectCon == null
      ) {
        self.$message.error("请填写驳回意见");
      } else {
        self.bhVisible = false;
        this.agreeFormPop();
      }
    },
    agreeFormPop() {
      if (!this.isOfficalSea) {
        this.loading = true;
        this.agreeForm();
      } else {
        var self = this;
        var verseArrList = [];
        var posid = $(".posTest").attr("pos");
        var signatureCreator = Signature.create();
        signatureCreator.run({
          position: posid, //设置盖章定位dom的ID，必须设置
          okCall: function(fn, image) {
            for (var i = 0; i < $(".kg-wrapper>div").length; i++) {
              var obj = {};
              obj.title = $(".kg-wrapper>div")
                .eq(i)
                .attr("title");
              obj.src = $(".kg-wrapper img")
                .eq(2 * i)
                .attr("src");
              verseArrList.push(obj);
            }
            console.log(verseArrList);
            const _obj = {
              url: self.global.serverPath8 + "/flow/process/getImage",
              postData: {
                images: verseArrList
              },
              isGet: false
            };
            communication(_obj).then(
              res => {
                console.log(res);
                if (res.code == 0) {
                  self.iamgesArr = res.data;
                  self.xzlogBox = true;
                } else if (res.code != 1) {
                  self.$message.warning(res.message);
                }
              },
              function(res) {
                self.$message.error("请求失败");
              }
            );
          },
          cancelCall: function() {
            //点击取消后的回调方法
            console.log("取消！");
          }
        });
      }
    },
    timeLeft() {
      this.xzlogBoxNum--;
      if (this.xzlogBoxNum == -1) {
        this.xzlogBoxNum = $(".verseList li").length - 1;
      }
      $(".verseList li")
        .eq(this.xzlogBoxNum)
        .show()
        .siblings()
        .hide();
      console.log(this.xzlogBoxNum);
    },
    timeRight() {
      this.xzlogBoxNum++;
      if (this.xzlogBoxNum >= $(".verseList li").length) {
        this.xzlogBoxNum = 0;
      }
      $(".verseList li")
        .eq(this.xzlogBoxNum)
        .show()
        .siblings()
        .hide();
      console.log(this.xzlogBoxNum);
    },
    agreeForm() {
      var self = this;
      self.agreeFlag = true;
      if (self.btnType == 2) {
        const _obj = {
          url: self.global.serverPath8 + "/flow/process/rejected",
          postData: {
            fid: self.$route.query.fid,
            id: self.$route.query.alarmNo,
            workId: self.workId,
            applicantPeople: self.applicantPeople,
            type: "qxj",
            option: self.rejectForm.rejectCon,
            officeSealPath: $(".verseList img")
              .eq(this.xzlogBoxNum)
              .attr("src")
            // officeSealPath:
            //   "data:image/png;base64,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"
          },
          isGet: false
        };
        communication(_obj).then(
          res => {
            self.agreeFlag = false;
            console.log(res);
            if (res.code == -1) {
              self.$message.error("请配置下一步审批人");
            } else if (res.code == 0) {
              self.$message.success("保存成功");
              self.xzlogBox = false;
              this.$router.push("/approveManage");
            } else if (res.code != 1) {
              self.$message.error(res.message);
            }
          },
          function(res) {
            self.agreeFlag = false;
            self.$message.error("请求失败");
          }
        );
      } else if (self.btnType == 1) {
        const _obj = {
          url: self.global.serverPath8 + "/flow/process/agreeForm",
          postData: {
            fid: self.$route.query.fid,
            id: self.$route.query.alarmNo,
            workId: self.workId,
            applicantPeople: self.applicantPeople,
            type: "qxj",
            option: "",
            officeSealPath: $(".verseList img")
              .eq(this.xzlogBoxNum)
              .attr("src")
            // officeSealPath:
            //   "data:image/png;base64,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"
          },
          isGet: false
        };
        communication(_obj).then(
          res => {
            self.agreeFlag = false;
            console.log(res);
            if (res.code == 0) {
              if (res.data.ccflowSuccess == null) {
                self.$message.error("请设置审批人");
                self.loading = false;
              } else {
                self.$message.success("保存成功");
                self.xzlogBox = false;
                this.$router.push("/approveManage");
              }
            } else if (res.code != 1) {
              self.$message.error(res.message);
            }
          },
          function(res) {
            self.agreeFlag = false;
            self.$message.error("请求失败");
          }
        );
      }
      this.loading = false;
    },
    bohuiqueding() {
      var self = this;
      self.$http
        .post(
          self.global.serverPath4 + "/process_engine/PleaseSellOff/rejected",
          {
            //传递参数
            applyFormId: self.$route.query.alarmNo,
            personId: localStorage.getItem("loginpersonId"),
            opion: self.rejectForm.rejectCon
          }
        )
        .then(
          function(res) {
            this.$message.success("驳回成功");
            self.$router.push("/approveManage");
          },
          function(res) {
            self.$message.error("请求失败！");
          }
        );
    }
  }
};
</script>
<style scoped>
/*表格内容居中*/
.el-table td,
.el-table th {
  text-align: center;
}
.leaveApprovalBtn {
  padding-top: 0;
}
</style>
<style>
.LeaExamApproval .visible .el-dialog {
  background: url("../../../static/img/index/scoreBg.png") no-repeat center;
  background-size: 100% 100%;
  height: 460px;
  -webkit-box-shadow: none !important;
  box-shadow: none !important;
}
.LeaExamApproval .visible .el-dialog__headerbtn .el-dialog__close {
  color: #1890ff;
  font-size: 22px;
  font-weight: 900;
  position: relative;
  top: 23px;
  right: 23px;
}
.LeaExamApproval .visible .el-dialog__body .el-textarea__inner {
  height: 100px !important;
}
.LeaExamApproval .visible .el-dialog__body .el-row {
  margin-bottom: 10px;
}
.LeaExamApproval .visible .el-dialog__body {
  padding-bottom: 10px !important;
}
.LeaExamApproval .visible .el-dialog__footer {
  text-align: center;
}
.LeaExamApproval .visible .el-dialog__body .el-row .rateSpan {
  display: inline-block;
  width: 100px;
  text-align: left;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}
.applicantInfo {
  overflow: hidden;
  background: #f0f0f0;
}
.info {
  margin: 15px;
  background: white;
}
.submit {
  color: white;
}
.rejectLabel {
}
.rejectProp .el-form-item__content {
  margin-left: 75px !important;
  text-align: right;
}
.rejectBtn {
  padding: 9px 15px;
  border: 1px solid #dcdfe6;
  color: #606266;
}
.rejectBtn:hover {
  border-color: rgb(32, 160, 255) !important;
  color: rgb(32, 160, 255) !important;
}
.rejectProp .el-form-item__label {
  width: 75px !important;
}
.el-textarea__inner {
  height: 80px;
}
.applicantInfo .grid-content {
  padding: 15px;
}
.applyNum {
  padding: 10px 15px;
  background: white;
}
.apply {
  float: left;
}
.step {
  width: 90%;
  margin: 0 auto;
}
.stepCon .el-step__icon-inner[class*="el-icon"]:not(.is-status) {
  font-size: 16px !important;
}
.applyNum .apply {
  /*background: url(../../assets/leave/esInfor.png) no-repeat;*/
  background-position: 10px;
  height: 30px;
  line-height: 30px;
  padding-left: 45px;
}
.gridright {
  float: right;
  text-align: right;
}
.stepCon {
  width: 100%;
  overflow: hidden;
  background: white;
  margin: 0 auto;
}
.downLoad {
  color: #409eff;
}
.downLoad:hover {
  cursor: pointer;
}
.processBarLead {
  display: flex;
  flex-direction: row;
  width: 90%;
  margin: 0 auto;
  height: 130px;
  position: relative;
  text-align: center;
  font-size: 12px;
}
.processBarLead > div {
  position: absolute;
  height: 65px;
}
.processBarLead .alone {
  position: relative;
  height: 100%;
  width: 100%;
  margin-left: 10px;
}
.processBarLead > div:nth-of-type(1) {
  height: 130px;
}
.processBarLead > div:nth-of-type(2) {
  height: 130px;
}
.processBarLead .circle {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  height: 14px;
}
.processBarLead > div:nth-of-type(7) {
  height: 130px;
}
.processBarLead > div:nth-of-type(8) {
  height: 130px;
}
.processBarLead .dept {
  height: 14px;
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
}
.processBarLead .leadName {
  position: absolute;
  left: 0;
  top: 65%;
  padding-left: 20px;
}
.processBarLead .leadDate {
  position: absolute;
  left: 0;
  top: 84%;
  padding-left: 10px;
}
.processBarLead .deptName {
  text-align: center;
  background: white;
  z-index: 10;
  padding: 0 10px;
}
.processBarLead .yuan {
  height: 10px;
  width: 10px;
  border-radius: 5px;
  background: gray;
  display: inline-block;
}
.processBarLead .xian {
  height: 1px;
  background: gray;
  position: absolute;
  top: 51%;
  transform: translateY(-50%);
  width: 100%;
}
.aloneF:last-child .xian {
  display: none;
}
.processBarLead .green {
  color: #409eff;
}
.processBarLead .green .xian {
  background: #409eff;
}
.processBarLead .green .yuan {
  background: #409eff;
}
.processBarLead .red {
  color: red;
}
.processBarLead .red .xian {
  background: red;
}
.processBarLead .red .yuan {
  background: red;
}
.LeaveManCon {
  display: flex;
  flex-direction: row;
  width: 90%;
  margin: 0 auto;
}
.LeaveManCon > div {
  flex: 1;
  position: relative;
  height: 130px;
  overflow: hidden;
}
.LeaveManCon .multiple {
  display: flex;
  flex-direction: column;
}
.LeaveManCon .daughter {
  flex: 1;
  position: relative;
}
.LeaveManCon .circleDot,
.roundDot {
  position: absolute;
  background: white;
  z-index: 10;
}
.LeaveManCon .circle {
  position: relative;
  width: 100%;
  height: 100%;
  padding-left: 20px;
  padding-right: 20px;
  font-size: 12px;
  white-space: nowrap;
}
.LeaveManCon .circle > div {
  position: absolute;
  width: 10px;
  height: 10px;
  left: 5px;
  top: 50%;
  transform: translateY(-50%);
  background: #eee;
  border-radius: 5px;
}
.LeaveManCon .circleDot {
  left: 50%;
  transform: translate(-50%, -50%);
  top: 50%;
}
.LeaveManCon .roundDot {
  top: 50%;
  left: 5%;
  transform: translate(-50%, -50%);
}
.LeaveManCon .data {
  position: absolute;
  top: 50%;
  left: 10%;
  transform: translateY(-50%);
  font-size: 12px;
  min-width: 180px;
  background: white;
}
.LeaveManCon .name {
  position: absolute;
  top: 50%;
  right: 10%;
  transform: translateY(-50%);
  font-size: 12px;
}
.LeaveManCon .finishColor {
  background: #3385ff;
}
.LeaveManCon .procedure {
  background: #eee;
}
.LeaveManCon .contentCon {
  width: 100%;
  position: absolute;
  left: 50%;
  top: 105px;
  transform: translate(-50%, -50%);
  text-align: center;
}
.LeaveManCon .alone p {
  font-size: 12px;
  line-height: 20px;
  font-weight: 700;
}
.LeaveManCon > div .figure {
  width: 100%;
  height: 2px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}
.LeaveManCon > div:first-child .figure {
  width: calc(50% - 20px);
  height: 2px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 0;
}
.LeaveManCon > div:last-child .figure {
  width: calc(50% - 20px);
  height: 2px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  left: 0;
}
.LeaveManCon .multiple .figure {
  position: absolute;
  width: calc(100% - 20px);
  height: 2px;
  top: 50%;
  transform: translateY(-50%);
  left: 50%;
}
.gray .circle div {
  background: gray;
}
.gray .figure {
  background: gray;
}
.gray .contentCon p {
  color: gray;
}
.green .circle div {
  background: #409eff;
}
.green .figure {
  background: #409eff;
}
.green .contentCon {
  color: #409eff;
}
.red .circle div {
  background: #f56c6c;
}
.red .figure {
  background: #f56c6c;
}
.red .contentCon {
  color: #f56c6c;
}
.green .circle {
  color: #409eff;
}
.red .circle {
  color: #f56c6c;
}
.gray .circle {
  color: gray;
}

.titIcon .btn-group {
  margin-top: 0;
}

.cjglBtn {
  margin: 0 !important;
}
</style>
<style>
/*在获取到的样式里加上能限制范围的父层选择器，不然就变成全局样式  */
.ui-dialog {
  *zoom: 1;
  _float: left;
  position: relative;
  background-color: #fff;
  border: 1px solid #999;
  border-radius: 6px;
  outline: 0;
  background-clip: padding-box;
  font-family: Helvetica, arial, sans-serif;
  font-size: 14px;
  line-height: 1.428571429;
  color: #333;
  opacity: 0;
  -webkit-transform: scale(0);
  transform: scale(0);
  -webkit-transition: -webkit-transform 0.15s ease-in-out,
    opacity 0.15s ease-in-out;
  transition: transform 0.15s ease-in-out, opacity 0.15s ease-in-out;
}
.ui-popup-show .ui-dialog {
  opacity: 1;
  -webkit-transform: scale(1);
  transform: scale(1);
}
.ui-popup-focus .ui-dialog {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
}
.ui-popup-modal .ui-dialog {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1), 0 0 256px rgba(255, 255, 255, 0.3);
}
.ui-dialog-grid {
  width: auto;
  margin: 0;
  border: 0 none;
  border-collapse: collapse;
  border-spacing: 0;
  background: transparent;
}
.ui-dialog-header,
.ui-dialog-body,
.ui-dialog-footer {
  padding: 0;
  border: 0 none;
  text-align: left;
  background: transparent;
}
.ui-dialog-header {
  white-space: nowrap;
  border-bottom: 1px solid #e5e5e5;
}
.ui-dialog-close {
  position: relative;
  _position: absolute;
  float: right;
  top: 13px;
  right: 13px;
  _height: 26px;
  padding: 0 4px;
  font-size: 21px;
  font-weight: bold;
  line-height: 1;
  color: #000;
  text-shadow: 0 1px 0 #fff;
  opacity: 0.2;
  filter: alpha(opacity=20);
  cursor: pointer;
  background: transparent;
  _background: #fff;
  border: 0;
  -webkit-appearance: none;
}
.ui-dialog-close:hover,
.ui-dialog-close:focus {
  color: #000000;
  text-decoration: none;
  cursor: pointer;
  outline: 0;
  opacity: 0.5;
  filter: alpha(opacity=50);
}
.ui-dialog-title {
  margin: 0;
  line-height: 1.428571429;
  min-height: 16.428571429px;
  padding: 15px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  font-weight: bold;
  cursor: default;
}
.ui-dialog-body {
  padding: 20px;
  text-align: center;
}
.ui-dialog-content {
  display: inline-block;
  position: relative;
  vertical-align: middle;
  text-align: left;
}
.ui-dialog-footer {
  padding: 0 20px 20px 20px;
}
.ui-dialog-statusbar {
  float: left;
  margin-right: 20px;
  padding: 6px 0;
  line-height: 1.428571429;
  font-size: 14px;
  color: #888;
  white-space: nowrap;
}
.ui-dialog-statusbar label:hover {
  color: #333;
}
.ui-dialog-statusbar input,
.ui-dialog-statusbar .label {
  vertical-align: middle;
}
.ui-dialog-button {
  float: right;
  white-space: nowrap;
}
.ui-dialog-footer button + button {
  margin-bottom: 0;
  margin-left: 5px;
}
.ui-dialog-footer button {
  width: auto;
  overflow: visible;
  display: inline-block;
  padding: 6px 12px;
  _margin-left: 5px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;
}

.ui-dialog-footer button:focus {
  outline: thin dotted #333;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}

.ui-dialog-footer button:hover,
.ui-dialog-footer button:focus {
  color: #333333;
  text-decoration: none;
}

.ui-dialog-footer button:active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
  box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
}
.ui-dialog-footer button[disabled] {
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.65;
  filter: alpha(opacity=65);
  -webkit-box-shadow: none;
  box-shadow: none;
}

.ui-dialog-footer button {
  color: #333333;
  background-color: #ffffff;
  border-color: #cccccc;
}

.ui-dialog-footer button:hover,
.ui-dialog-footer button:focus,
.ui-dialog-footer button:active {
  color: #333333;
  background-color: #ebebeb;
  border-color: #adadad;
}

.ui-dialog-footer button:active {
  background-image: none;
}

.ui-dialog-footer button[disabled],
.ui-dialog-footer button[disabled]:hover,
.ui-dialog-footer button[disabled]:focus,
.ui-dialog-footer button[disabled]:active {
  background-color: #ffffff;
  border-color: #cccccc;
}

.ui-dialog-footer button.ui-dialog-autofocus {
  color: #ffffff;
  background-color: #428bca;
  border-color: #357ebd;
}

.ui-dialog-footer button.ui-dialog-autofocus:hover,
.ui-dialog-footer button.ui-dialog-autofocus:focus,
.ui-dialog-footer button.ui-dialog-autofocus:active {
  color: #ffffff;
  background-color: #3276b1;
  border-color: #285e8e;
}

.ui-dialog-footer button.ui-dialog-autofocus:active {
  background-image: none;
}
.ui-popup-top-left .ui-dialog,
.ui-popup-top .ui-dialog,
.ui-popup-top-right .ui-dialog {
  top: -8px;
}
.ui-popup-bottom-left .ui-dialog,
.ui-popup-bottom .ui-dialog,
.ui-popup-bottom-right .ui-dialog {
  top: 8px;
}
.ui-popup-left-top .ui-dialog,
.ui-popup-left .ui-dialog,
.ui-popup-left-bottom .ui-dialog {
  left: -8px;
}
.ui-popup-right-top .ui-dialog,
.ui-popup-right .ui-dialog,
.ui-popup-right-bottom .ui-dialog {
  left: 8px;
}

.ui-dialog-arrow-a,
.ui-dialog-arrow-b {
  position: absolute;
  display: none;
  width: 0;
  height: 0;
  overflow: hidden;
  border: 8px dashed transparent;
}
.ui-popup-follow .ui-dialog-arrow-a,
.ui-popup-follow .ui-dialog-arrow-b {
  display: block;
}
.ui-popup-top-left .ui-dialog-arrow-a,
.ui-popup-top .ui-dialog-arrow-a,
.ui-popup-top-right .ui-dialog-arrow-a {
  bottom: -16px;
  border-top: 8px solid #7c7c7c;
}
.ui-popup-top-left .ui-dialog-arrow-b,
.ui-popup-top .ui-dialog-arrow-b,
.ui-popup-top-right .ui-dialog-arrow-b {
  bottom: -15px;
  border-top: 8px solid #fff;
}
.ui-popup-top-left .ui-dialog-arrow-a,
.ui-popup-top-left .ui-dialog-arrow-b {
  left: 15px;
}
.ui-popup-top .ui-dialog-arrow-a,
.ui-popup-top .ui-dialog-arrow-b {
  left: 50%;
  margin-left: -8px;
}
.ui-popup-top-right .ui-dialog-arrow-a,
.ui-popup-top-right .ui-dialog-arrow-b {
  right: 15px;
}
.ui-popup-bottom-left .ui-dialog-arrow-a,
.ui-popup-bottom .ui-dialog-arrow-a,
.ui-popup-bottom-right .ui-dialog-arrow-a {
  top: -16px;
  border-bottom: 8px solid #7c7c7c;
}
.ui-popup-bottom-left .ui-dialog-arrow-b,
.ui-popup-bottom .ui-dialog-arrow-b,
.ui-popup-bottom-right .ui-dialog-arrow-b {
  top: -15px;
  border-bottom: 8px solid #fff;
}
.ui-popup-bottom-left .ui-dialog-arrow-a,
.ui-popup-bottom-left .ui-dialog-arrow-b {
  left: 15px;
}
.ui-popup-bottom .ui-dialog-arrow-a,
.ui-popup-bottom .ui-dialog-arrow-b {
  margin-left: -8px;
  left: 50%;
}
.ui-popup-bottom-right .ui-dialog-arrow-a,
.ui-popup-bottom-right .ui-dialog-arrow-b {
  right: 15px;
}
.ui-popup-left-top .ui-dialog-arrow-a,
.ui-popup-left .ui-dialog-arrow-a,
.ui-popup-left-bottom .ui-dialog-arrow-a {
  right: -16px;
  border-left: 8px solid #7c7c7c;
}
.ui-popup-left-top .ui-dialog-arrow-b,
.ui-popup-left .ui-dialog-arrow-b,
.ui-popup-left-bottom .ui-dialog-arrow-b {
  right: -15px;
  border-left: 8px solid #fff;
}
.ui-popup-left-top .ui-dialog-arrow-a,
.ui-popup-left-top .ui-dialog-arrow-b {
  top: 15px;
}
.ui-popup-left .ui-dialog-arrow-a,
.ui-popup-left .ui-dialog-arrow-b {
  margin-top: -8px;
  top: 50%;
}
.ui-popup-left-bottom .ui-dialog-arrow-a,
.ui-popup-left-bottom .ui-dialog-arrow-b {
  bottom: 15px;
}
.ui-popup-right-top .ui-dialog-arrow-a,
.ui-popup-right .ui-dialog-arrow-a,
.ui-popup-right-bottom .ui-dialog-arrow-a {
  left: -16px;
  border-right: 8px solid #7c7c7c;
}
.ui-popup-right-top .ui-dialog-arrow-b,
.ui-popup-right .ui-dialog-arrow-b,
.ui-popup-right-bottom .ui-dialog-arrow-b {
  left: -15px;
  border-right: 8px solid #fff;
}
.ui-popup-right-top .ui-dialog-arrow-a,
.ui-popup-right-top .ui-dialog-arrow-b {
  top: 15px;
}
.ui-popup-right .ui-dialog-arrow-a,
.ui-popup-right .ui-dialog-arrow-b {
  margin-top: -8px;
  top: 50%;
}
.ui-popup-right-bottom .ui-dialog-arrow-a,
.ui-popup-right-bottom .ui-dialog-arrow-b {
  bottom: 15px;
}

@-webkit-keyframes ui-dialog-loading {
  0% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
  }
}
@keyframes ui-dialog-loading {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.ui-dialog-loading {
  vertical-align: middle;
  position: relative;
  display: block;
  *zoom: 1;
  *display: inline;
  overflow: hidden;
  width: 32px;
  height: 32px;
  top: 50%;
  margin: -16px auto 0 auto;
  font-size: 0;
  text-indent: -999em;
  color: #666;
}
.ui-dialog-loading {
  width: 100%\9;
  text-indent: 0\9;
  line-height: 32px\9;
  text-align: center\9;
  font-size: 12px\9;
}

.ui-dialog-loading::after {
  position: absolute;
  content: "";
  width: 3px;
  height: 3px;
  margin: 14.5px 0 0 14.5px;
  border-radius: 100%;
  box-shadow: 0 -10px 0 1px #ccc, 10px 0px #ccc, 0 10px #ccc, -10px 0 #ccc,
    -7px -7px 0 0.5px #ccc, 7px -7px 0 1.5px #ccc, 7px 7px #ccc, -7px 7px #ccc;
  -webkit-transform: rotate(360deg);
  -webkit-animation: ui-dialog-loading 1.5s infinite linear;
  transform: rotate(360deg);
  animation: ui-dialog-loading 1.5s infinite linear;
  display: none;
}
.kg-show {
  display: block;
}

.kg-visble {
  visibility: hidden;
}

.kg-hide {
  display: none;
}

.kg-default {
  position: absolute;
  z-index: 89;
}

.display-landscape {
  position: static;
  overflow: auto;
}

.kg-landscape-container {
  position: relative;
}

.kg-landscape-container .kg-landscape {
  position: relative;
  display: inline-block;
  *display: inline;
  vertical-align: middle;
}

.kg-invalid {
  position: absolute;
  top: 33%;
  left: 0px;
  height: 33%;
  width: 100%;
  z-index: 90;
}
.kg-invalid .kg-invalid-item {
  width: 100%;
  position: relative;
}

.kg-shade {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.kg-date {
  position: absolute;
}

.kg-date-content {
  position: relative;
}

.kg-date-tmb {
  position: absolute;
}

.kg-date-lcr {
  min-width: 110px;
}
.kg-img-div:hover {
  cursor: pointer;
}

.kg-img-div .kg-img-icons {
  display: none;
  text-decoration: none;
  margin: 0px;
  padding: 0px;
  position: absolute;
  top: 0px;
  right: 0;
  z-index: 92;
  background-color: #d5d5d5;
  opacity: 0.85;
  filter: alpha(opacity=85);
  text-align: center;
  vertical-align: middle;
  border: 1px #adadad solid;
}

.kg-img-div:hover .kg-img-icons {
  display: block;
}

.kg-img-div .kg-img-icons .kg-img-icon {
  cursor: pointer;
  display: block;
  float: left;
  margin: 5px 3px 3px 3px;
}

.kg-img-div .kg-img-icons .kg-img-icon i {
  display: inline-block;
  width: 20px;
  height: 20px;
}

.kg-select {
  width: 100%;
  padding-top: 4px;
  margin-bottom: 0;
  margin-top: 3px;
  border: 1px solid #cccccc;
}

.kg-color-ul {
  padding: 0;
  margin: 0;
  list-style: none;
  width: 148px;
  border: 1px solid #cccccc;
  display: none;
}
.kg-color-ul li {
  background-color: #ffffff;
}
.kg-color-ul li a {
  display: inline-block;
  width: 148px;
  height: 16px;
  text-decoration: none;
}
.kg-color-div {
  width: 150px;
  height: 100px;
  overflow-y: scroll;
  overflow-x: hidden;
  position: absolute;
  z-index: 999999;
  display: none;
}

.kg-select-color {
  width: 100%;
  height: 21px;
  margin-top: 4px;
  border: 1px solid #cccccc;
}
.kg-group {
  display: inline-block;
  *display: inline;
  zoom: 1;
  margin-bottom: 0;
  vertical-align: middle;
  text-align: center;
  margin: 0 auto;
  width: 100%;
}
.kg-group label {
  display: inline-block;
  max-width: 30%;
  text-align: right;
  margin-bottom: 5px;
  font-weight: 700;
}
.kg-group .kg-control {
  display: inline-block;
  width: 135px;
  vertical-align: middle;
  height: 20px;
  padding: 6px 12px 6px 0px;
  font-size: 14px;
  line-height: 25px;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border-color ease-in-out 0.15s,
    -webkit-box-shadow ease-in-out 0.15s;
  -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}
.kg-switcher {
  width: 180px;
  height: 180px;
  padding: 10px 45px 25px 45px;
  margin: 0px auto;
  position: relative;
  display: none;
}
.kg-container {
  margin: 0 auto;
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 100%;
  color: #fff;
  text-align: center;
  backface-visibility: hidden;
  z-index: 1;
  border: 1px solid #dddddd;
}

.arrow-right {
  position: absolute;
  right: 8px;
  top: 50%;
  margin-top: -15px;
  text-decoration: none;
  display: block;
  z-index: 10;
}

.arrow-left {
  position: absolute;
  left: 8px;
  top: 50%;
  margin-top: -15px;
  text-decoration: none;
  display: block;
  z-index: 10;
}

.arrow-left .kg-icon,
.arrow-right .kg-icon {
  display: inline-block;
  width: 20px;
  height: 20px;
}
.kg-wrapper {
  position: relative;
  width: 100%;
  box-sizing: content-box;
}

.kg-slide {
  float: left;
  width: 172px;
  height: 172px;
  position: relative;
  margin-left: 11px;
  margin-top: 13px;
}

.kg-image {
  width: 158px;
  height: 158px;
  overflow: hidden;
  text-align: center;
  display: table;
  float: left;
  margin: 10px;
  position: relative;
  table-layout: fixed;
}

.kg-image:hover {
  border: 1px solid #beceeb;
}

.kg-image a.kg-img {
  display: table-cell;
  vertical-align: middle;
  width: 150px;
  height: 150px;
  max-width: 150px;
  max-height: 150px;
}

.kg-image .kg-img img {
  border: 0;
  margin: 0 auto;
  max-width: 150px;
  max-height: 150px;
}

.kg-slide div.title {
  position: absolute;
  text-align: center;
  display: none;
}

.kg-img-checked a.kg-img {
  border: 5px solid #0088cc;
}

.kg-pagin {
  position: absolute;
  text-align: center;
  bottom: 0px;
  text-align: center;
  width: 100%;
  left: 0;
  -webkit-transition: 0.3s;
  -moz-transition: 0.3s;
  -o-transition: 0.3s;
  transition: 0.3s;
  -webkit-transform: translate3d(0, 0, 0);
  -ms-transform: translate3d(0, 0, 0);
  -o-transform: translate3d(0, 0, 0);
  transform: translate3d(0, 0, 0);
  z-index: 10;
}

.kg-pagin .kg-guide {
  text-align: center;
  width: auto;
}
.kg-pagin .kg-guide span {
  display: inline-block;
  width: 8px;
  height: 8px;
  margin: 0 2px;
  background: #cccccc;
  cursor: pointer;
  font-size: 14px;
  border-radius: 100%;
}

.kg-guide span.active {
  opacity: 1;
  background: #ec3535;
}

.kg-title .kg-icon {
  display: inline-block;
  width: 48px;
  height: 48px;
  vertical-align: -18px;
  margin-left: 10px;
}
.kg-loading {
  text-align: center;
  padding: 20px 30px 15px 30px;
}
.kg-alert {
  min-width: 250px;
  padding-left: 20px;
  padding-right: 10px;
  margin-top: 10px;
  margin-bottom: 10px;
}

.kg-alert .kg-alert-icon,
.kg-alert .kg-alert-msg {
  float: left;
  height: 100%;
  vertical-align: middle;
}

.kg-alert .kg-alert-msg {
  vertical-align: middle;
  display: table-cell;
  width: 200px;
}
.success,
.danger {
  font-weight: bold;
}

.kg-title.danger .kg-icon {
  color: #e75033;
  text-decoration: none;
}

.clearfix:after {
  content: ".";
  display: block;
  height: 0;
  visibility: hidden;
  clear: both;
}
.clearfix {
  *zoom: 1;
}

.kg-content {
  padding: 10px;
  border-top: none;
}

/**
标签tab css
*/
.kg-tab {
}

.kg-tab .kg-nav {
  padding: 0;
  margin: 0;
  list-style: none;
  border-bottom: 1px solid #ddd;
}

.kg-nav > li {
  float: left;
  margin-bottom: -1px;
  position: relative;
  display: block;
  list-style: none;
}

.kg-nav > li > a {
  position: relative;
  display: block;
  padding: 8px 15px;
  color: #353535;
  -webkit-transition: all 0.2s cubic-bezier(0.175, 0.885, 0.32, 1);
  transition: all 0.2s cubic-bezier(0.175, 0.885, 0.32, 1);
  margin-right: 2px;
  text-decoration: none;
  line-height: 1.53846154;
  border: 1px solid transparent;
  border-radius: 4px 4px 0 0;
}

.kg-nav > li.active > a,
.kg-nav > li.active > a:focus,
.kg-nav > li.active > a:hover {
  color: grey;
  cursor: default;
  background-color: #fff;
  border: 1px solid #ddd;
  border-bottom-color: transparent;
}

.kg-tab-content {
  height: 230px;
  overflow: auto;
  border: 1px #ddd solid;
  border-top: none;
}

.kg-tab-content .kg-tab-pane {
  display: none;
}

.kg-tab-content > .active {
  display: block;
}

.kg-meta {
  padding: 10px 10px 0px 10px;
}

.kg-meta .kg-item {
  margin-top: 3px;
}

.kg-meta .kg-label {
  width: 16%;
  float: left;
  display: inline-block;
  text-align: right;
}
.kg-meta .kg-value {
  width: 84%;
  float: left;
  display: inline-block;
  text-align: left;
  word-break: break-all;
}

.kg-sm {
  position: relative;
  min-height: 1px;
  float: left;
}
.kg-sm-12 {
  width: 100%;
}
.kg-sm-11 {
  width: 91.66666667%;
}
.kg-sm-10 {
  width: 83.33333333%;
}
.kg-sm-9 {
  width: 75%;
}
.kg-sm-8 {
  width: 66.66666667%;
}
.kg-sm-7 {
  width: 58.33333333%;
}
.kg-sm-6 {
  width: 50%;
}
.kg-sm-5 {
  width: 41.66666667%;
}
.kg-sm-4 {
  width: 33.33333333%;
}
.kg-sm-3 {
  width: 25%;
}
.kg-sm-2 {
  width: 16.66666667%;
}

.kg-form {
  width: 280px;
  margin: 20px 0;
}

.kg-form .form-item .kg-label {
  display: inline-block;
  max-width: 100%;
  margin-bottom: 5px;
  padding-top: 7px;
  margin-bottom: 0;
  text-align: right;
}

.kg-form-static {
  margin: 0 0 10px;
  min-height: 34px;
  padding-top: 7px;
  margin-bottom: 0;
}

.form-control {
  display: block;
  padding: 6px 0px;
  font-size: 14px;
  width: 100%;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border-color ease-in-out 0.15s,
    -webkit-box-shadow ease-in-out 0.15s;
  -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}
.kg-dialog * {
  box-sizing: content-box;
  -moz-box-sizing: content-box;
  -ms-box-sizing: content-box;
  -webkit-box-sizing: content-box;
}

.kg-dialog-info {
  width: 670px;
  padding-bottom: 20px;
}

.kg-dialog-password {
  width: 300px;
  font-size: 14px;
  padding-left: 15px;
  padding-right: 15px;
}
.kg-dialog-Date {
  width: 300px;
  height: 160px;
  font-size: 14px;
  display: block;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border-color ease-in-out 0.15s,
    -webkit-box-shadow ease-in-out 0.15s;
  -o-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}

.kg-button {
  width: auto;
  overflow: visible;
  display: inline-block;
  padding: 6px 12px;
  _margin-left: 5px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.428571429;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;

  color: #ffffff;
  background-color: #c62929;
  border-color: #d00a0a;
}

.ui-dialog-body {
  padding: 10px;
}
.ui-dialog-button {
  float: none;
  text-align: center;
  white-space: nowrap;
}

.ui-dialog-footer button {
  letter-spacing: 5px;
  text-indent: 5px;
  text-align: center;
}

.ui-dialog-footer button.ui-dialog-autofocus {
  background-color: #c62929;
  border-color: #ca2121;
}
.ui-dialog-footer button.ui-dialog-autofocus:hover,
.ui-dialog-footer button.ui-dialog-autofocus:focus,
.ui-dialog-footer button.ui-dialog-autofocus:active {
  color: #ffffff;
  background-color: #c62929;
  border-color: #d00a0a;
}
.i-addDate {
  display: none;
}
#kg-switcher > a {
  display: none;
}
.kg-pagin {
  display: none;
}
.ui-dialog-button > button:first-child {
  display: none;
}
.ui-dialog-autofocus {
  border-color: #409eff !important;
  background: #409eff !important;
}
.ms-login .el-form-item__label {
  color: #eee;
}

.ms-login .el-input__inner {
  background: transparent;
  border-radius: 16px;
  color: #eee;
}

.ms-login .el-tabs__item.is-active {
  color: #fff;
}

.ms-login .el-tabs__item {
  color: #5b6979;
  font-size: 16px;
}

.ms-login .el-tabs--card > .el-tabs__header {
  border: none;
  width: 80%;
  margin: 4% auto;
}

.ms-login .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
  border-bottom: 2px solid #3b82ff;
}

.ms-login .el-tabs--card > .el-tabs__header .el-tabs__nav {
  border: none;
}

.ms-login .el-tabs--card > .el-tabs__header .el-tabs__item {
  border: none;
}
/*!
 * ui-dialog.css
 * Date: 2014-07-03
 * https://github.com/aui/artDialog
 * (c) 2009-2014 TangBin, http://www.planeArt.cn
 *
 * This is licensed under the GNU LGPL, version 2.1 or later.
 * For details, see: http://www.gnu.org/licenses/lgpl-2.1.html
 */
.ms-login .el-form-item__label {
  width: 70px !important;
}
.ms-login .el-form-item__content {
  margin-left: 70px !important;
}

/*选章弹框*/
.xzlogBox .xzlog {
  width: 800px;
  height: 400px;
  background: white;
  margin: 50px auto;
}
.verse {
  position: relative;
}
.verseTitle {
  padding: 17px 20px 18px;
  border-bottom: 1px solid #ebeef5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  font-size: 18px;
  color: #262626;
  font-weight: bold;
}
.xzlogBtn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  border-radius: 20px;
  background: rgba(0, 0, 0, 0.1);
}
.xzlogBtn:hover {
  cursor: pointer;
}
.xzlogBtn i {
  font-size: 40px;
}
.xzlogLeftBtn {
  left: 10px;
}
.xzlogRightBtn {
  right: 10px;
}
.verseList {
  overflow: auto;
  height: 250px;
  width: 250px;
  margin: 0 auto;
  background: #eee;
}
.verseList ul {
  position: relative;
  height: 100%;
  width: 100%;
}
.xzlogBox li {
  height: 100%;
  width: 100%;
  position: absolute;
  list-style: none;
  display: none;
}
.xzlogBox li div {
  position: relative;
  width: 100%;
  height: 100%;
}
.xzlogBox li img {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 80%;
}
.xzlogBox li:first-child {
  display: block;
}
.xzlogFooter {
  text-align: right;
  padding: 0 20px;
}
</style>
