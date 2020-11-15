<template>
    <div class="cjgl Dashboard3 dashCard3" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）销假</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <div class="DashbCon"></div>
                            </el-card>
                            <input v-show="pos" type="button" pos="pos0" class="posTest" value="电子签章">
                            <el-card class="card2" style="margin-bottom:15px">
                                <div slot="header" class="clearfix">
                                    <span>审批提醒信息</span>
                                </div>
                                <div class="sqxx-info3">
                                    <div class="handle-box3">
                                        <el-form :inline="true" label-position="left" label-width="" ref='txxxForm' :model="txxxForm" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:18px">
                                                <el-col :span="24" class="redtip">
                                                    <span>处级领导干部申请出国（境）旅游，一般2年内不超过1次，历史出国（境）记录显示距离本次申请为超过2年时间</span>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="24" v-for="(item,index) in txxxForm.formInfo" :key="index" style="margin-bottom:0">
                                                <el-col :span="6" class="bordernone" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="出国（境）历史记录：" prop="applicationTime">
                                                        <el-input v-model="item.applicationTime" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="5" class="bordernone" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="离境形式：" prop="departureForm" @change="changeStyle()">
                                                        <el-input v-model="item.departureForm" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="7" class="bordernone" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="目的地：" prop="destinationBroad">
                                                        <el-input v-model="item.destinationBroad" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6" class="bordernone" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="同行人员：" prop="isField">
                                                        <el-input v-model="item.isField" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <!-- <el-col :span="5" class="bordernone" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="申请单号：" prop="applicationFormId">
                                                        <el-input v-model="item.applicationFormId" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col> -->
                                            </el-row>
                                        </el-form>
                                    </div>
                                </div>
                            </el-card>
                            <el-card class="card2">
                                <div slot="header" class="clearfix">
                                    <span>申请信息</span>
                                </div>
                                <div class="sqxx-info3">
                                    <!--申请信息表单  -->
                                    <div class="handle-box3">
                                        <el-form :inline="true" label-position="left" label-width="" ref='sqxxForm' :model="sqxxForm" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="姓名：" prop="name">
                                                        <el-input v-model="sqxxForm.name" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="警号：" prop="policeNo">
                                                        <el-input v-model="sqxxForm.policeNo" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="工作单位：" prop="companyName">
                                                        <el-input v-model="sqxxForm.companyName"  placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="部门：" prop="deptName">
                                                        <el-input v-model="sqxxForm.deptName" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="开始时间：" prop="startTime" class="startTimeDate">
                                                        <el-date-picker v-model="sqxxForm.startTime" format='yyyy-MM-dd' style="width: 98%;" value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="结束时间：" prop="endTime" class="startTimeDate">
                                                        <el-date-picker v-model="sqxxForm.endTime" style="width:98%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="目的地：">
                                                        <el-select v-model="sqxxForm.destinationBroad" multiple placeholder="请选择">
                                                            <el-option v-for="item in options" :key="item.id" :label="item.text" :value="item.id" ></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="离境形式：" prop="departureForm">
                                                        <el-select v-model="sqxxForm.departureForm" >
                                                            <el-option key="跟团" label="跟团" value="跟团"></el-option>
                                                            <el-option key="自由行" label="自由行" value="自由行"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="离境原因：" prop="departureReason">
                                                        <el-select v-model="sqxxForm.departureReason">
                                                            <el-option key="旅游" label="旅游" value="旅游"></el-option>
                                                            <el-option key="探亲" label="探亲" value="探亲"></el-option>
                                                            <el-option key="看病" label="看病" value="看病"></el-option>
                                                            <el-option key="子女入学毕业" label="子女入学毕业" value="子女入学毕业"></el-option>
                                                            <el-option key="参会" label="参会" value="参会"></el-option>
                                                            <el-option key="其他" label="其他" value="其他"></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                 <el-col :span="8">
                                                    <el-form-item label="经停地：">
                                                        <el-select v-model="sqxxForm.passPlace" multiple placeholder="请选择">
                                                            <el-option v-for="item in optionse" :key="item.id" :label="item.text" :value="item.id" ></el-option>
                                                        </el-select>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="旅行社名称：" v-show="lxsNmaeis" prop="travelAgencyName">
                                                        <el-input v-model="sqxxForm.travelAgencyName" readonly=""></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="涉密人员：" prop="involvedSecretLevel">
                                                        <el-input v-model="sqxxForm.involvedSecretLevel" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" class="bordernone">
                                                    <el-form-item label="同行人员：" prop="isTogether">
                                                        <el-input v-model="sqxxForm.isTogether" readonly=""></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="境外联系方式：" prop="outlandPhone">
                                                        <el-input v-model="sqxxForm.outlandPhone"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="出国境申请表：" v-show="wordPaths" prop="attOption">
                                                        <div class="el-upload__text downLoad" @click="downWorld2()">
                                                            <span class="icon-world"></span>
                                                            <span ref="tipModel">出国境申请表</span>
                                                        </div>
                                                        <div class="exampleIm"></div>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8" v-show="showAttachment"><el-form-item class="" label="证明材料："><a :href="fileUrl" >{{sqxxForm.upFileName}}</a></u></el-form-item></el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="12">
                                                    <el-form-item label="备注：" class="bordernone" prop="remarks">
                                                        <el-input v-model="sqxxForm.remarks" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <!--申请信息表格  -->
                                    <div class="sqxx-table" v-show="Dashboard3Tab" style="padding:1px 20px 20px 20px">
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
                                </div>
                                <div class="titIcon">
                                    <div class="btn-group" v-show="now2" style="margin:0 20px">
                                        <el-button type="primary" @click="backBtn">返回</el-button>
                                        <el-button type="primary" @click="resumpDashboard">销假</el-button>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!-- 驳回提示框 -->
        <el-dialog title="驳回" :visible.sync="bhVisible" width="580px" class="bhlog">
            <el-form ref="rejectForm" :model="rejectForm" label-width="100px">
                <el-form-item label="审批意见:">
                    <el-input class="test_box" type="textarea" v-model="rejectForm.rejectCon"></el-input>
                    <!-- <div class="test_box" ref="view" contenteditable="true" style="-webkit-user-select:text"><br /></div> -->
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="bhVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveBh">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 同意提示框 -->
        <el-dialog title="操作提示" :visible.sync="agreeVisible" width="580px" class="agreelog">
            <div class="del-dialog-cnt">
                <p>
                    <i class="el-icon-success"></i>
                </p>
                <br/>
                <p>您已成功审批</p>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="agreeTip">确 定</el-button>
            </span>
        </el-dialog>
        <!--copyright  -->
        <!-- 评分提示框 -->
        <!-- :style="{'background':'url('+require('static/img/index/scoreBg.png')+') no-repeat center','background-size':'100% 100%'}" -->
        <el-dialog :visible.sync="pingfen" width="620px" class="pingfen">
            <div class="del-dialog-cnt" style="margin-top:60px;">
                <div style="text-align:center;margin-bottom:20px;margin-left:120px;">
                    <el-row>
                        <el-col :span="8"><span class="rateSpan">首页</span></el-col>
                        <el-col :span="8">
                            <el-rate v-model="scoreValue1" show-text @change="scoreValue1con"></el-rate>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><span class="rateSpan">请销假管理</span></el-col>
                        <el-col :span="8">
                            <el-rate v-model="scoreValue2" show-text @change="scoreValue2con"></el-rate>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><span class="rateSpan">出国（境）管理</span></el-col>
                        <el-col :span="8">
                            <el-rate v-model="scoreValue3" show-text @change="scoreValue3con"></el-rate>
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
            </span>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>
