<template>
	 <div class="table LeaveManage" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
		<el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                    <el-breadcrumb-item>请销假申请</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="titIcon">
                <i class="faSelf sq-icon"></i>
                <p v-show="applyFormIdShow">申请单号：
                    <span>{{applyFormId}}</span>
                </p>
            </div>
			<div class="LeaveManCon" v-show="noLead"></div>
			<div class="processBarLead" v-show="lead"></div>
            <div class="LeaveUser-info">
                <p></p>
                <p></p>
            </div>
        </el-card>
		<div class="containe">
			<div class="leave is-always-shadow">
				<el-row>
					<el-col :span="24" class="title">
						<div class="grid-content bg-purple-dark clearfix"><span>申请信息</span></div>
					</el-col>
					<el-row>
					  	<el-col :span="24">
					  		<el-form :inline="true" :rules="rules2" :label-position="'right'" label-width="120px" ref='leaveForm' :model="leaveForm" class="demo-form-inline fuzzinfoCon">
								<el-row :gutter="6">
									<el-col :span="24">
										<div class="promptCon" style="position: relative;" :class="toggleShow?class1:class2">
                      <img :src="require('static/img/manage/fenzu.png')" style="position: absolute;"/>
											<p class="promptse compassionate" v-show="leaveForm.applyType == '事假'?true:false">提示：事假一般不超过5天，超出需抵算当年年休假天数，仍然超出的，按年度类推进行抵算。</p>
											<p class="promptse sick" v-show="leaveForm.applyType == '病假'?true:false">提示：病假须出具公费医疗定点医院诊断病休证明，否则不予批准；由本人向所在单位申请，病假期限视病情而定。</p>
											<p class="promptse mothering" v-show="leaveForm.applyType == '探亲假'?true:false">提示：探亲假是按规定探望长期分居两地的配偶及父、母或子女，假期包括公休假日和法定节日，不顺延。（具体情形可参考假期规定）</p>
											<p class="promptse marriage" v-show="leaveForm.applyType == '婚假'?true:false">提示：对依法办理结婚登记的夫妻，可享受婚假15天（含法定假期3天）。</p>
											<p class="promptse maternity" v-show="leaveForm.applyType == '产假'?true:false">
												提示：产假为90天（含产前假15天），如难产增加15天；多胞胎生育的，每多生一个婴儿，增加产假15天。符合晚育年龄（女年满24周岁的初育）的夫妻，女方可享受产假120天（含法定产假90天），男方可享受护理假15天。怀孕3个月以内流产的，根据医疗单位证明，给予20-30天产假；怀孕4-7个月流产的，产假可休42天；怀孕7个月以上流产的，产假可休90天。怀孕7个月以上（含7个月）的，上班确有困难的，经本人申请，单位批准，可休产前假60天。
											</p>
											<p class="promptse die" v-show="leaveForm.applyType == '丧假'?true:false">提示：丧假是工作人员在其主要亲属（本人父母、岳父母或公婆、配偶和子女）死亡后，用于处理丧事的假期。假期为3天，在批准的丧假期间，工资福利待遇照发。</p>
											<p class="promptse lateChildbirth" v-show="leaveForm.applyType == '陪产假'?true:false">提示：符合晚育年龄的夫妻，女方可享受产假120天（含法定产假90天），男方可享受护理假15天</p>
											<p class="promptse annualLeave;" v-show="leaveForm.applyType == '年休假'?true:false">
												提示：机关、事业单位工作人员连续工作满一年及一年以上的享受带薪年休假。具体参见《机关工作人员各类假期及待遇的有关规定》。
											</p>
										</div>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="applyType" class="formSelect" label="休假类型：">
											<el-select v-model="leaveForm.applyType" @change="selects()">
												<el-option label="离宁外出" value="离宁外出"></el-option>
												<el-option label="年休假" value="年休假"></el-option>
												<el-option label="探亲假" value="探亲假"></el-option>
												<el-option label="陪产假" value="陪产假"></el-option>
												<el-option label="产假" value="产假"></el-option>
												<el-option label="婚假" value="婚假"></el-option>
												<el-option label="病假" value="病假"></el-option>
												<el-option label="事假" value="事假"></el-option>
												<el-option label="丧假" value="丧假"></el-option>
												<el-option label="调休" value="调休"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="16">
 										<el-form-item prop="cause" label="请假事由：">
                      <el-input v-model="leaveForm.cause" ></el-input>
                    </el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="lateMarriage" class="formSelect" label="是否晚婚：" v-show="leaveForm.applyType == '婚假'?true:false">
											<el-select v-model="leaveForm.lateMarriage">
												<el-option label="是" value="是" key="是" ></el-option>
												<el-option label="否" value="否" key="否" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="maternityLeaveType" class="formSelect" label="产假类型：" v-show="leaveForm.applyType == '产假'?true:false">
											<el-select v-model="leaveForm.maternityLeaveType">
												<el-option label="顺产" value="顺产" key="顺产" ></el-option>
												<el-option label="难产" value="难产" key="难产" ></el-option>
												<el-option label="多胞胎" value="多胞胎" key="多胞胎" ></el-option>
												<el-option label="流产" value="流产" key="流产" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="isLateChildbirth" class="formSelect" label="是否晚育：" v-show="leaveForm.applyType == '产假'?true:false">
											<el-select v-model="leaveForm.isLateChildbirth">
												<el-option label="是" value="是" key="是" ></el-option>
												<el-option label="否" value="否" key="否" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="births"  label="胎数：" v-show="leaveForm.applyType == '产假'?true:false">
											<el-input class='' v-model="leaveForm.births"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="kinship" label="亲属关系：" v-show="leaveForm.applyType == '丧假'?true:false">
											<el-input v-model="leaveForm.kinship"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="workingYears" class="formSelect" label="工作年限：" v-show="leaveForm.applyType == '年休假'?true:false">
											<el-input v-model="leaveForm.workingYears" disabled = "disabled"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" label="请假累计：" prop="applyType" v-show="leaveForm.applyType == '年休假'?true:false">
											<el-input v-model="leaveForm.applyType == '年休假'?'0':leaveForm.casualLeaveDays" disabled = "disabled" ></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" label="病假累计：" prop="applyType"  v-show="leaveForm.applyType == '年休假'?true:false">
											<el-input v-model="leaveForm.applyType == '年休假'?'0':leaveForm.sickLeaveDays" disabled = "disabled" ></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" label="今年已调休：" prop="applyType" v-show="leaveForm.applyType == '调休'?true:false">
											<el-input v-model="leaveForm.applyType == '调休'?'0':leaveForm.paidLeaveDays" disabled = "disabled"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item label="开始时间：" prop="valueBegin" class="timeInput">
											<el-date-picker v-model="leaveForm.valueBegin" :picker-options="pickerOptions0" format='yyyy-MM-dd' style="width: 76%;" value-format="yyyy-MM-dd" type="date"></el-date-picker>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="valueEnd" class="valueEnd" label="结束时间：">
											<el-date-picker :pciker-options="endDatePicker" :picker-options="pickerOptions1" v-model="leaveForm.valueEnd" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="willa" label="休假天数：">
											<el-input type="number" v-model.number="leaveForm.willa" autocomplete="off"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8" >
										<el-form-item  label="已休天数：" prop="casualLeaveDays" v-show="leaveForm.applyType == '事假'?true:false">
											<el-input v-model="leaveForm.casualLeaveDays" disabled="disabled"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8" >
										<el-form-item  label="已休天数：" prop="sickLeaveDays" v-show="leaveForm.applyType == '病假'?true:false">
											<el-input v-model="leaveForm.sickLeaveDays" disabled="disabled"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8" >
										<el-form-item  label="已休天数：" prop="homeLeaveDays" v-show="leaveForm.applyType == '探亲假'?true:false">
											<el-input v-model="leaveForm.homeLeaveDays" disabled="disabled"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="Partir" class="formSelect isLeaveNJ" label="是否离宁：">
											<el-select v-model="leaveForm.Partir" v-bind:disabled="disabledInput" @change="changeStyle()">
												<el-option label="是" value="是" key="是" ></el-option>
												<el-option label="否" value="否" key="否" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="transportation" class="formSelect" label="交通工具：" v-if="leaveForm.Partir == '是'?true:false">
											<el-select v-model="leaveForm.transportation">
												<el-option label="火车" value="火车" key="火车" ></el-option>
												<el-option label="飞机" value="飞机" key="飞机" ></el-option>
												<el-option label="客车" value="客车" key="客车" ></el-option>
												<el-option label="自驾" value="自驾" key="自驾" ></el-option>
												<el-option label="其他" value="其他" key="其他" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item  prop="address" class="formSelect" label="外出地点：" v-if="leaveForm.Partir == '是'?true:false">
											<!-- <el-input v-model="leaveForm.address"></el-input> -->
                      <el-cascader placeholder="" v-model="leaveForm.address" :options="optionse" :props="{ multiple: true }" collapse-tags :show-all-levels="false" filterable></el-cascader>
										</el-form-item>
									</el-col>
                  <el-col :span="8">
										<el-form-item prop="fraternity" class="formSelect" label="同行人员：">
											<el-select v-model="leaveForm.fraternity" @change="fraternityStyle()">
												<el-option label="有" value="有" key="有" ></el-option>
												<el-option label="无" value="无" key="无" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<div class="sqxx-table txryTable txryTableInput"  v-if="leaveForm.fraternity == '有'?true:false">
					          <el-table size="mini" :data="master_user.data" border style="width: 100%" highlight-current-row>
					              <el-table-column v-for="(v,j) in master_user.columns" :key="j" :prop="v.field" :label="v.title" :width="v.width">
					                  <template slot-scope="scope">
					                      <span v-if="scope.row.isSet">
					                          <el-input size="mini" v-model="master_user.sel[v.field]">
					                          </el-input>
					                      </span>
					                      <span v-else>{{scope.row[v.field]}}</span>
					                  </template>
					              </el-table-column>
					              <el-table-column label="操作" width="180">
					                  <template slot-scope="scope" style="text-align: center;">
					                      <span class="el-tag el-tag--info el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,true)">
					                          {{scope.row.isSet?'保存':"编辑"}}
					                      </span>
					                      <span v-if="!scope.row.isSet" class="el-tag el-tag--danger el-tag--mini" @click="deleteRow(scope.$index, scope.row)" style="cursor: pointer;">删除</span>
					                      <span v-else class="el-tag  el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,false)">取消</span>
					                  </template>
					              </el-table-column>
					          </el-table>
					          <div class="el-table-add-row" style="width: 100%;" @click="addMasterUser()">
					              <span>+ 新增同行人员</span>
					          </div>
					        </div>
                  <el-col :span="24" class="reasonLeaveBox leaveManage">
										<el-form-item class="formSelect" label="备注：">
											<el-input class="reasonLeaveCon" maxlength="500" placeholder="请输入备注" v-model="leaveForm.remarks"  @input="descInput" ></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="24">
										<el-form-item class="formSelect upClass" prop="enclosure" label="上传附件：" pro="upOption">
											<el-upload :on-success="handleSuccess" :on-progress="handleProcess" :on-error="handleError" v-model="leaveForm.enclosure" class="upload-demo"  :data="uploadParam" :action="actss" :limit="1" drag  multiple  :headers = "headers" :file-list="fileList">
												<el-button size="small" type="primary">证明材料请点击上传</el-button>
											</el-upload>
										</el-form-item>
									</el-col>
								</el-row>
								<!-- <el-col :span="24" class="LeaveManageUpload">
									<el-form-item label="行程证明模板：" prop="attOption">
										<div class="el-upload__text downLoad" @click="downWorldWord()">
											<span class="icon-world"></span>
											<span ref="tipModel">离宁外出请假审批表</span>
										</div>
										<div class="exampleIm"></div>
										<div ref="redTips" class="el-upload__tip"></div>
									</el-form-item>
								</el-col> -->
								<el-row class="LeaveManageBtn">
									<el-col :span="24">
										 <div class="btn-group">
											 <el-button @click="saveForm('leaveForm')">保存</el-button>
											<el-button @click="resetForm('leaveForm')">取消</el-button>
											<el-button type="primary" :disabled="submitBtn" @click="submitLeave('leaveForm')">提交</el-button>
										 </div>
									</el-col>
								</el-row>
							</el-form>
					  	</el-col>
					</el-row>
				</el-row>

			</div>
		</div>
		 <el-footer :style="'text-align:center'">&copy;2019-2022&emsp;公安局&emsp;版权所有</el-footer>
	 </div>
