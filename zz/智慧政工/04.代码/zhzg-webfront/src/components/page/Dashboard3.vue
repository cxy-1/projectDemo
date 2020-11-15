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
                                        <el-breadcrumb-item>出国（境）申请</el-breadcrumb-item>
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
                                        <el-form :inline="true" label-position="right" label-width="" ref='txxxForm' :model="txxxForm" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:18px">
                                                <el-col :span="24" class="redtip">
                                                    <span>处级领导干部申请出国（境）旅游，一般2年内不超过1次，历史出国（境）记录显示距离本次申请为超过2年时间</span>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="24" v-for="(item,index) in txxxForm.formInfo" :key="index" style="margin-bottom:0">
                                                <el-col :span="6" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="出国(境)历史记录：" prop="applicationTime">
                                                        <el-input v-model="item.applicationTime" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="7" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="目的地：" prop="destinationBroad">
                                                        <el-input v-model="item.destinationBroad" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="5" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="离境形式：" prop="departureForm">
                                                        <el-input v-model="item.departureForm" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="同行人员：" prop="isField">
                                                        <el-input v-model="item.isField" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <!-- <el-col :span="6" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">
                                                    <el-form-item label="申请单号：" prop="applicationFormId">
                                                        <el-input style="width: 180px;" v-model="item.applicationFormId" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col> -->
                                            </el-row>
                                        </el-form>
                                    </div>
                                </div>
                            </el-card>
                            <el-card class="card2" style="margin-bottom:15px">
                                <div slot="header" class="clearfix">
                                    <span>申请信息</span>
                                </div>
                                <div class="sqxx-info3">
                                    <!--申请信息表单  -->
                                    <div class="handle-box3">
                                        <el-form :inline="true" label-position="left" label-width="" ref='sqxxForm' :model="sqxxForm" class="demo-form-inline">
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名：" prop="name">
                                                        <el-input v-model="sqxxForm.name" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号：" prop="policeNo">
                                                        <el-input v-model="sqxxForm.policeNo" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="工作单位：" prop="companyName">
                                                        <el-input v-model="sqxxForm.companyName" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="部门：" prop="deptName">
                                                        <el-input v-model="sqxxForm.deptName" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="开始时间：" prop="startTime">
                                                        <el-input v-model="sqxxForm.startTime" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="结束时间：" prop="endTime">
                                                        <el-input v-model="sqxxForm.endTime" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="目的地：" prop="destinationBroad">
                                                      <el-popover trigger="hover" placement="right">
                                                        <p>{{ sqxxForm.destinationBroad }}</p>
                                                        <div slot="reference" class="name-wrapper">
                                                          <el-tag size="medium" v-if="sqxxForm.destinationBroad.length>4?true:false">{{ sqxxForm.destinationBroad.substring(0, 4) }}...</el-tag>
                                                          <el-tag size="medium" v-else>{{ sqxxForm.destinationBroad}}</el-tag>
                                                        </div>
                                                      </el-popover>
                                                        <!-- <el-input v-model="sqxxForm.destinationBroad" readonly="" placeholder="暂无数据"></el-input> -->
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="离境形式：" prop="departureForm">
                                                        <label v-show="sqxxForm.departureForm=='跟团'" for="">跟团</label>
                                                        <label v-show="sqxxForm.departureForm=='自由行'" for="">自由行</label>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="离境原因：" prop="departureReason">
                                                        <label v-show="sqxxForm.departureReason=='旅游'">旅游</label>
                                                        <label v-show="sqxxForm.departureReason=='探亲'">探亲</label>
                                                        <label v-show="sqxxForm.departureReason=='看病'">看病</label>
                                                        <label v-show="sqxxForm.departureReason=='参会'">参会</label>
                                                        <label v-show="sqxxForm.departureReason=='子女入学毕业'">子女入学毕业</label>
                                                        <label v-show="sqxxForm.departureReason=='其他'">其他</label>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                            <el-row :gutter="6" style="margin-bottom:0">
                                              <el-col :span="8">
                                                    <el-form-item label="经停地：" prop="passPlace">
                                                      <el-popover trigger="hover" placement="right" v-if="sqxxForm.passPlace?true:false">
                                                        <p>{{ sqxxForm.passPlace }}</p>
                                                        <div slot="reference" class="name-wrapper">
                                                          <el-tag size="medium" v-if="sqxxForm.passPlace.length>4?true:false">{{ sqxxForm.passPlace.substring(0, 4) }}...</el-tag>
                                                          <el-tag size="medium" v-else>{{ sqxxForm.passPlace}}</el-tag>
                                                        </div>
                                                      </el-popover>
                                                        <!-- <el-input v-model="sqxxForm.passPlace" readonly="" placeholder="暂无数据"></el-input> -->
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="旅行社名称：" v-show="lxsNmaeis" prop="travelAgencyName">
                                                        <el-input v-model="sqxxForm.travelAgencyName" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="涉密人员：" prop="involvedSecretLevel">
                                                        <el-input v-model="sqxxForm.involvedSecretLevel" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="同行人员：" prop="isTogether">
                                                        <el-input v-model="sqxxForm.isTogether" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="境外联系方式：" prop="outlandPhone">
                                                        <el-input v-model="sqxxForm.outlandPhone" readonly="" placeholder="暂无数据"></el-input>
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
                                                    <el-form-item label="备注：" prop="remarks">
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
                                <div class="titIcon" v-if="!wordPathe">
                                    <div class="btn-group" v-show="now0" style="margin:0 20px">
                                        <el-button type="primary" @click="backBtn">返回</el-button>
                                    </div>
                                    <div class="btn-group cjglBtn" v-show="now1">
                                        <el-button @click="bhVisible = true">驳回</el-button>
                                        <el-button @click="backBtn">返回</el-button>
                                        <el-button type="primary" @click="agreeFormPop">同意</el-button>
                                    </div>
                                    <div class="btn-group" v-show="now2" style="margin:0 20px">
                                        <el-button type="primary" @click="backBtn">返回</el-button>
                                        <el-button type="primary" @click="resumpDashboard">销假</el-button>
                                    </div>
                                </div>
                            </el-card>
                            <el-card class="card2"  v-if="wordPathe" >
                                <div slot="header" class="clearfix">
                                    <span>销假信息</span>
                                </div>
                                <div class="sqxx-info3">
                                    <!--申请信息表单  -->
                                    <div class="handle-box3">
                                        <el-form :inline="true" label-position="left" label-width="" ref='sqxxForm' :model="sqxxForm" class="demo-form-inline">
                                            <el-row :gutter="6" v-if="wordPathe" style="margin-bottom:0">
                                                <el-col :span="8">
                                                    <el-form-item label="开始时间：" prop="updateGoAbroadForm.startTime">
                                                        <el-input v-model="sqxxForm.updateGoAbroadForm.startTime" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="结束时间：" prop="updateGoAbroadForm.endTime">
                                                        <el-input v-model="sqxxForm.updateGoAbroadForm.endTime" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-form-item label="目的地：" prop="updateGoAbroadForm.destinationBroad">
                                                        <el-input v-model="sqxxForm.updateGoAbroadForm.destinationBroad" readonly="" placeholder="暂无数据"></el-input>
                                                </el-form-item>
                                                <el-form-item label="销假时间：" prop="updateGoAbroadForm.terminateTime">
                                                        <el-input v-model="sqxxForm.updateGoAbroadForm.terminateTime" readonly="" placeholder="暂无数据"></el-input>
                                                </el-form-item>
                                            </el-row>
                                        </el-form>
                                    </div>
                                </div>
                                <div class="titIcon">
                                    <div class="btn-group" v-show="now0" style="margin:0 20px">
                                        <el-button type="primary" @click="backBtn">返回</el-button>
                                    </div>
                                    <div class="btn-group cjglBtn" v-show="now1">
                                        <el-button @click="bhVisible = true">驳回</el-button>
                                        <el-button @click="backBtn">返回</el-button>
                                        <el-button type="primary" @click="agreeFormPop">同意</el-button>
                                    </div>
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
        <!-- 选章 -->
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
                <el-button type="primary" @click="agreeForm" :loading="agreeFormFlag">确 定</el-button>
            </div>
        </el-dialog>
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
  name: "dashboard3",
  data() {
    return {
      btnType: 1, //1为同意   2为驳回
      applicantPeople: "",
      wordPathUrl: "",
      wordPaths: false,
      wordPathe: false,
      xzlogBoxNum: 0,
      xzlogBox: false,
      agreeFormFlag: false,
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
      now0: null,
      now1: null, //按钮切换
      now2: null,
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
        departureForm: "",
        departureReason: "",
        involvedSecretLevel: "",
        isTogether: "",
        outlandPhone: "",
        remarks: "",
        uploadAttached: "",
        upFileName: "",
        updateGoAbroadForm: "",
        passPlace: ""
      },
      showAttachment: false,
      fileUrl: "",
      lxsNmaeis: false,
      idx: -1,
      tableData: [],
      fujianUrl: "",
      workId: null,
      isOfficalSea: true //需要盖章
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      if (
        this.$route.query.btnTypeNum === 0 ||
        this.$route.query.btnTypeNum === "0"
      ) {
        this.now0 = true;
        this.now1 = false;
        this.now2 = false;
      } else if (
        this.$route.query.btnTypeNum === 1 ||
        this.$route.query.btnTypeNum === "1"
      ) {
        this.now0 = false;
        this.now1 = true;
        this.now2 = false;
      } else if (
        this.$route.query.btnTypeNum === 2 ||
        this.$route.query.btnTypeNum === "2"
      ) {
        this.now0 = false;
        this.now1 = false;
        this.now2 = true;
      }
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
            self.idNum = res.data.bsGoAbroad.id;
            self.workId = res.data.bsGoAbroad.workId;
            this.ready();
            self.txxxForm.formInfo = res.data.historyForm;
            self.sqxxForm.name = res.data.bsGoAbroad.name;
            self.sqxxForm.policeNo = res.data.bsGoAbroad.policeNo;
            self.sqxxForm.companyName = res.data.bsGoAbroad.companyName;
            self.sqxxForm.deptName = res.data.bsGoAbroad.deptName;
            self.sqxxForm.startTime = res.data.bsGoAbroad.startTime;
            self.sqxxForm.endTime = res.data.bsGoAbroad.endTime;
            self.sqxxForm.destinationBroad =
              res.data.bsGoAbroad.destinationBroad;
            self.sqxxForm.passPlace = res.data.bsGoAbroad.passPlace;
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
            if (res.data.updateGoAbroadForm != null) {
              self.sqxxForm.updateGoAbroadForm = res.data.updateGoAbroadForm;
              self.wordPathe = true;
            } else {
              self.wordPathe = false;
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
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/process/agreeForm",
        postData: {
          updateGoAbroadForm: {
            // startTime:
            // endTime:
            // destinationBroad:
            // outlandPhone:
            // departureForm:
            // departureReason:
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
    agreeForm() {
      var self = this;
      self.agreeFormFlag = true;
      if (self.btnType == 2) {
        const _obj = {
          url: self.global.serverPath8 + "/flow/process/rejected",
          postData: {
            fid: self.$route.query.fid,
            id: self.idNum,
            workId: self.workId,
            applicantPeople: self.applicantPeople,
            type: "cgj",
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
            self.agreeFormFlag = false;
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
            self.agreeFormFlag = false;
            self.$message.error("请求失败");
          }
        );
      } else if (self.btnType == 1) {
        const _obj = {
          url: self.global.serverPath8 + "/flow/process/agreeForm",
          postData: {
            fid: self.$route.query.fid,
            id: self.idNum,
            workId: self.workId,
            applicantPeople: self.applicantPeople,
            type: "cgj",
            option: "",
            officeSealPath: $(".verseList img")
              .eq(this.xzlogBoxNum)
              .attr("src")
            // officeSealPath:
            //   "data:image/png;base64,R0lGODlhrQBeAOMAADk6O2ZmmWaZmZlmZpmZZpmZmZmZzJnMzMyZmcyZzMzMmfv7+wAAAAAAAAAAAAAAACH5BAEAAAsALAAAAACtAF4AAAT+cMl5DipjhFFUnWAojmRpnmiqrmx4FPChVgocBECu38XX/kBQpSMLGo+kAy7QO1kKN12OQWVIeUWkNrlsbr+/F8AayJKgU6t0vR6YwVtljvmG20dicj2Eo+6YBQYvNmtld3E4AHSHjCJ5insgiXM9PhQvAlMAXo1AcpAtFaKRnSIFY6AmNotOaKl2Q5wrn4YooqtYpbYBeraiM6evs5YuBQSJtbPByXhPUYWkuhK00UbL1Y4YskOTagU/wZsusRo6VWrC0o7I2EDXoUtF3OZ+zDOJAqOrUudqPAbSRtWhNq3ChRjW5rQDEa6SKzasWNDa8IxeP0WVFiJ5cSPiNGT+PfbpkKVMocRTZCpSgkESWB82ay4GEHRJY5hwOSpNQ8ArJb96NikkamlCDro/GYNSQHNxTscx5zbJwxCAANGNUCZRQsAR6lE2V0+4+saCp9c5AsJK7BMVoyBXVV59wqj0xAVAWuek8eO0o0dfQ0y6PJvry1yokGSISYNRyEOpYAbpy+vVX9JfLKBwYJdiEFsyNGEVSDup1lw/JOfRjcMyi2p+dOs6BuuEY6K49uCMmlRJay88rnInISXZ0SBCOfIhIizvQI2kj80BUK5rImUmTji3Ilq8xO6csl18lvoab6GKanVT1pR+gYF4TghQwrygeyvwWxT0JMOy21F/dBD+pI5tT/3WSgDUefeSX4q9sNBiwoFzliKUxeVUaBCG50ksvGlEDBeI7RDLg+9ogZKFV0SBjkcZqiPeSG+MYsQjT23SGjCCIXFiWwE2FFoIJbpYXzjMOINdEDQG91dRQc64Yz06tXhGji4G51pXVK6FSj44tScUKhnNOMCEM7mm3QhNBgRSg3BZ4SUepeXhZmeeoULhkp2xhZ8QQ6mSZSlzZdSmgT+8x86JMxVV5158RZgdlHWkCaRg9t1xgHwKYclXOieVBgNUEb5QDqP9uKmUEqgNJClDJtlGlqVE9lcIQjPe5hOhIJilCT28vsnnn/XhE807Aur2UI1o6YSErij+4jrBiU4du8OrWkK2Tk5i4VcsGEzphUqA3tmEU6mcSnCsIBAuWNcnRK3KKmQolXuEUXzFpValTFJYGVBJoMEKTiMFES+1FIwp77Pg0ajhtU2JSEpWXnKU1r7OXkLrAgAfnO2c104XzWIOfbvwr03ViCfI7XBkA7mOXoIwbrI9QrC5AedLnrqGfWbZay2xm3IMULAcFLR45glmxy0PmXBPGs8y3mrTvDcfkEP93BsqMJ+EmK8Mi/Oitd4FExJiSduiH5k/ThMcJxDO3O/VvJZNctOPcvxuovdJpafcYesJdTYgfUm32nAzxrfS/A30INPbnKlgTkw5+wJXB5KdHpb+tcTLt2QQarJ5MKbWxFJ7Rgnj8y45jZm1YzyEFfnfNpM38IMGhKxvxYB/K2hWyACUnchLAYu0zvbYRmHPOJVZEkbduh1uYDY6XsLrfq1HOs4fZfrhpN4y5nsxyPylRGFa60XF4fXdAp67l+gM07TtHGsmeAc9/P7RwHkaZvrb3xO3TQ5CmRxa1pVSHeUf/QOcdjKkMvtFRUVeA06IYDev14GtM7a7WsvO1rDW0aczd6FU1cbnPAnoJyqfwtbjHjiyL/khQcCAngaHxZhofVAigxDMMt5yg4/trXZ9KkomyNbCl9lNGWOTytladixwbSQBKTTEJ/IxQB9urQKJ+F7+376lRROBqosYTCITpAc+JqDLMCwxib++wa7YSTGLGMSeFuR0wTCmkB9Jk5GxoihDxVStKB0hSxvjKKLl1AOMBwpOGtBnLAH0zl9TEV5BlDVIQv4pgRJcAwxtwbvkWElIrAPJH5U2uF9F0JIXxNfjvIU+Aq3vBpt0kfFY6Rr2geiUqPSCbaqhPsiNEoNrECTXRKOSPdFMhU5D5vISBrGPZcVhtixGMEGpitsU0pRl41wQQ9FE+IjFU9mro5/sVEJqRk5yDcHkkMyDS2XkpWjYxFA0WcUvaoKIL+0C5xY75KQ1daZ2JGlIMnFnTzpeZTHKhJN0hjmOGCxMZiSCkT3xV9jOUJYNdASlZukOt8uJhk2SBVGnCzMKyo0CUKQTnWcLusXIUnzSo+opZUnOV0Q0kg+my6noRsiIUz3iNGcJNYwjGfrTosIjqIZBqVGXiqRtMvWpUO1YOaNK1Z46tapYLeoAEZnVrmrUm14Na0HBKtayDoisZk1rI+RAVLW6VQVomOpb5+oOY9L1rk5CKl73iiOd8vWvHz0SYAfrhDG1lLB0rSJiF4sHDcSSsYg9gAbaClmzKraykL0sZher2c0StrOeBSxoQ8tXlZI2raY9bVlTq1qvSqm1eN0WbOeKstnuVWy23ev4appbmKqyt0+NAAA7"
          },
          isGet: false
        };
        communication(_obj).then(
          res => {
            self.agreeFormFlag = false;
            console.log(res);
            if (res.code == 0) {
              if (res.data.ccflowSuccess == null) {
                self.loading = false;
                self.$message.error("请设置审批人");
              } else {
                self.$message.success("保存成功");
                self.xzlogBox = false;
                this.$router.push("/dashboard4");
              }
            } else if (res.code != 1) {
              self.$message.error(res.message);
            }
          },
          function(res) {
            self.agreeFormFlag = false;
            self.$message.error("请求失败");
          }
        );
      }
      this.loading = false;
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
          if (res.data == "0" || res.data == 0) {
            self.isOfficalSea = false;
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
      this.$router.go(-1);
      // this.$router.push('/dashboard4');
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
        this.agreeFormPop();
        this.bhVisible = false;
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

.sqxx-info3 .el-form-item__label {
  padding: 0;
}

.sqxx-info3 .el-input__inner {
  border: none;
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
  width: 76%;
}

.handle-box3 .el-input__inner {
  width: 98%;
}

.timeInput .el-input__inner {
  width: 100%;
}

.handle-box3 .el-select .el-input__inner {
  width: 100%;
}
.handle-box3 .el-upload--text {
  width: 76%;
  height: 28px;
  border: none;
  text-align: left;
}

.handle-box3 .el-upload-dragger {
  border: none;
  height: 100%;
  text-align: left;
  padding-left: 3px;
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
.multiple > .data {
  font-size: 12px;
  color: #409eff !important;
  padding-left: 20px;
  font-weight: 700;
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
  padding: 0 10px;
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
  padding-left: 5px;
  align-items: center;
  display: -webkit-flex;
  float: left;
  height: 100%;
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