<script>
import { communication } from "../../data/getAxios";
export default {
  data() {
    return {
      options: [],
      optionse: [],
      btnType: 1, //1为同意   2为驳回
      applicantPeople: "",
      wordPathUrl: "",
      wordPaths: false,
      xzlogBoxNum: 0,
      xzlogBox: false,
      pos: false,
      verseArr: [],
      iamgesArr: [],
      xuanZhang: false,
      idNum: "",
      scoreValue1: null,
      scoreValue2: null,
      scoreValue3: null,
      scoreView: "",
      pingfen: false, //willa记得改false
      fujian: false,
      loading: false,
      titIconTypenum: true,
      Dashboard3Tab: false,
      stepActive: 2,
      showLxs: false, //显示旅行社
      bhVisible: false, //驳回弹窗
      agreeVisible: false, //同意弹窗
      now2: true,
      step1: localStorage.getItem("step1"), //步骤一描述
      step2: localStorage.getItem("step2"),
      step3: localStorage.getItem("step3"),
      step4: localStorage.getItem("step4"),
      step5: "",
      nowTime: "", //当前时间
      // tableShow:false,
      routeForm: [], //列表页传来的数据
      rejectForm: {
        rejectCon: ""
      },
      txxxForm: {
        formInfo: [
          {
            hisTime: "",
            abroadField: "",
            applicationFormId: "",
            departureForm: "",
            destinationBroad: ""
          }
        ]
      },
      sqxxForm: {
        name: "",
        policeNo: "",
        companyName: "",
        deptName: "",
        startTime: "",
        endTime: "",
        destinationBroad: "",
        passPlace: "",
        departureForm: "",
        departureReason: "",
        involvedSecretLevel: "",
        isTogether: "",
        outlandPhone: "",
        remarks: "",
        uploadAttached: "",
        upFileName: ""
      },
      showAttachment: false,
      fileUrl: "",
      lxsNmaeis: false,
      idx: -1,
      tableData: [],
      fujianUrl: "",
      workId: null
    };
  },
  created() {
    this.getCountry1();
    // this.ready();
    this.init();
  },
  methods: {
    getCountry1() {
      this.options = [];
      this.optionse = [];
      this.$http.get("../static/country.json").then(res => {
        for (var i = 0; i < res.body.list.length; i++) {
          //   let fullName = pinyin.getFullChars(res.body.list[i].label);
          //   let fullName1 = fullName.toLocaleLowerCase();
          //   this.options.push({
          //     label: res.body.list[i].id,
          //     label: res.body.list[i].label + "(" + fullName1 + ")"
          //   });
          //   this.optionse.push({
          //     id: res.body.list[i].id,
          //     label: res.body.list[i].label + "(" + fullName1 + ")"
          //   });
          this.options.push(res.body.list[i]);
          this.optionse.push(res.body.list[i]);
        }
      });
      console.log(this.options);
    },
    changeStyle() {
      var self = this;
      // if (self.sqxxForm.liveStyle == '跟团') {
      self.getCountry1();
      self.showLxs = true;
      // }
      // else {
      //     self.sqxxForm.otherPlace=null;//先清空，自由行不能选择除港澳台的其他地方
      //     self.getCountry2();
      //     self.showLxs = false;
      // }
    },
    init() {
      var self = this;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/flow/query/formInformation?type=cgjInfo&id=" +
          self.$route.query.alarmNo,
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.workId = res.data.bsGoAbroad.workId;
            this.ready();
            self.idNum = res.data.bsGoAbroad.id;
            self.txxxForm.formInfo = res.data.historyForm;
            self.sqxxForm.name = res.data.bsGoAbroad.name;
            self.sqxxForm.policeNo = res.data.bsGoAbroad.policeNo;
            self.sqxxForm.companyName = res.data.bsGoAbroad.companyName;
            self.sqxxForm.deptName = res.data.bsGoAbroad.deptName;
            self.sqxxForm.startTime = res.data.bsGoAbroad.startTime;
            self.sqxxForm.endTime = res.data.bsGoAbroad.endTime;
            self.sqxxForm.destinationBroad = res.data.bsGoAbroad.destinationBroad
              .split("[")[1]
              .split("]")[0]
              .split(",");
            if (res.data.bsGoAbroad.passPlace) {
              self.sqxxForm.passPlace = res.data.bsGoAbroad.passPlace
                .split("[")[1]
                .split("]")[0]
                .split(",");
            }
            self.sqxxForm.departureForm = res.data.bsGoAbroad.departureForm;
            self.sqxxForm.departureReason = res.data.bsGoAbroad.departureReason;
            self.sqxxForm.involvedSecretLevel =
              res.data.bsGoAbroad.involvedSecretLevel;
            self.sqxxForm.isTogether = res.data.bsGoAbroad.isTogether;
            self.sqxxForm.outlandPhone = res.data.bsGoAbroad.outlandPhone;
            self.sqxxForm.remarks = res.data.bsGoAbroad.remarks;
            self.sqxxForm.uploadAttached = res.data.bsGoAbroad.uploadAttached;
            self.sqxxForm.travelAgencyName =
              res.data.bsGoAbroad.travelAgencyName;
            self.applicantPeople = res.data.bsGoAbroad.applicantPeople;
            if (res.data.wordPath != null) {
              self.wordPaths = true;
              var downPath = res.data.wordPath.replace(
                "/home/apache-tomcat-8.5.40/webapps",
                ""
              );
              self.wordPathUrl = self.global.serverPath6 + downPath;
            }
            if (res.data.bsGoAbroad.departureForm == "跟团") {
              self.lxsNmaeis = true;
            }
            if (
              res.data.goAbroadTogetherForms != null &&
              res.data.goAbroadTogetherForms.length
            ) {
              self.Dashboard3Tab = true;
              self.tableData = res.data.goAbroadTogetherForms;
            }
            if (
              res.data.bsGoAbroad.upFilePath != null &&
              res.data.bsGoAbroad.upFilePath != ""
            ) {
              self.showAttachment = true;
              self.sqxxForm.upFileName = res.data.bsGoAbroad.upFileName;
              self.sqxxForm.upFilePath = res.data.bsGoAbroad.upFilePath;
              self.fileUrl =
                self.global.serverPath10 +
                "/process/download?fileName=" +
                self.sqxxForm.upFileName +
                "&filePath=" +
                encodeURI(self.sqxxForm.upFilePath);
            }
          } else if (res.code != 1) {
            self.$message.warning(res.message);
          }
        })
        .catch(err => {
          console.log("获取信息失败！");
        });
    },
    scoreValue1con(con) {
      this.scoreValue1Con = con;
    },
    scoreValue2con(con) {
      this.scoreValue2Con = con;
    },
    scoreValue3con(con) {
      this.scoreValue3Con = con;
    },
    resumpDashboard() {
      this.loading = true;
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/process/agreeForm",
        postData: {
          id: self.idNum,
          workId: self.workId,
          type: "cgj",
          fid: self.$route.query.fid,
          updateGoAbroadForm: {
            startTime: self.sqxxForm.startTime, //开始时间
            endTime: self.sqxxForm.endTime, //结束时间
            destinationBroad: self.sqxxForm.destinationBroad, //目的地
            passPlace: self.sqxxForm.passPlace, //经停地
            outlandPhone: self.sqxxForm.outlandPhone, //境外联系方式
            departureForm: self.sqxxForm.departureForm, //离境形式
            departureReason: self.sqxxForm.departureReason //离境原因
          }
        },
        isGet: false
      };
      communication(_obj).then(
        res => {
          console.log(res);
          if (res.code == -1) {
            self.$message.error("请配置下一步审批人");
          } else if (res.code == 0) {
            self.$message.success("销假成功");
            this.loading = false;
            self.xzlogBox = false;
            this.$router.push("/dashboard4");
          } else if (res.code != 1) {
            self.$message.error(res.message);
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
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
    agreeFormPop() {
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
    },
    agreeForm() {
      var self = this;
      if (self.btnType == 2) {
        const _obj = {
          url: self.global.serverPath8 + "/flow/process/rejected",
          postData: {
            fid: self.$route.query.fid,
            id: self.idNum,
            workId: self.$route.query.workId,
            applicantPeople: self.applicantPeople,
            type: "cgj",
            option: self.rejectForm.rejectCon,
            // "officeSealPath":$('.verseList img').eq(this.xzlogBoxNum).attr('src'),
            officeSealPath:
              "data:image/png;base64,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"
          },
          isGet: false
        };
        communication(_obj).then(
          res => {
            console.log(res);
            if (res.code == 0) {
              self.$message.success("保存成功");
              self.xzlogBox = false;
              this.$router.push("/dashboard4");
            } else if (res.code != 1) {
              self.$message.error(res.message);
            }
          },
          function(res) {
            self.$message.error("请求失败");
          }
        );
      } else if (self.btnType == 1) {
        const _obj = {
          url: self.global.serverPath8 + "/flow/process/agreeForm",
          postData: {
            fid: self.$route.query.fid,
            id: self.idNum,
            workId: self.$route.query.workId,
            applicantPeople: self.applicantPeople,
            type: "cgj",
            option: "",
            // "officeSealPath":$('.verseList img').eq(this.xzlogBoxNum).attr('src'),
            officeSealPath:
              "data:image/png;base64,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"
          },
          isGet: false
        };
        communication(_obj).then(
          res => {
            console.log(res);
            if (res.code == -1) {
              self.$message.error("请配置下一步审批人");
            } else if (res.code == 0) {
              self.$message.success("保存成功");
              self.xzlogBox = false;
              this.$router.push("/dashboard4");
            } else if (res.code != 1) {
              self.$message.error(res.message);
            }
          },
          function(res) {
            self.$message.error("请求失败");
          }
        );
      }
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
            if (res.data.processNo == "011") {
            } else {
              var stepArr = [];
              var msg = res.data.hashMap;
              for (var val in msg) {
                stepArr.push(msg[val]);
              }
              console.log(stepArr); //返回值数组
              for (var i = 0; i < stepArr.length; i++) {
                if (stepArr[i].size == 0) {
                  $(".DashbCon").append(
                    '<div class="alone ' +
                      (stepArr[i].applyStatus == 0
                        ? "gray"
                        : stepArr[i].applyStatus == 1 ? "green" : "red") +
                      '"><div class="circleDot finishType"><div class="circle"><div></div>' +
                      (stepArr[i].applyName ? stepArr[i].applyName : "") +
                      '</div></div><div class="figure"></div><div class="contentCon"><p>' +
                      (stepArr[i].applyUser == null
                        ? ""
                        : stepArr[i].applyUser == 0
                          ? ""
                          : stepArr[i].applyUser) +
                      "</p><p>" +
                      (stepArr[i].endTime == null
                        ? ""
                        : stepArr[i].endTime == 0 ? "" : stepArr[i].endTime) +
                      "</p></div></div>"
                  );
                } else {
                  $(".DashbCon").append(
                    '<div class="multiple"><div class="figure" style="background: ' +
                      (stepArr[i].applyStatus == 0
                        ? "gray"
                        : stepArr[i].applyStatus == 1 ? "#409EFF" : "red") +
                      '"></div><div>'
                  );
                  for (var k = 0; k < stepArr[i].size; k++) {
                    $(".multiple").append(
                      '<div class="daughter ' +
                        (stepArr[i].applyStatus == 0
                          ? "gray"
                          : stepArr[i].applyStatus == 1 ? "green" : "red") +
                        '"><div class="roundDot finishType"><div class="circle"><div></div>' +
                        (stepArr[i].applyName || "") +
                        '</div><span class="name">' +
                        (stepArr[i].applyUser == null
                          ? ""
                          : stepArr[i].applyUser == 0
                            ? ""
                            : stepArr[i].applyUser) +
                        '</span></div></div><div class="data"> ' +
                        (stepArr[i].endTime == null
                          ? ""
                          : stepArr[i].endTime == 0 ? "" : stepArr[i].endTime) +
                        " </div>"
                    );
                    i++;
                  }
                  i -= 1;
                }
              }
            }
            for (var r = 0; r < $(".data").length; r++) {
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
          } else if (res.code != 1) {
            self.$message.error(res.message);
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
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
    // 下载文档
    downWorld() {
      var $eleForm = $("<form method='get'></form>");
      var con =
        this.global.serverPath6 + "/serviceManagement/" + this.fujianUrl;
      $eleForm.attr(
        "action",
        this.global.serverPath6 + "/serviceManagement/" + this.fujianUrl
      );
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    downWorld2() {
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr("action", this.wordPathUrl);
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    // 获取当前时间函数
    timeFormate(timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month =
        new Date(timeStamp).getMonth() + 1 < 10
          ? "0" + (new Date(timeStamp).getMonth() + 1)
          : new Date(timeStamp).getMonth() + 1;
      let date =
        new Date(timeStamp).getDate() < 10
          ? "0" + new Date(timeStamp).getDate()
          : new Date(timeStamp).getDate();
      let hh =
        new Date(timeStamp).getHours() < 10
          ? "0" + new Date(timeStamp).getHours()
          : new Date(timeStamp).getHours();
      let mm =
        new Date(timeStamp).getMinutes() < 10
          ? "0" + new Date(timeStamp).getMinutes()
          : new Date(timeStamp).getMinutes();
      this.nowTime = year + "-" + month + "-" + date + " " + hh + ":" + mm;
    },
    //取消
    backBtn() {
      this.$router.push("/dashboard4");
    },
    // 审批同意
    agreeTip() {},
    // 确定驳回
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
        this.agreeForm();
      }
    }
  },
  mounted() {},
  computed: {}
};
</script>
<style scoped>
.downLoad {
  color: #20a0ff;
  cursor: pointer;
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

.del-dialog-cnt p {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
  text-align: center;
}

.del-dialog-cnt i {
  font-family: PingFangSC-Regular;
  font-size: 32px;
  color: green;
  opacity: 0.7;
  text-align: center;
}

.test_box {
  width: 400px;
  min-height: 120px;
  max-height: 300px;
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

.steps {
  padding: 3% 3% 0 3%;
}

.el-row {
  margin-bottom: 20px;
}

.mgb20 {
  margin-bottom: 16px;
}
.Dashboard3 .el-form--inline .el-form-item {
  margin-bottom: 10px !important;
}
</style>
<style>
.dashCard3 .el-card__header {
  padding: 10px 16px;
}
.Dashboard3 .pingfen .el-dialog {
  background: url("../../../static/img/index/scoreBg.png") no-repeat center;
  background-size: 100% 100%;
  height: 460px;
  -webkit-box-shadow: none !important;
  box-shadow: none !important;
}

.Dashboard3 .pingfen .el-dialog__headerbtn .el-dialog__close {
  color: #1890ff;
  font-size: 22px;
  font-weight: 900;
  position: relative;
  top: 23px;
  right: 23px;
}
.Dashboard3 .pingfen .el-dialog__body .el-textarea__inner {
  height: 100px !important;
}
.Dashboard3 .pingfen .el-dialog__body .el-row {
  margin-bottom: 10px;
}
.Dashboard3 .pingfen .el-dialog__body {
  padding-bottom: 10px !important;
}
.Dashboard3 .pingfen .el-dialog__footer {
  text-align: center;
}
.Dashboard3 .pingfen .el-dialog__body .el-row .rateSpan {
  display: inline-block;
  width: 100px;
  text-align: right;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}
.test_box textarea {
  height: 120px !important;
}
/*步骤条插槽 begin */
.step-row {
  height: 130px;
  margin-top: -74px;
}
.long-step.is-center .el-step__main {
  text-align: left !important;
}
.long-step .is-vertical .el-step__title {
  padding-bottom: 0 !important;
}
.long-step.is-center .el-step__description {
  background: #fff;
  position: relative;
  z-index: 999;
  width: 130px;
}
.long-step.is-center .el-step__description {
  padding-left: 5%;
  padding-right: 5%;
}
/*步骤条插槽end  */
.redtip span {
  color: #ff0000 !important;
  font-size: 14px;
}

.agreelog .el-dialog__header,
.bhlog .el-dialog__header {
  border-bottom: 1px solid #e8e8e8;
}

.agreelog .el-dialog__footer,
.bhlog .el-dialog__footer {
  border-top: 1px solid #e8e8e8;
}

.agreelog .el-dialog__body {
  padding: 50px 20px;
}

.sqxx-table .el-table th > .cell {
  font-family: PingFangSC-Medium;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.sqxx-table .el-table td div {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.sqxx-info3 .el-row[data-v-6aef4342] {
  margin-bottom: 0;
}
.sqxx-info3 .bordernone .el-input__inner {
  border: 0;
  padding: 0;
}
.sqxx-info3 .el-input__inner {
  padding: 0;
}
.handle-box3 .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
  line-height: 32px;
}

.handle-box3 .el-form-item__label {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
  letter-spacing: 0;
}

.handle-box3 .el-select {
  width: 100%;
}

.handle-box3 .el-input__inner {
  width: 98%;
  padding: 0 45px 0 15px;
}
.handle-box3 .el-upload--text {
  width: 76%;
  height: 28px;
  text-align: left;
}

.handle-box3 .el-upload-dragger {
  height: 100%;
  text-align: left;
  padding-left: 3px;
}
.vue-treeselect__control {
  height: 30px !important;
}
.vue-treeselect__multi-value-item-container {
  padding-top: 0 !important;
}

.vue-treeselect__input-container {
  height: 29px !important;
}
.vue-treeselect--searchable.vue-treeselect--multi.vue-treeselect--has-value
  .vue-treeselect__input-container {
  padding: 0 !important;
}
.vue-treeselect--has-value .vue-treeselect__multi-value {
  margin: 0 !important;
}
.vue-treeselect__multi-value-item {
  padding: 0 !important;
}
.handle-box3 .el-upload__tip {
  margin-top: 0;
}

.handle-box3 .el-upload-dragger {
  width: 100%;
}

.handle-box3 .el-upload-dragger .el-upload__text {
  text-align: left;
}

.steps .el-step__title {
  font-size: 14px !important;
}

.steps .el-step__icon-inner[class*="el-icon"]:not(.is-status) {
  font-size: 17px !important;
}

.steps .el-step__title.is-process {
  font-weight: normal;
  color: rgba(0, 0, 0, 0.65);
}

.steps .el-step__head.is-process {
  color: #c0c4cc;
}

.steps .el-step__title.is-wait {
  color: rgba(0, 0, 0, 0.65);
}

.steps .el-step__title.is-finish {
  color: rgba(0, 0, 0, 0.85);
  font-family: PingFangSC-Medium;
  font-size: 14px;
  font-weight: 600;
}
.DashbCon {
  display: flex;
  flex-direction: row;
  width: 90%;
  margin: 0 auto;
}
.DashbCon > div {
  flex: 1;
  position: relative;
  height: 150px;
  overflow: hidden;
}
.DashbCon .multiple {
  display: flex;
  flex-direction: column;
}
.DashbCon .circle {
  font-size: 12px;
  padding-right: 5px;
  position: relative;
  padding-left: 10px;
}
.DashbCon .circle > div {
  position: absolute;
  width: 10px;
  height: 10px;
  background: gray;
  border-radius: 5px;
  top: 50%;
  transform: translateY(-50%);
  left: 0;
}
.DashbCon .daughter {
  flex: 1;
  position: relative;
}
.DashbCon .circleDot,
.roundDot {
  position: absolute;
  background: white;
  z-index: 10;
  /* padding: 0 10px; */
}
.DashbCon .roundDot .circle {
  float: left;
  font-size: 12px;
  align-items: center;
  display: -webkit-flex;
  height: 100%;
}
.DashbCon .circleDot {
  left: 50%;
  transform: translate(-50%, -50%);
  top: 50%;
  align-items: center;
  display: -webkit-flex;
}
.DashbCon .roundDot {
  top: 50%;
  transform: translateY(-50%);
}
.DashbCon .roundDot .data {
  font-size: 12px;
  background: white;
  padding-left: 10px;
  align-items: center;
  display: -webkit-flex;
  float: left;
  height: 100%;
}
.multiple > .data {
  font-size: 12px;
  color: #409eff !important;
  padding-left: 20px;
  font-weight: 700;
}
.DashbCon .roundDot .name {
  font-size: 12px;
  align-items: center;
  display: -webkit-flex;
  float: left;
  height: 100%;
}
.DashbCon .finishColor {
  background: #3385ff;
}
.DashbCon .procedure {
  background: #eee;
}
.DashbCon .contentCon {
  width: 100%;
  position: absolute;
  left: 50%;
  top: 130px;
  transform: translate(-50%, -50%);
  text-align: center;
}
.DashbCon .alone p {
  font-size: 12px;
  line-height: 20px;
  font-weight: 700;
}
.DashbCon > div .figure {
  width: 100%;
  height: 2px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}
.DashbCon > div:first-child .figure {
  width: calc(50% - 20px);
  height: 2px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 0;
}
.DashbCon > div:last-child .figure {
  width: calc(50% - 20px);
  height: 2px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  left: 0;
}
.DashbCon .multiple .figure {
  position: absolute;
  width: calc(100% - 20px);
  height: 2px;
  top: 50%;
  transform: translateY(-50%);
  left: 50%;
}
.DashbCon .gray .circle div {
  background: gray;
}
.DashbCon .gray .figure {
  background: gray;
}
.DashbCon .gray .contentCon p {
  color: gray;
}
.DashbCon .green .circle div {
  background: #409eff;
}
.DashbCon .green .figure {
  background: #409eff;
}
.DashbCon .red .circle div {
  background: #f56c6c;
}
.DashbCon .red .figure {
  background: #f56c6c;
}
.DashbCon .red {
  color: #f56c6c;
}
.DashbCon .green {
  color: #409eff !important;
}
.DashbCon .gray .data {
  color: gray;
}
.cjglBtn {
  margin: 0 !important;
}
.handle-box3 .startTimeDate .el-input__inner {
  padding: 0 30px;
}
</style>