</template>

<script>
import { communication } from "../../data/getAxios";
export default {
  data() {
    return {
      noLead: false,
      lead: false,
      uploadStatus: "1", // -1 - 上传失败；0 - 上传中；1 - 上传成功
      headers: {
        token: localStorage.getItem("setToken")
      },
      uploadParam: {
        type: "qxj",
        workId: localStorage.getItem("workId")
      },
      enclosure: "",
      loading: false,
      disabledInput: false,
      // http://192.168.8.135:8084/process_engine/UploadFile/uploadUserPotoByPersonIdNew
      actss: this.global.serverPath8 + "/flow/process/upload",
      workId: "",
      txtVal: 0,
      applyFormIdShow: false,
      imgUrl: "",
      options: "",
      stepsTitle: [],
      processArr: [],
      optionse: [
        {
          value: "国外",
          label: "国外",
          children: []
        },
        {
          value: "公海邮轮",
          label: "公海邮轮",
          children: []
        }
      ],
      submitBtn: false,
      titles: {
        title0: "",
        title1: "",
        title2: "",
        title3: "",
        title4: ""
      },
      toggleShow: true,
      class1: "borderShow",
      class2: "borderHide",
      prompting: false,
      fileList: [],
      tableShow: false,
      showAddress: false,
      getUrl: "./static/userTable.json",
      tableData: [], //表格当前页数据
      cur_page: 1,
      cur_page2: 1,
      pagesize: 10,
      pagesize2: 10,
      pageShow1: true,
      radios: [],
      pageShow2: false,
      multipleSelection: [], //多选数组
      select_cate: "",
      select_word: "",
      data_length: 0,
      data_length2: 0,
      del_list: [],
      is_search: false,
      editVisible: false,
      pwVisible: false,
      powerVisible: false,
      delVisible: false,
      addTableForm: false,
      addFormVisible: false, //新增界面是否显示
      stepActive: 0, //步骤条状态
      description: "", //步骤条姓名
      applyFormId: "", //申请单号
      endDatePicker: this.processDate(),
      formPerson: {
        appellation: "",
        name: "",
        unit: "",
        duties: ""
      },
      pickerOptions0: {
        //开始时间
        // disabledDate: time => {
        //   let endDateVal = this.leaveForm.valueEnd;
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
          let beginDateVal = this.leaveForm.valueBegin;
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
      master_user: {
        sel: null, //选中行
        columns: [
          { field: "relationUserName", title: "姓名", width: 140 },
          { field: "appellation", title: "称谓", width: 160 },
          { field: "workUnits", title: "工作单位" },
          { field: "jobPosition", title: "职务", width: 240 }
          // { field: "info", title: "其他信息" }
        ],
        data: []
      },
      leaveForm: {
        willa: "",
        valueBegin: "",
        valueEnd: "",
        fate: "",
        applyType: "事假",
        Subject: "",
        Partir: "",
        address: "",
        cause: "",
        remarks: "",
        vacations: "",
        transportation: "",
        fraternity: "",
        isLateChildbirth: "",
        births: "",
        maternityType: "",
        lateMarriage: "",
        workingYears: "",
        upFileName: "",
        upFilePath: ""
      },
      powerform: [], //选中复选框
      radios: [], //所有复选框
      idx: -1,
      rules1: {
        appellation: [
          { required: true, message: "请输入同行人称谓", trigger: "change" }
        ],
        name: [
          { required: true, message: "请输入同行人姓名", trigger: "change" }
        ],
        unit: [
          { required: true, message: "请输入同行人工作单位", trigger: "change" }
        ],
        duties: [
          { required: true, message: "请输入同行人职务", trigger: "change" }
        ]
      },
      rules2: {
        address: [
          // { required: true, message: "请输入外出地点", trigger: "blur" }
          { required: true, message: "请选择外出地点", trigger: "change" }
        ],
        valueBegin: [
          { required: true, message: "请选择日期", trigger: "change" }
        ],
        valueEnd: [
          { required: true, message: "请选择日期", trigger: "change" }
        ],
        applyType: [
          { required: true, message: "请选择休假类型", trigger: "change" }
        ],
        cause: [
          { required: true, message: "请选择请假事由", trigger: "change" }
        ],
        Partir: [
          { required: true, message: "请选择是否离宁", trigger: "change" }
        ],
        transportation: [
          { required: true, message: "请选择交通工具", trigger: "change" }
        ],
        fraternity: [
          { required: true, message: "同行人员", trigger: "change" }
        ],
        willa: [
          // { required: true, message: "请输入休假天数", trigger: "blur" }
          { required: true, message: "休假天数不能为空", trigger: "blur" },
          { type: "number", message: "休假天数必须为数字值", trigger: "blur" }
        ]
        // remarks:[
        // 	{required: true, message: '请填写备注', trigger: 'change' }
        // ],
        //				maternityLeaveType:[
        //					{required: true, message: '请选择产假类型', trigger: 'change' }
        //				],
        //				isLateChildbirth:[
        //					{required: true, message: '请选择是否晚育', trigger: 'change' }
        //				],
        //				births:[
        //					{ required: true, message: '请输入胎数', trigger: 'blur' },
        //				],
        //				kinship:[
        //					{ required: true, message: '请输入亲属关系', trigger: 'blur' },
        //				],
        //				workingYears:[
        //					{ required: true, message: '请选择工作年限', trigger: 'change' },
        //				]
      }
    };
  },
  created() {
    this.getDatass();
  },
  methods: {
    checkNumber(value) {
      if (value && value % 0.5 != 0) {
        return false;
        // callback(new Error('请输入0.5的倍数'));
      } else {
        return true;
      }
    },
    downWorldWord() {
      var $eleForm = $("<form method='get'></form>");
      $eleForm.attr(
        "action",
        self.global.serverPath10 + "s/download/goAbroad/行程证明.doc"
      ); //待修改0
      $(document.body).append($eleForm);
      //提交表单，实现下载
      $eleForm.submit();
    },
    // 文本域计数
    descInput() {
      this.txtVal = this.leaveForm.cause.length;
    },
    fraternityStyle() {
      var self = this;
      if (self.leaveForm.fraternity == "0") {
        self.tableShow = true;
      } else {
        self.tableShow = false;
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
    },
    // 结束时间限制
    processDate() {
      const self = this;
      // return {
      //   disabledDate(time) {
      //     if (self.leaveForm.valueEnd) {
      //       //如果开始时间不为空，则结束时间大于开始时间
      //       return new Date(self.leaveForm.valueEnd).getTime() > time.getTime();
      //     } else {
      //       return time.getTime() > Date.now(); //开始时间不选时，结束时间最大值小于等于当天
      //     }
      //   }
      // };
    },
    handleSuccess(response, file, fileList) {
      this.leaveForm.upFileName = file.name;
      this.leaveForm.upFilePath = response.data.filePath;
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
    resetForm() {
      this.addTableForm = false;
    },
    cancel(formName3) {
      if (this.$refs[formName3] !== undefined) {
        this.$refs[formName3].resetFields();
      }
    },
    // 删除表格行
    deleteRow(index, row) {
      this.idx = index;
      this.master_user.data.splice(this.idx, 1);
      this.$message.success("删除成功");
    },
    //读取表格数据
    readMasterUser() {
      this.master_user.data.map(i => {
        //              i.id = generateId.get();//模拟后台插入成功后有了id
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
        userName: "",
        userNo: "",
        workUnit: "",
        department: "",
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
          let data = JSON.parse(JSON.stringify(this.master_user.sel));
          for (let k in data) row[k] = data[k];
          this.$message({
            type: "success",
            message: "保存表格!"
          });

          //然后这边重新读取表格数据
          this.readMasterUser();
          row.isSet = false;
        } else {
          this.master_user.sel = JSON.parse(JSON.stringify(row));
          row.isSet = true;
        }
      }
    },
    changeStyle() {
      var self = this;
      if (self.leaveForm.Partir == "有") {
        self.showAddress = true;
      } else {
        self.showAddress = false;
      }
    },
    //点击  提交
    submitLeave(leaveForm) {
      var togatherValid = true;
      var self = this;
      if (self.leaveForm.fraternity == "有") {
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
      self.$refs[leaveForm].validate(rules2 => {
        if (rules2 && togatherValid) {
          if (self.checkNumber(self.leaveForm.willa)) {
            self.loading = true;
            let addresse = "";
            if (self.leaveForm.Partir == "是") {
              let addresss = self.leaveForm.address;
              addresss.forEach(item => {
                if (addresse) {
                  addresse = addresse + "," + item[1];
                } else {
                  addresse = item[1];
                }
              });
            }
            const obj = {
              url: self.global.serverPath8 + "/flow/process/submitForm",
              postData: {
                pleaseSellOffRequest: {
                  id: self.applyFormId,
                  applicantPeople: localStorage.getItem("loginpersonId"), //申请人id
                  workId: self.workId,
                  beginTime: self.leaveForm.valueBegin, //开始时间
                  endTime: self.leaveForm.valueEnd, //结束时间
                  leaveDay: self.leaveForm.willa, //请假天数
                  leaveSubject: self.leaveForm.Subject, //请假主体
                  isLeaveNj: self.leaveForm.Partir, //是否离宁
                  address: addresse,
                  applyType: self.leaveForm.applyType, //请假事由
                  leaveCause: self.leaveForm.cause, //请假原因
                  remarks: self.leaveForm.remarks, //请假原因
                  enclosure: self.leaveForm.enclosure, //附件
                  pleSeOffTogetherInfo: self.master_user.data,
                  isLateMarriage: self.leaveForm.lateMarriage, //是否晚婚
                  maternityLeaveType: self.leaveForm.maternityLeaveType, //产假类型
                  isLateChildbirth: self.leaveForm.isLateChildbirth, //是否晚育
                  births: self.leaveForm.births, //胎数
                  kinship: self.leaveForm.kinship, //亲属关系
                  isTogether: self.leaveForm.fraternity,
                  workingYears: self.leaveForm.workingYears, //工作年限
                  vehicle: self.leaveForm.transportation,
                  upFilePath: self.leaveForm.upFilePath, //附件路径
                  upFileName: self.leaveForm.upFileName //附件路径
                },
                type: "qxj",
                workId: self.workId,
                status: "998"
              }
            };
            communication(obj).then(res => {
              if (res.code == 0) {
                if (res.data.ccflowSuccess == null) {
                  self.$message.error("请设置审批人");
                  self.loading = false;
                } else {
                  self.$message.success("提交成功，请至个人中心查看");
                  self.$refs[leaveForm].resetFields();
                  self.leaveForm.enclosure = "";
                  self.loading = false;
                }
              } else {
                self.$message.error("系统错误");
                self.loading = false;
              }
            });
          } else {
            self.$message.error("休假天数必须是0.5的倍数");
          }
        }
      });
    },
    //点击 保存
    saveForm(leaveForm) {
      var togatherValid = true;
      var self = this;
      if (self.leaveForm.fraternity == "有") {
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
      this.$refs[leaveForm].validate(rules2 => {
        if (rules2 && togatherValid) {
          let addresse = "";
          if (self.leaveForm.Partir == "是") {
            let addresss = self.leaveForm.address;
            addresss.forEach(item => {
              if (addresse) {
                addresse = addresse + "," + item[1];
              } else {
                addresse = item[1];
              }
            });
          }
          const obj = {
            url: self.global.serverPath8 + "/flow/process/saveForm",
            postData: {
              pleaseSellOffRequest: {
                id: self.applyFormId,
                applicantPeople: localStorage.getItem("loginpersonId"), //申请人id
                workId: self.workId,
                beginTime: self.leaveForm.valueBegin, //开始时间
                endTime: self.leaveForm.valueEnd, //结束时间
                leaveDay: self.leaveForm.willa, //请假天数
                leaveSubject: self.leaveForm.Subject, //请假主体
                isLeaveNj: self.leaveForm.Partir, //是否离宁
                address: addresse,
                applyType: self.leaveForm.applyType, //请假事由
                leaveCause: self.leaveForm.cause, //请假原因
                remarks: self.leaveForm.remarks, //请假原因
                enclosure: self.leaveForm.enclosure, //附件
                pleSeOffTogetherInfo: self.master_user.data,
                lateMarriage: self.leaveForm.lateMarriage, //是否晚婚
                maternityLeaveType: self.leaveForm.maternityLeaveType, //产假类型
                isLateChildbirth: self.leaveForm.isLateChildbirth, //是否晚育
                births: self.leaveForm.births, //胎数
                kinship: self.leaveForm.kinship, //亲属关系
                isTogether: self.leaveForm.fraternity,
                workingYears: self.leaveForm.workingYears, //工作年限
                vehicle: self.leaveForm.transportation,
                upFilePath: self.leaveForm.upFilePath, //附件路径
                upFileName: self.leaveForm.upFileName //附件路径
              },
              type: "qxj",
              workId: self.workId,
              status: "999"
            }
          };
          communication(obj).then(res => {
            if (res.code == 0) {
              this.$message.success("保存成功");
              this.$router.push("/wdlc");
            }
          });
        }
      });
    },
    selects() {
      var self = this;
      if (
        self.leaveForm.applyType == "调休" ||
        self.leaveForm.applyType == "其他"
      ) {
        self.disabledInput = false;
        this.toggleShow = false;
        self.leaveForm.Partir = "";
      } else if (self.leaveForm.applyType == "离宁外出") {
        this.toggleShow = false;
        self.leaveForm.Partir = "是";
        this.disabledInput = true;
      } else {
        self.disabledInput = false;
        this.toggleShow = true;
        self.leaveForm.Partir = "请选择";
      }
    },
    handleRemove(file, fileList) {},
    handlePreview(file) {},
    // 分页导航
    handleCurrentChange(val) {
      this.loading = true;
      this.cur_page = val;
      this.getData();
    },
    // 分页导航
    handleCurrentChange2(val) {
      this.loading = true;
      this.cur_page2 = val;
      this.getSearchData();
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    getDatass() {
      var self = this;
      self.$http.get("../static/country.json").then(res => {
        for (var i = 0; i < res.body.list.length; i++) {
          if (res.body.list[i].id == "公海邮轮") {
            let fullName = pinyin.getFullChars(res.body.list[i].label);
            let fullName1 = fullName.toLocaleLowerCase();
            self.optionse[1].children.push({
              value: res.body.list[i].id,
              label: res.body.list[i].label + "(" + fullName1 + ")"
            });
          } else if (
            res.body.list[i].id == "香港" ||
            res.body.list[i].id == "澳门" ||
            res.body.list[i].id == "台湾"
          ) {
          } else {
            let fullName = pinyin.getFullChars(res.body.list[i].label);
            let fullName1 = fullName.toLocaleLowerCase();
            self.optionse[0].children.push({
              value: res.body.list[i].id,
              label: res.body.list[i].label + "(" + fullName1 + ")"
            });
          }
        }
        self.$http.get("../static/provincese.json").then(res => {
          let provinces = [];
          for (var key = 0; key < res.body.provinces.length; key++) {
            let provincss = [];
            if (res.body.provinces[key].cities.length > 0) {
              res.body.provinces[key].cities.forEach(itemse => {
                let fullNames = pinyin.getFullChars(itemse.name);
                let fullName2 = fullNames.toLocaleLowerCase();
                provincss.push({
                  value: itemse.name,
                  label: itemse.name + "(" + fullName2 + ")",
                  level: itemse.level
                });
              });
            }
            let fullNamese = pinyin.getFullChars(res.body.provinces[key].name);
            let fullName3 = fullNamese.toLocaleLowerCase();
            provinces.push({
              value: res.body.provinces[key].name,
              label: res.body.provinces[key].name + "(" + fullName3 + ")",
              level: res.body.provinces[key].level,
              children: provincss
            });
          }
          self.optionse = provinces.concat(self.optionse);
          self.init();
          console.log(self.optionse);
        });
      });
    },
    init() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/flow/process/queryForm?type=qxj",
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
            self.workId = res.data.workId;
            localStorage.setItem("workId", self.workId);
            this.ready();
            self.leaveForm.casualLeaveDays =
              res.data.basicPleaseOffForm.casualLeaveDays;
            this.$forceUpdate();
            self.leaveForm.sickLeaveDays =
              res.data.basicPleaseOffForm.sickLeaveDays;
            self.leaveForm.homeLeaveDays =
              res.data.basicPleaseOffForm.homeLeaveDays;
            self.leaveForm.paidLeaveDays =
              res.data.basicPleaseOffForm.paidLeaveDays;
            self.workId = res.data.workId;
            self.leaveForm.workingYears = res.data.workingYears;
          } else if (res.code == 0) {
            self.stepType = res.data.pleSeOffForm.applyType;
            self.applyFormId = res.data.pleSeOffForm.id;
            self.workId = res.data.pleSeOffForm.workId;
            localStorage.setItem("workId", self.workId);
            this.ready();
            self.leaveForm.workingYears = res.data.pleSeOffForm.workingYears;
            self.leaveForm.valueBegin = res.data.pleSeOffForm.beginTime;
            self.leaveForm.valueEnd = res.data.pleSeOffForm.endTime;
            self.leaveForm.willa = Number(res.data.pleSeOffForm.leaveDay);
            self.leaveForm.address = [];
            if (res.data.pleSeOffForm.address) {
              let addresses = res.data.pleSeOffForm.address;
              let addresse = addresses.split(",");
              addresse.forEach((element, i) => {
                self.optionse.forEach(elements => {
                  if (elements.children) {
                    elements.children.forEach(items => {
                      if (items.value == element) {
                        self.leaveForm.address.push([elements.value, element]);
                      }
                    });
                  }
                });
              });
            }
            self.leaveForm.cause = res.data.pleSeOffForm.leaveCause;
            self.leaveForm.remarks = res.data.pleSeOffForm.remarks;
            self.leaveForm.applyType = res.data.pleSeOffForm.applyType;
            if (
              self.leaveForm.applyType == "调休" ||
              self.leaveForm.applyType == "其他"
            ) {
              self.disabledInput = false;
              self.toggleShow = false;
            } else if (self.leaveForm.applyType == "离宁外出") {
              self.toggleShow = false;
              self.disabledInput = true;
            } else {
              self.disabledInput = false;
              self.toggleShow = true;
            }
            self.leaveForm.Subject = res.data.pleSeOffForm.leaveSubject;
            self.leaveForm.Partir = res.data.pleSeOffForm.isLeaveNj;
            self.leaveForm.lateMarriage = res.data.pleSeOffForm.lateMarriage;
            self.leaveForm.maternityLeaveType =
              res.data.pleSeOffForm.maternityLeaveType;
            self.leaveForm.isLateChildbirth =
              res.data.pleSeOffForm.isLateChildbirth;
            self.leaveForm.births = res.data.pleSeOffForm.births;
            self.leaveForm.kinship = res.data.pleSeOffForm.kinship;
            self.leaveForm.fraternity = res.data.pleSeOffForm.isTogether;
            self.leaveForm.upFileName = res.data.pleSeOffForm.upFileName;
            self.leaveForm.upFilePath = res.data.pleSeOffForm.upFilePath;
            self.leaveForm.casualLeaveDays =
              res.data.basicPleaseOffForm.casualLeaveDays || 0;
            self.leaveForm.sickLeaveDays =
              res.data.basicPleaseOffForm.sickLeaveDays || 0;
            self.leaveForm.homeLeaveDays =
              res.data.basicPleaseOffForm.homeLeaveDays || 0;
            self.leaveForm.paidLeaveDays =
              res.data.basicPleaseOffForm.paidLeaveDays || 0;
            self.workId = res.data.pleSeOffForm.workId;
            self.leaveForm.workingYears = res.data.workingYears;
            self.master_user.data =
              res.data.pleSeOffTogetherInfo == null
                ? []
                : res.data.pleSeOffTogetherInfo;
            // self.upFileName.push(res.body.data.pleSeOffForm.enclosure);
            self.leaveForm.enclosure = res.data.pleSeOffForm.enclosure;
            self.leaveForm.transportation = res.data.pleSeOffForm.vehicle;

            self.fileList.push({
              name: res.data.pleSeOffForm.upFileName,
              status: "finished"
            });
          } else if (res.code != 1) {
            self.$message.error(res.message);
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    search() {
      this.pageShow1 = false;
      this.pageShow2 = true;
      this.getSearchData();
      this.is_search = true;
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //新增同行人员确定
    defineForm(formName1) {
      this.$refs[formName1].validate(valid => {
        if (valid) {
          this.$refs.formPerson.clearValidate();
          this.addTableForm = false;
        }
      });
    }
  }
};
</script>

<style scoped>
.wrapper .el-table th {
  text-align: center !important;
}
.el-form-item {
  margin-bottom: 12px;
}
.el-tabs__nav-wrap,
.el-tabs {
  width: 100% !important;
  margin-left: 0px !important;
}
.right-btn {
  float: right;
}
.handle-select {
  width: 200px;
}
.pagination {
  margin-bottom: 10px;
}
.pagination2 {
  height: 32px;
  margin-bottom: 10px;
}
.pagination2 .el-pagination {
  position: absolute;
  right: 30px;
  bottom: 10px;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.txryTable .el-table--border th,
.txryTable .el-table__fixed-right-patch {
  text-align: center;
}
.el-footer {
  height: 20px !important;
  font-size: 12px;
  color: #333;
  line-height: 20px;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
/*表格内容居中*/
.el-table td,
.el-table th {
  text-align: center;
}
.LeaveManage .el-table__row td {
  text-align: center;
}
</style>
<style>
.upClass .el-upload-dragger {
  width: auto;
  height: auto;
  border: none;
}
.leaveManage .el-form-item__content {
  width: calc(100% - 145px) !important;
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
  color: #409eff;
  margin-top: 10px;
  border-color: #b3d8ff;
}
.table .crumbs {
  background: white;
  margin-bottom: 0;
}
.table .titIcon {
  margin-top: 10px;
}
.leave {
  margin: 16px 20px;
  background: white;
}
.leaveList .leave .el-row {
  margin-bottom: 0px !important;
}
.leave .el-rowL:first-child {
  margin-bottom: 0;
}
.infoTab .el-table--border {
  border: 0;
}
.applyNum .grid-content {
  padding: 20px;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 15px;
  font-weight: bold;
}
.fuzzinfoCon {
  padding: 20px 20px 10px;
}
.fuzzinfoCon input {
  width: 208px !important;
}
.txryTableInput input {
  width: 72% !important;
}
.fuzzinfoCon select {
  width: 208px !important;
}
.el-upload {
  width: initial;
  height: initial;
  border: 0;
}
.el-upload-list {
  float: left;
}
.el-upload {
  float: left;
}
.el-upload-list__item:first-child {
  margin-top: 5px;
}
.infoTab .el-table__empty-block {
  width: 100% !important;
}
.el-upload-list__item-name {
  color: rgb(32, 160, 255);
}
.content .apply {
  background-position: 10px !important;
  height: 30px;
  line-height: 30px;
  padding-left: 45px;
}
.applyNum {
  padding: 10px 15px;
  background: white;
}
.applyNum .grid-content {
  margin-bottom: 0;
  border: 0;
}
.applyNum .grid-content:nth-of-type(2) {
  padding: 0;
}
.apply,
.gridright {
  padding: 0;
}
.gridright {
  text-align: right;
}
.submit {
  color: white;
}
.stepCon {
  width: 100%;
  overflow: hidden;
  background: white;
  margin: 0 auto;
  padding: 20px;
}
.steps {
  padding: 3% 3% 0 3%;
}
.infoTab .el-table__empty-block {
  min-height: 53px;
}
.sqxx-table {
  padding: 0 20px 20px;
}
.sqxx-table .el-tag--info {
  background-color: transparent;
  border: none;
}
.containe .title {
  float: inherit;
  padding: 10px 16px;
  border-bottom: 1px solid #ebeef5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
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
.steps .el-step__icon-inner[class*="el-icon"]:not(.is-status) {
  font-size: 17px !important;
}
.steps .el-step__title {
  font-size: 14px !important;
}
.sqxx-table .el-tag {
  border: none;
  font-size: 14px;
  background-color: transparent;
}
.LeaveUser-info {
  padding: 0 11.7%;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.65);
}
.LeaveUser-info p {
  margin: 10px 0;
}
.sq-icon {
  background: url("../../../static/img/sq.png") no-repeat center;
  background-size: 100% 100%;
}
.faSelf {
  display: inline-block;
  width: 30px;
  height: 30px;
  position: relative;
}
.leave .title {
  float: inherit;
}
.figurePortrait {
  position: relative;
  width: 125px;
  height: 175px;
}
.figurePortrait img {
  position: absolute;
  left: 26%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
}
.promptse {
  font-size: 14px !important;
  color: #409eff !important;
  line-height: 22px;
  text-indent: 2.5rem;
  font-weight: 600;
}
.reasonLeaveBox .formSelect {
  width: 100%;
  position: relative;
}
.reasonLeaveBox .reasonLeaveCon .el-textarea__inner {
  height: 60px !important;
}
.reasonLeaveBox .numberV {
  position: absolute;
  left: 80%;
  bottom: 55px;
}
.promptCon {
  border-radius: 5px;
  padding: 8px;
  margin-bottom: 15px;
}
.fuzzinfoCon .borderShow {
  border: 1px solid #dcdfe6;
  display: block;
  width: calc(100% - 50px);
}
.fuzzinfoCon .borderHide {
  border: 0;
  display: none;
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
.aloneF:last-child .xian {
  display: none;
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
.green {
  color: #409eff;
}
.red .data {
  color: #f56c6c;
}
.gray .data {
  color: gray;
}

.reasonLeaveBox .reasonLeaveCon {
  width: 100%;
}
.reasonLeaveBox .reasonLeaveCon input {
  width: 95% !important;
}

.LeaveManageUpload {
  float: left;
}
.LeaveManageUpload .downLoad span {
  color: #409eff;
}
.LeaveManageUpload .downLoad span:hover {
  cursor: pointer;
}
.LeaveManageBtn {
  height: 52px;
  width: 100%;
  float: left;
}
</style>

