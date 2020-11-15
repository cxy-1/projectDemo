<template>
    <div class="cjgl Dashboard2"  v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20" style="overflow:hidden">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>出国（境）申请</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                                <div class="titIcon">
                                    <i class="faSelf sq-icon"></i>
                                    <p v-show="applyFormIdShow">申请单号：
                                    <span>{{applicationFormId}}</span>
                                    </p>
                                </div>
                                <el-row>
                                    <el-col :span="24">
                                        <div class="DashbCon"></div>
                                    </el-col>
                                </el-row>
                                <!--<div v-show="warnCon" class="Dashboard2Warn">提示：{{Warn}}</div>-->
                            </el-card>
                            <el-card class="card2">
                                <div slot="header" class="clearfix">
                                    <span>申请信息</span>
                                </div>
                                <div class="sqxx-info2">
                                    <div class="handle-box2">
                                        <el-form :inline="false" :rules="sqxxRules" :label-position="'right'" label-width="130px" ref='sqxxForm' :model="sqxxForm" class="demo-form-inline inputPadding">
                                            <el-row :gutter="6">
                                                <el-col :span="24">
                                                    <div class="promptCon" v-show="warnCon">
                                                        <div class="Dashboard2Warn">提示：{{Warn}}</div>
                                                    </div>
                                                </el-col>
                                                 <el-col :span="24">
                                                    <el-col :span="8">
                                                        <el-form-item label="开始时间" prop="startTime" class="timeInput">
                                                            <el-date-picker v-model="sqxxForm.startTime" :picker-options="pickerOptions0" format='yyyy-MM-dd' style="width: 76%;" value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8">
                                                        <el-form-item label="结束时间" prop="endTime" class="timeInput">
                                                            <el-date-picker :picker-options="pickerOptions1" v-model="sqxxForm.endTime" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8">
                                                        <el-form-item label="离境原因" prop="liveReason">
                                                            <el-select v-model="sqxxForm.liveReason" readonly="" @change="changeModel()">
                                                                <el-option key="旅游" label="旅游" value="旅游"></el-option>
                                                                <el-option key="探亲" label="探亲" value="探亲"></el-option>
                                                                <el-option key="看病" label="看病" value="看病"></el-option>
                                                                <el-option key="子女入学毕业" label="子女入学毕业" value="子女入学毕业"></el-option>
                                                                <el-option key="参会" label="参会" value="参会"></el-option>
                                                                <el-option key="其他" label="其他" value="其他"></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </el-col>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-col :span="8">
                                                        <el-form-item label="离境形式" prop="liveStyle">
                                                            <el-select v-model="sqxxForm.liveStyle" readonly="" @change="changeStyle()">
                                                                <el-option key="跟团" label="跟团" value="跟团"></el-option>
                                                                <el-option key="自由行" label="自由行" value="自由行"></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8" v-show="sqxxForm.liveStyle == '跟团'?true:false">
                                                        <el-form-item label="旅行社名称" prop="tripName">
                                                            <el-input v-model="sqxxForm.tripName"></el-input>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8">
                                                        <el-form-item label="目的地" prop="otherPlace">
                                                            <tree-select style="width:76%;" :multiple="true" placeholder="请选择" :disable-branch-nodes="true" v-model="sqxxForm.otherPlace" :options="options" />
                                                            <!-- <el-input v-model="sqxxForm.otherPlace"></el-input> -->
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8"  v-if="sqxxForm.liveStyle == '跟团'?false:true">
                                                        <el-form-item label="经停地" prop="passPlace">
                                                            <tree-select style="width:76%;" :multiple="true" placeholder="请选择" :disable-branch-nodes="true" v-model="sqxxForm.passPlace" :options="optionse" />
                                                        </el-form-item>
                                                    </el-col>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-col :span="8"  v-if="sqxxForm.liveStyle == '跟团'?true:false">
                                                        <el-form-item label="经停地" prop="passPlace">
                                                            <tree-select style="width:76%;" :multiple="true" placeholder="请选择" :disable-branch-nodes="true" v-model="sqxxForm.passPlace" :options="optionse" />
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8">
                                                        <el-form-item label="涉密人员" prop="selSecret">
                                                        <el-select v-model="sqxxForm.selSecret" readonly="">
                                                            <el-option key="核心涉密人员" label="核心涉密人员" value="核心涉密人员"></el-option>
                                                            <el-option key="重要涉密人员" label="重要涉密人员" value="重要涉密人员"></el-option>
                                                            <el-option key="一般涉密人员" label="一般涉密人员" value="一般涉密人员"></el-option>
                                                            <el-option key="非涉密人员" label="非涉密人员" value="非涉密人员"></el-option>
                                                        </el-select>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8">
                                                        <el-form-item label="境外联系方式" prop="outlandPhone">
                                                            <el-input v-model="sqxxForm.outlandPhone"></el-input>
                                                        </el-form-item>
                                                    </el-col>
                                                    <el-col :span="8">
                                                        <el-form-item label="同行人员" prop="togatherPro">
                                                            <el-select v-model="sqxxForm.togatherPro" readonly="" @change="txTable()">
                                                                <el-option key="有" label="有" value="有"></el-option>
                                                                <el-option key="无" label="无" value="无"></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </el-col>
                                                </el-col>
                                                <el-col :span="24" v-show="sqxxForm.applyPapers=='1'?true:false">
                                                    <div class="papersType">
                                                        <el-row :gutter="6" style="margin-bottom:0">
                                                            <el-col :span="24"><span class="papersFont">普通护照</span></el-col>
                                                            <el-col :span="24">
                                                                <template>
                                                                    <el-checkbox-group v-model="sqxxForm.normalPapers">
                                                                        <el-checkbox label="首次申领"></el-checkbox>
                                                                        <el-checkbox label="换发"></el-checkbox>
                                                                        <el-checkbox label="补发"></el-checkbox>
                                                                        <el-checkbox label="过期重领"></el-checkbox>
                                                                        <!-- <el-checkbox label="禁用" disabled></el-checkbox> -->
                                                                    </el-checkbox-group>
                                                                </template>
                                                            </el-col>
                                                            <el-col :span="24"><span class="papersFont">往来港澳通行证及签注</span></el-col>
                                                            <el-col :span="24">
                                                                <template>
                                                                    <el-checkbox-group v-model="sqxxForm.gangaoPapers">
                                                                        <el-checkbox label="旅游(一次/两次)"></el-checkbox>
                                                                        <el-checkbox label="探亲"></el-checkbox>
                                                                        <el-checkbox label="商务"></el-checkbox>
                                                                        <el-checkbox label="逗留(多次往返)"></el-checkbox>
                                                                        <el-checkbox label="其他"></el-checkbox>
                                                                    </el-checkbox-group>
                                                                </template>
                                                            </el-col>
                                                            <el-col :span="24"><span class="papersFont">往来台湾通行证及签注</span></el-col>
                                                            <el-col :span="24">
                                                                <template>
                                                                    <el-checkbox-group v-model="sqxxForm.taiwanPapers">
                                                                        <el-checkbox label="旅游"></el-checkbox>
                                                                        <el-checkbox label="探亲"></el-checkbox>
                                                                        <el-checkbox label="商务"></el-checkbox>
                                                                        <el-checkbox label="应邀"></el-checkbox>
                                                                        <el-checkbox label="定居"></el-checkbox>
                                                                        <el-checkbox label="学习"></el-checkbox>
                                                                        <el-checkbox label="乘务"></el-checkbox>
                                                                        <el-checkbox label="其他"></el-checkbox>
                                                                    </el-checkbox-group>
                                                                </template>
                                                            </el-col>
                                                        </el-row>
                                                    </div>
                                                </el-col>
                                                <el-col :span="24" v-show="tableShow">
                                                    <div class="sqxx-table txryTable">
                                                        <el-table size="mini" align="center" :data="master_user.data" border style="width: 100%" highlight-current-row>
                                                            <!-- <el-table-column type="index"></el-table-column> -->
                                                            <el-table-column v-for="(item,j) in master_user.columns" :key="j" :prop="item.field" :label="item.title" :width="item.width">
                                                                <template slot-scope="scope">
                                                                    <span v-if="scope.row.isSet">
                                                                        <el-input size="mini" v-model="master_user.sel[item.field]">
                                                                        </el-input>
                                                                    </span>
                                                                    <span v-else>{{scope.row[item.field]}}</span>
                                                                </template>
                                                            </el-table-column>
                                                            <el-table-column label="操作" width="180">
                                                                <template slot-scope="scope">
                                                                    <span class="el-tag el-tag--info el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,true)">
                                                                        {{scope.row.isSet?'保存':"编辑"}}
                                                                    </span>
                                                                    <span v-if="!scope.row.isSet" class="el-tag el-tag--danger el-tag--mini" @click="deleteRow(scope.$index, scope.row)" style="cursor: pointer;">
                                                                        删除
                                                                    </span>
                                                                    <span v-else class="el-tag  el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,false)">
                                                                        取消
                                                                    </span>
                                                                </template>
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="el-table-add-row" style="width: 100%;" @click="addMasterUser()">
                                                            <span>+ 新增同行人员</span>
                                                        </div>
                                                    </div>
                                                </el-col>
                                                <el-col :span="24" class="input-remarks" style="margin-top:10px;">
                                                    <el-form-item label="备注">
                                                         <el-input v-model="sqxxForm.remarks" placeholder="请输入备注"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-row :gutter="6">
                                                        <el-col :span="10">
                                                            <el-form-item label="所需证明材料提示" prop="attOption">
                                                                <div v-show="downShow" class="el-upload__text downLoad" @click="downWorld()">
                                                                    <span class="icon-world"></span>
                                                                    <span ref="tipModel">行程证明</span>
                                                                </div>
                                                                <div class="exampleIm"></div>
                                                                <div ref="redTips" class="el-upload__tip"></div>
                                                            </el-form-item>
                                                        </el-col>
                                                        <el-col :span="8" class="upBtn">
                                                            <el-form-item label="证明材料上传" prop="upOption">
                                                                <el-upload ref="my-upload" class="upload-demo" :on-success="handleSuccess"  :on-progress="handleProcess" :on-error="handleError" v-model="sqxxForm.upOption" drag :action="actss" limit="1" multiple :data="uploadParam"  :headers = "headers" :file-list="fileList">
                                                                    <el-button size="mini">
                                                                        <i class="el-icon-upload2"></i>上传</el-button>
                                                                    <!--<div class="el-upload__tex downloadt" style="display:block;">
                                                                        <span class="icon-world"></span>
                                                                        <em>
                                                                            <span>{{applyName}}</span>出国（境）申请表</em>
                                                                    </div>-->
                                                                </el-upload>
                                                                <!-- <el-input v-model="sqxxForm.upOption"></el-input> -->
                                                            </el-form-item>
                                                        </el-col>
                                                    </el-row>
                                                </el-col>
                                            </el-row>
                                            <el-row>
                                                <el-col :span="24">
                                                        <div class="btn-group" style="margin-top:-30px;">
                                                            <el-button @click="saveForm('sqxxForm')">保存</el-button>
                                                            <el-button @click="resetForm('sqxxForm')">取消</el-button>
                                                            <el-button type="primary" @click="submitForm('sqxxForm')">提交</el-button>
                                                            <!-- <el-button type="primary" @click.native="sqxxSubmit">提交</el-button> -->
                                                        </div>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                    <!--申请信息表格  -->
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
var generateId = {
  _count: 1,
  get() {
    return +new Date() + "_" + this._count++;
  }
};
// 电话的正则验证
var checkPhone = (rule, value, callback) => {
  if (value) {
    const reg = /^\s*\+?\s*(\(\s*\d+\s*\)|\d+)(\s*-?\s*(\(\s*\d+\s*\)|\s*\d+\s*))*\s*$/;
    if (reg.test(value)) {
      callback();
    } else {
      return callback(new Error("请输入正确的手机号"));
    }
  }
};
import { communication } from "../../data/getAxios";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  components: { TreeSelect },
  data() {
    return {
      uploadStatus: "1", // -1 - 上传失败；0 - 上传中；1 - 上传成功
      headers: {
        token: localStorage.getItem("setToken")
      },
      uploadParam: {
        type: "cgj",
        workId: localStorage.getItem("workId")
      },
      processArr: 0,
      Dashboard2Upload: "",
      warnCon: false,
      Warn: "",
      loading: false,
      actss: this.global.serverPath8 + "/flow/process/upload",
      applicationFormId: "",
      workId: "",
      applyFormIdShow: false,
      // imgIp:'http://10.33.66.86:9999/download/userImage/'+localStorage.getItem("loginpersonId")+".jpg",
      word: false,
      options: [],
      optionse: [],
      stepActive: 1,
      downShow: true,
      startDatePicker: "",
      applyName: localStorage.getItem("jc_name"),
      step1: "", //步骤一描述
      step2: "",
      step3: "",
      step4: "",
      step5: "",
      nowTime: "", //当前时间
      // upActives: this.serverPath + '/zhzg/upload/uploadFile',//上传文件路径
      tableShow: false,
      txtVal: 0,
      fileList: [],
      sqxxForm: {
        startTime: "",
        endTime: "",
        otherPlace: null, //初始化搜索下拉框
        passPlace: null, //初始化搜索下拉框
        outlandPhone: "",
        liveStyle: "",
        tripName: "",
        selSecret: "",
        remarks: "",
        togatherPro: "", //同行人员
        applyPapers: "", //证件申办
        normalPapers: [], //普通护照选中
        gangaoPapers: [], //港澳通行证选中
        taiwanPapers: [], //台湾通行证选中
        liveReason: "",
        attOption: "",
        upOption: "",
        upFileName: "",
        upFilePath: ""
      },
      pickerOptions0: {
        //开始时间
        // disabledDate: time => {
        //     let endDateVal = this.sqxxForm.endTime;
        //     if (endDateVal) {//不能早于当前日期
        //         return (time.getTime() > new Date(endDateVal).getTime()) || (time.getTime()  < Date.now() - 8.64e7);
        //     }else{
        //         return time.getTime()  < Date.now() - 8.64e7;
        //     }
        // }
      },
      pickerOptions1: {
        //结束时间
        disabledDate: time => {
          let beginDateVal = this.sqxxForm.startTime;
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
      idx: -1,
      master_user: {
        sel: null, //选中行
        columns: [
          { field: "relationUserName", title: "姓名", width: 140 },
          { field: "appellation", title: "称谓", width: 160 },
          { field: "workUnits", title: "工作单位" },
          { field: "jobPosition", title: "职务", width: 240 }
        ],
        data: []
      },
      sqxxRules: {
        startTime: [
          { required: true, message: "请选择开始时间", trigger: "blur" }
        ],
        // remarks: [
        //     { required: true, message: '请填写备注', trigger: 'blur' }
        // ],
        endTime: [
          { required: true, message: "请选择结束时间", trigger: "blur" }
        ],
        otherPlace: [
          { required: true, message: "请选择目的地", trigger: "blur" }
        ],
        selSecret: [{ required: true, message: "涉密人员", trigger: "blur" }],
        liveStyle: [
          { required: true, message: "请选择离境形式", trigger: "blur" }
        ],
        liveReason: [
          { required: true, message: "请选择离境原因", trigger: "blur" }
        ],
        togatherPro: [
          { required: true, message: "请填写同行人员", trigger: "blur" }
        ],
        tripName: [],
        outlandPhone: [
          { required: true, message: "请填写联系方式", trigger: "change" }
        ]
      }
    };
  },

  created() {
    // 获取国家名
    this.getCountry1();
    this.init();
    this.warn();
  },
  methods: {
    warn() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/message/reminder/get",
        isGet: true
      };
      communication(_obj).then(
        res => {
          console.log(res);
          if (res.code == 0) {
            if (res.data.message != "") {
              self.warnCon = true;
              self.Warn = res.data.message;
            }
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    getCountry1() {
      this.options = [];
      this.optionse = [];
      this.$http.get("../static/country.json").then(res => {
        for (var i = 0; i < res.body.list.length; i++) {
          let fullName = pinyin.getFullChars(res.body.list[i].label);
          let fullName1 = fullName.toLocaleLowerCase();
          this.options.push({
            id: res.body.list[i].id,
            label: res.body.list[i].label + "(" + fullName1 + ")"
          });
          this.optionse.push({
            id: res.body.list[i].id,
            label: res.body.list[i].label + "(" + fullName1 + ")"
          });
        }
      });
    },
    getCountry2() {
      this.options = [];
      this.optionse = [];
      this.$http.get("../static/country.json").then(res => {
        for (var i = 0; i < res.body.list.length; i++) {
          var gat = res.body.list[i].label;
          if (
            gat.indexOf("香港") > -1 ||
            gat.indexOf("澳门") > -1 ||
            gat.indexOf("台湾") > -1
          ) {
            res.body.list[i].isDisabled = false;
          } else {
            res.body.list[i].isDisabled = true;
          }
          let fullName = pinyin.getFullChars(res.body.list[i].label);
          let fullName1 = fullName.toLocaleLowerCase();
          this.options.push({
            id: res.body.list[i].id,
            label: res.body.list[i].label + "(" + fullName1 + ")",
            isDisabled: res.body.list[i].isDisabled
          });
          this.optionse.push({
            id: res.body.list[i].id,
            label: res.body.list[i].label + "(" + fullName1 + ")",
            isDisabled: res.body.list[i].isDisabled
          });
        }
      });
    },
    init() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/process/queryForm?type=cgj",
        postData: {
          personId: localStorage.getItem("loginpersonId")
        },
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res.code == -1) {
            self.$message.error("请配置下一步审批人");
          } else if (res.code == 10) {
            self.workId = res.data;
            localStorage.setItem("workId", self.workId);
            this.ready();
          } else if (res.code == 0) {
            var arr = [];
            var arrs = [];
            var outlandPhoneArr = [];
            var outlandPhoneArrs = [];
            self.applicationFormId = res.data.bsGoAbroad.id;
            self.workId = res.data.bsGoAbroad.workId;
            localStorage.setItem("workId", self.workId);
            this.ready();
            self.sqxxForm.startTime = res.data.bsGoAbroad.startTime;
            self.sqxxForm.endTime = res.data.bsGoAbroad.endTime;
            self.sqxxForm.outlandPhone = res.data.bsGoAbroad.outlandPhone;
            self.sqxxForm.liveStyle = res.data.bsGoAbroad.departureForm;
            self.sqxxForm.tripName = res.data.bsGoAbroad.travelAgencyName;
            self.sqxxForm.selSecret = res.data.bsGoAbroad.involvedSecretLevel;
            self.sqxxForm.remarks = res.data.bsGoAbroad.remarks;
            self.sqxxForm.togatherPro = res.data.bsGoAbroad.isTogether;
            self.sqxxForm.liveReason = res.data.bsGoAbroad.departureReason;
            self.sqxxForm.attOption = res.data.bsGoAbroad.uploadAttached;
            self.sqxxForm.upFileName = res.data.bsGoAbroad.upFileName;
            self.sqxxForm.upFilePath = res.data.bsGoAbroad.upFilePath;
            self.fileList.push({
              name: res.data.bsGoAbroad.upFileName,
              status: "finished"
            });
            if (res.data.goAbroadTogetherForms != null) {
              self.tableShow = true;
              self.master_user.data = res.data.goAbroadTogetherForms;
            }
            arr = res.data.bsGoAbroad.destinationBroad
              .split("[")[1]
              .split("]")[0]
              .split(",");
            for (var i = 0; i < arr.length; i++) {
              outlandPhoneArr.push($.trim(arr[i]));
            }
            self.sqxxForm.otherPlace = outlandPhoneArr;
            if (res.data.bsGoAbroad.passPlace) {
              arrs = res.data.bsGoAbroad.passPlace
                .split("[")[1]
                .split("]")[0]
                .split(",");
              for (var k = 0; k < arrs.length; k++) {
                outlandPhoneArrs.push($.trim(arrs[k]));
              }
              self.sqxxForm.passPlace = outlandPhoneArrs;
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
    getSameNum(val, arr) {
      this.processArr = arr.filter(function(value) {
        return value == val;
      });
      console.log(this.processArr);
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
                        : stepArr[i].applyStatus == 1 ? "green" : "red") +
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
                        '</span><div class="data"> ' +
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
    // 文本域计数
    descInput() {
      this.txtVal = this.sqxxForm.remarks.length;
    },
    // 文件上传成功钩子
    handleSuccess(response, file, fileList) {
      this.sqxxForm.upFileName = file.name;
      this.sqxxForm.upFilePath = response.data.filePath;
      this.uploadStatus = "1";
      this.$message.success("证明材料上传成功");
    },
    handleProcess(event, file, fileList) {
      this.uploadStatus = "0";
    },
    handleError(err, file, fileList) {
      this.uploadStatus = "-1";
      this.$message.error("证明材料上传失败，请重新上传");
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
    // 离境原因控制附件模版要求
    changeModel() {
      // 旅游 探亲 看病 子女入学毕业 参会 其他
      var self = this;
      if (self.sqxxForm.liveReason == "旅游") {
        self.$refs.tipModel.innerText = "行程证明";
        self.downShow = true;
        self.$refs.redTips.innerText =
          "（提示：请根据行程证明模板上传相关证明材料）"; //文本信息
      } else if (self.sqxxForm.liveReason == "探亲") {
        self.downShow = false;
        self.$refs.redTips.innerText = "（提示：请上传相关证明材料）"; //文本信息
      } else if (self.sqxxForm.liveReason == "看病") {
        self.downShow = false;
        self.$refs.redTips.innerText =
          "（提示：赴国外看病的需提供近期三甲以上医院的诊断报告复印件）";
      } else if (self.sqxxForm.liveReason == "子女入学毕业") {
        self.downShow = false;
        self.$refs.redTips.innerText =
          "（提示：参加子女毕业典礼需提供邀请函的复印件和翻译件）";
      } else if (self.sqxxForm.liveReason == "参会") {
        self.downShow = false;
        self.$refs.redTips.innerText =
          "（提示：赴国外参加会议、学术研究的需提供本人专家学者身份证明的复印件，国外邀请函的复印件和翻译件）";
      } else {
        self.downShow = false;
        // self.$refs.tipModel.innerText='无';
        self.$refs.redTips.innerText =
          "（提示：其他因特殊原因申请出国（境）需要提供的相关证明件）";
      }
    },

    //下载
    downWorld() {
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr(
        "action",
        self.global.serverPath6 + "/download/goAbroad/行程证明.doc"
      );
      $(document.body).append($eleForm);
      $eleForm.submit();
    },
    // 重置表单
    resetForm(formName) {
      this.upFileName = [];
      this.$refs[formName].resetFields();
    },
    // 保存申请信息
    saveForm(formName) {
      var togatherValid = true;
      var self = this;
      if (self.sqxxForm.togatherPro == "有") {
        if (self.master_user.data.length == 0) {
          self.$message.error("至少添加一名同行人");
          togatherValid = false;
        } else {
          self.master_user.data.forEach(function(data, index) {
            if (data.isSet) {
              togatherValid = false;
              self.$message.error("请保存所有同行人信息");
              return;
            }
          });
        }
      }
      if (self.uploadStatus == "0") {
        self.$message.warning("证明材料上传中");
        return;
      }
      this.$refs[formName].validate(valid => {
        if (valid && togatherValid) {
          console.log(self.workId);
          const _obj = {
            url: self.global.serverPath8 + "/flow/process/saveForm",
            postData: {
              bsGoAbroadRequest: {
                workId: self.workId,
                id: self.applicationFormId,
                userId: localStorage.getItem("loginpersonId"), //申请人id
                startTime: self.sqxxForm.startTime, //开始时间
                endTime: self.sqxxForm.endTime, //结束时间
                destinationBroad: self.sqxxForm.otherPlace, //出国目的地
                passPlace: self.sqxxForm.passPlace, //经停地
                departureForm: self.sqxxForm.liveStyle, //离境形式
                travelAgencyName: self.sqxxForm.tripName, //旅行社名称
                involvedSecretLevel: self.sqxxForm.selSecret, //是否涉密
                isTogether: self.sqxxForm.togatherPro, //是否有同行人员
                departureReason: self.sqxxForm.liveReason, //离境原因
                uploadAttached: self.Dashboard2Upload, //上传附件
                outlandPhone: self.sqxxForm.outlandPhone, //境外联系电话
                remarks: self.sqxxForm.remarks, //备注
                goAbroadTogetherList: self.master_user.data,
                upFilePath: self.sqxxForm.upFilePath, //附件路径
                upFileName: self.sqxxForm.upFileName //附件路径
              },
              pleaseSellOffRequest: {},
              type: "cgj",
              workId: self.workId,
              status: "999",
              personId: ""
            },
            isGet: false
          };
          communication(_obj).then(
            res => {
              if (res.code == 0) {
                self.$message.success("保存成功，请至个人中心查看");
                self.master_user.data = []; //清空表格元素
                self.sqxxForm.otherPlace = [];
                self.sqxxForm.passPlace = [];
                self.$refs[formName].resetFields();
              }
            },
            function(res) {
              self.$message.error("请求失败");
            }
          );
        }
      });
    },
    // 提交申请信息
    submitForm(formName) {
      var togatherValid = true;
      var self = this;
      if (self.sqxxForm.togatherPro == "有") {
        if (self.master_user.data.length == 0) {
          self.$message.error("至少添加一名同行人");
          togatherValid = false;
        } else {
          self.master_user.data.forEach(function(data, index) {
            if (data.isSet) {
              togatherValid = false;
              self.$message.error("请保存所有同行人信息");
              return;
            }
          });
        }
      }
      if (self.uploadStatus == "0") {
        self.$message.warning("证明材料上传中");
        return;
      }
      this.$refs[formName].validate(valid => {
        if (valid && togatherValid) {
          console.log(self.workId);
          self.loading = true;
          const _obj = {
            url: self.global.serverPath8 + "/flow/process/submitForm",
            postData: {
              bsGoAbroadRequest: {
                workId: self.workId,
                id: self.applicationFormId,
                userId: localStorage.getItem("loginpersonId"), //申请人id
                startTime: self.sqxxForm.startTime, //开始时间
                endTime: self.sqxxForm.endTime, //结束时间
                destinationBroad: self.sqxxForm.otherPlace, //出国目的地
                passPlace: self.sqxxForm.passPlace, //经停地
                departureForm: self.sqxxForm.liveStyle, //离境形式
                travelAgencyName: self.sqxxForm.tripName, //旅行社名称
                involvedSecretLevel: self.sqxxForm.selSecret, //是否涉密
                isTogether: self.sqxxForm.togatherPro, //是否有同行人员
                departureReason: self.sqxxForm.liveReason, //离境原因
                uploadAttached: self.Dashboard2Upload, //上传附件
                outlandPhone: self.sqxxForm.outlandPhone, //境外联系电话
                remarks: self.sqxxForm.remarks, //备注
                goAbroadTogetherList: self.master_user.data,
                upFilePath: self.sqxxForm.upFilePath, //附件路径
                upFileName: self.sqxxForm.upFileName //附件路径
              },
              pleaseSellOffRequest: {},
              type: "cgj",
              workId: self.workId,
              status: "998",
              personId: ""
            },
            isGet: false
          };
          communication(_obj).then(
            res => {
              if (res.code == 0) {
                if (res.data.ccflowSuccess == null) {
                  self.$message.error("请设置审批人");
                  self.loading = false;
                } else {
                  self.$message.success("提交成功，请至个人中心查看");
                  self.master_user.data = []; //清空表格元素
                  self.sqxxForm.otherPlace = [];
                  self.sqxxForm.passPlace = [];
                  self.$refs[formName].resetFields();
                  self.loading = false;
                }
              } else {
                // self.$message.error('暂无数据')
                self.loading = false;
              }
            },
            function(res) {
              self.$message.error("请求失败");
              self.loading = false;
            }
          );
        }
      });
    },
    // 删除表格行
    deleteRow(index, row) {
      this.idx = index;
      this.master_user.data.splice(this.idx, 1);
      this.$message.success("删除成功");
    },
    //读取表格数据
    readMasterUser() {
      //根据实际情况，自己改下啊
      this.master_user.data.map(i => {
        // i.id = generateId.get();//模拟后台插入成功后有了id
        i.isSet = false; //给后台返回数据添加`isSet`标识
        return i;
      });
    },
    //添加账号
    addMasterUser() {
      for (let i of this.master_user.data) {
        if (i.isSet) return this.$message.warning("请先保存当前编辑项");
      }
      let j = {
        id: 0,
        relationUserName: "",
        appellation: "",
        workUnits: "",
        jobPosition: "",
        isSet: true,
        _temporary: true
      };
      this.master_user.data.push(j);
      this.master_user.sel = JSON.parse(JSON.stringify(j));
    },
    //修改
    pwdChange(row, index, cg) {
      //点击修改 判断是否已经保存所有操作
      if (
        cg &&
        (this.master_user.sel.appellation == "" ||
          this.master_user.sel.jobPosition == "" ||
          this.master_user.sel.workUnits == "" ||
          this.master_user.sel.relationUserName == "")
      ) {
        console.log(this.master_user.sel);
        this.$message.warning("请填写同行人员完整信息！");
        return false;
      } else {
        for (let i of this.master_user.data) {
          if (i.isSet && i.id != row.id) {
            this.$message.warning("请先保存当前编辑项");
            return false;
          }
        }
        //是否是取消操作
        if (!cg) {
          if (!this.master_user.sel.id) this.master_user.data.splice(index, 1);
          return (row.isSet = !row.isSet);
        }
        //提交数据
        if (row.isSet) {
          //项目是模拟请求操作 自己修改下
          // (function() {
          let data = JSON.parse(JSON.stringify(this.master_user.sel));
          for (let k in data) row[k] = data[k];
          this.$message({
            type: "success",
            message: "保存表格!"
          });

          //然后这边重新读取表格数据
          this.readMasterUser();
          row.isSet = false;
          // })();
        } else {
          this.master_user.sel = JSON.parse(JSON.stringify(row));
          row.isSet = true;
        }
      }
    },
    // 是否有同行人员
    txTable() {
      var self = this;
      if (self.sqxxForm.togatherPro == "有") {
        self.tableShow = true;
      } else {
        self.tableShow = false;
      }
    },
    changeStyle() {
      var self = this;
      if (self.sqxxForm.liveStyle == "跟团") {
        self.sqxxRules.tripName = [
          { required: true, message: "请填写旅行社名称", trigger: "blur" }
        ];
        self.getCountry1();
      } else {
        self.sqxxForm.otherPlace = null; //先清空，自由行不能选择除港澳台的其他地方
        self.sqxxRules.tripName = [];
        self.getCountry2();
      }
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
.promptCon {
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #dcdfe6;
  display: block;
  width: calc(100% - 50px);
}
.Dashboard2 .el-table th {
  text-align: center !important;
}
.papersType {
  line-height: 24px;
  padding-left: 90px;
}
.papersFont {
  font-size: 14px;
}
.areaInput {
  border-radius: 4px;
  border-color: #ddd;
  width: 59%;
}
.downLoad {
  color: #20a0ff;
  cursor: pointer;
}
.sq-icon {
  background: url("../../../static/img/sq.png") no-repeat center;
  background-size: 100% 100%;
}

.card2 {
  margin: 0 20px;
}

.steps {
  padding: 3% 0 0 2%;
}

.el-row {
  margin-bottom: 20px;
}

.mgb20 {
  margin-bottom: 16px;
}
.el-table td,
.el-table th {
  text-align: center;
}
.handle-box2 .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
  line-height: 28px !important;
}
.txryTable .el-table--enable-row-transition .el-table__body td {
  text-align: center;
}
.el-card {
  overflow: visible !important;
}
</style>
<style>
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

.el-upload__tip {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #ff0000;
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

.handle-box2 .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
  line-height: 28px;
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
/*.vue-treeselect__menu-container,.vue-treeselect__menu{*/
/*    max-height: 200px!important;*/
/*}*/
.handle-box2 .el-form-item__label {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
  letter-spacing: 0;
}

.handle-box2 .el-select {
  width: 76%;
}

.handle-box2 .el-input__inner {
  width: 76%;
}
.input-remarks .el-input__inner {
  width: 94%;
}

.timeInput .el-input__inner {
  width: 100%;
}

.handle-box2 .el-select .el-input__inner {
  width: 100%;
}

.sqxx-table .el-tag--info {
  background-color: transparent;
  border: none;
}

.sqxx-table .el-tag--info,
.sqxx-table .el-tag--info .el-tag__close {
  font-size: 14px;
  color: #1890ff;
}

.sqxx-table .el-tag--danger {
  background-color: transparent;
  border: none;
  font-size: 14px;
}

.sqxx-table .el-tag {
  border: none;
  font-size: 14px;
  background-color: transparent;
}
.handle-box2 .el-upload--text {
  width: 76%;
  height: 32px;
  border: none;
  text-align: left;
}

/*.upBtn .el-upload--text {*/
/*height: 50px!important;*/
/*}*/

.handle-box2 .el-upload-dragger {
  border: none;
  height: 100%;
  text-align: left;
  padding-left: 3px;
}

.handle-box2 .el-upload__tip {
  margin-top: 0;
}

.handle-box2 .el-upload-dragger {
  width: 100%;
}

.handle-box2 .el-upload-dragger .el-upload__tex downloadt {
  text-align: left;
}

.steps .el-step__title {
  font-size: 14px !important;
}

.steps .el-step__icon-inner {
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

.el-table-add-row {
  cursor: pointer;
  width: 100%;
  text-align: center;
  height: 32px;
  line-height: 32px;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  border: 1px dashed #d9d9d9;
  color: rgba(0, 0, 0, 0.85);
  margin-top: 10px;
}

.steps .el-step__title.is-finish {
  color: rgba(0, 0, 0, 0.85);
  font-family: PingFangSC-Medium;
  font-size: 14px;
  font-weight: 600;
}
.fuzzinfoCon .el-form-item__label {
  height: 32px;
  line-height: 32px;
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
  height: 130px;
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
  top: 105px;
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
.DashbCon .green {
  color: #409eff !important;
}
.DashbCon .gray .data {
  color: gray;
}

.Dashboard2Warn {
  font-size: 12px;
  color: red;
  text-indent: 1em;
}
.Dashboard2 .downLoad {
  display: inline-block;
}
.Dashboard2 .upBtn .el-form-item__content {
  /*width: 95px!important;*/
  height: 30px !important;
  /*overflow: hidden*/
}
/*.Dashboard2 .el-upload-list{*/
/*display: none*/
/*}*/
</style>

